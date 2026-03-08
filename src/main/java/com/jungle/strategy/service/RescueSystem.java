package com.jungle.strategy.service;

import com.jungle.strategy.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Context class trong Strategy Pattern.
 * Quản lý danh sách yêu cầu cứu hộ, danh sách người cứu hộ,
 * và ủy quyền việc phân công cho strategy hiện tại.
 */
public class RescueSystem {

    private final List<RescueRequest> requests = new ArrayList<>();
    private final List<Rescuer> rescuers = new ArrayList<>();
    private RescuerAssignmentStrategy strategy;

    public RescueSystem(RescuerAssignmentStrategy strategy) {
        this.strategy = strategy;
    }

    // ======================== STRATEGY ========================

    public void setStrategy(RescuerAssignmentStrategy strategy) {
        this.strategy = strategy;
        System.out.println(">> Đã chuyển chiến lược sang: " + strategy.getStrategyName());
    }

    public String getCurrentStrategyName() {
        return strategy.getStrategyName();
    }

    // ======================== RESCUER ========================

    public void addRescuer(Rescuer rescuer) {
        rescuers.add(rescuer);
    }

    public List<Rescuer> getAllRescuers() {
        return List.copyOf(rescuers);
    }

    public List<Rescuer> getAvailableRescuers() {
        return rescuers.stream()
                .filter(Rescuer::isAvailable)
                .toList();
    }

    // ======================== REQUEST ========================

    /**
     * Nạn nhân gửi yêu cầu cứu hộ lên hệ thống.
     */
    public RescueRequest submitRequest(String victimName, String phone, String description,
                                       double lat, double lng, UrgencyLevel urgency) {
        RescueRequest request = new RescueRequest(victimName, phone, description, lat, lng, urgency);
        requests.add(request);
        System.out.println(">> Yêu cầu cứu hộ #" + request.getId() + " đã được ghi nhận!");
        System.out.println("   Nạn nhân: " + victimName + " | Mức độ: " + urgency.getDisplayName());
        return request;
    }

    public List<RescueRequest> getAllRequests() {
        return List.copyOf(requests);
    }

    public List<RescueRequest> getPendingRequests() {
        return requests.stream()
                .filter(r -> r.getStatus() == RequestStatus.PENDING)
                .toList();
    }

    // ======================== PHÂN CÔNG (dùng Strategy) ========================

    /**
     * Phân công 1 yêu cầu cứu hộ → dùng strategy hiện tại để chọn rescuer.
     */
    public void assignRescuer(RescueRequest request) {
        List<Rescuer> available = getAvailableRescuers();

        if (available.isEmpty()) {
            System.out.println(">> CẢNH BÁO: Không còn người cứu hộ nào sẵn sàng!");
            return;
        }

        System.out.println(">> Đang phân công bằng chiến lược: " + strategy.getStrategyName());

        Optional<Rescuer> chosen = strategy.assign(request, available);

        if (chosen.isPresent()) {
            Rescuer rescuer = chosen.get();

            // Cập nhật trạng thái
            request.setAssignedRescuer(rescuer);
            request.setStatus(RequestStatus.ASSIGNED);
            rescuer.setStatus(RescuerStatus.BUSY);

            double distance = rescuer.distanceTo(request.getLatitude(), request.getLongitude());

            System.out.println(">> ✔ PHÂN CÔNG THÀNH CÔNG!");
            System.out.println("   Yêu cầu #" + request.getId() + " (" + request.getVictimName() + ")");
            System.out.println("   → Người cứu hộ: " + rescuer.getName()
                    + " [" + rescuer.getSpecialty() + "]");
            System.out.printf("   → Khoảng cách: %.2f km%n", distance);
        } else {
            System.out.println(">> Không tìm được người cứu hộ phù hợp cho yêu cầu #" + request.getId());
        }
    }

    /**
     * Phân công tất cả yêu cầu đang chờ (PENDING).
     */
    public void assignAllPendingRequests() {
        List<RescueRequest> pending = getPendingRequests();
        if (pending.isEmpty()) {
            System.out.println(">> Không có yêu cầu nào đang chờ phân công.");
            return;
        }

        System.out.println(">> Bắt đầu phân công " + pending.size() + " yêu cầu...\n");
        int successCount = 0;

        for (RescueRequest request : pending) {
            List<Rescuer> available = getAvailableRescuers();
            if (available.isEmpty()) {
                System.out.println(">> Hết người cứu hộ sẵn sàng! Dừng phân công.");
                break;
            }

            Optional<Rescuer> chosen = strategy.assign(request, available);
            if (chosen.isPresent()) {
                Rescuer rescuer = chosen.get();
                request.setAssignedRescuer(rescuer);
                request.setStatus(RequestStatus.ASSIGNED);
                rescuer.setStatus(RescuerStatus.BUSY);

                double distance = rescuer.distanceTo(request.getLatitude(), request.getLongitude());
                System.out.printf("   ✔ #%d (%s) → %s [%s] - %.2f km%n",
                        request.getId(), request.getVictimName(),
                        rescuer.getName(), rescuer.getSpecialty(), distance);
                successCount++;
            } else {
                System.out.println("   ✘ #" + request.getId() + " - Không tìm được người phù hợp");
            }
        }

        System.out.println("\n>> Kết quả: Đã phân công " + successCount + "/" + pending.size() + " yêu cầu.");
    }

    // ======================== HOÀN THÀNH NHIỆM VỤ ========================

    /**
     * Hoàn thành nhiệm vụ cứu hộ → giải phóng rescuer.
     */
    public void completeRequest(int requestId) {
        Optional<RescueRequest> found = requests.stream()
                .filter(r -> r.getId() == requestId)
                .findFirst();

        if (found.isEmpty()) {
            System.out.println(">> Không tìm thấy yêu cầu #" + requestId);
            return;
        }

        RescueRequest request = found.get();

        if (request.getStatus() != RequestStatus.ASSIGNED) {
            System.out.println(">> Yêu cầu #" + requestId + " không ở trạng thái ASSIGNED (hiện tại: "
                    + request.getStatus().getDisplayName() + ")");
            return;
        }

        Rescuer rescuer = request.getAssignedRescuer();
        request.setStatus(RequestStatus.COMPLETED);
        if (rescuer != null) {
            rescuer.setStatus(RescuerStatus.AVAILABLE);
            rescuer.incrementCompletedMissions();
            System.out.println(">> ✔ Hoàn thành yêu cầu #" + requestId
                    + " | Người cứu hộ " + rescuer.getName() + " đã sẵn sàng nhận nhiệm vụ mới.");
        }
    }
}

