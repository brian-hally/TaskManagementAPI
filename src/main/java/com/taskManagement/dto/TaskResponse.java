package com.taskManagement.dto;

import com.taskManagement.entity.Task;
import com.taskManagement.enums.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private String assignee;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;
    private Integer estimatedDurationMinutes;

    public static TaskResponse fromEntity(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getAssignee(),
                task.getCreatedBy(),
                task.getCreatedAt(),
                task.getDueDate(),
                task.getEstimatedDurationMinutes()
        );
    }
}
