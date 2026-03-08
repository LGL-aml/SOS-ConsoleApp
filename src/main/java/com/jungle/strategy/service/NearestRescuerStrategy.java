package com.jungle.strategy.service;

import com.jungle.strategy.model.RescueRequest;
import com.jungle.strategy.model.Rescuer;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Chiến lược 1: Phân công người cứu hộ GẦN NHẤT.
 * Tìm rescuer có khoảng cách ngắn nhất đến vị trí nạn nhân.
 */
public class NearestRescuerStrategy implements RescuerAssignmentStrategy {

    @Override
    public Optional<Rescuer> assign(RescueRequest request, List<Rescuer> availableRescuers) {
        if (availableRescuers.isEmpty()) {
            return Optional.empty();
        }

        return availableRescuers.stream()
                .min(Comparator.comparingDouble(
                        rescuer -> rescuer.distanceTo(request.getLatitude(), request.getLongitude())
                ));
    }

    @Override
    public String getStrategyName() {
        return "Người cứu hộ gần nhất (Nearest Rescuer)";
    }
}

