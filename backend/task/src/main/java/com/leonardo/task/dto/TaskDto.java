package com.leonardo.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    @Schema(name = "id", example = "3755d13d-e41e-49c7-b36f-c79f38513b4e", description = "Task UUID",
            accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @NotBlank(message = "Title cannot be null")
    @Schema(name = "title", example = "Study React", description = "Task title")
    private String title;

    @NotNull(message = "amount cannot be null")
    @Schema(name = "amount", example = "120", description = "Amount that a task costs")
    private BigDecimal amount;

    @NotNull(message = "Due Date cannot be null")
    @Schema(name = "dueDate", example = "2024-11-28", description = "Date that a task is due to")
    private LocalDate dueDate;
}
