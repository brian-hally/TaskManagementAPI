package com.taskManagement.entity;

import com.taskManagement.dto.TaskRequest;
import com.taskManagement.enums.TaskStatus;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

    @Entity
    @Table(name = "TDAI_TASKS")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Task {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String title;

        @Column(length = 1000)
        private String description;

        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        private TaskStatus status;

        @Column(nullable = false)
        private String assignee;

        @Column(nullable = false, name = "CREATED_BY")
        private String createdBy;

        @Column(nullable = false, updatable = false, name = "CREATED_AT")
        private LocalDateTime createdAt = LocalDateTime.now();

        @Column(name = "DUE_DATE")
        private LocalDateTime dueDate;

        @Column(name = "ESTIMATED_DURATION_MINUTES")
        private Integer estimatedDurationMinutes;


        public Task(TaskRequest request) {
            this.title = request.getTitle();
            this.description = request.getDescription();
            this.status = request.getStatus();
            this.assignee = request.getAssignee();
            this.createdBy = request.getCreatedBy();
            this.dueDate = request.getDueDate();
            this.estimatedDurationMinutes = request.getEstimatedDurationMinutes();
        }

        public void updateTask(TaskRequest request) {
            this.title = request.getTitle();
            this.description = request.getDescription();
            this.status = request.getStatus();
            this.assignee = request.getAssignee();
            this.createdBy = request.getCreatedBy();
            this.dueDate = request.getDueDate();
            this.estimatedDurationMinutes = request.getEstimatedDurationMinutes();
        }

    }


