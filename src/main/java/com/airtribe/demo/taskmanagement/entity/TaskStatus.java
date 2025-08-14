package com.airtribe.demo.taskmanagement.entity;

public enum TaskStatus {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String displayName;

    TaskStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean canTransitionTo(TaskStatus newStatus) {
        if (newStatus == null || this == newStatus) {
            return false;
        }

        return switch (this) {
            case PENDING -> newStatus == IN_PROGRESS;
            case IN_PROGRESS -> newStatus == COMPLETED;
            case COMPLETED -> false;
        };
    }

    public static TaskStatus fromString(String status) {
        if (status == null || status.trim().isEmpty()) {
            return null;
        }
        try {
            return TaskStatus.valueOf(status.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid task status: " + status + 
                ". Valid values are: PENDING, IN_PROGRESS, COMPLETED");
        }
    }
}