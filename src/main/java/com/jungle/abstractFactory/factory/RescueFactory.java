package com.jungle.abstractFactory.factory;

import com.jungle.abstractFactory.product.RescueEquipment;
import com.jungle.abstractFactory.product.RescueTeam;
import com.jungle.abstractFactory.product.RescueVehicle;
import com.jungle.strategy.model.UrgencyLevel;

public interface RescueFactory {

    RescueTeam createTeam();

    RescueVehicle createVehicle();

    RescueEquipment createEquipment();

    String getDisasterType();

    UrgencyLevel getUrgencyLevel();
}
