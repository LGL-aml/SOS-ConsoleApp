package com.jungle.strategy.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RescueRequest {
    private static int idCounter = 0;

    private final int id;
    private final String victimName;
    private final String phoneNumber;
    private final String description;
    private final double latitude;
    private final double longitude;
    private final UrgencyLevel urgencyLevel;
    private final LocalDateTime createdAt;
    private RequestStatus status;
    private Rescuer assignedRescuer;

    public RescueRequest(String victimName, String phoneNumber, String description,
                         double latitude, double longitude, UrgencyLevel urgencyLevel) {
        this.id = ++idCounter;
        this.victimName = victimName;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.urgencyLevel = urgencyLevel;
        this.createdAt = LocalDateTime.now();
        this.status = RequestStatus.PENDING;
    }

    // Getters
    public int getId() { return id; }
    public String getVictimName() { return victimName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getDescription() { return description; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public UrgencyLevel getUrgencyLevel() { return urgencyLevel; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public RequestStatus getStatus() { return status; }
    public Rescuer getAssignedRescuer() { return assignedRescuer; }

    public void setStatus(RequestStatus status) { this.status = status; }
    public void setAssignedRescuer(Rescuer rescuer) { this.assignedRescuer = rescuer; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String rescuerInfo = assignedRescuer != null ? assignedRescuer.getName() : "Chưa phân công";
        return String.format(
            "  [#%d] Nạn nhân: %-12s | SĐT: %-12s | Vị trí: (%.4f, %.4f)\n" +
            "        Mô tả: %s\n" +
            "        Mức độ: %-10s | Trạng thái: %-15s | Người cứu hộ: %s\n" +
            "        Thời gian: %s",
            id, victimName, phoneNumber, latitude, longitude,
            description,
            urgencyLevel.getDisplayName(), status.getDisplayName(), rescuerInfo,
            createdAt.format(fmt)
        );
    }
}

