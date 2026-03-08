package com.jungle.abstractFactory.flood.equipment;

import com.jungle.abstractFactory.product.RescueEquipment;

/** Concrete Product C1b - Thiết bị cứu hộ lũ mức HIGH */
public class FloodHighEquipment implements RescueEquipment {
    @Override public String getEquipmentName() { return "Áo Phao + Dây Cứu Sinh 50m + Bộ Sơ Cứu Nâng Cao"; }
    @Override public String getEquipmentType() { return "Thiết bị nổi & y tế"; }

    @Override
    public String describe() {
        return String.format("  🔧 [THIẾT BỊ LŨ - CAO] %s | Loại: %s",
                getEquipmentName(), getEquipmentType());
    }
}

