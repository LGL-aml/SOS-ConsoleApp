package com.jungle.strategy.service;

import com.jungle.strategy.model.RescueRequest;
import com.jungle.strategy.model.Rescuer;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Chiến lược 3: Phân công LUÂN PHIÊN (Round-Robin).
 * Chọn người cứu hộ có số nhiệm vụ hoàn thành ít nhất → cân bằng tải.
 * Nếu nhiều người cùng số nhiệm vụ → chọn người gần nhất.
 */
public class RoundRobinStrategy implements RescuerAssignmentStrategy {

    @Override
    public Optional<Rescuer> assign(RescueRequest request, List<Rescuer> availableRescuers) {
        if (availableRescuers.isEmpty()) {
            return Optional.empty();
        }

        // Tìm số nhiệm vụ tối thiểu
        int minMissions = availableRescuers.stream()
                .mapToInt(Rescuer::getCompletedMissions)
                .min()
                .orElse(0);

        // Trong các rescuer có ít nhiệm vụ nhất, chọn người gần nhất
        return availableRescuers.stream()
                .filter(r -> r.getCompletedMissions() == minMissions)
                .min(Comparator.comparingDouble(
                        r -> r.distanceTo(request.getLatitude(), request.getLongitude())
                ));
    }

    @Override
    public String getStrategyName() {
        return "Luân phiên cân bằng tải (Round-Robin)";
    }
}

