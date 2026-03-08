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

    /**
     * Chọn người cứu hộ phù hợp nhất cho yêu cầu cứu hộ.
     *
     * @param request          yêu cầu cứu hộ cần phân công
     * @param availableRescuers danh sách người cứu hộ đang sẵn sàng
     * @return Optional chứa rescuer được chọn, hoặc empty nếu không tìm được
     */
    Optional<Rescuer> assign(RescueRequest request, List<Rescuer> availableRescuers);

    /**
     * Tên chiến lược (dùng để hiển thị trên console)
     */
    String getStrategyName();
}

