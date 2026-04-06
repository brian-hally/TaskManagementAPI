package com.taskManagement.service;

import com.taskManagement.dto.TaskRequest;
import com.taskManagement.dto.TaskResponse;
import com.taskManagement.entity.Task;
import com.taskManagement.exception.TaskNotFoundException;
import com.taskManagement.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.taskManagement.dto.ExecutionPlanResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    public TaskResponse createNewTask(TaskRequest newTask) {
        Task task = new Task(newTask);
        Task savedTask = taskRepository.save(task);
        return TaskResponse.fromEntity(savedTask);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
        return TaskResponse.fromEntity(task);
    }

    @Transactional
    public TaskResponse updateTask(Long id, TaskRequest updatedTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        task.updateTask(updatedTask);

        Task newTask = taskRepository.save(task);
        return TaskResponse.fromEntity(newTask);
    }

    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    /**
     * Greedy algorithm to select tasks that fit within available time parameter.
     * 1. Filter tasks with due dates and estimated durations.
     * 2. Sort by due date (earliest first).
     * 3. Select tasks that fit within available time using a greedy approach which just takes the first task that fits.
     * 4. Prioritize tasks with earlier due dates.
     */
    @Transactional(readOnly = true)
    public ExecutionPlanResponse generateExecutionPlan(Integer availableTimeMinutes) {
        List<Task> allTasks = taskRepository.findAll();

        List<Task> eligibleTasks = allTasks.stream()
                .filter(task -> task.getDueDate() != null && task.getEstimatedDurationMinutes() != null)
                .sorted(Comparator.comparing(Task::getDueDate))
                .toList();

        List<Task> selectedTasks = new ArrayList<>();
        int totalDuration = 0;

        for (Task task : eligibleTasks) {
            if (totalDuration + task.getEstimatedDurationMinutes() <= availableTimeMinutes) {
                selectedTasks.add(task);
                totalDuration += task.getEstimatedDurationMinutes();
            }
        }

        List<TaskResponse> taskResponses = selectedTasks.stream()
                .map(TaskResponse::fromEntity)
                .collect(Collectors.toList());

        int remainingTime = availableTimeMinutes - totalDuration;

        return new ExecutionPlanResponse(taskResponses, totalDuration, remainingTime);
    }
}
