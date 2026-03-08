package com.jungle.abstractFactory.flood.vehicle;

import com.jungle.abstractFactory.product.RescueVehicle;

/** Concrete Product B1b - Phương tiện cứu hộ lũ mức HIGH */
public class FloodHighVehicle implements RescueVehicle {
    @Override public String getVehicleName() { return "Xuồng Máy Tốc Độ Cao"; }
    @Override public String getVehicleType() { return "Xuồng máy"; }
    @Override public int    getCapacity()    { return 8; }

    @Override
    public String describe() {
        return String.format("  🚤 [PHƯƠNG TIỆN LŨ - CAO] %s (%s) | Sức chứa: %d người",
                getVehicleName(), getVehicleType(), getCapacity());
    }
}

