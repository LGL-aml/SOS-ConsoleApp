package com.jungle.strategy.service;

import com.jungle.strategy.model.RescueRequest;
import com.jungle.strategy.model.Rescuer;
import com.jungle.strategy.model.UrgencyLevel;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Concrete Strategy 2: Phân công theo MỨC ĐỘ KHẨN CẤP.
 *
 * - CRITICAL: chọn người cứu hộ kinh nghiệm nhất (nhiều nhiệm vụ hoàn thành
 * nhất)
 * - LOW: chọn người gần nhất (tiết kiệm nguồn lực)
 */
public class UrgencyBasedStrategy implements RescuerAssignmentStrategy {

    @Override
    public Optional<Rescuer> assign(RescueRequest request, List<Rescuer> availableRescuers) {
        if (availableRescuers.isEmpty()) {
            return Optional.empty();
        }

        UrgencyLevel urgency = request.getUrgencyLevel();

        return switch (urgency) {
            case CRITICAL ->
                // Ưu tiên người có kinh nghiệm nhất (missions cao nhất)
                // Nếu bằng nhau → chọn người gần nhất
                availableRescuers.stream()
                        .max(Comparator
                                .comparingInt(Rescuer::getCompletedMissions)
                                .thenComparingDouble(
                                        r -> -r.distanceTo(request.getLatitude(), request.getLongitude())));

            case LOW ->
                // Chọn người gần nhất - tiết kiệm nguồn lực
                availableRescuers.stream()
                        .min(Comparator
                                .comparingDouble(r -> r.distanceTo(request.getLatitude(), request.getLongitude())));
        };
    }

    @Override
    public String getStrategyName() {
        return "Theo mức độ khẩn cấp (Urgency-Based)";
    }
}
