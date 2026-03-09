package com.jungle.util;

import com.jungle.abstractFactory.factory.RescueSystemWithFactory;
import com.jungle.strategy.model.Rescuer;

public class DataInit {

    // ═══════════════════════════════════════════════════════════
    //  DỮ LIỆU MẪU
    // ═══════════════════════════════════════════════════════════
    public static void initSampleRescuers(RescueSystemWithFactory system) {
        system.addRescuer(new Rescuer("Nguyễn Văn An",  "0901000001", "Lặn",          16.0544, 108.2022));
        system.addRescuer(new Rescuer("Trần Thị Bình",  "0901000002", "Y tế",          16.0620, 108.2150));
        system.addRescuer(new Rescuer("Lê Hoàng Cường", "0901000003", "Cứu hộ chung", 16.0400, 108.1900));
        system.addRescuer(new Rescuer("Phạm Minh Đức",  "0901000004", "Lặn",          16.4637, 107.5909));
        system.addRescuer(new Rescuer("Hoàng Thị Em",   "0901000005", "Y tế",          15.8794, 108.3350));
        system.addRescuer(new Rescuer("Võ Thanh Phong", "0901000006", "Cứu hộ chung", 16.0750, 108.2240));
        System.out.println(">> Đã tải " + system.getAllRescuers().size() + " người cứu hộ vào hệ thống.");
    }
}
