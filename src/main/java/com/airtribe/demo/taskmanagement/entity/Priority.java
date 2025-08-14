package com.airtribe.demo.taskmanagement.entity;

public enum Priority {
    LOW(1, "Low Priority"),
    MEDIUM(2, "Medium Priority"), 
    HIGH(3, "High Priority"),
    CRITICAL(4, "Critical Priority");

    private final int level;
    private final String displayName;

    Priority(int level, String displayName) {
        this.level = level;
        this.displayName = displayName;
    }

    public int getLevel() {
        return level;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Priority fromString(String priority) {
        if (priority == null || priority.trim().isEmpty()) {
            return null;
        }
        try {
            return Priority.valueOf(priority.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid priority: " + priority + 
                ". Valid values are: LOW, MEDIUM, HIGH, CRITICAL");
        }
    }
}