
## 1. MODULE 1: AUTH & USER MANAGEMENT

*   **Context (Bối cảnh):** Quản lý danh tính và quyền truy cập cho các vai trò người dùng (Citizen, Admin). Là nền tảng bảo mật cho toàn bộ hệ thống.
*   **Objective (Mục tiêu):** Đảm bảo người dùng có thể đăng ký, đăng nhập an toàn, quản lý profile cá nhân. Admin có thể phân quyền và quản lý trạng thái kích hoạt của người dùng.
*   **Risks (Rủi ro):**
    *   Bảo mật JWT (lưu trữ, hết hạn).
    *   Logic phân quyền (Role-based access control) cần chặt chẽ giữa Backend và Frontend.
*   **Goals/Metrics (Mục tiêu/Đo lường):**
    *   Entity: `User`, `Role`, `Permission`.
    *   Hoàn thành Trang Login / Register và Trang Admin với UserTable, RoleAssignment.
*   **Intermediate Steps (Các bước thực hiện):**
    *   Backend: `POST /api/auth/register`, `POST /api/auth/login`, `GET/PUT /api/users/{id}/profile`, `PUT /api/admin/users/{id}/role`.
    *   Frontend: Trang Login / Register, AuthContext + route guards.

---

## 2. MODULE 2: RESCUE REQUEST (Yêu cầu cứu hộ)

*   **Context (Bối cảnh):** Chức năng cốt lõi cho phép công dân gửi yêu cầu cứu hộ, đồng thời cung cấp giao diện cho Coordinator để xác minh, phân loại khẩn cấp và cập nhật trạng thái yêu cầu.
*   **Objective (Mục tiêu):** Xây dựng quy trình xử lý yêu cầu cứu hộ từ khi được gửi đến khi được xác nhận/xử lý xong.
*   **Risks (Rủi ro):**
    *   Xử lý file upload (ảnh) hiệu quả (MinIO).
    *   Đảm bảo tính chính xác của tọa độ vị trí (lat/lng).
    *   Quản lý chuỗi trạng thái phức tạp.
*   **Goals/Metrics (Mục tiêu/Đo lường):**
    *   Entity: `RescueRequest` (bao gồm `location lat/lng`, `imageUrls`, `urgencyLevel`).
    *   Enum Status: `PENDING` → `VERIFIED` → `ASSIGNED` → `IN_PROGRESS` → `COMPLETED / CANCELLED`.
*   **Intermediate Steps (Các bước thực hiện):**
    *   Backend: `POST /api/rescue-requests` (multipart), `PUT /api/rescue-requests/{id}/verify`, `PUT /api/rescue-requests/{id}/status`.
    *   Frontend: Form gửi yêu cầu (map pick location + upload ảnh), Trang "Yêu cầu của tôi", Bảng yêu cầu của Coordinator.

---

## 3. MODULE 3: TASK ASSIGNMENT (Điều phối nhiệm vụ)

*   **Context (Bối cảnh):** Liên kết yêu cầu cứu hộ đã xác minh với đội cứu hộ và phương tiện sẵn có.
*   **Objective (Mục tiêu):** Cho phép Coordinator tạo, điều phối, và theo dõi nhiệm vụ. Hỗ trợ Rescue Team cập nhật trạng thái và báo cáo kết quả.
*   **Risks (Rủi ro):**
    *   Đảm bảo chỉ định đúng đội/xe *sẵn sàng* (available).
    *   Xử lý báo cáo nhiệm vụ (text + ảnh).
*   **Goals/Metrics (Mục tiêu/Đo lường):**
    *   Entity: `Task` (liên kết `rescueRequest`, `rescueTeam`, `vehicle`).
    *   Status Task: `ACCEPTED` → `IN_PROGRESS` → `COMPLETED`.
    *   Dashboard nhiệm vụ dạng Kanban.
*   **Intermediate Steps (Các bước thực hiện):**
    *   Backend: `POST /api/tasks`, `GET /api/tasks/my-team`, `PUT /api/tasks/{id}/status`, `POST /api/tasks/{id}/report`.
    *   Frontend: Modal "Tạo nhiệm vụ" cho Coordinator, Trang "Nhiệm vụ của tôi" cho Rescue Team.

---

## 4. MODULE 4: VEHICLE MANAGEMENT (Phương tiện)

*   **Context (Bối cảnh):** Cung cấp khả năng quản lý danh sách và theo dõi trạng thái của các phương tiện cứu hộ (xe cứu thương, xe chữa cháy, v.v.).
*   **Objective (Mục tiêu):** Quản lý vòng đời thông tin phương tiện. Lọc được danh sách xe sẵn sàng (AVAILABLE) để phục vụ cho việc gán nhiệm vụ.
*   **Risks (Rủi ro):**
    *   Độ chính xác và kịp thời của việc cập nhật trạng thái.
*   **Goals/Metrics (Mục tiêu/Đo lường):**
    *   Entity: `Vehicle` (bao gồm `status`: `AVAILABLE / IN_USE / MAINTENANCE`).
    *   API `GET /api/vehicles/available`.
*   **Intermediate Steps (Các bước thực hiện):**
    *   Backend: `GET/POST /api/vehicles`, `PUT /api/vehicles/{id}`, `PUT /api/vehicles/{id}/status`.
    *   Frontend: VehicleTable + form thêm/sửa, Dropdown chọn vehicle (chỉ show AVAILABLE).

---

## 5. MODULE 5: RELIEF SUPPLY MANAGEMENT (Kho hàng cứu trợ)

*   **Context (Bối cảnh):** Quản lý hàng tồn kho cứu trợ (thực phẩm, thuốc men, vật tư) và theo dõi lịch sử phân phối.
*   **Objective (Mục tiêu):** Đảm bảo kiểm soát được số lượng hàng tồn kho và có cảnh báo khi hàng sắp hết. Ghi nhận chi tiết quá trình phân phối.
*   **Risks (Rủi ro):**
    *   Đồng bộ tồn kho khi nhập/xuất kho.
    *   Logic cảnh báo hàng sắp hết.
*   **Goals/Metrics (Mục tiêu/Đo lường):**
    *   Entity: `SupplyItem`, `Distribution`.
    *   API `GET /api/supply-items/low-stock`.
    *   Cột tồn kho có màu cảnh báo.
*   **Intermediate Steps (Các bước thực hiện):**
    *   Backend: `PUT /api/supply-items/{id}/stock`, `POST /api/distributions`.
    *   Frontend: SupplyTable + form nhập/xuất kho, Form ghi nhận phân phối.

---

## 6. MODULE 6: REAL-TIME & NOTIFICATIONS

*   **Context (Bối cảnh):** Cung cấp thông báo tức thời (real-time) về các thay đổi quan trọng trong hệ thống (trạng thái yêu cầu, nhiệm vụ mới).
*   **Objective (Mục tiêu):** Triển khai WebSocket để đẩy thông báo và cập nhật dữ liệu tự động mà không cần refresh trang.
*   **Risks (Rủi ro):**
    *   Quản lý kết nối và hiệu suất WebSocket.
    *   Đảm bảo thông báo được gửi đến đúng người nhận.
*   **Goals/Metrics (Mục tiêu/Đo lường):**
    *   WebSocket endpoint `/ws/notifications` (sử dụng Spring WebSocket + STOMP).
    *   Component: NotificationBell (badge số chưa đọc).
*   **Intermediate Steps (Các bước thực hiện):**
    *   Backend: Gửi notification khi yêu cầu/task thay đổi, `GET /api/notifications/my`.
    *   Frontend: WebSocket client, Toast notification, Auto-refresh danh sách.

---

## 7. MODULE 7: MAP & LOCATION

*   **Context (Bối cảnh):** Tích hợp tính năng bản đồ để trực quan hóa vị trí yêu cầu cứu hộ và các nhiệm vụ đang diễn ra.
*   **Objective (Mục tiêu):** Cung cấp Map View cho Coordinator (để điều phối) và Rescue Team (để nhận chỉ đường).
*   **Risks (Rủi ro):**
    *   Tối ưu hóa hiệu suất hiển thị (marker, cluster).
    *   Tính chính xác của dữ liệu vị trí trên bản đồ.
*   **Goals/Metrics (Mục tiêu/Đo lường):**
    *   Sử dụng Leaflet map component.
    *   API trả về dữ liệu map: `/api/rescue-requests/map-data`.
*   **Intermediate Steps (Các bước thực hiện):**
    *   Frontend: Coordinator map view (marker màu theo urgency), Rescue Team (hiển thị vị trí yêu cầu + link Google Maps), Cluster markers.

---

## 8. MODULE 8: STATISTICS & REPORTS

*   **Context (Bối cảnh):** Cung cấp các công cụ báo cáo và thống kê để đánh giá hiệu suất hoạt động.
*   **Objective (Mục tiêu):** Xây dựng các API trả về dữ liệu thống kê đa chiều (theo thời gian, theo khẩn cấp, theo đội) và Dashboard trực quan hóa.
*   **Risks (Rủi ro):**
    *   Hiệu suất truy vấn dữ liệu lớn cho các báo cáo phức tạp.
    *   Đảm bảo tính chính xác khi xuất file (CSV/Excel).
*   **Goals/Metrics (Mục tiêu/Đo lường):**
    *   Các API báo cáo: `overview`, `by-urgency`, `by-team`, `timeline`.
    *   Dashboard với Recharts (biểu đồ cột/pie/line).
*   **Intermediate Steps (Các bước thực hiện):**
    *   Backend: `GET /api/reports/...`, `GET /api/admin/reports/export` (Apache POI).
    *   Frontend: Dashboard với Recharts, Bộ lọc thời gian + nút Export CSV.

---

## 9. MODULE 9: SYSTEM CONFIG (Admin)

*   **Context (Bối cảnh):** Cung cấp chức năng quản trị để cấu hình các tham số hệ thống và quản lý dữ liệu danh mục (master data).
*   **Objective (Mục tiêu):** Cho phép Admin tùy chỉnh các ngưỡng (thresholds) và quản lý các loại danh mục (loại phương tiện, loại hàng cứu trợ).
*   **Risks (Rủi ro):**
    *   Bảo mật quyền truy cập Admin.
    *   Đảm bảo dữ liệu cấu hình được áp dụng đồng bộ.
*   **Goals/Metrics (Mục tiêu/Đo lường):**
    *   API cấu hình: `GET/PUT /api/admin/config`.
    *   API danh mục: `GET/POST/PUT/DELETE /api/admin/categories`.
*   **Intermediate Steps (Các bước thực hiện):**
    *   Frontend: Trang Config (form chỉnh tham số), Trang Categories (CRUD table).