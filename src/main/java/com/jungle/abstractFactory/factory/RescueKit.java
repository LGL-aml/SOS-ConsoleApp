package com.jungle.abstractFactory.factory;

import com.jungle.abstractFactory.product.RescueEquipment;
import com.jungle.abstractFactory.product.RescueTeam;
import com.jungle.abstractFactory.product.RescueVehicle;

/**
 * Bộ công cụ cứu hộ hoàn chỉnh được tạo ra bởi Abstract Factory.
 * Client chỉ cần gọi RescueFactory.createKit() - không cần biết concrete class nào.
 *
 * Đây là "product family" - 3 sản phẩm được tạo từ cùng một factory đảm bảo
 * tính nhất quán (compatible) với nhau.
 */
public class RescueKit {

    private final String       disasterType;
    private final String       urgencyName;
    private final RescueTeam      team;
    private final RescueVehicle   vehicle;
    private final RescueEquipment equipment;

    /**
     * Tạo RescueKit từ một Concrete Factory.
     * Factory đã mang sẵn thông tin loại thảm họa + mức độ khẩn cấp,
     * nên factory method createTeam/createVehicle/createEquipment KHÔNG cần nhận parameter.
     */
    public static RescueKit from(RescueFactory factory) {
        return new RescueKit(
                factory.getDisasterType(),
                factory.getUrgencyLevel().getDisplayName(),
                factory.createTeam(),
                factory.createVehicle(),
                factory.createEquipment()
        );
    }

    private RescueKit(String disasterType, String urgencyName,
                      RescueTeam team, RescueVehicle vehicle, RescueEquipment equipment) {
        this.disasterType = disasterType;
        this.urgencyName  = urgencyName;
        this.team      = team;
        this.vehicle   = vehicle;
        this.equipment = equipment;
    }

    public String          getDisasterType() { return disasterType; }
    public RescueTeam      getTeam()         { return team; }
    public RescueVehicle   getVehicle()      { return vehicle; }
    public RescueEquipment getEquipment()    { return equipment; }

    public void printKit() {
        System.out.println("  ┌─────────────────────────────────────────────────────────────┐");
        System.out.printf ("  │  📦 BỘ CỨU HỘ [%s - Mức: %s]%n", disasterType, urgencyName);
        System.out.println("  ├─────────────────────────────────────────────────────────────┤");
        System.out.println(team.describe());
        System.out.println(vehicle.describe());
        System.out.println(equipment.describe());
        System.out.println("  └─────────────────────────────────────────────────────────────┘");
    }
}
