package com.jungle.abstractFactory.fire;

import com.jungle.abstractFactory.factory.RescueFactory;
import com.jungle.abstractFactory.fire.equipment.FireLowEquipment;
import com.jungle.abstractFactory.fire.team.FireLowTeam;
import com.jungle.abstractFactory.fire.vehicle.FireLowVehicle;
import com.jungle.abstractFactory.product.RescueEquipment;
import com.jungle.abstractFactory.product.RescueTeam;
import com.jungle.abstractFactory.product.RescueVehicle;
import com.jungle.strategy.model.UrgencyLevel;

/**
 * Concrete Factory - Bộ cứu hộ HỎA HOẠN mức THẤP (LOW).
 */
public class FireLowFactory implements RescueFactory {

    @Override public RescueTeam      createTeam()      { return new FireLowTeam(); }
    @Override public RescueVehicle   createVehicle()   { return new FireLowVehicle(); }
    @Override public RescueEquipment createEquipment() { return new FireLowEquipment(); }
    @Override public String          getDisasterType() { return "Hỏa Hoạn"; }
    @Override public UrgencyLevel    getUrgencyLevel() { return UrgencyLevel.LOW; }
}

