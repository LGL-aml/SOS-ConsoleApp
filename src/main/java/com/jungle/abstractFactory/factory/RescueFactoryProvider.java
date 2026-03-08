package com.jungle.abstractFactory.factory;

import com.jungle.abstractFactory.fire.*;
import com.jungle.abstractFactory.flood.*;
import com.jungle.strategy.model.UrgencyLevel;

/**
 * Factory Provider - Điểm vào DUY NHẤT để lấy Concrete Factory.
 *
 * Tra cứu theo (disasterType + urgencyLevel) → trả về đúng Concrete Factory
 * đại diện cho "họ sản phẩm" phù hợp. Client code chỉ phụ thuộc vào
 * interface RescueFactory, không bao giờ biết đến concrete class.
 */
public class RescueFactoryProvider {

    public static final String FLOOD = "flood";
    public static final String FIRE  = "fire";

    private RescueFactoryProvider() {}

    /**
     * Trả về Concrete Factory phù hợp dựa trên loại thảm họa và mức độ khẩn cấp.
     *
     * @param disasterType "flood" hoặc "fire"
     * @param urgency      mức độ khẩn cấp của yêu cầu cứu hộ
     * @return  Concrete Factory tương ứng - mỗi factory tạo ra một họ sản phẩm nhất quán
     */
    public static RescueFactory getFactory(String disasterType, UrgencyLevel urgency) {
        return switch (disasterType.trim().toLowerCase()) {
            case FLOOD -> switch (urgency) {
                case CRITICAL -> new FloodCriticalFactory();
                case HIGH     -> new FloodHighFactory();
                case MEDIUM   -> new FloodMediumFactory();
                case LOW      -> new FloodLowFactory();
            };
            case FIRE -> switch (urgency) {
                case CRITICAL -> new FireCriticalFactory();
                case HIGH     -> new FireHighFactory();
                case MEDIUM   -> new FireMediumFactory();
                case LOW      -> new FireLowFactory();
            };
            default -> throw new IllegalArgumentException(
                    "Loại thảm họa không hỗ trợ: '" + disasterType + "'. Chọn: 'flood' hoặc 'fire'.");
        };
    }
}
