package com.jungle.abstractFactory.flood.team;

import com.jungle.abstractFactory.product.RescueTeam;

/** Concrete Product A1d - Đội cứu hộ lũ lụt mức LOW */
public class FloodLowTeam implements RescueTeam {
    @Override public String getTeamName()      { return "Tổ Hỗ Trợ Lũ Lụt"; }
    @Override public int    getMemberCount()   { return 2; }
    @Override public String getSpecialization(){ return "Hỗ trợ sơ tán, phát đồ cứu trợ"; }

    @Override
    public String describe() {
        return String.format("  🌊 [ĐỘI LŨ - THẤP] %s | %d thành viên | %s",
                getTeamName(), getMemberCount(), getSpecialization());
    }
}

