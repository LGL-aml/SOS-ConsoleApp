package com.jungle.strategy.model;

/**
 * Enum mức độ khẩn cấp của yêu cầu cứu hộ.
 * Chỉ giữ 2 mức để demo Abstract Factory Pattern rõ ràng:
 * - CRITICAL → factory tạo bộ cứu hộ cấp cao
 * - LOW → factory tạo bộ cứu hộ cơ bản
 */
public enum UrgencyLevel {
    LOW("Thấp"),
    CRITICAL("Nguy kịch");

    private final String displayName;

    UrgencyLevel(String displayName) {
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
