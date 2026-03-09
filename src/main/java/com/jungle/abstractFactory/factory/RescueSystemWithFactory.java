package com.jungle.abstractFactory.factory;

import com.jungle.strategy.model.*;
import com.jungle.strategy.service.RescuerAssignmentStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Context class – Tích hợp cả 2 pattern:
 * - Giữ reference tới Strategy hiện tại (Strategy Pattern)
 * - Gọi RescueFactoryProvider để lấy Factory (Abstract Factory Pattern)
 */
public class RescueSystemWithFactory {

    private final List<RescueRequest> requests = new ArrayList<>();
    private final List<Rescuer> rescuers = new ArrayList<>();

    private RescuerAssignmentStrategy strategy; // ← Strategy Pattern
    private String disasterType; // ← Abstract Factory key

    public RescueSystemWithFactory(RescuerAssignmentStrategy strategy, String disasterType) {
        this.strategy = strategy;
        this.disasterType = disasterType;
    }

    // ─────────── STRATEGY PATTERN: thay đổi thuật toán tại runtime ───────────

    public void setStrategy(RescuerAssignmentStrategy strategy) {
        this.strategy = strategy;
        System.out.println("  >> Đã chuyển chiến lược: " + strategy.getStrategyName());
    }

    public String getCurrentStrategyName() {
        return strategy.getStrategyName();
    }

    // ─────────── ABSTRACT FACTORY: thay đổi "họ sản phẩm" tại runtime ───────────

    public void setDisasterType(String disasterType) {
        this.disasterType = disasterType;
        System.out.println("  >> Đã chuyển loại thảm họa: " + disasterType);
    }

    public String getDisasterType() {
        return disasterType;
    }

    // ─────────── QUẢN LÝ RESCUER & REQUEST ───────────

    public void addRescuer(Rescuer rescuer) {
        rescuers.add(rescuer);
    }

    public List<Rescuer> getAllRescuers() {
        return List.copyOf(rescuers);
    }

    public List<Rescuer> getAvailableRescuers() {
        return rescuers.stream().filter(Rescuer::isAvailable).toList();
    }

    public RescueRequest submitRequest(String victimName, String phone, String description,
            double lat, double lng, UrgencyLevel urgency) {
        RescueRequest request = new RescueRequest(victimName, phone, description, lat, lng, urgency);
        requests.add(request);
        System.out.println("\n  ✔ Yêu cầu cứu hộ #" + request.getId() + " đã được ghi nhận!");
        System.out.println("    Nạn nhân: " + victimName + " | Mức độ: " + urgency.getDisplayName());
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

    // ─────────── PHÂN CÔNG: nơi 2 pattern phối hợp ───────────

    /**
     * Phân công 1 yêu cầu cứu hộ:
     * 1) [Strategy Pattern] → strategy.assign() chọn rescuer tối ưu
     * 2) [Abstract Factory Pattern] → factory tạo RescueKit nhất quán
     */
    public RescueKit assignRescuer(RescueRequest request) {
        List<Rescuer> available = getAvailableRescuers();
        if (available.isEmpty()) {
            System.out.println("  ⚠ Không còn người cứu hộ sẵn sàng!");
            return null;
        }

        // ① Strategy Pattern: chọn rescuer
        System.out.println("\n  >> [Strategy] Đang dùng: " + strategy.getStrategyName());
        Optional<Rescuer> chosen = strategy.assign(request, available);

        if (chosen.isEmpty()) {
            System.out.println("  Không tìm được người cứu hộ phù hợp cho #" + request.getId());
            return null;
        }

        Rescuer rescuer = chosen.get();
        request.setAssignedRescuer(rescuer);
        request.setStatus(RequestStatus.ASSIGNED);
        rescuer.setStatus(RescuerStatus.BUSY);

        // ② Abstract Factory Pattern: tạo bộ công cụ cứu hộ
        RescueFactory factory = RescueFactoryProvider.getFactory(disasterType, request.getUrgencyLevel());
        RescueKit kit = RescueKit.from(factory);

        double distance = rescuer.distanceTo(request.getLatitude(), request.getLongitude());

        System.out.println("\n  ✔ PHÂN CÔNG THÀNH CÔNG!");
        System.out.printf("    Yêu cầu  : #%d (%s)%n", request.getId(), request.getVictimName());
        System.out.printf("    Người CH : %s [%s]%n", rescuer.getName(), rescuer.getSpecialty());
        System.out.printf("    Khoảng   : %.2f km%n", distance);
        System.out.println("\n  >> [Abstract Factory] Bộ cứu hộ được tạo:");
        kit.printKit();

        return kit;
    }

    /**
     * Phân công tất cả yêu cầu đang chờ.
     */
    public void assignAllPendingRequests() {
        List<RescueRequest> pending = getPendingRequests();
        if (pending.isEmpty()) {
            System.out.println("  >> Không có yêu cầu nào đang chờ phân công.");
            return;
        }

        System.out.println("  >> Bắt đầu phân công " + pending.size() + " yêu cầu...");
        int success = 0;
        for (RescueRequest req : pending) {
            if (getAvailableRescuers().isEmpty()) {
                System.out.println("  ⚠ Hết người cứu hộ sẵn sàng! Dừng phân công.");
                break;
            }
            if (assignRescuer(req) != null)
                success++;
        }
        System.out.printf("%n  >> Kết quả: Đã phân công %d/%d yêu cầu.%n", success, pending.size());
    }
}
