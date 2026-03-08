package com.jungle.abstractFactory.flood.vehicle;

import com.jungle.abstractFactory.product.RescueVehicle;

/** Concrete Product B1c - Phương tiện cứu hộ lũ mức MEDIUM */
public class FloodMediumVehicle implements RescueVehicle {
    @Override public String getVehicleName() { return "Xuồng Cứu Hộ Tiêu Chuẩn"; }
    @Override public String getVehicleType() { return "Xuồng"; }
    @Override public int    getCapacity()    { return 5; }

    @Override
    public String describe() {
        return String.format("  🚤 [PHƯƠNG TIỆN LŨ - TRUNG BÌNH] %s (%s) | Sức chứa: %d người",
                getVehicleName(), getVehicleType(), getCapacity());
    }
}

