package com.jungle.abstractFactory.factory;

import com.jungle.abstractFactory.fire.*;
import com.jungle.abstractFactory.flood.*;
import com.jungle.strategy.model.UrgencyLevel;

/**
 * Factory Provider – điểm vào DUY NHẤT để lấy Concrete Factory.
 *
 * Client chỉ gọi: RescueFactoryProvider.getFactory(disasterType, urgency)
 * → nhận về interface RescueFactory, KHÔNG biết concrete class bên trong.
 */
public class RescueFactoryProvider {

    public static final String FLOOD = "flood";
    public static final String FIRE = "fire";

    private RescueFactoryProvider() {
    }

    public static RescueFactory getFactory(String disasterType, UrgencyLevel urgency) {
        return switch (disasterType.trim().toLowerCase()) {
            case FLOOD -> switch (urgency) {
                case CRITICAL -> new FloodCriticalFactory();
                case LOW -> new FloodLowFactory();
            };
            case FIRE -> switch (urgency) {
                case CRITICAL -> new FireCriticalFactory();
                case LOW -> new FireLowFactory();
            };
            default -> throw new IllegalArgumentException(
                    "Loại thảm họa không hỗ trợ: '" + disasterType + "'. Chọn: 'flood' hoặc 'fire'.");
        };
    }
}
