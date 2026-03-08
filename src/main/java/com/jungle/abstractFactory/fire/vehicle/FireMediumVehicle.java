package com.jungle.abstractFactory.fire.vehicle;

import com.jungle.abstractFactory.product.RescueVehicle;

/** Concrete Product B2c - Phương tiện cứu hộ hỏa hoạn mức MEDIUM */
public class FireMediumVehicle implements RescueVehicle {
    @Override public String getVehicleName() { return "Xe Cứu Hỏa Tiêu Chuẩn"; }
    @Override public String getVehicleType() { return "Xe cứu hỏa"; }
    @Override public int    getCapacity()    { return 5; }

    @Override
    public String describe() {
        return String.format("  [PHƯƠNG TIỆN LỬA - TRUNG BÌNH] %s (%s) | Sức chứa: %d người",
                getVehicleName(), getVehicleType(), getCapacity());
    }
}

