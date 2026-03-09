package com.jungle.util;

import java.util.Scanner;


public final class ConsoleUtil {

    private ConsoleUtil() {}

    // ─────────────────────── INPUT ───────────────────────
    public static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Vui lòng nhập số thực hợp lệ (VD: 16.0470).");
            }
        }
    }

    public static int readInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("  Vui lòng nhập số nguyên hợp lệ: ");
            }
        }
    }

    // ─────────────────────── FORMAT ───────────────────────

    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    // ─────────────────────── SEPARATOR ───────────────────────

    public static void printSeparator() {
        System.out.println("  ──────────────────────────────────────────────────────────");
    }

    public static void printFooter() {
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}

