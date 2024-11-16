package com.leonardo.task.controller;

import com.leonardo.task.dto.MoveDto;
import com.leonardo.task.dto.TaskDto;
import com.leonardo.task.enumerator.MoveType;
import com.leonardo.task.exception.ErrorMessageBuilder;
import com.leonardo.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/task")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Create Task", description = "Route to create a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))}),

            @ApiResponse(responseCode = "422", description = "Unprocessable entity",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageBuilder.class))}),
    })
    public ResponseEntity<TaskDto> create(@RequestBody @Valid TaskDto taskDto) {
        TaskDto task = taskService.create(taskDto);
        return ResponseEntity.status(201).body(task);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Task", description = "Route to delete a task by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task successfully deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))}),

            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageBuilder.class))}),
    })
    public void delete(@PathVariable UUID uuid) {
        taskService.delete(uuid);
    }

    @GetMapping
    @PageableAsQueryParam
    @Operation(summary = "List all tasks", description = "Route to list all tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks successfully listed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))}),
    })
    public ResponseEntity<Page<TaskDto>> getAll(@Parameter(hidden = true) Pageable pageable) {
        Page<TaskDto> taskDtoPage = taskService.getAll(pageable);
        return ResponseEntity.ok(taskDtoPage);
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Edit single tasks", description = "Route to edit a task by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task successfully edited",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))}),

            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageBuilder.class))}),

    })
    public ResponseEntity<TaskDto> update(@PathVariable UUID uuid, @RequestBody @Valid TaskDto taskDto) {
        TaskDto task = taskService.update(taskDto, uuid);
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Edit task order", description = "Route to edit a task order by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task successfully moved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))}),

            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageBuilder.class))}),
    })
    public void changeOrder(@PathVariable UUID uuid, @RequestBody @Valid MoveDto moveDto) {
        taskService.updateDisplayOrder(uuid, moveDto.getMoveType());
    }

}
