package com.jungle.abstractFactory.factory;

import com.jungle.abstractFactory.product.RescueEquipment;
import com.jungle.abstractFactory.product.RescueTeam;
import com.jungle.abstractFactory.product.RescueVehicle;
import com.jungle.strategy.model.UrgencyLevel;

/**
 * Abstract Factory Interface - CHUẨN PATTERN.
 *
 * Mỗi Concrete Factory đại diện cho một "họ sản phẩm" cụ thể:
 *   (loại thảm họa + mức độ khẩn cấp) → bộ sản phẩm nhất quán.
 *
 * Factory method KHÔNG nhận runtime parameter.
 * Mọi quyết định về sản phẩm được đóng gói trong từng Concrete Factory.
 */
public interface RescueFactory {

    /** Tạo đội cứu hộ phù hợp với họ sản phẩm này */
    RescueTeam createTeam();

    /** Tạo phương tiện cứu hộ phù hợp với họ sản phẩm này */
    RescueVehicle createVehicle();

    /** Tạo thiết bị cứu hộ phù hợp với họ sản phẩm này */
    RescueEquipment createEquipment();

    /** Tên loại thảm họa mà factory này phục vụ */
    String getDisasterType();

    /** Mức độ khẩn cấp mà factory này phục vụ */
    UrgencyLevel getUrgencyLevel();
}
