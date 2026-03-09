# 🆘 SOS Console App – Hệ Thống Cứu Hộ Thảm Họa

---

# 🇻🇳 PHẦN TIẾNG VIỆT

---

## 1. Project này làm gì?

Đây là ứng dụng **Java console** mô phỏng hệ thống cứu hộ thảm họa (lũ lụt / hỏa hoạn).

Khi có nạn nhân gửi yêu cầu cứu hộ, hệ thống sẽ:
1. **Chọn người cứu hộ** phù hợp nhất → dùng **Strategy Pattern**
2. **Chuẩn bị bộ công cụ** (đội + xe + thiết bị) phù hợp với loại thảm họa → dùng **Abstract Factory Pattern**

---

## 2. Strategy Pattern là gì?

### Ý tưởng đơn giản

> **Strategy = "Nhiều cách làm cùng một việc, chọn cách nào tùy lúc"**

Giống như đi từ nhà đến trường, bạn có thể chọn:
- 🚶 Đi bộ (nếu gần)
- 🏍️ Đi xe máy (nếu xa)
- 🚕 Gọi taxi (nếu trời mưa)

→ **Cùng 1 mục đích** (đến trường), nhưng **thuật toán/cách làm khác nhau**, và bạn **chọn tùy tình huống**.

### Áp dụng vào project như nào?

Trong project, **mục đích** là: **Chọn 1 người cứu hộ** từ danh sách.

Nhưng **cách chọn** có thể khác nhau:

| Chiến lược | Cách chọn |
|---|---|
| **NearestRescuerStrategy** | Chọn người **gần nhất** với nạn nhân |
| **UrgencyBasedStrategy** | Nếu CRITICAL → chọn người **kinh nghiệm nhất**. Nếu LOW → chọn người **gần nhất** |

### Code cụ thể trong project

**Bước 1: Tạo Interface** (định nghĩa "việc cần làm" chung)

```java
// File: RescuerAssignmentStrategy.java
public interface RescuerAssignmentStrategy {
    // Input:  1 yêu cầu cứu hộ + danh sách người có thể đi
    // Output: 1 người cứu hộ được chọn
    Optional<Rescuer> assign(RescueRequest request, List<Rescuer> availableRescuers);
    String getStrategyName();
}
```

**Bước 2: Mỗi "cách làm" = 1 class riêng**

```java
// File: NearestRescuerStrategy.java
// Cách 1: Chọn người GẦN nhất
public class NearestRescuerStrategy implements RescuerAssignmentStrategy {
    @Override
    public Optional<Rescuer> assign(RescueRequest request, List<Rescuer> availableRescuers) {
        // Tính khoảng cách từng người → lấy người có khoảng cách nhỏ nhất
        return availableRescuers.stream()
                .min(Comparator.comparingDouble(
                    rescuer -> rescuer.distanceTo(request.getLatitude(), request.getLongitude())
                ));
    }
}
```

```java
// File: UrgencyBasedStrategy.java
// Cách 2: Chọn theo mức độ khẩn cấp
public class UrgencyBasedStrategy implements RescuerAssignmentStrategy {
    @Override
    public Optional<Rescuer> assign(RescueRequest request, List<Rescuer> availableRescuers) {
        return switch (request.getUrgencyLevel()) {
            case CRITICAL -> // chọn người kinh nghiệm nhất (nhiều nhiệm vụ nhất)
                availableRescuers.stream()
                    .max(Comparator.comparingInt(Rescuer::getCompletedMissions));
            case LOW -> // chọn người gần nhất (tiết kiệm)
                availableRescuers.stream()
                    .min(Comparator.comparingDouble(r ->
                        r.distanceTo(request.getLatitude(), request.getLongitude())));
        };
    }
}
```

**Bước 3: Context** (nơi giữ strategy và gọi nó)

```java
// File: RescueSystemWithFactory.java
public class RescueSystemWithFactory {

    private RescuerAssignmentStrategy strategy;  // ← giữ strategy hiện tại

    // Đổi strategy bất cứ lúc nào (RUNTIME) mà không sửa code
    public void setStrategy(RescuerAssignmentStrategy strategy) {
        this.strategy = strategy;
    }

    // Khi cần chọn người → GỌI strategy, không cần biết bên trong làm gì
    public RescueKit assignRescuer(RescueRequest request) {
        Optional<Rescuer> chosen = strategy.assign(request, available);
        // ...
    }
}
```

### Tại sao dùng Strategy?

```
❌ KHÔNG dùng Strategy (xấu):
if (type == "nearest") { ... tìm gần nhất ... }
else if (type == "urgency") { ... tìm theo khẩn cấp ... }
else if (type == "new_algo") { ... phải SỬA code ở đây ... }
→ Vi phạm Open/Closed: mỗi lần thêm cách mới phải MỞ code cũ ra sửa

✅ Dùng Strategy (tốt):
→ Thêm cách mới = tạo class mới implement interface
→ KHÔNG cần sửa bất kỳ code cũ nào
→ Đổi cách dùng tại runtime: system.setStrategy(new NewStrategy());
```

### Sơ đồ Strategy trong project

```
         «interface»
  RescuerAssignmentStrategy          ← "Việc cần làm" (chọn người cứu hộ)
  ┌─────────────────────────────┐
  │ + assign(request, rescuers) │
  └─────────────────────────────┘
              ▲
       ┌──────┴──────────┐
       │                 │
NearestRescuer      UrgencyBased      ← "2 cách làm" khác nhau
  Strategy            Strategy

       ▲
       │ uses
RescueSystemWithFactory               ← "Context" – giữ strategy, gọi khi cần
```

---

## 3. Abstract Factory Pattern là gì?

### Ý tưởng đơn giản

> **Abstract Factory = "Nhà máy sản xuất BỘ sản phẩm đồng bộ"**

Giống như mua combo đồ ăn:
- 🍔 Combo McDonald's → Burger + Cola + Fries (đồ McDonald's, đồng bộ!)
- 🍜 Combo Phở → Phở + Trà đá + Rau sống (đồ Việt, đồng bộ!)

→ Mỗi "nhà máy" (factory) tạo ra **BỘ sản phẩm nhất quán**. Bạn không thể lỡ tay gọi Burger + Trà đá + Rau sống (sai bộ!).

### Áp dụng vào project như nào?

Trong project, **BỘ sản phẩm** gồm 3 thứ:
- 🧑‍🤝‍🧑 **RescueTeam** (đội cứu hộ)
- 🚗 **RescueVehicle** (phương tiện)
- 🔧 **RescueEquipment** (thiết bị)

**4 "nhà máy"** tạo ra 4 bộ khác nhau:

| Factory | Team | Vehicle | Equipment |
|---|---|---|---|
| `FloodCriticalFactory` | Đội đặc nhiệm lũ (10 người) | Trực thăng Mi-17 | Đồ lặn chuyên nghiệp |
| `FloodLowFactory` | Tổ hỗ trợ lũ (2 người) | Thuyền bơm hơi | Áo phao + đèn pin |
| `FireCriticalFactory` | Đội đặc nhiệm cháy (12 người) | Xe thang 52m | Đồ chống cháy 1000°C |
| `FireLowFactory` | Tổ hỗ trợ cháy (2 người) | Xe cứu thương | Mặt nạ khói + đèn pin |

→ Mỗi factory tạo ra **bộ đồng bộ**: không bao giờ nhầm Đội lũ lụt + Xe cứu hỏa!

### Code cụ thể trong project

**Bước 1: Định nghĩa 3 Abstract Product** (3 loại sản phẩm)

```java
// 3 interface = 3 loại sản phẩm cần tạo
public interface RescueTeam      { String describe(); }  // Sản phẩm A: Đội
public interface RescueVehicle   { String describe(); }  // Sản phẩm B: Xe
public interface RescueEquipment { String describe(); }  // Sản phẩm C: Thiết bị
```

**Bước 2: Abstract Factory Interface** (nhà máy tạo cả bộ)

```java
// File: RescueFactory.java
public interface RescueFactory {
    RescueTeam      createTeam();       // tạo Đội
    RescueVehicle   createVehicle();    // tạo Xe
    RescueEquipment createEquipment();  // tạo Thiết bị
}
```

**Bước 3: Concrete Factory** (nhà máy cụ thể cho từng trường hợp)

```java
// File: FloodCriticalFactory.java  ← Nhà máy cho: Lũ lụt + Nguy kịch
public class FloodCriticalFactory implements RescueFactory {
    @Override
    public RescueTeam createTeam() {
        return new FloodCriticalTeam();      // Đội đặc nhiệm lũ
    }
    @Override
    public RescueVehicle createVehicle() {
        return new FloodCriticalVehicle();   // Trực thăng Mi-17
    }
    @Override
    public RescueEquipment createEquipment() {
        return new FloodCriticalEquipment(); // Bộ lặn chuyên nghiệp
    }
}

// Tương tự có: FloodLowFactory, FireCriticalFactory, FireLowFactory
```

**Bước 4: Concrete Product** (sản phẩm cụ thể)

```java
// File: FloodCriticalTeam.java  ← 1 sản phẩm cụ thể
public class FloodCriticalTeam implements RescueTeam {
    @Override public String getTeamName()      { return "Đội Đặc Nhiệm Lũ Lụt"; }
    @Override public int    getMemberCount()   { return 10; }
    @Override public String getSpecialization(){ return "Lặn nước sâu, cứu hộ khẩn cấp"; }
}
```

**Bước 5: Factory Provider** (chọn đúng nhà máy)

```java
// File: RescueFactoryProvider.java
// Client gọi → nhận factory → KHÔNG cần biết class cụ thể bên trong
public static RescueFactory getFactory(String disasterType, UrgencyLevel urgency) {
    return switch (disasterType) {
        case "flood" -> switch (urgency) {
            case CRITICAL -> new FloodCriticalFactory();
            case LOW      -> new FloodLowFactory();
        };
        case "fire" -> switch (urgency) {
            case CRITICAL -> new FireCriticalFactory();
            case LOW      -> new FireLowFactory();
        };
    };
}
```

### Tại sao dùng Abstract Factory?

```
❌ KHÔNG dùng Abstract Factory (xấu):
RescueTeam team = new FloodCriticalTeam();
RescueVehicle vehicle = new FireLowVehicle();   // BUG! Đội lũ + xe cứu hỏa LOW
RescueEquipment equip = new FloodCriticalEquipment();
→ Dễ tạo nhầm tổ hợp, không đồng bộ

✅ Dùng Abstract Factory (tốt):
RescueFactory factory = RescueFactoryProvider.getFactory("flood", CRITICAL);
RescueKit kit = RescueKit.from(factory);
// → Tự động tạo: FloodCriticalTeam + FloodCriticalVehicle + FloodCriticalEquipment
// → Luôn đồng bộ, không bao giờ nhầm!
```

### Sơ đồ Abstract Factory trong project

```
      «interface»                  «interface»      «interface»       «interface»
    RescueFactory                 RescueTeam      RescueVehicle    RescueEquipment
   (Nhà máy trừu tượng)          (Sản phẩm A)    (Sản phẩm B)     (Sản phẩm C)
  ┌───────────────────┐              ▲                 ▲                 ▲
  │ + createTeam()    │──creates──→  │                 │                 │
  │ + createVehicle() │──creates──→  │                 │                 │
  │ + createEquipment()│─creates──→  │                 │                 │
  └───────────────────┘              │                 │                 │
          ▲                          │                 │                 │
    ┌─────┴─────┐              ┌─────┴─────┐    ┌─────┴─────┐    ┌─────┴─────┐
    │           │              │           │    │           │    │           │
FloodCritical FireCritical  FloodCritical FloodLow  FloodCritical FloodLow  ...
  Factory      Factory       Team        Team     Vehicle      Vehicle
```

---

## 4. Hai pattern phối hợp ở đâu?

Trong phương thức `assignRescuer()` của class `RescueSystemWithFactory`:

```java
public RescueKit assignRescuer(RescueRequest request) {

    // ★ BƯỚC 1: STRATEGY PATTERN – chọn NGƯỜI cứu hộ
    Optional<Rescuer> chosen = strategy.assign(request, available);

    // ★ BƯỚC 2: ABSTRACT FACTORY – tạo BỘ CÔNG CỤ cứu hộ
    RescueFactory factory = RescueFactoryProvider.getFactory(disasterType, request.getUrgencyLevel());
    RescueKit kit = RescueKit.from(factory);  // Team + Vehicle + Equipment
}
```

```
Nạn nhân gửi yêu cầu
        │
        ▼
  ┌─────────────────────────────────────────────┐
  │          assignRescuer(request)              │
  │                                             │
  │  ① Strategy Pattern                        │
  │     strategy.assign(request, rescuers)      │
  │     → Chọn NGƯỜI cứu hộ tối ưu              │
  │                                             │
  │  ② Abstract Factory Pattern                 │
  │     factory = getFactory("flood", CRITICAL) │
  │     kit = factory.createTeam()              │
  │         + factory.createVehicle()           │
  │         + factory.createEquipment()         │
  │     → Tạo BỘ CÔNG CỤ đồng bộ               │
  └─────────────────────────────────────────────┘
        │
        ▼
  Kết quả: Người cứu hộ X + Bộ công cụ Y
```

---

## 5. Cấu trúc thư mục

```
src/main/java/com/jungle/
│
├── strategy/                               ← ★ STRATEGY PATTERN
│   ├── service/
│   │   ├── RescuerAssignmentStrategy.java  ← Interface (chiến lược)
│   │   ├── NearestRescuerStrategy.java     ← Concrete Strategy 1
│   │   └── UrgencyBasedStrategy.java       ← Concrete Strategy 2
│   └── model/
│       ├── RescueRequest.java, Rescuer.java
│       ├── UrgencyLevel.java (LOW, CRITICAL)
│       ├── RequestStatus.java, RescuerStatus.java
│
├── abstractFactory/                        ← ★ ABSTRACT FACTORY PATTERN
│   ├── product/
│   │   ├── RescueTeam.java                ← Abstract Product A
│   │   ├── RescueVehicle.java             ← Abstract Product B
│   │   └── RescueEquipment.java           ← Abstract Product C
│   ├── factory/
│   │   ├── RescueFactory.java             ← Abstract Factory Interface
│   │   ├── RescueFactoryProvider.java     ← Chọn factory phù hợp
│   │   ├── RescueKit.java                 ← Gom 3 sản phẩm thành 1 bộ
│   │   └── RescueSystemWithFactory.java   ← ★ NƠI 2 PATTERN GẶP NHAU
│   ├── flood/                             ← Concrete cho lũ lụt
│   │   ├── FloodCriticalFactory.java, FloodLowFactory.java
│   │   ├── team/, vehicle/, equipment/    ← Concrete Products
│   └── fire/                              ← Concrete cho hỏa hoạn
│       ├── FireCriticalFactory.java, FireLowFactory.java
│       ├── team/, vehicle/, equipment/    ← Concrete Products
│
└── view/
    ├── Main.java                          ← Điểm vào, menu console
    └── Menu.java                          ← Hiển thị menu
```

---

## 6. Chạy ứng dụng

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.jungle.view.Main"
```

---
---
---

# 🇬🇧 ENGLISH VERSION

---

## 1. What does this project do?

This is a **Java console app** that simulates a disaster rescue system (flood / fire).

When a victim sends a rescue request, the system:
1. **Picks the best rescuer** → uses **Strategy Pattern**
2. **Prepares a rescue kit** (team + vehicle + equipment) matching the disaster type → uses **Abstract Factory Pattern**

---

## 2. What is Strategy Pattern?

### Simple idea

> **Strategy = "Multiple ways to do the same thing — pick whichever fits"**

Like going from home to school:
- 🚶 Walk (if it's close)
- 🏍️ Ride a motorbike (if it's far)
- 🚕 Take a taxi (if it's raining)

→ **Same goal** (get to school), **different algorithms**, you **choose at runtime**.

### How it's applied in this project

The **goal** is: **Pick 1 rescuer** from a list.

But the **way to pick** can differ:

| Strategy | How it picks |
|---|---|
| **NearestRescuerStrategy** | Pick the rescuer **closest** to the victim |
| **UrgencyBasedStrategy** | If CRITICAL → pick the **most experienced**. If LOW → pick the **closest** |

### Actual code in the project

**Step 1: Define a Strategy Interface** (what needs to be done)

```java
// File: RescuerAssignmentStrategy.java
public interface RescuerAssignmentStrategy {
    // Input:  1 rescue request + list of available rescuers
    // Output: 1 chosen rescuer
    Optional<Rescuer> assign(RescueRequest request, List<Rescuer> availableRescuers);
    String getStrategyName();
}
```

**Step 2: Each "way of doing it" = a separate class**

```java
// File: NearestRescuerStrategy.java
// Way 1: Pick the CLOSEST rescuer
public class NearestRescuerStrategy implements RescuerAssignmentStrategy {
    @Override
    public Optional<Rescuer> assign(RescueRequest request, List<Rescuer> availableRescuers) {
        // Calculate distance for each rescuer → pick the one with shortest distance
        return availableRescuers.stream()
                .min(Comparator.comparingDouble(
                    rescuer -> rescuer.distanceTo(request.getLatitude(), request.getLongitude())
                ));
    }
}
```

```java
// File: UrgencyBasedStrategy.java
// Way 2: Pick based on urgency level
public class UrgencyBasedStrategy implements RescuerAssignmentStrategy {
    @Override
    public Optional<Rescuer> assign(RescueRequest request, List<Rescuer> availableRescuers) {
        return switch (request.getUrgencyLevel()) {
            case CRITICAL -> // pick the most experienced (most completed missions)
                availableRescuers.stream()
                    .max(Comparator.comparingInt(Rescuer::getCompletedMissions));
            case LOW -> // pick the nearest (save resources)
                availableRescuers.stream()
                    .min(Comparator.comparingDouble(r ->
                        r.distanceTo(request.getLatitude(), request.getLongitude())));
        };
    }
}
```

**Step 3: Context** (holds the strategy and delegates to it)

```java
// File: RescueSystemWithFactory.java
public class RescueSystemWithFactory {

    private RescuerAssignmentStrategy strategy;  // ← holds current strategy

    // Switch strategy anytime at RUNTIME without modifying code!
    public void setStrategy(RescuerAssignmentStrategy strategy) {
        this.strategy = strategy;
    }

    // When assigning → DELEGATE to strategy, don't care about internals
    public RescueKit assignRescuer(RescueRequest request) {
        Optional<Rescuer> chosen = strategy.assign(request, available);
        // ...
    }
}
```

### Why use Strategy?

```
❌ WITHOUT Strategy (bad):
if (type == "nearest") { ... find nearest ... }
else if (type == "urgency") { ... find by urgency ... }
else if (type == "new_algo") { ... must MODIFY existing code here ... }
→ Violates Open/Closed Principle: adding a new algorithm requires changing old code

✅ WITH Strategy (good):
→ Adding a new algorithm = create a new class that implements the interface
→ NO need to change any existing code
→ Switch at runtime: system.setStrategy(new NewStrategy());
```

### Strategy diagram in this project

```
         «interface»
  RescuerAssignmentStrategy          ← "What to do" (pick a rescuer)
  ┌─────────────────────────────┐
  │ + assign(request, rescuers) │
  └─────────────────────────────┘
              ▲
       ┌──────┴──────────┐
       │                 │
NearestRescuer      UrgencyBased      ← "2 ways to do it"
  Strategy            Strategy

       ▲
       │ uses
RescueSystemWithFactory               ← "Context" – holds strategy, calls it when needed
```

---

## 3. What is Abstract Factory Pattern?

### Simple idea

> **Abstract Factory = "A factory that produces a FAMILY of related products"**

Like ordering a food combo:
- 🍔 McDonald's combo → Burger + Cola + Fries (all McDonald's, consistent!)
- 🍜 Pho combo → Pho + Iced tea + Herbs (all Vietnamese, consistent!)

→ Each "factory" produces a **consistent set of products**. You can never accidentally mix Burger + Iced tea + Herbs (wrong combo!).

### How it's applied in this project

In this project, the **product family** has 3 items:
- 🧑‍🤝‍🧑 **RescueTeam** (rescue team)
- 🚗 **RescueVehicle** (vehicle)
- 🔧 **RescueEquipment** (equipment)

**4 factories** produce 4 different families:

| Factory | Team | Vehicle | Equipment |
|---|---|---|---|
| `FloodCriticalFactory` | Special flood team (10 ppl) | Mi-17 Helicopter | Professional diving gear |
| `FloodLowFactory` | Support team (2 ppl) | Inflatable boat | Life jacket + flashlight |
| `FireCriticalFactory` | Special fire team (12 ppl) | 52m Ladder truck | 1000°C fireproof suit |
| `FireLowFactory` | Support team (2 ppl) | Ambulance | Smoke mask + flashlight |

→ Each factory produces a **consistent set**: never mixing a Flood team + Fire vehicle!

### Actual code in the project

**Step 1: Define 3 Abstract Products** (3 types of products)

```java
// 3 interfaces = 3 product types
public interface RescueTeam      { String describe(); }  // Product A: Team
public interface RescueVehicle   { String describe(); }  // Product B: Vehicle
public interface RescueEquipment { String describe(); }  // Product C: Equipment
```

**Step 2: Abstract Factory Interface** (factory that creates the whole set)

```java
// File: RescueFactory.java
public interface RescueFactory {
    RescueTeam      createTeam();       // create Team
    RescueVehicle   createVehicle();    // create Vehicle
    RescueEquipment createEquipment();  // create Equipment
}
```

**Step 3: Concrete Factory** (specific factory for each case)

```java
// File: FloodCriticalFactory.java  ← Factory for: Flood + Critical
public class FloodCriticalFactory implements RescueFactory {
    @Override
    public RescueTeam createTeam() {
        return new FloodCriticalTeam();      // Special flood team
    }
    @Override
    public RescueVehicle createVehicle() {
        return new FloodCriticalVehicle();   // Mi-17 Helicopter
    }
    @Override
    public RescueEquipment createEquipment() {
        return new FloodCriticalEquipment(); // Professional diving gear
    }
}

// Similarly: FloodLowFactory, FireCriticalFactory, FireLowFactory
```

**Step 4: Concrete Product** (specific product)

```java
// File: FloodCriticalTeam.java  ← One specific product
public class FloodCriticalTeam implements RescueTeam {
    @Override public String getTeamName()      { return "Special Flood Task Force"; }
    @Override public int    getMemberCount()   { return 10; }
    @Override public String getSpecialization(){ return "Deep diving, emergency rescue"; }
}
```

**Step 5: Factory Provider** (picks the right factory)

```java
// File: RescueFactoryProvider.java
// Client calls this → gets a factory → doesn't know the concrete class inside
public static RescueFactory getFactory(String disasterType, UrgencyLevel urgency) {
    return switch (disasterType) {
        case "flood" -> switch (urgency) {
            case CRITICAL -> new FloodCriticalFactory();
            case LOW      -> new FloodLowFactory();
        };
        case "fire" -> switch (urgency) {
            case CRITICAL -> new FireCriticalFactory();
            case LOW      -> new FireLowFactory();
        };
    };
}
```

### Why use Abstract Factory?

```
❌ WITHOUT Abstract Factory (bad):
RescueTeam team = new FloodCriticalTeam();
RescueVehicle vehicle = new FireLowVehicle();   // BUG! Flood team + Fire LOW vehicle
RescueEquipment equip = new FloodCriticalEquipment();
→ Easy to create WRONG combinations, no consistency guaranteed

✅ WITH Abstract Factory (good):
RescueFactory factory = RescueFactoryProvider.getFactory("flood", CRITICAL);
RescueKit kit = RescueKit.from(factory);
// → Automatically creates: FloodCriticalTeam + FloodCriticalVehicle + FloodCriticalEquipment
// → Always consistent, never mixed up!
```

### Abstract Factory diagram in this project

```
      «interface»                  «interface»      «interface»       «interface»
    RescueFactory                 RescueTeam      RescueVehicle    RescueEquipment
  (Abstract Factory)             (Product A)      (Product B)       (Product C)
  ┌───────────────────┐              ▲                 ▲                 ▲
  │ + createTeam()    │──creates──→  │                 │                 │
  │ + createVehicle() │──creates──→  │                 │                 │
  │ + createEquipment()│─creates──→  │                 │                 │
  └───────────────────┘              │                 │                 │
          ▲                          │                 │                 │
    ┌─────┴─────┐              ┌─────┴─────┐    ┌─────┴─────┐    ┌─────┴─────┐
    │           │              │           │    │           │    │           │
FloodCritical FireCritical  FloodCritical FloodLow  FloodCritical FloodLow  ...
  Factory      Factory       Team        Team     Vehicle      Vehicle
```

---

## 4. Where do both patterns work together?

In the `assignRescuer()` method of `RescueSystemWithFactory`:

```java
public RescueKit assignRescuer(RescueRequest request) {

    // ★ STEP 1: STRATEGY PATTERN – pick the RESCUER
    Optional<Rescuer> chosen = strategy.assign(request, available);

    // ★ STEP 2: ABSTRACT FACTORY – create the RESCUE KIT
    RescueFactory factory = RescueFactoryProvider.getFactory(disasterType, request.getUrgencyLevel());
    RescueKit kit = RescueKit.from(factory);  // Team + Vehicle + Equipment
}
```

```
Victim sends request
        │
        ▼
  ┌─────────────────────────────────────────────┐
  │          assignRescuer(request)              │
  │                                             │
  │  ① Strategy Pattern                        │
  │     strategy.assign(request, rescuers)      │
  │     → Pick the best RESCUER                 │
  │                                             │
  │  ② Abstract Factory Pattern                 │
  │     factory = getFactory("flood", CRITICAL) │
  │     kit = factory.createTeam()              │
  │         + factory.createVehicle()           │
  │         + factory.createEquipment()         │
  │     → Create a consistent RESCUE KIT        │
  └─────────────────────────────────────────────┘
        │
        ▼
  Result: Rescuer X  +  Rescue Kit Y
```

---

## 5. Project Structure

```
src/main/java/com/jungle/
│
├── strategy/                               ← ★ STRATEGY PATTERN
│   ├── service/
│   │   ├── RescuerAssignmentStrategy.java  ← Strategy Interface
│   │   ├── NearestRescuerStrategy.java     ← Concrete Strategy 1
│   │   └── UrgencyBasedStrategy.java       ← Concrete Strategy 2
│   └── model/
│       ├── RescueRequest.java, Rescuer.java
│       ├── UrgencyLevel.java (LOW, CRITICAL)
│       ├── RequestStatus.java, RescuerStatus.java
│
├── abstractFactory/                        ← ★ ABSTRACT FACTORY PATTERN
│   ├── product/
│   │   ├── RescueTeam.java                ← Abstract Product A
│   │   ├── RescueVehicle.java             ← Abstract Product B
│   │   └── RescueEquipment.java           ← Abstract Product C
│   ├── factory/
│   │   ├── RescueFactory.java             ← Abstract Factory Interface
│   │   ├── RescueFactoryProvider.java     ← Picks the right factory
│   │   ├── RescueKit.java                 ← Bundles 3 products into 1 kit
│   │   └── RescueSystemWithFactory.java   ← ★ WHERE BOTH PATTERNS MEET
│   ├── flood/                             ← Concrete for flood
│   │   ├── FloodCriticalFactory.java, FloodLowFactory.java
│   │   ├── team/, vehicle/, equipment/    ← Concrete Products
│   └── fire/                              ← Concrete for fire
│       ├── FireCriticalFactory.java, FireLowFactory.java
│       ├── team/, vehicle/, equipment/    ← Concrete Products
│
└── view/
    ├── Main.java                          ← Entry point, console menu
    └── Menu.java                          ← Menu display
```

---

## 6. Run the Application

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.jungle.view.Main"
```
