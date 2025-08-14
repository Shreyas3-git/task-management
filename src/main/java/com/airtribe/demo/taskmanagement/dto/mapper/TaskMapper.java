package com.airtribe.demo.taskmanagement.dto.mapper;

import com.airtribe.demo.taskmanagement.dto.request.TaskCreateRequest;
import com.airtribe.demo.taskmanagement.dto.request.TaskUpdateRequest;
import com.airtribe.demo.taskmanagement.dto.response.TaskResponse;
import com.airtribe.demo.taskmanagement.entity.Task;
import com.airtribe.demo.taskmanagement.entity.Priority;
import com.airtribe.demo.taskmanagement.entity.TaskStatus;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(source = "priority", target = "priority", qualifiedByName = "stringToPriority")
    Task toEntity(TaskCreateRequest request);

    @Mapping(source = "status", target = "status", qualifiedByName = "taskStatusToString")
    @Mapping(source = "priority", target = "priority", qualifiedByName = "priorityToString")
    @Mapping(source = "status", target = "statusDisplayName", qualifiedByName = "taskStatusToDisplayName")
    @Mapping(source = "priority", target = "priorityDisplayName", qualifiedByName = "priorityToDisplayName")
    @Mapping(source = "priority", target = "priorityLevel", qualifiedByName = "priorityToLevel")
    @Mapping(target = "overdue", expression = "java(task.isOverdue())")
    TaskResponse toResponse(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "status", target = "status", qualifiedByName = "stringToTaskStatus")
    @Mapping(source = "priority", target = "priority", qualifiedByName = "stringToPriority")
    void updateEntity(TaskUpdateRequest request, @MappingTarget Task task);

    @Named("stringToTaskStatus")
    default TaskStatus stringToTaskStatus(String status) {
        return TaskStatus.fromString(status);
    }

    @Named("taskStatusToString")
    default String taskStatusToString(TaskStatus status) {
        return status != null ? status.name() : null;
    }

    @Named("taskStatusToDisplayName")
    default String taskStatusToDisplayName(TaskStatus status) {
        return status != null ? status.getDisplayName() : null;
    }

    @Named("stringToPriority")
    default Priority stringToPriority(String priority) {
        return Priority.fromString(priority);
    }

    @Named("priorityToString")
    default String priorityToString(Priority priority) {
        return priority != null ? priority.name() : null;
    }

    @Named("priorityToDisplayName")
    default String priorityToDisplayName(Priority priority) {
        return priority != null ? priority.getDisplayName() : null;
    }

    @Named("priorityToLevel")
    default Integer priorityToLevel(Priority priority) {
        return priority != null ? priority.getLevel() : null;
    }
}