package com.jungle.abstractFactory.fire;

import com.jungle.abstractFactory.product.RescueTeam;
import com.jungle.strategy.model.UrgencyLevel;

/**
 * Concrete Product A2 - Đội cứu hộ hỏa hoạn
 */
public class FireRescueTeam implements RescueTeam {

    private final String teamName;
    private final int memberCount;
    private final String specialization;

    public FireRescueTeam(UrgencyLevel urgency) {
        switch (urgency) {
            case CRITICAL -> {
                this.teamName       = "Đội Đặc Nhiệm Chữa Cháy";
                this.memberCount    = 12;
                this.specialization = "Chữa cháy nâng cao, cứu người trong lửa, xử lý hóa chất";
            }
            case HIGH -> {
                this.teamName       = "Đội Chữa Cháy Chuyên Nghiệp";
                this.memberCount    = 8;
                this.specialization = "Chữa cháy, sơ cứu bỏng, vận hành xe cứu hỏa";
            }
            case MEDIUM -> {
                this.teamName       = "Đội Chữa Cháy Cơ Bản";
                this.memberCount    = 5;
                this.specialization = "Phun nước, sơ tán, sơ cứu cơ bản";
            }
            default -> {
                this.teamName       = "Tổ Hỗ Trợ Phòng Cháy";
                this.memberCount    = 2;
                this.specialization = "Hỗ trợ sơ tán, kiểm soát khu vực";
            }
        }
    }

    @Override public String getTeamName()       { return teamName; }
    @Override public int    getMemberCount()     { return memberCount; }
    @Override public String getSpecialization()  { return specialization; }

    @Override
    public String describe() {
        return String.format("  🔥 [ĐỘI CHỮA CHÁY] %s | %d thành viên | Chuyên môn: %s",
                teamName, memberCount, specialization);
    }
}

