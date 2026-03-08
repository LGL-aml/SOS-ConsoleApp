package com.jungle.abstractFactory.flood;

import com.jungle.abstractFactory.product.RescueTeam;
import com.jungle.strategy.model.UrgencyLevel;

/**
 * Concrete Product A1 - Đội cứu hộ lũ lụt
 */
public class FloodRescueTeam implements RescueTeam {

    private final String teamName;
    private final int memberCount;
    private final String specialization;

    public FloodRescueTeam(UrgencyLevel urgency) {
        switch (urgency) {
            case CRITICAL -> {
                this.teamName        = "Đội Đặc Nhiệm Lũ Lụt";
                this.memberCount     = 10;
                this.specialization  = "Lặn nước sâu, cứu hộ khẩn cấp, sơ cứu nâng cao";
            }
            case HIGH -> {
                this.teamName        = "Đội Cứu Hộ Lũ Chuyên Nghiệp";
                this.memberCount     = 7;
                this.specialization  = "Lặn nước, sơ cứu, vận hành xuồng máy";
            }
            case MEDIUM -> {
                this.teamName        = "Đội Cứu Hộ Lũ Cơ Bản";
                this.memberCount     = 4;
                this.specialization  = "Bơi lội, sơ cứu cơ bản, vận hành xuồng";
            }
            default -> {
                this.teamName        = "Tổ Hỗ Trợ Lũ Lụt";
                this.memberCount     = 2;
                this.specialization  = "Hỗ trợ sơ tán, phát đồ cứu trợ";
            }
        }
    }

    @Override public String getTeamName()       { return teamName; }
    @Override public int    getMemberCount()     { return memberCount; }
    @Override public String getSpecialization()  { return specialization; }

    @Override
    public String describe() {
        return String.format("  🌊 [ĐỘI LŨ LỤT] %s | %d thành viên | Chuyên môn: %s",
                teamName, memberCount, specialization);
    }
}

