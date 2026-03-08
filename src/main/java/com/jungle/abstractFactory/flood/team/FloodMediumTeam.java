package com.jungle.abstractFactory.flood.team;

import com.jungle.abstractFactory.product.RescueTeam;

/** Concrete Product A1c - Đội cứu hộ lũ lụt mức MEDIUM */
public class FloodMediumTeam implements RescueTeam {
    @Override public String getTeamName()      { return "Đội Cứu Hộ Lũ Cơ Bản"; }
    @Override public int    getMemberCount()   { return 4; }
    @Override public String getSpecialization(){ return "Bơi lội, sơ cứu cơ bản, vận hành xuồng"; }

    @Override
    public String describe() {
        return String.format("  🌊 [ĐỘI LŨ - TRUNG BÌNH] %s | %d thành viên | %s",
                getTeamName(), getMemberCount(), getSpecialization());
    }
}

