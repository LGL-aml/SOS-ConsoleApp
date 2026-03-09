package com.jungle.view;

import com.jungle.abstractFactory.factory.RescueSystemWithFactory;
import com.jungle.util.ConsoleUtil;

public class Menu {

    // ═══════════════════════════════════════════════════════════
    //  MENU & BANNER
    // ═══════════════════════════════════════════════════════════

    public static void printBanner() {
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║        HỆ THỐNG CỨU HỘ THẢM HỌA - SOS CONSOLE APP              ║");
        System.out.println("║     Strategy Pattern  +  Abstract Factory Pattern (Java)       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }

    public static void printMenu(RescueSystemWithFactory system) {
        String strategy = ConsoleUtil.padRight(system.getCurrentStrategyName(), 38);
        String disaster = ConsoleUtil.padRight(system.getDisasterType(), 38);
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

}
