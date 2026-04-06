package com.taskManagement.service;

import com.taskManagement.dto.TaskRequest;
import com.taskManagement.dto.TaskResponse;
import com.taskManagement.entity.Task;
import com.taskManagement.enums.TaskStatus;
import com.taskManagement.exception.TaskNotFoundException;
import com.taskManagement.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createNewTask_ShouldReturnTaskResponse() {
        TaskRequest request = new TaskRequest(
                "Take out the Bins",
                "Take out bins onto street for collection",
                TaskStatus.TODO,
                "John Ryan",
                "Mary Ryan",
                LocalDateTime.now().plusDays(1),
                10
        );

        Task savedTask = new Task(request);
        savedTask.setId(1L);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskResponse response = taskService.createNewTask(request);

        assertNotNull(response);
        assertEquals("Take out the Bins", response.getTitle());
        assertEquals("Take out bins onto street for collection", response.getDescription());
        assertEquals(TaskStatus.TODO, response.getStatus());
        assertEquals("John Ryan", response.getAssignee());
        assertEquals("Mary Ryan", response.getCreatedBy());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void getTaskById_WhenTaskNotFound_ShouldThrowException() {
        Long taskId = 99999L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(
                TaskNotFoundException.class,
                () -> taskService.getTaskById(taskId)
        );

        assertEquals("Task not found with id: 99999", exception.getMessage());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void getAllTasks_ShouldReturnListOfTaskResponses() {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Take out the Bins");
        task1.setDescription("Take out bins onto street for collection");
        task1.setStatus(TaskStatus.TODO);
        task1.setAssignee("John Ryan");
        task1.setCreatedBy("Mary Ryan");
        task1.setCreatedAt(LocalDateTime.now());

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Go To the Gym");
        task2.setDescription("Go to the gym for 45 minutes");
        task2.setStatus(TaskStatus.IN_PROGRESS);
        task2.setAssignee("Sarah Ryan");
        task2.setCreatedBy("Sarah Ryan");
        task2.setCreatedAt(LocalDateTime.now());

        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskRepository.findAll()).thenReturn(tasks);

        List<TaskResponse> responses = taskService.getAllTasks();

        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("Take out the Bins", responses.get(0).getTitle());
        assertEquals("Go To the Gym", responses.get(1).getTitle());
        assertEquals(TaskStatus.TODO, responses.get(0).getStatus());
        assertEquals(TaskStatus.IN_PROGRESS, responses.get(1).getStatus());

        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getAllTasks_WhenNoTasks_ShouldReturnEmptyList() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        List<TaskResponse> responses = taskService.getAllTasks();

        assertNotNull(responses);
        assertEquals(0, responses.size());
        assertTrue(responses.isEmpty());

        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void UpdateTask_ShouldReturnTaskResponse() {

        TaskRequest oldTask = new TaskRequest(
                "Take out the Bins",
                "Take out bins onto street for collection",
                TaskStatus.TODO,
                "David Ryan",
                "Mary Ryan",
                LocalDateTime.now().plusDays(1),
                10
        );

        TaskRequest newTask = new TaskRequest(
                "Take out the Bins",
                "Take out bins onto street for collection",
                TaskStatus.TODO,
                "John Ryan",
                "Mary Ryan",
                LocalDateTime.now().plusDays(1),
                10
        );

        Task savedTask = new Task(oldTask);
        savedTask.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(savedTask));

        when(taskRepository.save(any(Task.class))).thenReturn(new Task(newTask));

        TaskResponse response = taskService.updateTask(1L, newTask);

        assertNotNull(response);
        assertEquals("Take out the Bins", response.getTitle());
        assertEquals("Take out bins onto street for collection", response.getDescription());
        assertEquals(TaskStatus.TODO, response.getStatus());
        assertEquals("John Ryan", response.getAssignee());
        assertEquals("Mary Ryan", response.getCreatedBy());

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }
}
