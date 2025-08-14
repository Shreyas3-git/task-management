package com.airtribe.demo.taskmanagement.service;

import com.airtribe.demo.taskmanagement.dto.request.TaskCreateRequest;
import com.airtribe.demo.taskmanagement.dto.request.TaskUpdateRequest;
import com.airtribe.demo.taskmanagement.dto.response.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskCreateRequest request);

    TaskResponse getTaskById(Long id);

    Page<TaskResponse> getAllTasks(Pageable pageable);

    List<TaskResponse> getTasksByStatus(String status);

    List<TaskResponse> getTasksByPriority(String priority);

    TaskResponse updateTask(Long id, TaskUpdateRequest request);

    void deleteTask(Long id);

    List<TaskResponse> searchTasksByTitle(String title);
}