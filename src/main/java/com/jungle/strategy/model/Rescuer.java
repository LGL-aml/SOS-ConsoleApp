package com.jungle.strategy.model;

public class Rescuer {
    private static int idCounter = 0;

    private final int id;
    private final String name;
    private final String phoneNumber;
    private final String specialty; // e.g. "Lặn", "Y tế", "Cứu hộ chung"
    private double latitude;
    private double longitude;
    private RescuerStatus status;
    private int completedMissions;

    public Rescuer(String name, String phoneNumber, String specialty,
                   double latitude, double longitude) {
        this.id = ++idCounter;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.specialty = specialty;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = RescuerStatus.AVAILABLE;
        this.completedMissions = 0;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getSpecialty() { return specialty; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public RescuerStatus getStatus() { return status; }
    public int getCompletedMissions() { return completedMissions; }

    // Setters
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public void setStatus(RescuerStatus status) { this.status = status; }
    public void incrementCompletedMissions() { this.completedMissions++; }

    public boolean isAvailable() {
        return this.status == RescuerStatus.AVAILABLE;
    }

    /**
     * Tính khoảng cách Haversine (km) giữa rescuer và một tọa độ
     */
    public double distanceTo(double lat, double lng) {
        final double R = 6371.0; // Bán kính Trái Đất (km)
        double dLat = Math.toRadians(lat - this.latitude);
        double dLng = Math.toRadians(lng - this.longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                 + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(lat))
                 * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    @Override
    public String toString() {
        return String.format(
            "  [#%d] %-15s | SĐT: %-12s | Chuyên môn: %-15s | Vị trí: (%.4f, %.4f) | Trạng thái: %-15s | Nhiệm vụ hoàn thành: %d",
            id, name, phoneNumber, specialty, latitude, longitude, status.getDisplayName(), completedMissions
        );
    }
}

