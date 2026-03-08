package com.jungle.abstractFactory.fire.equipment;

import com.jungle.abstractFactory.product.RescueEquipment;

/** Concrete Product C2a - Thiết bị cứu hộ hỏa hoạn mức CRITICAL */
public class FireCriticalEquipment implements RescueEquipment {
    @Override public String getEquipmentName() { return "Bộ Đồ Chống Cháy 1000°C + Bình Thở SCBA + Cưa Cắt Thủy Lực"; }
    @Override public String getEquipmentType() { return "Thiết bị chống cháy chuyên nghiệp"; }

    @Override
    public String describe() {
        return String.format("  [THIẾT BỊ LỬA - NGUY KỊCH] %s | Loại: %s",
                getEquipmentName(), getEquipmentType());
    }
}

