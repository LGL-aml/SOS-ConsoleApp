# 🗺️ Luồng hoạt động BE + FE — Presentation Guide
> **FE-03**: Confirm Rescue or Relief Completion  
> **FE-06**: Verify and Classify Rescue Requests

---

## FE-06: Verify and Classify Rescue Requests
### *(Nhận, xác minh & phân loại yêu cầu cứu hộ)*

---

### 📌 UC 1: Receive & Verify Requests — *Nhận & xác minh yêu cầu*

#### 🔷 FLOW OVERVIEW
```
Citizen submits SOS → BE saves as PENDING → Coordinator sees list →
AI analyzes → Coordinator verifies → Status becomes VERIFIED

(Người dân gửi SOS → BE lưu trạng thái PENDING → Coordinator thấy danh sách →
AI phân tích → Coordinator xác minh → Trạng thái chuyển thành VERIFIED)
```

---

#### 🟩 BACKEND FILES

| File | Role | *(Vai trò)* |
|------|------|-------------|
| `controller/RescueRequestController.java` | HTTP entry point, exposes REST endpoints | *Cổng vào HTTP, định nghĩa các API endpoint* |
| `service/RescueRequestService.java` | Interface contract | *Giao diện định nghĩa các phương thức nghiệp vụ* |
| `service/impl/RescueRequestServiceImpl.java` | Business logic | *Logic nghiệp vụ thực tế* |
| `entity/RescueRequest.java` | JPA entity mapped to `rescue_requests` table | *Entity JPA ánh xạ vào bảng `rescue_requests`* |
| `entity/enums/RequestStatus.java` | State machine values: `PENDING → VERIFIED → ...` | *Các giá trị trạng thái của yêu cầu* |
| `entity/enums/UrgencyLevel.java` | `LOW / MEDIUM / HIGH / CRITICAL` | *Mức độ khẩn cấp* |

**Key endpoint called:**
```
PUT /api/rescue-requests/{id}/verify
→ RescueRequestController.verify()
→ RescueRequestServiceImpl.verify()
  - Validates current status == PENDING
    (Kiểm tra trạng thái hiện tại phải là PENDING)
  - Sets status = VERIFIED
    (Chuyển trạng thái thành VERIFIED)
  - Sets verifiedBy, verifyNote, verifiedAt
    (Lưu thông tin người xác minh, ghi chú, thời gian)
  - Sends notification to citizen via NotificationService
    (Gửi thông báo về cho người dân)
```

---

#### 🟦 FRONTEND FILES

| File | Role | *(Vai trò)* |
|------|------|-------------|
| `app/coordinator/requests/page.tsx` | Main page — list all requests, Verify modal, Assign modal | *Trang chính — danh sách yêu cầu, modal xác minh, modal điều phối* |
| `services/rescueRequest.service.ts` | `rescueRequestService.verify(id, verifyNote)` → calls `PUT /verify` | *Service gọi API xác minh* |
| `services/ai.service.ts` | `aiService.analyzeRequest()` → AI-powered urgency analysis | *Service gọi AI phân tích mức độ khẩn cấp* |

**Frontend flow in `coordinator/requests/page.tsx`:**
```
1. useQuery(['admin-requests']) → GET /api/rescue-requests
   (Lấy toàn bộ danh sách yêu cầu, tự động refresh)

2. Coordinator clicks "Verify" → handleOpenVerify(request)
   (Click nút Xác minh → mở modal xác minh)

3. [Optional] handleAnalyze() → aiService.analyzeRequest()
   → AI returns urgency suggestion
   (Tùy chọn: phân tích AI → AI trả về gợi ý mức độ khẩn cấp)

4. Submit verifyMutation → rescueRequestService.verify(id, verifyNote)
   → invalidateQueries → list refreshes
   (Gửi xác minh → cập nhật danh sách tự động)
```

---

### 📌 UC 1a: View Rescue Map — *Xem bản đồ cứu hộ*

#### 🟩 BACKEND FILES

| File | Role | *(Vai trò)* |
|------|------|-------------|
| `controller/RescueRequestController.java` | `GET /api/rescue-requests/map-data` | *Endpoint lấy dữ liệu bản đồ* |
| `service/impl/RescueRequestServiceImpl.java` | `getMapData()` — fetches only active requests with lat/lng/urgency | *Lấy các yêu cầu đang active kèm tọa độ và mức khẩn* |
| `controller/MapController.java` | `GET /api/map/directions` — Goong Maps routing | *Tính toán đường đi qua Goong Maps* |

#### 🟦 FRONTEND FILES

| File | Role | *(Vai trò)* |
|------|------|-------------|
| `app/coordinator/map/page.tsx` | Full-screen interactive map page for Coordinator | *Trang bản đồ toàn màn hình cho Coordinator* |
| `components/common/GoongMap.tsx` | Reusable map component, renders colored pins by urgency level | *Component bản đồ tái sử dụng, hiển thị ghim màu theo mức khẩn cấp* |
| `services/rescueRequest.service.ts` | `getMapData()` / `getAll()` → feeds the map | *Cung cấp dữ liệu cho bản đồ* |

```
GoongMap renders pins by urgency color:
  🔴 CRITICAL  →  emergency color (đỏ — nguy hiểm)
  🟠 HIGH      →  orange pin      (cam — khẩn cấp)
  🟡 MEDIUM    →  warning color   (vàng — trung bình)
  🔵 LOW       →  primary color   (xanh — thấp)

Auto-refresh every 15 seconds:
  refetchInterval: 15000
  (Tự động làm mới dữ liệu bản đồ mỗi 15 giây)
```

---

### 📌 UC 1b: Merge Duplicates — *Gộp yêu cầu trùng lặp*

#### 🟩 BACKEND FILES

| File | Role | *(Vai trò)* |
|------|------|-------------|
| `controller/RescueRequestController.java` | `POST /api/rescue-requests/{id}/merge` | *Endpoint gộp yêu cầu trùng* |
| `service/impl/RescueRequestServiceImpl.java` | Merge logic — keeps primary, cancels duplicates | *Logic giữ yêu cầu chính, hủy các bản trùng* |

#### 🟦 FRONTEND FILES

| File | Role | *(Vai trò)* |
|------|------|-------------|
| `app/coordinator/requests/page.tsx` | Merge mode toggle, checkbox selection, merge modal | *Bật chế độ gộp, chọn checkbox, mở modal xác nhận gộp* |
| `services/rescueRequest.service.ts` | `mergeDuplicates(primaryId, duplicateIds[])` | *Gọi API gộp yêu cầu* |

```
FE Flow:
  1. Coordinator clicks "Merge Mode" → isMergeMode = true
     (Click "Chế độ gộp" → bật chế độ chọn nhiều)

  2. Selects multiple suspected duplicate requests via checkbox
     (Chọn các yêu cầu nghi trùng bằng checkbox)

  3. Opens Merge Modal → selects which one is "primary"
     (Mở modal → chọn yêu cầu chính cần giữ lại)

  4. mergeMutation → POST /api/rescue-requests/{primaryId}/merge
     → duplicates get status = CANCELLED
     (Gửi request gộp → các bản trùng bị chuyển thành CANCELLED)
```

---

### 📌 UC 2: Categorize Emergency Level — *Phân loại mức độ khẩn cấp*

#### 🟩 BACKEND FILES

| File | Role | *(Vai trò)* |
|------|------|-------------|
| `entity/enums/UrgencyLevel.java` | `LOW / MEDIUM / HIGH / CRITICAL` | *4 cấp độ khẩn cấp* |
| `controller/AIController.java` | `POST /api/ai/analyze-request` | *Endpoint AI phân tích nội dung + tọa độ → đề xuất UrgencyLevel* |
| `service/impl/AIServiceImpl.java` | Calls AI model, returns structured analysis | *Gọi mô hình AI, trả về phân tích có cấu trúc* |
| `controller/RescueRequestController.java` | `PUT /api/rescue-requests/{id}/verify` — Coordinator can override urgency in `verifyNote` | *Coordinator có thể ghi đè mức khẩn khi xác minh* |

#### 🟦 FRONTEND FILES

| File | Role | *(Vai trò)* |
|------|------|-------------|
| `app/coordinator/requests/page.tsx` | Displays urgency badge per request; "AI Analyze" button inside Verify modal | *Hiển thị badge mức khẩn; nút phân tích AI trong modal xác minh* |
| `services/ai.service.ts` | `analyzeRequest({ description, latitude, longitude, locationNote })` | *Gọi AI phân tích để gợi ý mức độ khẩn cấp* |

```
Urgency badge colors in FE:
  CRITICAL → bg-emergency  (đỏ)
  HIGH     → bg-orange-500 (cam)
  MEDIUM   → bg-warning    (vàng)
  LOW      → bg-primary    (xanh)
```

---
---

## FE-03: Confirm Rescue or Relief Completion
### *(Xác nhận hoàn thành cứu hộ / cứu trợ)*

---

### 📌 UC 1: View Assigned Mission — *Xem nhiệm vụ được phân công*

#### 🟩 BACKEND FILES

| File | Role | *(Vai trò)* |
|------|------|-------------|
| `controller/TaskController.java` | `GET /api/tasks/my-team` | *Endpoint lấy danh sách nhiệm vụ của team mình* |
| `service/impl/TaskServiceImpl.java` | `getMyTeamTasks()` — queries by team member's email | *Truy vấn nhiệm vụ theo email thành viên team* |
| `entity/Task.java` | Entity: links `RescueRequest` + `RescueTeam` + `Vehicle` | *Entity kết nối yêu cầu cứu hộ, đội và phương tiện* |
| `entity/enums/TaskStatus.java` | `ACCEPTED → IN_PROGRESS → COMPLETED / FAILED` | *Các giá trị trạng thái nhiệm vụ* |

#### 🟦 FRONTEND FILES

| File | Role | *(Vai trò)* |
|------|------|-------------|
| `app/rescue-team/page.tsx` | **Main dashboard for Rescue Team** — mission list, map, GPS, chat | *Dashboard chính cho đội cứu hộ — danh sách nhiệm vụ, bản đồ, GPS, chat* |
| `services/task.service.ts` | `getMyTeamTasks()` → `GET /api/tasks/my-team` | *Lấy danh sách nhiệm vụ của team mình* |

```
Auto-refresh every 15 seconds:
  useQuery(['my-team-tasks'], refetchInterval: 15000)
  → missions: filter status !== 'COMPLETED'         (nhiệm vụ đang active)
  → completedMissions: filter status === 'COMPLETED' (nhiệm vụ đã xong)
  (Tự động làm mới mỗi 15 giây)

Real-time GPS tracking (in rescue-team/page.tsx):
  navigator.geolocation.watchPosition()
  → teamService.updateLocation(teamId, lat, lng)    (cập nhật REST API)
  → Convex real-time: updateTeamLocation(...)       (cập nhật real-time map)
  (Theo dõi GPS liên tục — cập nhật vị trí lên server và Convex real-time)
```

---

### 📌 UC 2: Confirm Rescue — *Xác nhận hoàn thành cứu hộ*

#### 🟩 BACKEND FILES

| File | Role | *(Vai trò)* |
|------|------|-------------|
| `controller/TaskController.java` | `PUT /api/tasks/{id}/status` | *Cập nhật trạng thái nhiệm vụ* |
| `service/impl/TaskServiceImpl.java` | `updateStatus()` — state machine: `ACCEPTED → IN_PROGRESS → COMPLETED` | *Máy trạng thái nhiệm vụ* |
| | When `COMPLETED`: frees Vehicle → `AVAILABLE`, sets `RescueRequest` → `COMPLETED` | *Khi hoàn tất: giải phóng phương tiện, đánh dấu yêu cầu SOS hoàn tất* |
| `controller/TaskController.java` | `POST /api/tasks/{id}/report` (multipart/form-data) | *Gửi báo cáo kết quả + hình ảnh* |
| `service/impl/TaskServiceImpl.java` | `addReport()` — saves `TaskReport` entity, uploads images via MinIO | *Lưu báo cáo + upload ảnh vào MinIO storage* |

**State transition on COMPLETE:**
```
Task: IN_PROGRESS → COMPLETED
  ├── Vehicle.status     = AVAILABLE   (phương tiện được giải phóng)
  ├── RescueRequest.status = COMPLETED (yêu cầu SOS được đóng lại)
  └── Notification → Coordinator      (thông báo cho Coordinator)
```

#### 🟦 FRONTEND FILES

| File | Role | *(Vai trò)* |
|------|------|-------------|
| `app/rescue-team/page.tsx` | `completeMutation` → `updateStatus(id, "COMPLETED", note)` | *Mutation xác nhận hoàn thành kèm ghi chú báo cáo* |
| | `inProgressMutation` → `updateStatus(id, "IN_PROGRESS")` | *Mutation chuyển sang đang thực hiện* |
| `services/task.service.ts` | `updateStatus(id, status, note?)` → `PUT /api/tasks/{id}/status` | *Gọi API cập nhật trạng thái nhiệm vụ* |
| | `addReport(id, formData)` → `POST /api/tasks/{id}/report` (multipart) | *Gửi báo cáo + ảnh đính kèm* |

```
FE Flow (in rescue-team/page.tsx):
  1. Rescue Team selects active mission → activeMission state
     (Chọn nhiệm vụ đang hoạt động từ danh sách)

  2. Click "Start" → inProgressMutation → status = IN_PROGRESS
     (Bắt đầu xuất phát → chuyển trạng thái thành Đang thực hiện)

  3. Click "Complete" → isReportModalOpen = true
     (Click Hoàn tất → mở modal điền báo cáo kết quả)

  4. Submit → completeMutation(id, reportNote)
     → updateStatus(COMPLETED) + optionally addReport()
     (Gửi → xác nhận hoàn tất + đính kèm báo cáo có ảnh)
```

---

### 📌 UC 3: Cancel Request — *Hủy yêu cầu*

#### 🟩 BACKEND FILES

| File | Role | *(Vai trò)* |
|------|------|-------------|
| `controller/RescueRequestController.java` | `PUT /api/rescue-requests/{id}/cancel` | *Endpoint hủy yêu cầu* |
| `service/impl/RescueRequestServiceImpl.java` | `cancelByRequester()` — validates ownership, sets status = `CANCELLED` | *Kiểm tra quyền sở hữu, chuyển trạng thái thành CANCELLED* |
| | `confirmSafe()` — Citizen confirms they are safe → `COMPLETED` | *Người dân xác nhận đã an toàn → tự đóng yêu cầu thành COMPLETED* |

**Rules enforced by BE:**
```
- Cannot cancel if status == COMPLETED or CANCELLED
  (Không thể hủy nếu đã hoàn thành hoặc đã bị hủy rồi)

- Authenticated user can only cancel THEIR OWN request
  (Người dùng đã đăng nhập chỉ được hủy yêu cầu của chính mình)

- Anonymous guests can cancel by requestId only (no ownership check)
  (Khách ẩn danh có thể hủy bằng requestId mà không cần xác thực)
```

#### 🟦 FRONTEND FILES

| File | Role | *(Vai trò)* |
|------|------|-------------|
| `app/citizen/` | Citizen dashboard — calls `rescueRequestService.cancel(id)` | *Dashboard người dân — hủy yêu cầu của mình* |
| `app/coordinator/requests/page.tsx` | Coordinator can cancel via `updateStatus(id, 'CANCELLED')` or hard delete via `deleteMutation` | *Coordinator cũng có thể hủy hoặc xóa cứng yêu cầu* |
| `services/rescueRequest.service.ts` | `cancel(id)` → `PUT /api/rescue-requests/{id}/cancel` | *Gọi API hủy yêu cầu* |
| | `confirmSafe(id)` → `PUT /api/rescue-requests/{id}/confirm-safe` | *Người dân tự xác nhận đã an toàn* |

---
---

## 🔄 Complete State Machine Summary
### *(Tổng hợp máy trạng thái toàn luồng)*

```
                 ◄──── FE-06 territory ────►              ◄──── FE-03 territory ────►

  Citizen submits SOS
         │
         ▼
     [PENDING]  ──(Coordinator verifies)──►  [VERIFIED]  ──(Coordinator assigns)──►  [ASSIGNED]
         │                 FE-06: UC1                           FE-06 → FE-03              │
         │                                                                          Task: [ACCEPTED]
         │                                                                                 │
         ▼                                                                   (Team starts work)
    [CANCELLED]  ◄── cancel at any point ──────────────────────────────────►  Task: [IN_PROGRESS]
                       FE-03: UC3                                            Request: [IN_PROGRESS]
                                                                                         │
                                                                              (Team completes + report)
                                                                                         │
                                                                               Task: [COMPLETED]
                                                                            Request: [COMPLETED]  ◄── FE-03: UC2
                                                                                         │
                                                                               Vehicle: [AVAILABLE]
                                                                               Notification → Coordinator
```

**Task Status transitions only:**
```
ACCEPTED → IN_PROGRESS → COMPLETED
                       ↘ FAILED  (incident reported → Request resets to VERIFIED for reassignment)
                                  (Sự cố → yêu cầu được trả về VERIFIED để điều phối lại)
```

---

## 📁 Quick File Reference Card
### *(Bảng tham chiếu file nhanh)*

| Feature | BE Controller | BE Service Impl | FE Page | FE Service |
|---------|--------------|-----------------|---------|------------|
| **Verify Request** | `RescueRequestController.java` | `RescueRequestServiceImpl.java` | `coordinator/requests/page.tsx` | `rescueRequest.service.ts` |
| **Rescue Map** | `RescueRequestController.java` + `MapController.java` | `RescueRequestServiceImpl.getMapData()` | `coordinator/map/page.tsx` + `GoongMap.tsx` | `rescueRequest.service.ts` |
| **Merge Duplicates** | `RescueRequestController.java` | `RescueRequestServiceImpl.java` | `coordinator/requests/page.tsx` | `rescueRequest.service.ts` |
| **Categorize Level** | `AIController.java` | `AIServiceImpl.java` | `coordinator/requests/page.tsx` | `ai.service.ts` |
| **View Mission** | `TaskController.java` | `TaskServiceImpl.java` | `rescue-team/page.tsx` | `task.service.ts` |
| **Confirm Rescue** | `TaskController.java` | `TaskServiceImpl.java` | `rescue-team/page.tsx` | `task.service.ts` |
| **Cancel Request** | `RescueRequestController.java` | `RescueRequestServiceImpl.java` | `coordinator/requests/page.tsx` | `rescueRequest.service.ts` |

---

## 🗂️ Key Entity Relationships
### *(Quan hệ giữa các Entity chính)*

```
RescueRequest (rescue_requests)
  ├── citizen_id      → User           (người dân đã đăng nhập / null nếu ẩn danh)
  ├── urgencyLevel    → UrgencyLevel   (LOW / MEDIUM / HIGH / CRITICAL)
  ├── status          → RequestStatus  (PENDING → VERIFIED → ASSIGNED → IN_PROGRESS → COMPLETED / CANCELLED)
  ├── verified_by     → User           (coordinator xác minh)
  └── imageUrls       → List<String>   (ảnh lưu trên MinIO)

Task (tasks)
  ├── rescue_request_id → RescueRequest  (yêu cầu SOS được gán)
  ├── rescue_team_id    → RescueTeam     (đội được điều phối)
  ├── vehicle_id        → Vehicle        (phương tiện sử dụng)
  ├── created_by        → User           (coordinator tạo task)
  ├── status            → TaskStatus     (ACCEPTED / IN_PROGRESS / COMPLETED / FAILED)
  └── reports           → List<TaskReport> (báo cáo kết quả + ảnh)
```

