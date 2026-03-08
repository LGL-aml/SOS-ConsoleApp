package com.jungle.abstractFactory.fire.vehicle;

import com.jungle.abstractFactory.product.RescueVehicle;

/** Concrete Product B2b - Phương tiện cứu hộ hỏa hoạn mức HIGH */
public class FireHighVehicle implements RescueVehicle {
    @Override public String getVehicleName() { return "Xe Cứu Hỏa Bồn Nước 10.000L"; }
    @Override public String getVehicleType() { return "Xe cứu hỏa lớn"; }
    @Override public int    getCapacity()    { return 8; }

    @Override
    public String describe() {
        return String.format("  [PHƯƠNG TIỆN LỬA - CAO] %s (%s) | Sức chứa: %d người",
                getVehicleName(), getVehicleType(), getCapacity());
    }
}

