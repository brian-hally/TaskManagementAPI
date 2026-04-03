package com.taskManagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionPlanRequest {

    @NotNull(message = "Available time in minutes is required")
    @Min(value = 1, message = "Available time must be at least 1 minute")
    private Integer availableTimeMinutes;
}
