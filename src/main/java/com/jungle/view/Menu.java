package com.jungle.view;

import com.jungle.abstractFactory.factory.RescueSystemWithFactory;

public class Menu {

    public static void printBanner() {
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║     🆘 HỆ THỐNG CỨU HỘ THẢM HỌA – SOS CONSOLE APP      ║");
        System.out.println("║     Strategy Pattern  +  Abstract Factory Pattern         ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }

    public static void printMenu(RescueSystemWithFactory system) {
        System.out.println();
        System.out.println("┌──────────────────────────────────────────────────────────┐");
        System.out.println("│                      MENU CHÍNH                          │");
        System.out.println("├──────────────────────────────────────────────────────────┤");
        System.out.println("│  1. Gửi yêu cầu cứu hộ                                 │");
        System.out.println("│  2. Phân công tất cả yêu cầu đang chờ                   │");
        System.out.println("├──────────────────────────────────────────────────────────┤");
        System.out.println("│  3. Đổi chiến lược phân công  (Strategy Pattern)        │");
        System.out.println("│  4. Đổi loại thảm họa         (Abstract Factory)        │");
        System.out.println("├──────────────────────────────────────────────────────────┤");
        System.out.println("│  5. Xem tất cả yêu cầu cứu hộ                          │");
        System.out.println("│  6. Xem danh sách người cứu hộ                          │");
        System.out.println("│  0. Thoát                                               │");
        System.out.println("├──────────────────────────────────────────────────────────┤");
        System.out.printf("│  Chiến lược: %-42s│%n", system.getCurrentStrategyName());
        System.out.printf("│  Thảm họa  : %-42s│%n", system.getDisasterType());
        System.out.println("└──────────────────────────────────────────────────────────┘");
        System.out.print(">> Chọn chức năng: ");
    }
}
