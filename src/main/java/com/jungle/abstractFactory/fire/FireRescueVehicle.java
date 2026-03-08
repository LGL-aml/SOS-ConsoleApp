package com.jungle.abstractFactory.fire;

import com.jungle.abstractFactory.product.RescueVehicle;
import com.jungle.strategy.model.UrgencyLevel;

/**
 * Concrete Product B2 - Phương tiện cứu hộ hỏa hoạn
 */
public class FireRescueVehicle implements RescueVehicle {

    private final String vehicleName;
    private final String vehicleType;
    private final int capacity;

    public FireRescueVehicle(UrgencyLevel urgency) {
        switch (urgency) {
            case CRITICAL -> {
                this.vehicleName = "Xe Thang Chữa Cháy 52m";
                this.vehicleType = "Xe thang cao tầng";
                this.capacity    = 12;
            }
            case HIGH -> {
                this.vehicleName = "Xe Cứu Hỏa Bồn Nước 10.000L";
                this.vehicleType = "Xe cứu hỏa lớn";
                this.capacity    = 8;
            }
            case MEDIUM -> {
                this.vehicleName = "Xe Cứu Hỏa Tiêu Chuẩn";
                this.vehicleType = "Xe cứu hỏa";
                this.capacity    = 5;
            }
            default -> {
                this.vehicleName = "Xe Cứu Thương";
                this.vehicleType = "Xe cứu thương";
                this.capacity    = 3;
            }
        }
    }

    @Override public String getVehicleName() { return vehicleName; }
    @Override public String getVehicleType() { return vehicleType; }
    @Override public int    getCapacity()    { return capacity; }

    @Override
    public String describe() {
        return String.format("  🚒 [PHƯƠNG TIỆN CHỮA CHÁY] %s (%s) | Sức chứa: %d người",
                vehicleName, vehicleType, capacity);
    }
}

