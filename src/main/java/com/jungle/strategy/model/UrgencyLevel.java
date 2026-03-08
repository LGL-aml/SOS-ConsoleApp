package com.jungle.strategy.model;

public enum UrgencyLevel {
    LOW("Thấp", 1),
    MEDIUM("Trung bình", 2),
    HIGH("Cao", 3),
    CRITICAL("Nguy kịch", 4);

    private final String displayName;
    private final int priority;

    UrgencyLevel(String displayName, int priority) {
        this.displayName = displayName;
        this.priority = priority;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

