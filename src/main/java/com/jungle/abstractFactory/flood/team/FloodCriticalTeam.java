package com.jungle.abstractFactory.flood.team;

import com.jungle.abstractFactory.product.RescueTeam;

/** Concrete Product A1a - Đội cứu hộ lũ lụt mức CRITICAL */
public class FloodCriticalTeam implements RescueTeam {
    @Override public String getTeamName()      { return "Đội Đặc Nhiệm Lũ Lụt"; }
    @Override public int    getMemberCount()   { return 10; }
    @Override public String getSpecialization(){ return "Lặn nước sâu, cứu hộ khẩn cấp, sơ cứu nâng cao"; }

    @Override
    public String describe() {
        return String.format("  🌊 [ĐỘI LŨ - NGUY KỊCH] %s | %d thành viên | %s",
                getTeamName(), getMemberCount(), getSpecialization());
    }
}

