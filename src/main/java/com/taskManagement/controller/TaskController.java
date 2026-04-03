package com.taskManagement.controller;

import com.taskManagement.dto.ExecutionPlanRequest;
import com.taskManagement.dto.ExecutionPlanResponse;
import com.taskManagement.dto.TaskRequest;
import com.taskManagement.dto.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.taskManagement.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest newTask) {
        TaskResponse response = taskService.createNewTask(newTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        TaskResponse task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<TaskResponse> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest updatedTask) {
        TaskResponse response = taskService.updateTask(id, updatedTask);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/execution-plan")
    public ResponseEntity<ExecutionPlanResponse> generateExecutionPlan(
            @Valid @RequestBody ExecutionPlanRequest request) {
        ExecutionPlanResponse plan = taskService.generateExecutionPlan(request.getAvailableTimeMinutes());
        return ResponseEntity.ok(plan);
    }

}
