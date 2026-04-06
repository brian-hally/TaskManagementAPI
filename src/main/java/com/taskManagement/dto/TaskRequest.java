package com.taskManagement.dto;

import com.taskManagement.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Status is required")
    private TaskStatus status;

    @NotBlank(message = "Assignee is required")
    private String assignee;

    @NotBlank(message = "CreatedBy is required")
    private String createdBy;

    private LocalDateTime dueDate;

    private Integer estimatedDurationMinutes;
}
