package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.mapper.TaskMapper;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }
    public TaskResponse createTask(TaskRequest request) {
        // 1) request -> entity (use taskMapper)
        Task task = taskMapper.toEntity(request);
        // 2) save it (use taskRepository)
        Task savedTask = taskRepository.save(task);
        // 3) saved entity -> response, and return it
        return taskMapper.toResponse(savedTask);
    }

    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();      // all rows from DB

        List<TaskResponse> responses = new ArrayList<>(); // empty list to fill
        for (Task task : tasks) {                         // for each task...
            responses.add(taskMapper.toResponse(task));   // ...convert and add
        }
        return responses;
    }
    public TaskResponse getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException(id);
        }
        return taskMapper.toResponse(task.get());
    }

    public void deleteTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.delete(task.get());
    }
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException(id);
        }
        Task existingTask = task.get();
        taskMapper.updateEntity(request, existingTask);
        return taskMapper.toResponse(taskRepository.save(existingTask));
    }
}