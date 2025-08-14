package com.airtribe.demo.taskmanagement.repository;

import com.airtribe.demo.taskmanagement.entity.Task;
import com.airtribe.demo.taskmanagement.entity.Priority;
import com.airtribe.demo.taskmanagement.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriorityOrderByCreatedAtDesc(Priority priority);

    @Query("SELECT t FROM Task t WHERE t.dueDate < :currentDate AND t.status != 'COMPLETED'")
    List<Task> findOverdueTasks(@Param("currentDate") LocalDate currentDate);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.status = :status")
    long countByStatus(@Param("status") TaskStatus status);

    List<Task> findByTitleContainingIgnoreCase(String title);
}