package com.jungle.view;

import com.jungle.abstractFactory.factory.*;
import com.jungle.strategy.model.*;
import com.jungle.strategy.service.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Main class – Điểm vào ứng dụng console.
 * Demo tích hợp Strategy Pattern + Abstract Factory Pattern.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static RescueSystemWithFactory system;

    public static void main(String[] args) {
        // Khởi tạo hệ thống với Strategy mặc định + loại thảm họa mặc định
        system = new RescueSystemWithFactory(
                new NearestRescuerStrategy(), // ← Strategy mặc định
                RescueFactoryProvider.FLOOD // ← Factory family mặc định
        );

        // Tải dữ liệu mẫu
        initSampleData();

        Menu.printBanner();

        boolean running = true;
        while (running) {
            Menu.printMenu(system);
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> submitRescueRequest();
                case "2" -> assignAllPending();
                case "3" -> changeStrategy();
                case "4" -> changeDisasterType();
                case "5" -> viewAllRequests();
                case "6" -> viewAllRescuers();
                case "0" -> {
                    System.out.println("\n  Cảm ơn bạn đã sử dụng hệ thống SOS. Tạm biệt!");
                    running = false;
                }
                default -> System.out.println("  Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
        scanner.close();
    }

    // ═══════════════════ DỮ LIỆU MẪU ═══════════════════

    private static void initSampleData() {
        system.addRescuer(new Rescuer("Nguyễn Văn An", "0901000001", "Lặn", 16.0544, 108.2022));
        system.addRescuer(new Rescuer("Trần Thị Bình", "0901000002", "Y tế", 16.0620, 108.2150));
        system.addRescuer(new Rescuer("Lê Hoàng Cường", "0901000003", "Cứu hộ chung", 16.0400, 108.1900));
        System.out.println(">> Đã tải " + system.getAllRescuers().size() + " người cứu hộ mẫu.\n");
    }

    // ═══════════════════ 1. GỬI YÊU CẦU CỨU HỘ ═══════════════════

    private static void submitRescueRequest() {
        System.out.println("\n╔══ GỬI YÊU CẦU CỨU HỘ ══════════════════════════════════╗");

        System.out.print("  Họ tên nạn nhân : ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("  Tên không được để trống!");
            return;
        }

        System.out.print("  Mô tả tình huống: ");
        String desc = scanner.nextLine().trim();

        System.out.print("  Vĩ độ  (VD: 16.047) : ");
        double lat = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("  Kinh độ (VD: 108.20): ");
        double lng = Double.parseDouble(scanner.nextLine().trim());

        // Chọn mức độ khẩn cấp
        System.out.println("  Mức độ khẩn cấp:");
        System.out.println("    1 - Thấp      (LOW)");
        System.out.println("    2 - Nguy kịch (CRITICAL)");
        System.out.print("  Chọn (1-2): ");
        UrgencyLevel urgency = switch (scanner.nextLine().trim()) {
            case "2" -> UrgencyLevel.CRITICAL;
            default -> UrgencyLevel.LOW;
        };

        system.submitRequest(name, "N/A", desc, lat, lng, urgency);
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }

    // ═══════════════════ 2. PHÂN CÔNG TẤT CẢ YÊU CẦU ĐANG CHỜ ═══════════════════

    private static void assignAllPending() {
        System.out.println("\n╔══ PHÂN CÔNG TẤT CẢ YÊU CẦU ĐANG CHỜ ═══════════════════╗");
        system.assignAllPendingRequests();
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }

    // ═══════════════════ 3. ĐỔI CHIẾN LƯỢC (Strategy Pattern) ═══════════════════

    private static void changeStrategy() {
        System.out.println("\n╔══ ĐỔI CHIẾN LƯỢC (Strategy Pattern) ════════════════════╗");
        System.out.println("  Chiến lược hiện tại: " + system.getCurrentStrategyName());
        System.out.println();
        System.out.println("  1. Gần nhất    (NearestRescuerStrategy)");
        System.out.println("     → Chọn người cứu hộ có khoảng cách ngắn nhất.");
        System.out.println();
        System.out.println("  2. Theo khẩn cấp (UrgencyBasedStrategy)");
        System.out.println("     → CRITICAL: người kinh nghiệm nhất; LOW: gần nhất.");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.print(">> Chọn chiến lược (1-2): ");

        switch (scanner.nextLine().trim()) {
            case "1" -> system.setStrategy(new NearestRescuerStrategy());
            case "2" -> system.setStrategy(new UrgencyBasedStrategy());
            default -> System.out.println("  Lựa chọn không hợp lệ.");
        }
    }

    // ═══════════════════ 4. ĐỔI LOẠI THẢM HỌA (Abstract Factory)
    // ═══════════════════

    private static void changeDisasterType() {
        System.out.println("\n╔══ ĐỔI LOẠI THẢM HỌA (Abstract Factory Pattern) ═════════╗");
        System.out.println("  Loại thảm họa hiện tại: " + system.getDisasterType());
        System.out.println();
        System.out.println("  1. 🌊 Lũ Lụt  → Bộ cứu hộ nước (xuồng, đồ lặn...)");
        System.out.println("  2. 🔥 Hỏa Hoạn → Bộ chữa cháy (xe cứu hỏa, đồ chống cháy...)");
        System.out.println();
        System.out.println("  ℹ️  Factory cụ thể tự động chọn theo mức khẩn cấp từng yêu cầu.");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.print(">> Chọn loại thảm họa (1-2): ");

        switch (scanner.nextLine().trim()) {
            case "1" -> system.setDisasterType(RescueFactoryProvider.FLOOD);
            case "2" -> system.setDisasterType(RescueFactoryProvider.FIRE);
            default -> System.out.println("  Lựa chọn không hợp lệ.");
        }
    }

    // ═══════════════════ 5. XEM TẤT CẢ YÊU CẦU ═══════════════════

    private static void viewAllRequests() {
        System.out.println("\n╔══ DANH SÁCH YÊU CẦU CỨU HỘ ═════════════════════════════╗");
        List<RescueRequest> all = system.getAllRequests();
        if (all.isEmpty()) {
            System.out.println("  >> Chưa có yêu cầu nào.");
        } else {
            all.forEach(System.out::println);
        }
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }

    // ═══════════════════ 6. XEM DANH SÁCH NGƯỜI CỨU HỘ ═══════════════════

    private static void viewAllRescuers() {
        System.out.println("\n╔══ DANH SÁCH NGƯỜI CỨU HỘ ════════════════════════════════╗");
        List<Rescuer> all = system.getAllRescuers();
        all.forEach(System.out::println);
        long available = all.stream().filter(Rescuer::isAvailable).count();
        System.out.printf("  >> Tổng: %d | Sẵn sàng: %d | Đang bận: %d%n",
                all.size(), available, all.size() - available);
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
}
