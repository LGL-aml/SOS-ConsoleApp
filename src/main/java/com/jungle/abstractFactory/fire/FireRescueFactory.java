package com.jungle.abstractFactory.fire;

import com.jungle.abstractFactory.factory.RescueFactory;
import com.jungle.abstractFactory.fire.equipment.FireMediumEquipment;
import com.jungle.abstractFactory.fire.team.FireMediumTeam;
import com.jungle.abstractFactory.fire.vehicle.FireMediumVehicle;
import com.jungle.abstractFactory.product.RescueEquipment;
import com.jungle.abstractFactory.product.RescueTeam;
import com.jungle.abstractFactory.product.RescueVehicle;
import com.jungle.strategy.model.UrgencyLevel;

/**
 * @deprecated Lớp này giữ lại để tương thích ngược.
 * Hãy dùng FireCriticalFactory / FireHighFactory / FireMediumFactory / FireLowFactory
 * thông qua RescueFactoryProvider.getFactory(type, urgency).
 */
@Deprecated
public class FireRescueFactory implements RescueFactory {

    @Override public RescueTeam      createTeam()      { return new FireMediumTeam(); }
    @Override public RescueVehicle   createVehicle()   { return new FireMediumVehicle(); }
    @Override public RescueEquipment createEquipment() { return new FireMediumEquipment(); }
    @Override public String          getDisasterType() { return "Hỏa Hoạn"; }
    @Override public UrgencyLevel    getUrgencyLevel() { return UrgencyLevel.MEDIUM; }
}
