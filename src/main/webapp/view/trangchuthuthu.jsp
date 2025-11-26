<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  // 1. BẢO VỆ TRANG: Kiểm tra xem đã đăng nhập chưa
  // Nếu session không có "thuthu_account" thì đuổi về trang đăng nhập
  if (session.getAttribute("thuthu_account") == null) {
      response.sendRedirect(request.getContextPath() + "/view/dangnhap.jsp");
      return;
  }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <title>Trang chủ Thủ thư</title>
  <link rel="stylesheet" href="style.css" />
</head>
<body>
  <div class="container">
    <aside class="sidebar">
      <h2>📘 Thư viện ABC</h2>
      <ul>
        <li><a href="#">🏠 Trang chủ</a></li>
        <li><a href="#">📚 Quản lý Sách</a></li>
        <li><a href="#">✍️ Quản lý Tác giả</a></li>
        <li><a href="#">🧑‍💼 Quản lý Độc giả</a></li>
        <li><a href="#">🔄 Quản lý Mượn/Trả</a></li>
        <li><a href="#">🔍 Tra cứu</a></li>
        <li><a href="#">📊 Thống kê</a></li>
        <li>
            <a href="${pageContext.request.contextPath}/DangXuat">🚪 Đăng xuất</a>
        </li>
      </ul>
    </aside>

    <main class="main-content">
      <h1>Xin chào, ${sessionScope.thuthu_account.tenDangNhap} 👋</h1>

      <div class="stats">
        <div class="stat-box green">📚<br>Tổng số sách<br><strong>1,250</strong></div>
        <div class="stat-box blue">🧑‍💼<br>Tổng số độc giả<br><strong>340</strong></div>
        <div class="stat-box orange">🔄<br>Sách đang mượn<br><strong>75</strong></div>
        <div class="stat-box red">⚠️<br>Sách quá hạn<br><strong>8</strong></div>
      </div>

      <div class="quick-actions">
        <h3>⚡ Hành động nhanh</h3>
        <button class="btn-add">➕ Lập phiếu mượn</button>
        <button class="btn-add">➕ Thêm sách mới</button>
        <button class="btn-add">➕ Thêm độc giả mới</button>
      </div>

      <div class="alert-list">
        <h3>📌 Danh sách cần chú ý (Sách quá hạn)</h3>
        <p><strong>Tên sách:</strong> Nhà Giả Kim</p>
        <p><strong>Độc giả:</strong> Nguyễn Văn A</p>
        <p><strong>Ngày hẹn trả:</strong> 20/10/2025</p>
      </div>
    </main>
  </div>
</body>
</html>