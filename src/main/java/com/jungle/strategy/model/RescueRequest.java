package com.jungle.strategy.model;

/**
 * Entity yêu cầu cứu hộ.
 */
public class RescueRequest {
    private static int idCounter = 0;

    private final int id;
    private final String victimName;
    private final String description;
    private final double latitude;
    private final double longitude;
    private final UrgencyLevel urgencyLevel;
    private RequestStatus status;
    private Rescuer assignedRescuer;

    public RescueRequest(String victimName, String phone, String description,
            double latitude, double longitude, UrgencyLevel urgencyLevel) {
        this.id = ++idCounter;
        this.victimName = victimName;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.urgencyLevel = urgencyLevel;
        this.status = RequestStatus.PENDING;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getVictimName() {
        return victimName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public UrgencyLevel getUrgencyLevel() {
        return urgencyLevel;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public Rescuer getAssignedRescuer() {
        return assignedRescuer;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public void setAssignedRescuer(Rescuer rescuer) {
        this.assignedRescuer = rescuer;
    }

    @Override
    public String toString() {
        String rescuerInfo = assignedRescuer != null ? assignedRescuer.getName() : "Chưa phân công";
        return String.format(
                "  [#%d] %s | Mức: %s | Trạng thái: %s | Người CH: %s | Vị trí: (%.4f, %.4f)",
                id, victimName, urgencyLevel.getDisplayName(),
                status.getDisplayName(), rescuerInfo, latitude, longitude);
    }
}
