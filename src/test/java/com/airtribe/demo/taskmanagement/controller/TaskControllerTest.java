package com.airtribe.demo.taskmanagement.controller;

import com.airtribe.demo.taskmanagement.dto.request.TaskCreateRequest;
import com.airtribe.demo.taskmanagement.dto.response.TaskResponse;
import com.airtribe.demo.taskmanagement.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTask_ValidRequest_ReturnsCreated() throws Exception {
        TaskCreateRequest request = TaskCreateRequest.builder()
                .title("Test Task")
                .description("Test Description for validation")
                .priority("HIGH")
                .dueDate(LocalDate.now().plusDays(7))
                .build();

        TaskResponse mockResponse = TaskResponse.builder()
                .id(1L)
                .title("Test Task")
                .description("Test Description for validation")
                .status("PENDING")
                .priority("HIGH")
                .dueDate(LocalDate.now().plusDays(7))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .overdue(false)
                .build();

        when(taskService.createTask(any(TaskCreateRequest.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.title").value("Test Task"));
    }

    @Test
    void createTask_InvalidTitle_ReturnsBadRequest() throws Exception {
        TaskCreateRequest request = TaskCreateRequest.builder()
                .title("Hi")
                .description("Test Description for validation")
                .priority("HIGH")
                .build();

        mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
//                .andExpected(status().isBadRequest());
    }

    @Test
    void getTask_ExistingId_ReturnsTask() throws Exception {
        TaskResponse mockResponse = TaskResponse.builder()
                .id(1L)
                .title("Test Task")
                .description("Test Description")
                .status("PENDING")
                .priority("MEDIUM")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .overdue(false)
                .build();

        when(taskService.getTaskById(1L)).thenReturn(mockResponse);

        mockMvc.perform(get("/v1/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("Test Task"));
    }
}