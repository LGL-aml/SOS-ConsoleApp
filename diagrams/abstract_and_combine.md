# 🎤 Presentation Script — Person 2: Abstract Factory Pattern + Combined
> 💬 Format: **[English script]** — *(Việt sub: đang nói về gì)*

---

## PART A — ABSTRACT FACTORY PATTERN

---

## SLIDE: Title — Abstract Factory Pattern

**"Hi everyone. I'll present the second pattern — Abstract Factory. And at the end, I'll show how both patterns work together."**
*(Giới thiệu, trình bày Abstract Factory rồi phần Combined)*

---

## SLIDE: What is Abstract Factory? — Real-life Analogy

**"Think about a phone brand — say Samsung or Apple."**
*(Nghĩ về thương hiệu điện thoại — Samsung hay Apple)*

**"When Samsung releases a product line, they don't sell just a phone. They sell a matching set — phone, earbuds, charger — all in the same Galaxy family. Same design language, same compatibility."**
*(Samsung không bán riêng lẻ — họ bán cả bộ: điện thoại + tai nghe + sạc — cùng dòng Galaxy, đồng bộ với nhau)*

**"Apple does the same — iPhone, AirPods, MagSafe charger. All match. You'd never get an Apple box with a Samsung charger inside."**
*(Apple cũng vậy — iPhone + AirPods + MagSafe. Không bao giờ mở hộp Apple ra thấy sạc Samsung)*

> **"Abstract Factory = A factory that produces a matching SET of products. Everything in the set is guaranteed to go together."**
> *(Abstract Factory = Nhà máy sản xuất BỘ sản phẩm đồng bộ. Mọi thứ trong bộ đảm bảo khớp nhau)*

---

## SLIDE: The Problem It Solves

**"Without this pattern, the code has to manually pick and combine each product — Team, Vehicle, Equipment — one by one. And nothing stops someone from accidentally mixing a fire team with flood equipment. The combination would be wrong and inconsistent."**
*(Không có pattern → code phải tự ghép từng sản phẩm → không có gì ngăn việc mix nhầm đội cứu hỏa với thiết bị chống lũ)*

**"Abstract Factory solves this by grouping the creation logic into one factory class. One factory = one consistent family. You can't mix products from different families."**
*(Abstract Factory nhóm logic tạo sản phẩm vào 1 class factory. 1 factory = 1 bộ nhất quán. Không thể mix nhầm)*

---

## SLIDE: Applying to Our Project

**"In our SOS system, when a rescue request comes in, we need to prepare a full rescue kit — a Team, a Vehicle, and Equipment."**
*(Trong hệ thống SOS, khi có yêu cầu cứu hộ, cần chuẩn bị bộ kit đầy đủ: Team + Vehicle + Equipment)*

**"But this kit must match both the disaster type AND the urgency level."**
*(Nhưng bộ kit phải khớp với cả loại thảm họa LẪN mức độ khẩn cấp)*

**"We have 2 disaster types × 4 urgency levels = 8 different kits. So we built 8 Concrete Factories:"**
*(2 loại thảm họa × 4 mức độ = 8 bộ kit khác nhau → 8 Concrete Factory)*

| | CRITICAL | HIGH | MEDIUM | LOW |
|---|---|---|---|---|
| 🔥 **Fire** | `FireCriticalFactory` | `FireHighFactory` | `FireMediumFactory` | `FireLowFactory` |
| 🌊 **Flood** | `FloodCriticalFactory` | `FloodHighFactory` | `FloodMediumFactory` | `FloodLowFactory` |

**"Each factory creates exactly one consistent kit. `FireCriticalFactory` always produces `FireCriticalTeam` + `FireCriticalVehicle` + `FireCriticalEquipment`. Never mixed up."**
*(`FireCriticalFactory` luôn tạo ra Team + Vehicle + Equipment đều ở mức Critical Fire. Không bao giờ nhầm lẫn)*

---

## SLIDE: Class Diagram — Abstract Factory Pattern
> 👉 *(Chỉ vào diagram `01_abstract_factory_pattern`)*

**"`RescueFactory` is the Abstract Factory interface — it declares three methods: `createTeam()`, `createVehicle()`, `createEquipment()`. Each returns an Abstract Product interface, not a concrete class."**
*(Chỉ vào `RescueFactory` — khai báo 3 method, đều trả về interface, không phải class cụ thể)*

**"The 3 Abstract Products — `RescueTeam`, `RescueVehicle`, `RescueEquipment` — define what each product must be able to do, regardless of disaster type or level."**
*(3 Abstract Product định nghĩa sản phẩm phải làm được gì — bất kể loại hay mức độ nào)*

**"`RescueFactoryProvider` is the single entry point. You give it the disaster type and urgency level — it returns the right factory. No one ever calls `new FireCriticalFactory()` directly."**
*(Chỉ vào `RescueFactoryProvider` — điểm vào duy nhất. Truyền disaster type + urgency → nhận về đúng factory. Không ai gọi `new` trực tiếp)*

**"`RescueKit` is the client — it only talks to the `RescueFactory` interface. It calls `createTeam()`, `createVehicle()`, `createEquipment()` and packages the result."**
*(Chỉ vào `RescueKit` — client chỉ biết interface, gọi 3 method tạo sản phẩm rồi đóng gói lại)*

---

## SLIDE: Steps to Apply Abstract Factory in This Project

**"Three steps to apply this pattern:"**
*(3 bước áp dụng)*

**"Step 1 — Define Abstract Products."**
*(Bước 1 — Tạo Abstract Products)*
> *"We created 3 interfaces: `RescueTeam`, `RescueVehicle`, `RescueEquipment`. Each defines the contract a product must fulfill."*
> *(Tạo 3 interface — mỗi cái định nghĩa sản phẩm phải làm được gì)*

**"Step 2 — Define the Abstract Factory interface."**
*(Bước 2 — Tạo Abstract Factory interface)*
> *"We created `RescueFactory` with 3 create methods — one per product type."*
> *(Tạo `RescueFactory` với 3 method create — mỗi method cho 1 loại sản phẩm)*

**"Step 3 — Implement one Concrete Factory per product family."**
*(Bước 3 — Implement 1 Concrete Factory cho mỗi họ sản phẩm)*
> *"8 factories total — each one creates its own consistent set of Team, Vehicle, and Equipment. Then `RescueFactoryProvider` acts as the lookup table to return the right one."*
> *(8 factory — mỗi cái tạo bộ sản phẩm nhất quán của mình. `RescueFactoryProvider` đóng vai bảng tra cứu)*

---

## SLIDE: Benefit Summary — Abstract Factory

**"Three benefits in our project:"**
*(3 lợi ích)*

1. 🔒 **"Consistency guaranteed."** — *One factory = one family. Fire team never gets paired with flood equipment.*
   *(1 factory = 1 bộ. Đội cứu hỏa không bao giờ đi cùng thiết bị chống lũ)*

2. 🧩 **"Client is decoupled."** — *`RescueKit` doesn't know about any concrete class. Swap out a factory — the client code doesn't change.*
   *(Client không biết class cụ thể nào. Đổi factory → client không cần sửa)*

3. 📦 **"Easy to extend."** — *Need a new disaster type like 'earthquake'? Add 4 new factories and their products — nothing else touched.*
   *(Thêm loại thảm họa mới? Tạo thêm 4 factory + sản phẩm — không sửa gì cũ)*

---
---

## PART B — COMBINED: HOW BOTH PATTERNS WORK TOGETHER

---

## SLIDE: The Integration Point
> 👉 *(Chuyển sang diagram `03_combined_patterns`)*

**"Now — both patterns live inside the same system. Where do they meet?"**
*(2 pattern cùng tồn tại trong 1 hệ thống. Chúng gặp nhau ở đâu?)*

**"`RescueSystemWithFactory` is the integration point. It plays two roles at once — it's the Context for Strategy, and it's the one that triggers the Abstract Factory."**
*(`RescueSystemWithFactory` là điểm giao nhau: vừa là Context của Strategy, vừa kích hoạt Abstract Factory)*

---

## SLIDE: The Full Flow — `assignRescuer()`

**"When a rescue request is assigned, everything happens inside `assignRescuer()`. Two patterns fire in sequence:"**
*(Khi phân công 1 request, mọi thứ xảy ra trong `assignRescuer()`. 2 pattern kích hoạt tuần tự)*

```
assignRescuer(request)
    │
    ├─► STRATEGY kicks in first
    │       strategy.assign(request, availableRescuers)
    │       → Answers: WHO goes?       (chọn ai đi cứu hộ)
    │
    └─► ABSTRACT FACTORY kicks in next
            RescueFactoryProvider.getFactory(disasterType, urgency)
            RescueKit.from(factory)
            → Answers: WHAT to bring?  (mang theo bộ kit gì)
```

**"Strategy answers WHO. Abstract Factory answers WHAT. They don't overlap — they complement each other."**
*(Strategy trả lời AI đi. Abstract Factory trả lời mang GÌ theo. Không trùng nhau — bổ sung cho nhau)*

---

## SLIDE: Side-by-side Comparison

| | Strategy | Abstract Factory |
|---|---|---|
| **Solves** | *Chọn AI?* — WHO to assign | *Chuẩn bị GÌ?* — WHAT kit to prepare |
| **Varies by** | Tình huống vận hành | Loại thảm họa + Mức độ |
| **Swappable** | ✅ Runtime — `setStrategy()` | ✅ Runtime — `setDisasterType()` |
| **Analogy** | Chọn cách đi đến trường | Chọn bộ đồ dùng phù hợp khi đến nơi |

---

## SLIDE: Closing

**"To wrap up — our project shows that two design patterns can work side by side in a single app, each solving a completely different problem."**
*(2 pattern cùng tồn tại trong 1 app, mỗi cái giải quyết 1 vấn đề riêng biệt)*

**"Strategy gives us flexibility in HOW we assign. Abstract Factory gives us consistency in WHAT we deploy. Together, the system is both adaptable and reliable."**
*(Strategy: linh hoạt trong cách phân công. Abstract Factory: nhất quán trong bộ kit triển khai. Kết hợp: vừa linh hoạt vừa đáng tin)*

**"Thank you."**
*(Cảm ơn)*

