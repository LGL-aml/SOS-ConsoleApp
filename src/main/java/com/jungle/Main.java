package com.jungle;

import com.jungle.abstractFactory.factory.*;
import com.jungle.strategy.model.*;
import com.jungle.strategy.service.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static RescueSystemWithFactory system;

    public static void main(String[] args) {
        // Mặc định: Lũ lụt + chiến lược Gần nhất
        system = new RescueSystemWithFactory(
                new NearestRescuerStrategy(),
                RescueFactoryProvider.FLOOD
        );

        initSampleRescuers();
        printBanner();

        boolean running = true;
        while (running) {
            printMenu();
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
                    System.out.println("\n  Cảm ơn bạn đã sử dụng hệ thống SOS. Tạm biệt! 👋");
                    running = false;
                }
                default -> System.out.println("  ⚠️  Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
        scanner.close();
    }

    // ═══════════════════════════════════════════════════════════
    //  MENU & BANNER
    // ═══════════════════════════════════════════════════════════

    private static void printBanner() {
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║        HỆ THỐNG CỨU HỘ THẢM HỌA - SOS CONSOLE APP              ║");
        System.out.println("║     Strategy Pattern  +  Abstract Factory Pattern (Java)       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }

    private static void printMenu() {
        String strategy = padRight(system.getCurrentStrategyName(), 38);
        String disaster = padRight(system.getDisasterType(), 38);
        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────────────────┐");
        System.out.println("│                         MENU CHÍNH                             │");
        System.out.println("├────────────────────────────────────────────────────────────────┤");
        System.out.println("│  [CHỨC NĂNG NẠN NHÂN]                                          │");
        System.out.println("│   1. Gửi yêu cầu cứu hộ                                        │");
        System.out.println("├────────────────────────────────────────────────────────────────┤");
        System.out.println("│  [CHỨC NĂNG HỆ THỐNG - PHÂN CÔNG]                              │");
        System.out.println("│   2. Phân công tất cả yêu cầu đang chờ                         │");
        System.out.println("│   3. Phân công 1 yêu cầu theo ID                               │");
        System.out.println("├────────────────────────────────────────────────────────────────┤");
        System.out.println("│  [CẤU HÌNH PATTERN]                                            │");
        System.out.println("│   4. Đổi chiến lược phân công  (Strategy Pattern)              │");
        System.out.println("│   5. Đổi loại thảm họa         (Abstract Factory Pattern)      │");
        System.out.println("├────────────────────────────────────────────────────────────────┤");
        System.out.println("│  [XEM THÔNG TIN]                                               │");
        System.out.println("│   6. Xem tất cả yêu cầu cứu hộ                                 │");
        System.out.println("│   7. Xem yêu cầu đang chờ phân công                            │");
        System.out.println("│   8. Xem danh sách người cứu hộ                                │");
        System.out.println("│   9. Hoàn thành nhiệm vụ cứu hộ                                │");
        System.out.println("│   0. Thoát                                                     │");
        System.out.println("├────────────────────────────────────────────────────────────────┤");
        System.out.printf ("│  Chiến lược : %s          │%n", strategy);
        System.out.printf ("│  Thảm họa   : %s           │%n", disaster);
        System.out.println("└────────────────────────────────────────────────────────────────┘");
        System.out.print(">> Chọn chức năng: ");
    }

    // ═══════════════════════════════════════════════════════════
    //  1. GỬI YÊU CẦU CỨU HỘ
    // ═══════════════════════════════════════════════════════════

    private static void submitRescueRequest() {
        System.out.println("\n╔══ GỬI YÊU CẦU CỨU HỘ ══════════════════════════════════════╗");

        System.out.print("  Họ tên nạn nhân   : ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) { System.out.println("  ⚠️  Tên không được để trống!"); return; }

        System.out.print("  Số điện thoại      : ");
        String phone = scanner.nextLine().trim();

        System.out.print("  Mô tả tình huống   : ");
        String desc = scanner.nextLine().trim();

        double lat = readDouble("  Vĩ độ  (VD: 16.0470): ");
        double lng = readDouble("  Kinh độ (VD: 108.206): ");

        UrgencyLevel urgency = selectUrgencyLevel();

        System.out.println("╚════════════════════════════════════════════════════════════════╝");
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
            System.out.println("╚════════════════════════════════════════════════════════════════╝");
            return;
        }
        System.out.println("  >> Có " + pending.size() + " yêu cầu đang chờ. Tiến hành phân công...");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
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
            System.out.println("╚════════════════════════════════════════════════════════════════╝");
            return;
        }

        System.out.println("  Danh sách yêu cầu đang chờ:");
        pending.forEach(r -> System.out.printf("    [#%d] %s - %s - Mức: %s%n",
                r.getId(), r.getVictimName(), r.getDescription(), r.getUrgencyLevel().getDisplayName()));

        System.out.print("\n  Nhập ID yêu cầu cần phân công: ");
        int id = readInt();
        System.out.println("╚════════════════════════════════════════════════════════════════╝");

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
                System.out.println("  ──────────────────────────────────────────────────────────");
            });
            long pending   = all.stream().filter(r -> r.getStatus() == RequestStatus.PENDING).count();
            long assigned  = all.stream().filter(r -> r.getStatus() == RequestStatus.ASSIGNED).count();
            long completed = all.stream().filter(r -> r.getStatus() == RequestStatus.COMPLETED).count();
            System.out.printf("  >> Tổng: %d | Chờ: %d | Đã phân công: %d | Hoàn thành: %d%n",
                    all.size(), pending, assigned, completed);
        }
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
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
                System.out.println("  ──────────────────────────────────────────────────────────");
            });
            System.out.println("  >> Tổng: " + pending.size() + " yêu cầu đang chờ");
        }
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
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
            System.out.printf("%n  >> Tổng: %d | 🟢 Sẵn sàng: %d | 🔴 Đang bận: %d%n",
                    all.size(), available, all.size() - available);
        }
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
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
            System.out.println("╚════════════════════════════════════════════════════════════════╝");
            return;
        }

        System.out.println("  Nhiệm vụ đang thực hiện:");
        assigned.forEach(r -> System.out.printf("    [#%d] %s → Người CH: %s%n",
                r.getId(), r.getVictimName(),
                r.getAssignedRescuer() != null ? r.getAssignedRescuer().getName() : "N/A"));

        System.out.print("\n  Nhập ID yêu cầu đã hoàn thành: ");
        int id = readInt();
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        system.completeRequest(id);
    }

    // ═══════════════════════════════════════════════════════════
    //  DỮ LIỆU MẪU
    // ═══════════════════════════════════════════════════════════

    private static void initSampleRescuers() {
        system.addRescuer(new Rescuer("Nguyễn Văn An",  "0901000001", "Lặn",          16.0544, 108.2022));
        system.addRescuer(new Rescuer("Trần Thị Bình",  "0901000002", "Y tế",          16.0620, 108.2150));
        system.addRescuer(new Rescuer("Lê Hoàng Cường", "0901000003", "Cứu hộ chung", 16.0400, 108.1900));
        system.addRescuer(new Rescuer("Phạm Minh Đức",  "0901000004", "Lặn",          16.4637, 107.5909));
        system.addRescuer(new Rescuer("Hoàng Thị Em",   "0901000005", "Y tế",          15.8794, 108.3350));
        system.addRescuer(new Rescuer("Võ Thanh Phong", "0901000006", "Cứu hộ chung", 16.0750, 108.2240));
        System.out.println(">> Đã tải " + system.getAllRescuers().size() + " người cứu hộ vào hệ thống.");
    }

    // ═══════════════════════════════════════════════════════════
    //  HELPER
    // ═══════════════════════════════════════════════════════════

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  ⚠️  Vui lòng nhập số thực hợp lệ (VD: 16.0470).");
            }
        }
    }

    private static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("  ⚠️  Vui lòng nhập số nguyên hợp lệ: ");
            }
        }
    }

    private static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
}

