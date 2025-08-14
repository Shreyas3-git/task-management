package com.airtribe.demo.taskmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = {"title", "description"})
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @NotBlank(message = "Description is required") 
    @Size(min = 5, max = 1000, message = "Description must be between 5 and 1000 characters")
    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "TaskStatus can't be null")
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Priority can't be null") 
    @Column(name = "priority", nullable = false)
    private Priority priority;

    @FutureOrPresent(message = "Due date cannot be in the past")
    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public boolean isOverdue() {
        return dueDate != null && 
               LocalDate.now().isAfter(dueDate) && 
               status != TaskStatus.COMPLETED;
    }

    public boolean canChangeStatus(TaskStatus newStatus) {
        return status.canTransitionTo(newStatus);
    }
}