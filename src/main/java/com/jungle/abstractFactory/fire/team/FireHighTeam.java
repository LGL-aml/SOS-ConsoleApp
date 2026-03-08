package com.jungle.abstractFactory.fire.team;

import com.jungle.abstractFactory.product.RescueTeam;

/** Concrete Product A2b - Đội cứu hộ hỏa hoạn mức HIGH */
public class FireHighTeam implements RescueTeam {
    @Override public String getTeamName()      { return "Đội Chữa Cháy Chuyên Nghiệp"; }
    @Override public int    getMemberCount()   { return 8; }
    @Override public String getSpecialization(){ return "Chữa cháy, sơ cứu bỏng, vận hành xe cứu hỏa"; }

    @Override
    public String describe() {
        return String.format("  [ĐỘI CHỮA CHÁY - CAO] %s | %d thành viên | %s",
                getTeamName(), getMemberCount(), getSpecialization());
    }
}

