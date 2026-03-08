package com.jungle.abstractFactory.fire;

import com.jungle.abstractFactory.factory.RescueFactory;
import com.jungle.abstractFactory.fire.equipment.FireHighEquipment;
import com.jungle.abstractFactory.fire.team.FireHighTeam;
import com.jungle.abstractFactory.fire.vehicle.FireHighVehicle;
import com.jungle.abstractFactory.product.RescueEquipment;
import com.jungle.abstractFactory.product.RescueTeam;
import com.jungle.abstractFactory.product.RescueVehicle;
import com.jungle.strategy.model.UrgencyLevel;

/**
 * Concrete Factory - Bộ cứu hộ HỎA HOẠN mức CAO (HIGH).
 */
public class FireHighFactory implements RescueFactory {

    @Override public RescueTeam      createTeam()      { return new FireHighTeam(); }
    @Override public RescueVehicle   createVehicle()   { return new FireHighVehicle(); }
    @Override public RescueEquipment createEquipment() { return new FireHighEquipment(); }
    @Override public String          getDisasterType() { return "Hỏa Hoạn"; }
    @Override public UrgencyLevel    getUrgencyLevel() { return UrgencyLevel.HIGH; }
}

