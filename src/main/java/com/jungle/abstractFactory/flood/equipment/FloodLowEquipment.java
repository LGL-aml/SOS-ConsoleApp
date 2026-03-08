package com.jungle.abstractFactory.flood.equipment;

import com.jungle.abstractFactory.product.RescueEquipment;

/** Concrete Product C1d - Thiết bị cứu hộ lũ mức LOW */
public class FloodLowEquipment implements RescueEquipment {
    @Override public String getEquipmentName() { return "Áo Phao + Đèn Pin Chống Nước + Còi Báo Hiệu"; }
    @Override public String getEquipmentType() { return "Vật dụng hỗ trợ"; }

    @Override
    public String describe() {
        return String.format("  🔧 [THIẾT BỊ LŨ - THẤP] %s | Loại: %s",
                getEquipmentName(), getEquipmentType());
    }
}

