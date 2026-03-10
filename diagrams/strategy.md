# 🎤 Presentation Script — Person 1: Strategy Pattern
> 💬 Format: **[English script]** — *(Việt sub: đang nói về gì)*

---

## SLIDE: Title — Strategy Pattern

**"Hi everyone. I'll be presenting the first design pattern in our project — the Strategy Pattern."**
*(Giới thiệu, sẽ trình bày Strategy Pattern)*

---

## SLIDE: What is Strategy? — Real-life Analogy

**"Let me start with a simple real-life example."**
*(Bắt đầu bằng ví dụ đời thực)*

**"Every morning, you need to get to school. You have multiple ways to do it:"**
*(Mỗi sáng đi học, bạn có nhiều cách)*

- 🚶 **"Walk — if it's nearby."** *(Đi bộ — nếu gần)*
- 🏍️ **"Motorbike — if it's far."** *(Xe máy — nếu xa)*
- 🚕 **"Grab — if it's raining."** *(Gọi taxi — nếu trời mưa)*

**"Same goal — get to school. But the method changes depending on the situation. That's exactly the Strategy Pattern."**
*(Cùng 1 mục tiêu — đến trường. Nhưng cách làm thay đổi tùy tình huống. Đó chính là Strategy Pattern)*

> **"Strategy = Many ways to do the same thing. Pick one depending on the situation."**
> *(Strategy = Nhiều cách làm cùng 1 việc. Chọn cách nào tùy lúc)*

---

## SLIDE: The Problem It Solves

**"Without Strategy, every time you want to add a new way of doing something, you'd have to go inside the existing code and add another if-else. The class gets bigger, messier, and harder to maintain."**
*(Không có Strategy → mỗi lần thêm cách mới phải mò vào sửa if-else → class phình to, khó bảo trì)*

**"Strategy solves this by pulling each method out into its own class. Add a new method? Just create a new class — nothing else changes."**
*(Strategy tách mỗi cách ra class riêng. Thêm cách mới? Tạo class mới — không đụng gì cả)*

---

## SLIDE: Applying to Our Project

**"In our SOS rescue system, the 'same goal' is: assign a rescuer to a victim."**
*(Trong hệ thống SOS, "cùng 1 mục tiêu" là: phân công người cứu hộ cho nạn nhân)*

**"But we have 3 different ways to do it — 3 strategies:"**
*(Nhưng có 3 cách khác nhau — 3 chiến lược)*

| | Strategy | *(Cách hoạt động)* |
|---|---|---|
| 🗺️ | **NearestRescuerStrategy** | Chọn người gần nạn nhân nhất |
| ⚡ | **UrgencyBasedStrategy** | CRITICAL → người kinh nghiệm nhất; HIGH → kết hợp kinh nghiệm + khoảng cách; LOW/MEDIUM → người gần nhất |
| ⚖️ | **RoundRobinStrategy** | Chọn người có ít nhiệm vụ nhất → cân bằng tải |

**"Same goal — find a rescuer. Different algorithm each time."**
*(Cùng mục tiêu — tìm người cứu hộ. Thuật toán khác nhau mỗi lần)*

---

## SLIDE: Class Diagram — Strategy Pattern
> 👉 *(Chỉ vào diagram `02_strategy_pattern`)*

**"Looking at the diagram — `RescuerAssignmentStrategy` is the Strategy Interface. It declares one method: `assign()` — take a request and a list of available rescuers, return the chosen one."**
*(Chỉ vào interface — khai báo 1 method `assign()`: nhận request + danh sách rescuer, trả về người được chọn)*

**"The three concrete strategies each implement this method differently."**
*(3 Concrete Strategy implement method này theo 3 cách khác nhau)*

**"`RescueSystemWithFactory` is the Context — it holds the current strategy and can swap it at runtime by calling `setStrategy()`."**
*(Context giữ strategy hiện tại, đổi được ngay lúc chạy bằng `setStrategy()`)*

**"For example — switch from Nearest to Round-Robin while the app is running, without restarting or touching any other code."**
*(Đổi từ Nearest sang Round-Robin ngay lúc app đang chạy — không restart, không sửa code khác)*

---

## SLIDE: Steps to Apply Strategy in This Project

**"How did we actually apply this pattern? Three steps:"**
*(Cách áp dụng pattern vào dự án — 3 bước)*

**"Step 1 — Define the Strategy Interface."**
*(Bước 1 — Tạo Strategy Interface)*
> *"We created `RescuerAssignmentStrategy` with one method: `assign()`. This is the contract all strategies must follow."*
> *(Tạo interface `RescuerAssignmentStrategy` với method `assign()` — đây là hợp đồng mọi strategy phải tuân theo)*

**"Step 2 — Implement each strategy as a separate class."**
*(Bước 2 — Implement mỗi strategy thành class riêng)*
> *"`NearestRescuerStrategy`, `UrgencyBasedStrategy`, `RoundRobinStrategy` — each one contains its own algorithm inside `assign()`."*
> *(Mỗi class chứa thuật toán riêng bên trong `assign()`)*

**"Step 3 — Plug the strategy into the Context."**
*(Bước 3 — Gắn strategy vào Context)*
> *"`RescueSystemWithFactory` stores a `strategy` field. When `assignRescuer()` is called, it simply delegates to `strategy.assign()` — it doesn't know or care which algorithm runs."*
> *(Context chỉ gọi `strategy.assign()` — không biết và không cần biết thuật toán bên trong làm gì)*

---

## SLIDE: Benefit Summary

**"To summarize — Strategy gave us three things in this project:"**
*(Tóm lại 3 lợi ích)*

1. 🔌 **"Plug and play algorithms."** — *Add a new assignment rule = create one new class, zero existing code touched.*
   *(Thêm thuật toán mới = tạo 1 class mới, không sửa gì cũ)*

2. 🔄 **"Switch at runtime."** — *The operator can change the strategy on the fly based on the real situation.*
   *(Đổi thuật toán ngay lúc chạy, không cần restart)*

3. 🧪 **"Easy to test."** — *Each strategy class has one job — test it in isolation.*
   *(Mỗi class 1 việc — dễ test độc lập)*

---

**"That's the Strategy Pattern. I'll hand it over to my teammate for the Abstract Factory Pattern."**
*(Kết thúc, nhường lời cho người tiếp theo)*

