package com.jungle.abstractFactory.flood;

import com.jungle.abstractFactory.product.RescueEquipment;
import com.jungle.strategy.model.UrgencyLevel;

/**
 * Concrete Product C1 - Thiết bị cứu hộ lũ lụt
 */
public class FloodRescueEquipment implements RescueEquipment {

    private final String equipmentName;
    private final String equipmentType;

    public FloodRescueEquipment(UrgencyLevel urgency) {
        switch (urgency) {
            case CRITICAL -> {
                this.equipmentName = "Bộ Lặn Chuyên Nghiệp + Máy Thở + Dây Cứu Sinh 100m";
                this.equipmentType = "Thiết bị lặn khẩn cấp";
            }
            case HIGH -> {
                this.equipmentName = "Áo Phao + Dây Cứu Sinh 50m + Bộ Sơ Cứu Nâng Cao";
                this.equipmentType = "Thiết bị nổi & y tế";
            }
            case MEDIUM -> {
                this.equipmentName = "Áo Phao + Phao Vòng + Bộ Sơ Cứu Cơ Bản";
                this.equipmentType = "Thiết bị an toàn cơ bản";
            }
            default -> {
                this.equipmentName = "Áo Phao + Đèn Pin Chống Nước + Còi Báo Hiệu";
                this.equipmentType = "Vật dụng hỗ trợ";
            }
        }
    }

    @Override public String getEquipmentName() { return equipmentName; }
    @Override public String getEquipmentType() { return equipmentType; }

    @Override
    public String describe() {
        return String.format("  🔧 [THIẾT BỊ LŨ] %s | Loại: %s",
                equipmentName, equipmentType);
    }
}

