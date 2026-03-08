package com.jungle.abstractFactory.fire.equipment;

import com.jungle.abstractFactory.product.RescueEquipment;

/** Concrete Product C2b - Thiết bị cứu hộ hỏa hoạn mức HIGH */
public class FireHighEquipment implements RescueEquipment {
    @Override public String getEquipmentName() { return "Bộ Đồ Chống Cháy + Bình Thở + Rìu Cứu Hộ + Bộ Sơ Cứu Bỏng"; }
    @Override public String getEquipmentType() { return "Thiết bị chữa cháy nâng cao"; }

    @Override
    public String describe() {
        return String.format("  [THIẾT BỊ LỬA - CAO] %s | Loại: %s",
                getEquipmentName(), getEquipmentType());
    }
}

