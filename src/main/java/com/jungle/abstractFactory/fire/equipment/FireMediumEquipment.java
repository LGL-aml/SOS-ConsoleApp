package com.jungle.abstractFactory.fire.equipment;

import com.jungle.abstractFactory.product.RescueEquipment;

/** Concrete Product C2c - Thiết bị cứu hộ hỏa hoạn mức MEDIUM */
public class FireMediumEquipment implements RescueEquipment {
    @Override public String getEquipmentName() { return "Mặt Nạ Phòng Độc + Bình Chữa Cháy + Bộ Sơ Cứu Cơ Bản"; }
    @Override public String getEquipmentType() { return "Thiết bị phòng cháy cơ bản"; }

    @Override
    public String describe() {
        return String.format("  [THIẾT BỊ LỬA - TRUNG BÌNH] %s | Loại: %s",
                getEquipmentName(), getEquipmentType());
    }
}

