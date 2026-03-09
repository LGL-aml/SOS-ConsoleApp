package com.jungle.util;

import java.util.Scanner;

public final class LocationUtil {

    private LocationUtil() {}

    public enum Area {
        HAI_CHAU        ("Quận Hải Châu",       16.0544, 108.2022),
        THANH_KHE       ("Quận Thanh Khê",      16.0700, 108.1800),
        SON_TRA         ("Quận Sơn Trà",        16.0878, 108.2470),
        NGU_HANH_SON    ("Quận Ngũ Hành Sơn",  15.9897, 108.2539),
        CAM_LE          ("Quận Cẩm Lệ",         16.0200, 108.1900),
        HOA_VANG        ("Huyện Hòa Vang",      15.9700, 108.1400),
        HOA_LIEN        ("Xã Hòa Liên",         16.0800, 108.1200),
        HOA_PHUOC       ("Xã Hòa Phước",        15.9400, 108.2100);

        public final String displayName;
        public final double lat;
        public final double lng;

        Area(String displayName, double lat, double lng) {
            this.displayName = displayName;
            this.lat = lat;
            this.lng = lng;
        }
    }


    public static Area selectArea(Scanner scanner) {
        Area[] areas = Area.values();
        System.out.println("  Chọn khu vực của bạn:");
        for (int i = 0; i < areas.length; i++) {
            System.out.printf("    %d - %-22s (%.4f, %.4f)%n",
                    i + 1, areas[i].displayName, areas[i].lat, areas[i].lng);
        }
        System.out.print("  >> Chọn khu vực (1-" + areas.length + "): ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice >= 1 && choice <= areas.length) {
                Area selected = areas[choice - 1];
                System.out.printf("  ✔ Đã chọn: %s (%.4f, %.4f)%n",
                        selected.displayName, selected.lat, selected.lng);
                return selected;
            }
        } catch (NumberFormatException ignored) {}

        System.out.println("  >> Mặc định: Quận Hải Châu");
        return Area.HAI_CHAU;
    }
}

