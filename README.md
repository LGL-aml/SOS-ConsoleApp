# 🆘 SOS Console App – Hệ Thống Cứu Hộ Thảm Họa

Ứng dụng console Java mô phỏng hệ thống điều phối cứu hộ thảm họa (lũ lụt / cháy rừng).  
Dự án áp dụng **2 Design Pattern** chính: **Strategy Pattern** và **Abstract Factory Pattern**.

---

## 🗂️ Cấu Trúc Dự Án

```
src/main/java/com/jungle/
├── Main.java                          ← Điểm vào, vòng lặp menu console
├── strategy/
│   ├── model/
│   │   ├── RescueRequest.java         ← Entity yêu cầu cứu hộ
│   │   ├── Rescuer.java               ← Entity người cứu hộ
│   │   ├── RequestStatus.java         ← Enum trạng thái yêu cầu
│   │   ├── RescuerStatus.java         ← Enum trạng thái người cứu hộ
│   │   └── UrgencyLevel.java          ← Enum mức độ khẩn cấp
│   └── service/
│       ├── RescuerAssignmentStrategy.java  ← Strategy Interface
│       ├── NearestRescuerStrategy.java     ← Chiến lược: Gần nhất
│       ├── UrgencyBasedStrategy.java       ← Chiến lược: Theo mức độ khẩn cấp
│       ├── RoundRobinStrategy.java         ← Chiến lược: Luân phiên
│       └── RescueSystem.java               ← Context (Strategy Pattern)
└── abstractFactory/
    ├── factory/
    │   ├── RescueFactory.java              ← Abstract Factory Interface
    │   ├── RescueFactoryProvider.java      ← Factory lookup (disasterType + urgency)
    │   ├── RescueKit.java                  ← Bộ công cụ cứu hộ (Team + Vehicle + Equipment)
    │   └── RescueSystemWithFactory.java    ← Context tích hợp cả 2 pattern
    ├── flood/                              ← Concrete Factories cho lũ lụt
    │   ├── FloodRescueFactory.java
    │   ├── FloodCriticalFactory.java
    │   ├── FloodHighFactory.java
    │   ├── FloodMediumFactory.java
    │   └── FloodLowFactory.java
    ├── fire/                               ← Concrete Factories cho cháy rừng
    │   ├── FireRescueFactory.java
    │   ├── FireCriticalFactory.java
    │   ├── FireHighFactory.java
    │   ├── FireMediumFactory.java
    │   └── FireLowFactory.java
    └── product/                            ← Abstract Products
        ├── RescueTeam.java
        ├── RescueVehicle.java
        └── RescueEquipment.java
```

---

## ✨ Hai Chức Năng Chính

### 1. 📤 Nạn Nhân Gửi Yêu Cầu Cứu Hộ

Người gặp nạn nhập thông tin qua console:

| Trường          | Mô tả                                      |
|-----------------|--------------------------------------------|
| Họ tên          | Tên nạn nhân                               |
| Số điện thoại   | Liên hệ khẩn cấp                           |
| Mô tả tình huống| Mô tả ngắn về tình trạng                  |
| Vĩ độ / Kinh độ | Tọa độ GPS vị trí nạn nhân               |
| Mức độ khẩn cấp | LOW / MEDIUM / HIGH / CRITICAL             |

**Luồng xử lý:**
```
Nạn nhân nhập thông tin
    → Tạo RescueRequest (id tự tăng, status = PENDING)
    → Ghi nhận vào hệ thống
    → Hiển thị xác nhận
```

**Trạng thái yêu cầu (`RequestStatus`):**
```
PENDING → ASSIGNED → IN_PROGRESS → COMPLETED
                  ↘ CANCELLED
```

---

### 2. 🤝 Hệ Thống Phân Công Người Cứu Hộ

Hệ thống tự động chọn người cứu hộ tối ưu dựa trên **chiến lược đang được cấu hình**, sau đó chuẩn bị **bộ công cụ cứu hộ** phù hợp với loại thảm họa và mức độ khẩn cấp.

**Luồng xử lý:**
```
Lấy danh sách yêu cầu PENDING
    → Strategy chọn người cứu hộ tối ưu từ danh sách AVAILABLE
    → Cập nhật: request.status = ASSIGNED, rescuer.status = BUSY
    → Abstract Factory tạo RescueKit (Team + Vehicle + Equipment)
    → Hiển thị kết quả phân công + bộ công cụ
```

---

## 🎨 Design Pattern 1: Strategy Pattern

> **Mục đích:** Cho phép thay đổi thuật toán phân công người cứu hộ linh hoạt tại runtime mà không sửa code hệ thống.

### Sơ Đồ

```
         «interface»
  RescuerAssignmentStrategy
  ┌──────────────────────────┐
  │ + assign(request,        │
  │     availableRescuers)   │
  │ + getStrategyName()      │
  └──────────────────────────┘
           ▲
    ┌──────┴──────────────────┐
    │              │          │
NearestRescuer  RoundRobin  UrgencyBased
  Strategy       Strategy    Strategy
```

### Các Chiến Lược Hiện Có

| Chiến lược | Class | Thuật toán |
|---|---|---|
| **Gần nhất** | `NearestRescuerStrategy` | Chọn người cứu hộ có khoảng cách Haversine ngắn nhất đến nạn nhân |
| **Luân phiên** | `RoundRobinStrategy` | Chọn người có ít nhiệm vụ hoàn thành nhất (cân bằng tải); nếu bằng nhau → gần nhất |
| **Khẩn cấp** | `UrgencyBasedStrategy` | CRITICAL → kinh nghiệm cao nhất; HIGH → kinh nghiệm × khoảng cách; MEDIUM/LOW → gần nhất |

### Context Class

`RescueSystemWithFactory` đóng vai trò **Context** – giữ reference đến strategy hiện tại và ủy quyền việc chọn người cứu hộ:

```java
// Thay chiến lược tại runtime (không cần sửa hệ thống)
system.setStrategy(new RoundRobinStrategy());
system.setStrategy(new UrgencyBasedStrategy());
system.setStrategy(new NearestRescuerStrategy());
```

---

## 🏭 Design Pattern 2: Abstract Factory Pattern

> **Mục đích:** Tạo ra **bộ công cụ cứu hộ** (Team + Vehicle + Equipment) nhất quán theo từng tổ hợp `disasterType × urgencyLevel` mà không để client biết đến class cụ thể.

### Sơ Đồ

```
      «interface»
    RescueFactory
  ┌───────────────────┐
  │ + createTeam()    │
  │ + createVehicle() │
  │ + createEquipment()│
  └───────────────────┘
          ▲
   ┌──────┴──────────────────────────────┐
   │              │             │        │
FloodCritical  FloodHigh  FloodMedium  FloodLow
  Factory       Factory    Factory     Factory
FireCritical   FireHigh   FireMedium  FireLow
  Factory       Factory    Factory     Factory
```

### Ma Trận Factory

| Thảm họa \ Mức độ | CRITICAL | HIGH | MEDIUM | LOW |
|---|---|---|---|---|
| **Lũ lụt** (flood) | `FloodCriticalFactory` | `FloodHighFactory` | `FloodMediumFactory` | `FloodLowFactory` |
| **Cháy rừng** (fire) | `FireCriticalFactory` | `FireHighFactory` | `FireMediumFactory` | `FireLowFactory` |

### Factory Provider

`RescueFactoryProvider` là **điểm vào duy nhất** để lấy Concrete Factory – client chỉ biết `RescueFactory` interface:

```java
// Bên trong RescueSystemWithFactory.assignRescuer()
RescueFactory factory = RescueFactoryProvider.getFactory(disasterType, request.getUrgencyLevel());
RescueKit kit = RescueKit.from(factory); // Team + Vehicle + Equipment nhất quán
```

### Sản Phẩm (Products)

| Abstract Product | Mô tả |
|---|---|
| `RescueTeam` | Đội cứu hộ chuyên biệt theo thảm họa và mức độ |
| `RescueVehicle` | Phương tiện cứu hộ phù hợp |
| `RescueEquipment` | Trang thiết bị chuyên dụng |

---

## 🖥️ Menu Console

```
┌─────────────────────────────────────────┐
│              MENU CHÍNH                 │
├─────────────────────────────────────────┤
│  [CHỨC NĂNG NẠN NHÂN]                   │
│   1. Gửi yêu cầu cứu hộ                │
├─────────────────────────────────────────┤
│  [CHỨC NĂNG HỆ THỐNG - PHÂN CÔNG]       │
│   2. Phân công tất cả yêu cầu đang chờ │
│   3. Phân công 1 yêu cầu theo ID        │
├─────────────────────────────────────────┤
│  [CẤU HÌNH PATTERN]                     │
│   4. Đổi chiến lược (Strategy Pattern) │
│   5. Đổi loại thảm họa (Abs. Factory)  │
├─────────────────────────────────────────┤
│  [XEM THÔNG TIN]                        │
│   6. Xem tất cả yêu cầu cứu hộ         │
│   7. Xem yêu cầu đang chờ phân công    │
│   8. Xem danh sách người cứu hộ         │
│   9. Hoàn thành nhiệm vụ cứu hộ         │
│   0. Thoát                              │
└─────────────────────────────────────────┘
```

---

## 🔗 Tích Hợp Hai Pattern

Hai pattern phối hợp với nhau trong method `assignRescuer()`:

```
assignRescuer(request)
    │
    ├── [Strategy Pattern]
    │       strategy.assign(request, availableRescuers)
    │       → Chọn người cứu hộ tối ưu
    │
    └── [Abstract Factory Pattern]
            RescueFactoryProvider.getFactory(disasterType, urgency)
            → factory.createTeam() + createVehicle() + createEquipment()
            → Trả về RescueKit nhất quán với loại thảm họa
```

---

## 🛠️ Công Nghệ

| Mục | Chi tiết |
|---|---|
| Ngôn ngữ | Java 17+ |
| Build tool | Maven |
| Chạy | Console (CLI) – không cần database, không cần web server |
| Pattern | Strategy Pattern + Abstract Factory Pattern |

---

## ▶️ Chạy Ứng Dụng

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.jungle.view.Main"
```
