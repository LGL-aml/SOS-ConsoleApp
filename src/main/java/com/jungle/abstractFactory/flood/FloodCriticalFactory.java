package com.jungle.abstractFactory.flood;

import com.jungle.abstractFactory.factory.RescueFactory;
import com.jungle.abstractFactory.flood.equipment.FloodCriticalEquipment;
import com.jungle.abstractFactory.flood.team.FloodCriticalTeam;
import com.jungle.abstractFactory.flood.vehicle.FloodCriticalVehicle;
import com.jungle.abstractFactory.product.RescueEquipment;
import com.jungle.abstractFactory.product.RescueTeam;
import com.jungle.abstractFactory.product.RescueVehicle;
import com.jungle.strategy.model.UrgencyLevel;

/**
 * Concrete Factory - Bộ cứu hộ LŨ LỤT mức NGUY KỊCH (CRITICAL).
 * Tạo ra đúng một họ sản phẩm nhất quán: team + vehicle + equipment cùng cấp độ.
 * Factory method KHÔNG nhận parameter - tuân thủ chuẩn Abstract Factory Pattern.
 */
public class FloodCriticalFactory implements RescueFactory {

    @Override
    public RescueTeam createTeam() {
        return new FloodCriticalTeam();
    }

    @Override
    public RescueVehicle createVehicle() {
        return new FloodCriticalVehicle();
    }

    @Override
    public RescueEquipment createEquipment() {
        return new FloodCriticalEquipment();
    }

    @Override
    public String getDisasterType() { return "Lũ Lụt"; }

    @Override
    public UrgencyLevel getUrgencyLevel() { return UrgencyLevel.CRITICAL; }
}

