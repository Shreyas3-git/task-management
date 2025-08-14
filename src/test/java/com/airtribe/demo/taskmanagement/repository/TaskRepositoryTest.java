package com.airtribe.demo.taskmanagement.repository;

import com.airtribe.demo.taskmanagement.entity.Task;
import com.airtribe.demo.taskmanagement.entity.Priority;
import com.airtribe.demo.taskmanagement.entity.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void findByStatus_ReturnsTasksWithMatchingStatus() {
        Task task1 = Task.builder()
                .title("Task 1")
                .description("Description 1")
                .status(TaskStatus.PENDING)
                .priority(Priority.HIGH)
                .build();

        Task task2 = Task.builder()
                .title("Task 2")
                .description("Description 2")
                .status(TaskStatus.IN_PROGRESS)
                .priority(Priority.MEDIUM)
                .build();

        entityManager.persistAndFlush(task1);
        entityManager.persistAndFlush(task2);

        List<Task> pendingTasks = taskRepository.findByStatus(TaskStatus.PENDING);

        assertThat(pendingTasks).hasSize(1);
        assertThat(pendingTasks.get(0).getTitle()).isEqualTo("Task 1");
    }

    @Test
    void findByPriorityOrderByCreatedAtDesc_ReturnsTasksSortedByCreationDate() {
        Task task1 = Task.builder()
                .title("Task 1")
                .description("Description 1")
                .status(TaskStatus.PENDING)
                .priority(Priority.HIGH)
                .build();

        Task task2 = Task.builder()
                .title("Task 2")
                .description("Description 2")
                .status(TaskStatus.PENDING)
                .priority(Priority.HIGH)
                .build();

        entityManager.persistAndFlush(task1);
        entityManager.persistAndFlush(task2);

        List<Task> highPriorityTasks = taskRepository.findByPriorityOrderByCreatedAtDesc(Priority.HIGH);

        assertThat(highPriorityTasks).hasSize(2);
        assertThat(highPriorityTasks.get(0).getTitle()).isEqualTo("Task 2");
    }
}