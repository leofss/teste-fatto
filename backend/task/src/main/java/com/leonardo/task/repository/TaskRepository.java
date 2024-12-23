package com.leonardo.task.repository;

import com.leonardo.task.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    Optional<Task> findTopByOrderByDisplayOrderDesc();
    Page<Task> findAllByOrderByDisplayOrderAsc(Pageable pageable);

    boolean existsByTitleAndIdNot(String title, UUID id);
    boolean existsByTitle(String title);
}
