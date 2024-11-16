package com.leonardo.task.service;

import com.leonardo.task.dto.TaskDto;
import com.leonardo.task.enumerator.ErrorMessage;
import com.leonardo.task.enumerator.MoveType;
import com.leonardo.task.exception.NegativeAmountException;
import com.leonardo.task.exception.TaskNotFoundException;
import com.leonardo.task.exception.TaskWithTitleAlreadyExistsException;
import com.leonardo.task.mapper.TaskMapper;
import com.leonardo.task.model.Task;
import com.leonardo.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskDto create(TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        checkIfTaskExistsByTitle(task);
        checkIfAmountIsNegative(task.getAmount());
        task.setDisplayOrder(getNextOrder());
        Task createdTask = taskRepository.save(task);
        return taskMapper.toDto(createdTask);
    }

    @Transactional
    public void delete(UUID uuid) {
        Task task = getTaskByUUID(uuid);
        taskRepository.delete(task);
    }

    public Page<TaskDto> getAll(Pageable pageable) {
        Page<Task> taskDtoPage = taskRepository.findAllByOrderByDisplayOrderAsc(pageable);
        return taskDtoPage.map(taskMapper::toDto);
    }

    @Transactional
    public TaskDto update(TaskDto taskDto, UUID uuid) {
        Task task = getTaskByUUID(uuid);
        taskMapper.update(taskDto, task);
        checkIfTaskExistsByTitle(task);
        checkIfAmountIsNegative(task.getAmount());
        Task taskUpdated = taskRepository.save(task);
        return taskMapper.toDto(taskUpdated);
    }

    @Transactional
    public void updateDisplayOrder(UUID taskId, MoveType moveType) {
        Task taskToUpdate = getTaskByUUID(taskId);
        int currentOrder = taskToUpdate.getDisplayOrder();

        List<Task> tasks = taskRepository.findAll(Sort.by("displayOrder"));

        int newOrder = (moveType.equals(MoveType.UP)) ? currentOrder - 1 : currentOrder + 1;

        for (Task task : tasks) {
            if (task.getId().equals(taskId)) {
                task.setDisplayOrder(newOrder);
            } else if (currentOrder < newOrder && task.getDisplayOrder() > currentOrder && task.getDisplayOrder() <= newOrder) {
                task.setDisplayOrder(task.getDisplayOrder() - 1);
            } else if (currentOrder > newOrder && task.getDisplayOrder() < currentOrder && task.getDisplayOrder() >= newOrder) {
                task.setDisplayOrder(task.getDisplayOrder() + 1);
            }
        }

        taskRepository.saveAll(tasks);
    }

    private Integer getNextOrder() {
        Optional<Task> maxTask = taskRepository.findTopByOrderByDisplayOrderDesc();
        return maxTask.map(task -> task.getDisplayOrder() + 1).orElse(1);
    }

    private Task getTaskByUUID(UUID uuid) {
        return taskRepository.findById(uuid).orElseThrow(
                () -> new TaskNotFoundException(ErrorMessage.TASK_NOT_FOUND)
        );
    }

    private void checkIfAmountIsNegative(BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new NegativeAmountException(ErrorMessage.NEGATIVE_AMOUNT);
        }
    }
    private void checkIfTaskExistsByTitle(Task task) {
        boolean taskExistsByTitle = (task.getId() == null)
                ? taskRepository.existsByTitle(task.getTitle())
                : taskRepository.existsByTitleAndIdNot(task.getTitle(), task.getId());

        if (taskExistsByTitle) {
            throw new TaskWithTitleAlreadyExistsException(ErrorMessage.TASK_WITH_TITLE_ALREADY_EXISTS, task.getTitle());
        }
    }
}
