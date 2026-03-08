package com.jungle.abstractFactory.fire.team;

import com.jungle.abstractFactory.product.RescueTeam;

/** Concrete Product A2d - Đội cứu hộ hỏa hoạn mức LOW */
public class FireLowTeam implements RescueTeam {
    @Override public String getTeamName()      { return "Tổ Hỗ Trợ Phòng Cháy"; }
    @Override public int    getMemberCount()   { return 2; }
    @Override public String getSpecialization(){ return "Hỗ trợ sơ tán, kiểm soát khu vực"; }

    @Override
    public String describe() {
        return String.format("  [ĐỘI CHỮA CHÁY - THẤP] %s | %d thành viên | %s",
                getTeamName(), getMemberCount(), getSpecialization());
    }
}

