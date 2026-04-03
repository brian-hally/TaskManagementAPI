package com.taskManagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "Status is required")
    private String status;

    @NotBlank(message = "Assignee is required")
    private String assignee;

    @NotBlank(message = "CreatedBy is required")
    private String createdBy;

    private LocalDateTime dueDate;

    private Integer estimatedDurationMinutes;
}
