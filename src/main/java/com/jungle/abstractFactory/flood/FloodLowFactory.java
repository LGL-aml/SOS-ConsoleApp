package com.jungle.abstractFactory.flood;

import com.jungle.abstractFactory.factory.RescueFactory;
import com.jungle.abstractFactory.flood.equipment.FloodLowEquipment;
import com.jungle.abstractFactory.flood.team.FloodLowTeam;
import com.jungle.abstractFactory.flood.vehicle.FloodLowVehicle;
import com.jungle.abstractFactory.product.RescueEquipment;
import com.jungle.abstractFactory.product.RescueTeam;
import com.jungle.abstractFactory.product.RescueVehicle;
import com.jungle.strategy.model.UrgencyLevel;

/**
 * Concrete Factory - Bộ cứu hộ LŨ LỤT mức THẤP (LOW).
 */
public class FloodLowFactory implements RescueFactory {

    @Override public RescueTeam      createTeam()      { return new FloodLowTeam(); }
    @Override public RescueVehicle   createVehicle()   { return new FloodLowVehicle(); }
    @Override public RescueEquipment createEquipment() { return new FloodLowEquipment(); }
    @Override public String          getDisasterType() { return "Lũ Lụt"; }
    @Override public UrgencyLevel    getUrgencyLevel() { return UrgencyLevel.LOW; }
}

