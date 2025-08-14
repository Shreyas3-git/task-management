package com.airtribe.demo.taskmanagement.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateRequest {

    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @Size(min = 5, max = 1000, message = "Description must be between 5 and 1000 characters")
    private String description;

    @Pattern(regexp = "PENDING|IN_PROGRESS|COMPLETED", 
             message = "Status must be one of: PENDING, IN_PROGRESS, COMPLETED")
    private String status;

    @Pattern(regexp = "LOW|MEDIUM|HIGH|CRITICAL", 
             message = "Priority must be one of: LOW, MEDIUM, HIGH, CRITICAL")
    private String priority;

    @FutureOrPresent(message = "Due date cannot be in the past")
    private LocalDate dueDate;
}