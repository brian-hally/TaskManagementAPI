package com.taskManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionPlanResponse {

    private List<TaskResponse> tasks;
    private int totalDurationMinutes;
    private int remainingTimeMinutes;
}
