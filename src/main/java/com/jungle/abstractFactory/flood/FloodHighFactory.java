package com.jungle.abstractFactory.flood;

import com.jungle.abstractFactory.factory.RescueFactory;
import com.jungle.abstractFactory.flood.equipment.FloodHighEquipment;
import com.jungle.abstractFactory.flood.team.FloodHighTeam;
import com.jungle.abstractFactory.flood.vehicle.FloodHighVehicle;
import com.jungle.abstractFactory.product.RescueEquipment;
import com.jungle.abstractFactory.product.RescueTeam;
import com.jungle.abstractFactory.product.RescueVehicle;
import com.jungle.strategy.model.UrgencyLevel;

/**
 * Concrete Factory - Bộ cứu hộ LŨ LỤT mức CAO (HIGH).
 */
public class FloodHighFactory implements RescueFactory {

    @Override public RescueTeam      createTeam()      { return new FloodHighTeam(); }
    @Override public RescueVehicle   createVehicle()   { return new FloodHighVehicle(); }
    @Override public RescueEquipment createEquipment() { return new FloodHighEquipment(); }
    @Override public String          getDisasterType() { return "Lũ Lụt"; }
    @Override public UrgencyLevel    getUrgencyLevel() { return UrgencyLevel.HIGH; }
}

