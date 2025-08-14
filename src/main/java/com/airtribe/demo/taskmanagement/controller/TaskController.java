package com.airtribe.demo.taskmanagement.controller;

import com.airtribe.demo.taskmanagement.dto.request.TaskCreateRequest;
import com.airtribe.demo.taskmanagement.dto.request.TaskUpdateRequest;
import com.airtribe.demo.taskmanagement.dto.response.ApiResponse;
import com.airtribe.demo.taskmanagement.dto.response.TaskResponse;
import com.airtribe.demo.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(@Valid @RequestBody TaskCreateRequest request) {
        log.info("Creating task with title: {}", request.getTitle());
        TaskResponse response = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Task created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> getTask(@PathVariable Long id) {
        TaskResponse response = taskService.getTaskById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TaskResponse>>> getAllTasks(@PageableDefault(size = 20) Pageable pageable) {
        Page<TaskResponse> response = taskService.getAllTasks(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasksByStatus(@PathVariable String status) {
        List<TaskResponse> response = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasksByPriority(@PathVariable String priority) {
        List<TaskResponse> response = taskService.getTasksByPriority(priority);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> searchTasks(@RequestParam String title) {
        List<TaskResponse> response = taskService.searchTasksByTitle(title);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(@PathVariable Long id, @Valid @RequestBody TaskUpdateRequest request) {
        TaskResponse response = taskService.updateTask(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Task updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Task deleted successfully"));
    }
}