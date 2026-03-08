package com.jungle.strategy.model;

public enum RequestStatus {
    PENDING("Đang chờ"),
    ASSIGNED("Đã phân công"),
    IN_PROGRESS("Đang cứu hộ"),
    COMPLETED("Hoàn thành"),
    CANCELLED("Đã hủy");

    private final String displayName;

    RequestStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

