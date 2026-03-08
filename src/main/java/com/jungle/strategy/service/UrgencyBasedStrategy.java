package com.jungle.strategy.service;

import com.jungle.strategy.model.RescueRequest;
import com.jungle.strategy.model.Rescuer;
import com.jungle.strategy.model.UrgencyLevel;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Chiến lược 2: Phân công theo MỨC ĐỘ KHẨN CẤP.
 *
 * Strategy chỉ biết về TIÊU CHÍ chọn rescuer, KHÔNG phụ thuộc vào
 * domain cụ thể (không hard-code specialty string).
 *
 * Logic:
 *   - CRITICAL : chọn rescuer đã hoàn thành nhiều nhiệm vụ nhất (kinh nghiệm cao nhất)
 *   - HIGH      : kết hợp kinh nghiệm + khoảng cách (score = missions*0.6 - distance*0.4)
 *   - MEDIUM/LOW: chọn người gần nhất (tiết kiệm nguồn lực)
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
                                .thenComparingDouble(r ->
                                        -r.distanceTo(request.getLatitude(), request.getLongitude())));

            case HIGH ->
                // Kết hợp kinh nghiệm (60%) + khoảng cách ngắn (40%)
                availableRescuers.stream()
                        .min(Comparator.comparingDouble(r -> computeScore(r, request)));

            // MEDIUM, LOW: chọn người gần nhất - tiết kiệm nguồn lực
            default ->
                availableRescuers.stream()
                        .min(Comparator.comparingDouble(r ->
                                r.distanceTo(request.getLatitude(), request.getLongitude())));
        };
    }

    /**
     * Score thấp hơn = tốt hơn.
     * Cân bằng giữa khoảng cách (40%) và nghịch đảo kinh nghiệm (60%).
     */
    private double computeScore(Rescuer r, RescueRequest req) {
        double distance    = r.distanceTo(req.getLatitude(), req.getLongitude());
        int    missions    = r.getCompletedMissions();
        double expPenalty  = missions > 0 ? (1.0 / missions) : 1.0; // ít kinh nghiệm → penalty cao
        return 0.4 * distance + 0.6 * expPenalty * 100;
    }

    @Override
    public String getStrategyName() {
        return "Theo mức độ khẩn cấp (Urgency-Based)";
    }
}
