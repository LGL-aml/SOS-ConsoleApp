package com.jungle.abstractFactory.flood.vehicle;

import com.jungle.abstractFactory.product.RescueVehicle;

/** Concrete Product B1d - Phương tiện cứu hộ lũ mức LOW */
public class FloodLowVehicle implements RescueVehicle {
    @Override public String getVehicleName() { return "Thuyền Bơm Hơi"; }
    @Override public String getVehicleType() { return "Thuyền nhỏ"; }
    @Override public int    getCapacity()    { return 3; }

    @Override
    public String describe() {
        return String.format("  🚤 [PHƯƠNG TIỆN LŨ - THẤP] %s (%s) | Sức chứa: %d người",
                getVehicleName(), getVehicleType(), getCapacity());
    }
}

