package com.jungle.abstractFactory.fire.team;

import com.jungle.abstractFactory.product.RescueTeam;

/** Concrete Product A2c - Đội cứu hộ hỏa hoạn mức MEDIUM */
public class FireMediumTeam implements RescueTeam {
    @Override public String getTeamName()      { return "Đội Chữa Cháy Cơ Bản"; }
    @Override public int    getMemberCount()   { return 5; }
    @Override public String getSpecialization(){ return "Phun nước, sơ tán, sơ cứu cơ bản"; }

    @Override
    public String describe() {
        return String.format("  [ĐỘI CHỮA CHÁY - TRUNG BÌNH] %s | %d thành viên | %s",
                getTeamName(), getMemberCount(), getSpecialization());
    }
}

