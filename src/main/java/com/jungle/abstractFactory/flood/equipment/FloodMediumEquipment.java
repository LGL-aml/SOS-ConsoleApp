package com.jungle.abstractFactory.flood.equipment;

import com.jungle.abstractFactory.product.RescueEquipment;

/** Concrete Product C1c - Thiết bị cứu hộ lũ mức MEDIUM */
public class FloodMediumEquipment implements RescueEquipment {
    @Override public String getEquipmentName() { return "Áo Phao + Phao Vòng + Bộ Sơ Cứu Cơ Bản"; }
    @Override public String getEquipmentType() { return "Thiết bị an toàn cơ bản"; }

    @Override
    public String describe() {
        return String.format("  🔧 [THIẾT BỊ LŨ - TRUNG BÌNH] %s | Loại: %s",
                getEquipmentName(), getEquipmentType());
    }
}

