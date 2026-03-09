package com.jungle.abstractFactory.factory;

import com.jungle.strategy.model.*;
import com.jungle.strategy.service.RescuerAssignmentStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class RescueSystemWithFactory {

    private final List<RescueRequest> requests = new ArrayList<>();
    private final List<Rescuer>       rescuers = new ArrayList<>();

    private RescuerAssignmentStrategy strategy;
    private String disasterType;

    public RescueSystemWithFactory(RescuerAssignmentStrategy strategy, String disasterType) {
        this.strategy     = strategy;
        this.disasterType = disasterType;
    }

    // ─────────────────────── STRATEGY ───────────────────────

    public void setStrategy(RescuerAssignmentStrategy strategy) {
        this.strategy = strategy;
        System.out.println("  >> Đã chuyển chiến lược: " + strategy.getStrategyName());
    }

    public String getCurrentStrategyName() { return strategy.getStrategyName(); }

    // ─────────────────────── DISASTER TYPE ───────────────────────

    public void setDisasterType(String disasterType) {
        this.disasterType = disasterType;
        System.out.println("  >> Đã chuyển loại thảm họa: " + disasterType);
    }

    public String getDisasterType() { return disasterType; }

    // ─────────────────────── RESCUER ───────────────────────

    public void addRescuer(Rescuer rescuer) { rescuers.add(rescuer); }

    public List<Rescuer> getAllRescuers() { return List.copyOf(rescuers); }

    public List<Rescuer> getAvailableRescuers() {
        return rescuers.stream().filter(Rescuer::isAvailable).toList();
    }

    // ─────────────────────── REQUEST ───────────────────────

    public RescueRequest submitRequest(String victimName, String phone, String description,
                                       double lat, double lng, UrgencyLevel urgency) {
        RescueRequest request = new RescueRequest(victimName, phone, description, lat, lng, urgency);
        requests.add(request);
        System.out.println("\n Yêu cầu cứu hộ #" + request.getId() + " đã được ghi nhận!");
        System.out.println("     Nạn nhân  : " + victimName);
        System.out.println("     Mức độ    : " + urgency.getDisplayName());
        System.out.println("     Thảm họa  : " + disasterType);
        System.out.println("     Vị trí    : (" + lat + ", " + lng + ")");
        return request;
    }

    public List<RescueRequest> getAllRequests()     { return List.copyOf(requests); }

    public List<RescueRequest> getPendingRequests() {
        return requests.stream()
                .filter(r -> r.getStatus() == RequestStatus.PENDING)
                .toList();
    }

    public Optional<RescueRequest> findRequestById(int id) {
        return requests.stream().filter(r -> r.getId() == id).findFirst();
    }

    // ─────────────────────── PHÂN CÔNG ───────────────────────
    public RescueKit assignRescuer(RescueRequest request) {
        List<Rescuer> available = getAvailableRescuers();

        if (available.isEmpty()) {
            System.out.println("\n  CẢNH BÁO: Không còn người cứu hộ nào sẵn sàng!");
            return null;
        }

        System.out.println("\n  >> Đang phân công bằng chiến lược: " + strategy.getStrategyName());

        Optional<Rescuer> chosen = strategy.assign(request, available);
        if (chosen.isEmpty()) {
            System.out.println("  Không tìm được người cứu hộ phù hợp cho yêu cầu #" + request.getId());
            return null;
        }

        Rescuer rescuer = chosen.get();
        request.setAssignedRescuer(rescuer);
        request.setStatus(RequestStatus.ASSIGNED);
        rescuer.setStatus(RescuerStatus.BUSY);

        double distance = rescuer.distanceTo(request.getLatitude(), request.getLongitude());

        // Abstract Factory: lấy đúng factory theo (disasterType + urgencyLevel)
        // Mỗi Concrete Factory tạo ra họ sản phẩm nhất quán - factory method KHÔNG nhận parameter
        RescueFactory factory = RescueFactoryProvider.getFactory(disasterType, request.getUrgencyLevel());
        RescueKit     kit     = RescueKit.from(factory);

        System.out.println("\n  ✔  PHÂN CÔNG THÀNH CÔNG!");
        System.out.println("     Yêu cầu  : #" + request.getId() + " (" + request.getVictimName() + ")");
        System.out.printf ("     Người CH : %s [%s]%n", rescuer.getName(), rescuer.getSpecialty());
        System.out.printf ("     Khoảng   : %.2f km%n", distance);
        System.out.println("\n Bộ công cụ cứu hộ được chuẩn bị:");
        kit.printKit();

        return kit;
    }

    /**
     * Phân công tất cả yêu cầu đang chờ (PENDING).
     */
    public void assignAllPendingRequests() {
        List<RescueRequest> pending = getPendingRequests();
        if (pending.isEmpty()) {
            System.out.println("  >> Không có yêu cầu nào đang chờ phân công.");
            return;
        }

        System.out.println("\n  >> Bắt đầu phân công " + pending.size() + " yêu cầu đang chờ...");
        int success = 0;

        for (RescueRequest req : pending) {
            if (getAvailableRescuers().isEmpty()) {
                System.out.println(" Hết người cứu hộ sẵn sàng! Dừng phân công.");
                break;
            }
            RescueKit kit = assignRescuer(req);
            if (kit != null) success++;
        }

        System.out.printf("%n  >> Kết quả: Đã phân công %d/%d yêu cầu.%n", success, pending.size());
    }

    // ─────────────────────── HOÀN THÀNH ───────────────────────

    public void completeRequest(int requestId) {
        Optional<RescueRequest> found = findRequestById(requestId);
        if (found.isEmpty()) {
            System.out.println("  >> Không tìm thấy yêu cầu #" + requestId);
            return;
        }

        RescueRequest request = found.get();
        if (request.getStatus() != RequestStatus.ASSIGNED) {
            System.out.println("  >> Yêu cầu #" + requestId + " chưa được phân công (trạng thái: "
                    + request.getStatus().getDisplayName() + ")");
            return;
        }

        Rescuer rescuer = request.getAssignedRescuer();
        request.setStatus(RequestStatus.COMPLETED);
        if (rescuer != null) {
            rescuer.setStatus(RescuerStatus.AVAILABLE);
            rescuer.incrementCompletedMissions();
            System.out.printf("  ✔ Hoàn thành yêu cầu #%d | Người cứu hộ %s đã sẵn sàng nhận nhiệm vụ mới.%n",
                    requestId, rescuer.getName());
        }
    }
}
