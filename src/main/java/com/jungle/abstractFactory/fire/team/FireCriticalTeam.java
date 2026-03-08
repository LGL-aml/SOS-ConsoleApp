package com.jungle.abstractFactory.fire.team;

import com.jungle.abstractFactory.product.RescueTeam;

/** Concrete Product A2a - Đội cứu hộ hỏa hoạn mức CRITICAL */
public class FireCriticalTeam implements RescueTeam {
    @Override public String getTeamName()      { return "Đội Đặc Nhiệm Chữa Cháy"; }
    @Override public int    getMemberCount()   { return 12; }
    @Override public String getSpecialization(){ return "Chữa cháy nâng cao, cứu người trong lửa, xử lý hóa chất"; }

    @Override
    public String describe() {
        return String.format("  [ĐỘI CHỮA CHÁY - NGUY KỊCH] %s | %d thành viên | %s",
                getTeamName(), getMemberCount(), getSpecialization());
    }
}

