package com.jungle.abstractFactory.fire.vehicle;

import com.jungle.abstractFactory.product.RescueVehicle;

/** Concrete Product B2a - Phương tiện cứu hộ hỏa hoạn mức CRITICAL */
public class FireCriticalVehicle implements RescueVehicle {
    @Override public String getVehicleName() { return "Xe Thang Chữa Cháy 52m"; }
    @Override public String getVehicleType() { return "Xe thang cao tầng"; }
    @Override public int    getCapacity()    { return 12; }

    @Override
    public String describe() {
        return String.format("  [PHƯƠNG TIỆN LỬA - NGUY KỊCH] %s (%s) | Sức chứa: %d người",
                getVehicleName(), getVehicleType(), getCapacity());
    }
}

