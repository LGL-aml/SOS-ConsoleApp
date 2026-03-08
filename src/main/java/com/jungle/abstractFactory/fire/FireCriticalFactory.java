package com.jungle.abstractFactory.fire;

import com.jungle.abstractFactory.factory.RescueFactory;
import com.jungle.abstractFactory.fire.equipment.FireCriticalEquipment;
import com.jungle.abstractFactory.fire.team.FireCriticalTeam;
import com.jungle.abstractFactory.fire.vehicle.FireCriticalVehicle;
import com.jungle.abstractFactory.product.RescueEquipment;
import com.jungle.abstractFactory.product.RescueTeam;
import com.jungle.abstractFactory.product.RescueVehicle;
import com.jungle.strategy.model.UrgencyLevel;

/**
 * Concrete Factory - Bộ cứu hộ HỎA HOẠN mức NGUY KỊCH (CRITICAL).
 */
public class FireCriticalFactory implements RescueFactory {

    @Override public RescueTeam      createTeam()      { return new FireCriticalTeam(); }
    @Override public RescueVehicle   createVehicle()   { return new FireCriticalVehicle(); }
    @Override public RescueEquipment createEquipment() { return new FireCriticalEquipment(); }
    @Override public String          getDisasterType() { return "Hỏa Hoạn"; }
    @Override public UrgencyLevel    getUrgencyLevel() { return UrgencyLevel.CRITICAL; }
}

