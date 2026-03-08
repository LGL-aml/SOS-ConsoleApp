package com.jungle.abstractFactory.fire.equipment;

import com.jungle.abstractFactory.product.RescueEquipment;

/** Concrete Product C2d - Thiết bị cứu hộ hỏa hoạn mức LOW */
public class FireLowEquipment implements RescueEquipment {
    @Override public String getEquipmentName() { return "Mặt Nạ Khói + Đèn Pin + Còi Báo Hiệu"; }
    @Override public String getEquipmentType() { return "Vật dụng hỗ trợ thoát hiểm"; }

    @Override
    public String describe() {
        return String.format("  [THIẾT BỊ LỬA - THẤP] %s | Loại: %s",
                getEquipmentName(), getEquipmentType());
    }
}

