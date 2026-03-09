package com.jungle.strategy.service;

import com.jungle.strategy.model.RescueRequest;
import com.jungle.strategy.model.Rescuer;

import java.util.List;
import java.util.Optional;

/**
 * Strategy Interface - Chiến lược phân công người cứu hộ.
 * Mỗi chiến lược sẽ có cách chọn rescuer khác nhau.
 */
public interface RescuerAssignmentStrategy {

    Optional<Rescuer> assign(RescueRequest request, List<Rescuer> availableRescuers);

    String getStrategyName();
}

