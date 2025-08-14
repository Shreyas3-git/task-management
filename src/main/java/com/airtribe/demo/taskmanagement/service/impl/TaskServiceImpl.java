package com.airtribe.demo.taskmanagement.service.impl;

import com.airtribe.demo.taskmanagement.dto.request.TaskCreateRequest;
import com.airtribe.demo.taskmanagement.dto.request.TaskUpdateRequest;
import com.airtribe.demo.taskmanagement.dto.response.TaskResponse;
import com.airtribe.demo.taskmanagement.dto.mapper.TaskMapper;
import com.airtribe.demo.taskmanagement.entity.Task;
import com.airtribe.demo.taskmanagement.entity.Priority;
import com.airtribe.demo.taskmanagement.entity.TaskStatus;
import com.airtribe.demo.taskmanagement.exception.TaskNotFoundException;
import com.airtribe.demo.taskmanagement.exception.BusinessException;
import com.airtribe.demo.taskmanagement.repository.TaskRepository;
import com.airtribe.demo.taskmanagement.service.TaskService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponse createTask(TaskCreateRequest request) {
        log.info("Creating new task with title: {}", request.getTitle());

        Task task = taskMapper.toEntity(request);
        Task savedTask = taskRepository.save(task);
        log.info("Task created successfully with ID: {}", savedTask.getId());

        return taskMapper.toResponse(savedTask);
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        log.debug("Fetching task with ID: {}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));

        return taskMapper.toResponse(task);
    }

    @Override
    public Page<TaskResponse> getAllTasks(Pageable pageable) {
        log.debug("Fetching all tasks with pagination: {}", pageable);

        Page<Task> taskPage = taskRepository.findAll(pageable);
        return taskPage.map(taskMapper::toResponse);
    }

    @Override
    public List<TaskResponse> getTasksByStatus(String status) {
        log.debug("Fetching tasks with status: {}", status);

        try {
            TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
            List<Task> tasks = taskRepository.findByStatus(taskStatus);
            return tasks.stream()
                    .map(taskMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Invalid task status: " + status);
        }
    }

    @Override
    public List<TaskResponse> getTasksByPriority(String priority) {
        log.debug("Fetching tasks with priority: {}", priority);

        try {
            Priority taskPriority = Priority.valueOf(priority.toUpperCase());
            List<Task> tasks = taskRepository.findByPriorityOrderByCreatedAtDesc(taskPriority);
            return tasks.stream()
                    .map(taskMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Invalid priority: " + priority);
        }
    }

    @Override
    @Transactional
    public TaskResponse updateTask(Long id, TaskUpdateRequest request) {
        log.info("Updating task with ID: {}", id);

        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));

        if (request.getStatus() != null) {
            TaskStatus newStatus = TaskStatus.valueOf(request.getStatus().toUpperCase());
            if (!existingTask.canChangeStatus(newStatus)) {
                throw new BusinessException(
                    String.format("Cannot change task status from %s to %s", 
                                existingTask.getStatus(), newStatus));
            }
        }

        taskMapper.updateEntity(request, existingTask);
        Task updatedTask = taskRepository.save(existingTask);

        log.info("Task updated successfully with ID: {}", updatedTask.getId());
        return taskMapper.toResponse(updatedTask);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        log.info("Deleting task with ID: {}", id);

        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found with ID: " + id);
        }

        taskRepository.deleteById(id);
        log.info("Task deleted successfully with ID: {}", id);
    }

    @Override
    public List<TaskResponse> searchTasksByTitle(String title) {
        log.debug("Searching tasks with title containing: {}", title);

        List<Task> tasks = taskRepository.findByTitleContainingIgnoreCase(title);
        return tasks.stream()
                .map(taskMapper::toResponse)
                .collect(Collectors.toList());
    }
}