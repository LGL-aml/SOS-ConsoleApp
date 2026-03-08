package com.jungle.strategy.model;

public enum RescuerStatus {
    AVAILABLE("Sẵn sàng"),
    BUSY("Đang làm nhiệm vụ"),
    OFF_DUTY("Nghỉ phép");

    private final String displayName;

    RescuerStatus(String displayName) {
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

