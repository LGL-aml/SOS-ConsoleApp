package com.jungle.abstractFactory.fire;

import com.jungle.abstractFactory.product.RescueEquipment;
import com.jungle.strategy.model.UrgencyLevel;

/**
 * Concrete Product C2 - Thiết bị cứu hộ hỏa hoạn
 */
public class FireRescueEquipment implements RescueEquipment {

    private final String equipmentName;
    private final String equipmentType;

    public FireRescueEquipment(UrgencyLevel urgency) {
        switch (urgency) {
            case CRITICAL -> {
                this.equipmentName = "Bộ Đồ Chống Cháy 1000°C + Bình Thở SCBA + Cưa Cắt Thủy Lực";
                this.equipmentType = "Thiết bị chống cháy chuyên nghiệp";
            }
            case HIGH -> {
                this.equipmentName = "Bộ Đồ Chống Cháy + Bình Thở + Rìu Cứu Hộ + Bộ Sơ Cứu Bỏng";
                this.equipmentType = "Thiết bị chữa cháy nâng cao";
            }
            case MEDIUM -> {
                this.equipmentName = "Mặt Nạ Phòng Độc + Bình Chữa Cháy + Bộ Sơ Cứu Cơ Bản";
                this.equipmentType = "Thiết bị phòng cháy cơ bản";
            }
            default -> {
                this.equipmentName = "Mặt Nạ Khói + Đèn Pin + Còi Báo Hiệu";
                this.equipmentType = "Vật dụng hỗ trợ thoát hiểm";
            }
        }
    }

    @Override public String getEquipmentName() { return equipmentName; }
    @Override public String getEquipmentType() { return equipmentType; }

    @Override
    public String describe() {
        return String.format("  🔧 [THIẾT BỊ CHỮA CHÁY] %s | Loại: %s",
                equipmentName, equipmentType);
    }
}

