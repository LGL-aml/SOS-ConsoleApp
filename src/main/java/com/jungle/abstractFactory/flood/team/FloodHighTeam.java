package com.jungle.abstractFactory.flood.team;

import com.jungle.abstractFactory.product.RescueTeam;

/** Concrete Product A1b - Đội cứu hộ lũ lụt mức HIGH */
public class FloodHighTeam implements RescueTeam {
    @Override public String getTeamName()      { return "Đội Cứu Hộ Lũ Chuyên Nghiệp"; }
    @Override public int    getMemberCount()   { return 7; }
    @Override public String getSpecialization(){ return "Lặn nước, sơ cứu, vận hành xuồng máy"; }

    @Override
    public String describe() {
        return String.format("  🌊 [ĐỘI LŨ - CAO] %s | %d thành viên | %s",
                getTeamName(), getMemberCount(), getSpecialization());
    }
}

