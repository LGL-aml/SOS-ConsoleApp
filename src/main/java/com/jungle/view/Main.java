package com.jungle.view;

import com.jungle.abstractFactory.factory.*;
import com.jungle.strategy.model.*;
import com.jungle.strategy.service.*;
import com.jungle.util.ConsoleUtil;
import com.jungle.util.DataInit;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static RescueSystemWithFactory system;

    public static void main(String[] args) {
        system = new RescueSystemWithFactory(
                new NearestRescuerStrategy(),
                RescueFactoryProvider.FLOOD
        );

        DataInit.initSampleRescuers(system);
        Menu.printBanner();

        boolean running = true;
        while (running) {
            Menu.printMenu(system);
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> submitRescueRequest();
                case "2" -> assignPendingRequests();
                case "3" -> assignSingleRequest();
                case "4" -> changeStrategy();
                case "5" -> changeDisasterType();
                case "6" -> viewAllRequests();
                case "7" -> viewPendingRequests();
                case "8" -> viewAllRescuers();
                case "9" -> completeRequest();
                case "0" -> {
                    System.out.println("\n  Cảm ơn bạn đã sử dụng hệ thống SOS. Tạm biệt!");
                    running = false;
                }
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
        scanner.close();
    }

    // ═══════════════════════════════════════════════════════════
    //  1. GỬI YÊU CẦU CỨU HỘ
    // ═══════════════════════════════════════════════════════════

    private static void submitRescueRequest() {
        System.out.println("\n╔══ GỬI YÊU CẦU CỨU HỘ ══════════════════════════════════════╗");

        System.out.print("  Họ tên nạn nhân   : ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Tên không được để trống!"); return; }

        System.out.print("  Số điện thoại      : ");
        String phone = scanner.nextLine().trim();

        System.out.print("  Mô tả tình huống   : ");
        String desc = scanner.nextLine().trim();

        double lat = ConsoleUtil.readDouble(scanner, "  Vĩ độ  (VD: 16.0470): ");
        double lng = ConsoleUtil.readDouble(scanner, "  Kinh độ (VD: 108.206): ");

        UrgencyLevel urgency = selectUrgencyLevel();

        ConsoleUtil.printFooter();
        system.submitRequest(name, phone, desc, lat, lng, urgency);
    }

    private static UrgencyLevel selectUrgencyLevel() {
        System.out.println("  Mức độ khẩn cấp:");
        System.out.println("    1 - Thấp       (LOW)");
        System.out.println("    2 - Trung bình (MEDIUM)");
        System.out.println("    3 - Cao        (HIGH)");
        System.out.println("    4 - Nguy kịch  (CRITICAL)");
        System.out.print("  Chọn mức độ (1-4): ");
        return switch (scanner.nextLine().trim()) {
            case "1" -> UrgencyLevel.LOW;
            case "3" -> UrgencyLevel.HIGH;
            case "4" -> UrgencyLevel.CRITICAL;
            default  -> { System.out.println("  >> Mặc định: MEDIUM"); yield UrgencyLevel.MEDIUM; }
        };
    }

    // ═══════════════════════════════════════════════════════════
    //  2. PHÂN CÔNG TẤT CẢ YÊU CẦU ĐANG CHỜ
    // ═══════════════════════════════════════════════════════════

    private static void assignPendingRequests() {
        System.out.println("\n╔══ PHÂN CÔNG TẤT CẢ YÊU CẦU ĐANG CHỜ ══════════════════════╗");
        List<RescueRequest> pending = system.getPendingRequests();
        if (pending.isEmpty()) {
            System.out.println("  >> Không có yêu cầu nào đang chờ phân công.");
            ConsoleUtil.printFooter();
            return;
        }
        System.out.println("  >> Có " + pending.size() + " yêu cầu đang chờ. Tiến hành phân công...");
        ConsoleUtil.printFooter();
        system.assignAllPendingRequests();
    }

    // ═══════════════════════════════════════════════════════════
    //  3. PHÂN CÔNG 1 YÊU CẦU THEO ID
    // ═══════════════════════════════════════════════════════════

    private static void assignSingleRequest() {
        System.out.println("\n╔══ PHÂN CÔNG YÊU CẦU THEO ID ═══════════════════════════════╗");
        List<RescueRequest> pending = system.getPendingRequests();
        if (pending.isEmpty()) {
            System.out.println("  >> Không có yêu cầu nào đang chờ phân công.");
            ConsoleUtil.printFooter();
            return;
        }

        System.out.println("  Danh sách yêu cầu đang chờ:");
        pending.forEach(r -> System.out.printf("    [#%d] %s - %s - Mức: %s%n",
                r.getId(), r.getVictimName(), r.getDescription(), r.getUrgencyLevel().getDisplayName()));

        System.out.print("\n  Nhập ID yêu cầu cần phân công: ");
        int id = ConsoleUtil.readInt(scanner);
        ConsoleUtil.printFooter();

        Optional<RescueRequest> target = system.findRequestById(id);
        if (target.isPresent() && target.get().getStatus() == RequestStatus.PENDING) {
            system.assignRescuer(target.get());
        } else {
            System.out.println("  ⚠️  Không tìm thấy yêu cầu #" + id + " trong danh sách chờ.");
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  4. ĐỔI CHIẾN LƯỢC PHÂN CÔNG (Strategy Pattern)
    // ═══════════════════════════════════════════════════════════

    private static void changeStrategy() {
        System.out.println("\n╔══ ĐỔI CHIẾN LƯỢC PHÂN CÔNG (Strategy Pattern) ════════════╗");
        System.out.println("  Chiến lược hiện tại: " + system.getCurrentStrategyName());
        System.out.println();
        System.out.println("  1. Người cứu hộ gần nhất   (NearestRescuerStrategy)");
        System.out.println("     → Chọn người có khoảng cách ngắn nhất đến nạn nhân.");
        System.out.println();
        System.out.println("  2. Ưu tiên mức độ khẩn cấp (UrgencyBasedStrategy)");
        System.out.println("     → CRITICAL: người kinh nghiệm nhất; HIGH: cân bằng KN+khoảng cách.");
        System.out.println();
        System.out.println("  3. Luân phiên cân bằng tải  (RoundRobinStrategy)");
        System.out.println("     → Phân phối đều nhiệm vụ cho tất cả người cứu hộ.");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        System.out.print(">> Chọn chiến lược (1-3): ");

        switch (scanner.nextLine().trim()) {
            case "1" -> system.setStrategy(new NearestRescuerStrategy());
            case "2" -> system.setStrategy(new UrgencyBasedStrategy());
            case "3" -> system.setStrategy(new RoundRobinStrategy());
            default  -> System.out.println("  ⚠️  Lựa chọn không hợp lệ.");
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  5. ĐỔI LOẠI THẢM HỌA (Abstract Factory Pattern)
    // ═══════════════════════════════════════════════════════════

    private static void changeDisasterType() {
        System.out.println("\n╔══ ĐỔI LOẠI THẢM HỌA (Abstract Factory Pattern) ═══════════╗");
        System.out.println("  Loại thảm họa hiện tại: " + system.getDisasterType());
        System.out.println();
        System.out.println("  1. 🌊 Lũ Lụt   → Bộ cứu hộ: xuồng, đồ lặn, đội cứu hộ nước.");
        System.out.println("     (FloodCriticalFactory / FloodHighFactory / ...)");
        System.out.println();
        System.out.println("  2. 🔥 Hỏa Hoạn → Bộ cứu hộ: xe cứu hỏa, đồ chống cháy, đội chữa cháy.");
        System.out.println("     (FireCriticalFactory / FireHighFactory / ...)");
        System.out.println();
        System.out.println("  ℹ️  Factory cụ thể được chọn tự động theo mức độ khẩn cấp của từng yêu cầu.");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        System.out.print(">> Chọn loại thảm họa (1-2): ");

        switch (scanner.nextLine().trim()) {
            case "1" -> system.setDisasterType(RescueFactoryProvider.FLOOD);
            case "2" -> system.setDisasterType(RescueFactoryProvider.FIRE);
            default  -> System.out.println("  ⚠️  Lựa chọn không hợp lệ.");
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  6. XEM TẤT CẢ YÊU CẦU
    // ═══════════════════════════════════════════════════════════

    private static void viewAllRequests() {
        System.out.println("\n╔══ DANH SÁCH TẤT CẢ YÊU CẦU CỨU HỘ ═══════════════════════╗");
        List<RescueRequest> all = system.getAllRequests();
        if (all.isEmpty()) {
            System.out.println("  >> Chưa có yêu cầu nào.");
        } else {
            all.forEach(r -> {
                System.out.println(r);
                ConsoleUtil.printSeparator();
            });
            long pending   = all.stream().filter(r -> r.getStatus() == RequestStatus.PENDING).count();
            long assigned  = all.stream().filter(r -> r.getStatus() == RequestStatus.ASSIGNED).count();
            long completed = all.stream().filter(r -> r.getStatus() == RequestStatus.COMPLETED).count();
            System.out.printf("  >> Tổng: %d | Chờ: %d | Đã phân công: %d | Hoàn thành: %d%n",
                    all.size(), pending, assigned, completed);
        }
        ConsoleUtil.printFooter();
    }

    // ═══════════════════════════════════════════════════════════
    //  7. XEM YÊU CẦU ĐANG CHỜ
    // ═══════════════════════════════════════════════════════════

    private static void viewPendingRequests() {
        System.out.println("\n╔══ YÊU CẦU ĐANG CHỜ PHÂN CÔNG ═════════════════════════════╗");
        List<RescueRequest> pending = system.getPendingRequests();
        if (pending.isEmpty()) {
            System.out.println("  >> Không có yêu cầu nào đang chờ.");
        } else {
            pending.forEach(r -> {
                System.out.println(r);
                ConsoleUtil.printSeparator();
            });
            System.out.println("  >> Tổng: " + pending.size() + " yêu cầu đang chờ");
        }
        ConsoleUtil.printFooter();
    }

    // ═══════════════════════════════════════════════════════════
    //  8. XEM DANH SÁCH NGƯỜI CỨU HỘ
    // ═══════════════════════════════════════════════════════════

    private static void viewAllRescuers() {
        System.out.println("\n╔══ DANH SÁCH NGƯỜI CỨU HỘ ══════════════════════════════════╗");
        List<Rescuer> all = system.getAllRescuers();
        if (all.isEmpty()) {
            System.out.println("  >> Chưa có người cứu hộ nào.");
        } else {
            all.forEach(System.out::println);
            long available = all.stream().filter(Rescuer::isAvailable).count();
            System.out.printf("%n  >> Tổng: %d | 🟢 Sẵn sàng: %d | Đang bận: %d%n",
                    all.size(), available, all.size() - available);
        }
        ConsoleUtil.printFooter();
    }

    // ═══════════════════════════════════════════════════════════
    //  9. HOÀN THÀNH NHIỆM VỤ
    // ═══════════════════════════════════════════════════════════

    private static void completeRequest() {
        System.out.println("\n╔══ HOÀN THÀNH NHIỆM VỤ CỨU HỘ ═════════════════════════════╗");
        List<RescueRequest> assigned = system.getAllRequests().stream()
                .filter(r -> r.getStatus() == RequestStatus.ASSIGNED)
                .toList();

        if (assigned.isEmpty()) {
            System.out.println("  >> Không có nhiệm vụ nào đang được thực hiện.");
            ConsoleUtil.printFooter();
            return;
        }

        System.out.println("  Nhiệm vụ đang thực hiện:");
        assigned.forEach(r -> System.out.printf("    [#%d] %s → Người CH: %s%n",
                r.getId(), r.getVictimName(),
                r.getAssignedRescuer() != null ? r.getAssignedRescuer().getName() : "N/A"));

        System.out.print("\n  Nhập ID yêu cầu đã hoàn thành: ");
        int id = ConsoleUtil.readInt(scanner);
        ConsoleUtil.printFooter();
        system.completeRequest(id);
    }

}

