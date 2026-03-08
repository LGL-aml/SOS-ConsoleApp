package com.jungle.abstractFactory.flood;

import com.jungle.abstractFactory.factory.RescueFactory;
import com.jungle.abstractFactory.flood.equipment.FloodMediumEquipment;
import com.jungle.abstractFactory.flood.team.FloodMediumTeam;
import com.jungle.abstractFactory.flood.vehicle.FloodMediumVehicle;
import com.jungle.abstractFactory.product.RescueEquipment;
import com.jungle.abstractFactory.product.RescueTeam;
import com.jungle.abstractFactory.product.RescueVehicle;
import com.jungle.strategy.model.UrgencyLevel;

/**
 * Concrete Factory - Bộ cứu hộ LŨ LỤT mức TRUNG BÌNH (MEDIUM).
 */
public class FloodMediumFactory implements RescueFactory {

    @Override public RescueTeam      createTeam()      { return new FloodMediumTeam(); }
    @Override public RescueVehicle   createVehicle()   { return new FloodMediumVehicle(); }
    @Override public RescueEquipment createEquipment() { return new FloodMediumEquipment(); }
    @Override public String          getDisasterType() { return "Lũ Lụt"; }
    @Override public UrgencyLevel    getUrgencyLevel() { return UrgencyLevel.MEDIUM; }
}

