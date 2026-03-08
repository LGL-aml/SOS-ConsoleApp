package com.jungle.abstractFactory.flood.vehicle;

import com.jungle.abstractFactory.product.RescueVehicle;

/** Concrete Product B1a - Phương tiện cứu hộ lũ mức CRITICAL */
public class FloodCriticalVehicle implements RescueVehicle {
    @Override public String getVehicleName() { return "Trực thăng Cứu hộ Mi-17"; }
    @Override public String getVehicleType() { return "Trực thăng"; }
    @Override public int    getCapacity()    { return 15; }

    @Override
    public String describe() {
        return String.format("  🚁 [PHƯƠNG TIỆN LŨ - NGUY KỊCH] %s (%s) | Sức chứa: %d người",
                getVehicleName(), getVehicleType(), getCapacity());
    }
}

