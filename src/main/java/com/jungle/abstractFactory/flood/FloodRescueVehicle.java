package com.jungle.abstractFactory.flood;

import com.jungle.abstractFactory.product.RescueVehicle;
import com.jungle.strategy.model.UrgencyLevel;

/**
 * Concrete Product B1 - Phương tiện cứu hộ lũ lụt
 */
public class FloodRescueVehicle implements RescueVehicle {

    private final String vehicleName;
    private final String vehicleType;
    private final int capacity;

    public FloodRescueVehicle(UrgencyLevel urgency) {
        switch (urgency) {
            case CRITICAL -> {
                this.vehicleName = "Trực thăng Cứu hộ Mi-17";
                this.vehicleType = "Trực thăng";
                this.capacity    = 15;
            }
            case HIGH -> {
                this.vehicleName = "Xuồng Máy Tốc Độ Cao";
                this.vehicleType = "Xuồng máy";
                this.capacity    = 8;
            }
            case MEDIUM -> {
                this.vehicleName = "Xuồng Cứu Hộ Tiêu Chuẩn";
                this.vehicleType = "Xuồng";
                this.capacity    = 5;
            }
            default -> {
                this.vehicleName = "Thuyền Bơm Hơi";
                this.vehicleType = "Thuyền nhỏ";
                this.capacity    = 3;
            }
        }
    }

    @Override public String getVehicleName() { return vehicleName; }
    @Override public String getVehicleType() { return vehicleType; }
    @Override public int    getCapacity()    { return capacity; }

    @Override
    public String describe() {
        return String.format("  🚤 [PHƯƠNG TIỆN LŨ] %s (%s) | Sức chứa: %d người",
                vehicleName, vehicleType, capacity);
    }
}

