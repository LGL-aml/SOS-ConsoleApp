package com.jungle.abstractFactory.flood.equipment;

import com.jungle.abstractFactory.product.RescueEquipment;

/** Concrete Product C1a - Thiết bị cứu hộ lũ mức CRITICAL */
public class FloodCriticalEquipment implements RescueEquipment {
    @Override public String getEquipmentName() { return "Bộ Lặn Chuyên Nghiệp + Máy Thở + Dây Cứu Sinh 100m"; }
    @Override public String getEquipmentType() { return "Thiết bị lặn khẩn cấp"; }

    @Override
    public String describe() {
        return String.format("  🔧 [THIẾT BỊ LŨ - NGUY KỊCH] %s | Loại: %s",
                getEquipmentName(), getEquipmentType());
    }
}

