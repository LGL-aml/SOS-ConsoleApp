package com.jungle.abstractFactory.fire.vehicle;

import com.jungle.abstractFactory.product.RescueVehicle;

/** Concrete Product B2d - Phương tiện cứu hộ hỏa hoạn mức LOW */
public class FireLowVehicle implements RescueVehicle {
    @Override public String getVehicleName() { return "Xe Cứu Thương"; }
    @Override public String getVehicleType() { return "Xe cứu thương"; }
    @Override public int    getCapacity()    { return 3; }

    @Override
    public String describe() {
        return String.format("  [PHƯƠNG TIỆN LỬA - THẤP] %s (%s) | Sức chứa: %d người",
                getVehicleName(), getVehicleType(), getCapacity());
    }
}

