<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
  // 1. BẢO VỆ TRANG: Kiểm tra xem đã đăng nhập chưa
  // Nếu session không có "admin_account" thì đuổi về trang đăng nhập
  if (session.getAttribute("admin_account") == null) {
      response.sendRedirect(request.getContextPath() + "/view/dangnhap.jsp");
      return;
  }
%>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <title>Trang chủ Admin</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/view/style.css" />
</head>
<body>
  <div class="container">
    <aside class="sidebar">
      <h2>📘 Thư viện ABC</h2>
      <ul>
        <li><a href="#">🏠 Trang chủ</a></li>
        <li><a href="${pageContext.request.contextPath}/ThuThu">👤 Tài khoản Thủ Thư</a></li>
        <li><a href="${pageContext.request.contextPath}/QuyDinh">⚙️ Cấu Hình Quy Định</a></li>
        <li><a href="#">📂 Quản Lý Thể loại</a></li>
        <li><a href="#">🏢 Nhà Xuất Bản</a></li>
        <li><a href="#">🧑‍🏫 Loại Độc Giả</a></li>
        
        <li>
            <a href="${pageContext.request.contextPath}/DangXuat">🚪 Đăng xuất</a>
        </li>
      </ul>
    </aside>

    <main class="main-content">
      
      <h1>Xin chào, ${sessionScope.admin_account.tenDangNhap} 👋</h1>

      <div class="stats">
        <div class="stat-box blue">📂<br>Thể loại sách<br><strong>12</strong></div>
        <div class="stat-box green">🏢<br>Nhà xuất bản<br><strong>8</strong></div>
        <div class="stat-box orange">🧑‍🏫<br>Loại độc giả<br><strong>3</strong></div>
        <div class="stat-box gray">👤<br>Tài khoản thủ thư<br><strong>5</strong></div>
      </div>

      <div class="quick-actions"> 
        <h3>⚡ Hành động nhanh</h3>
        <button class="btn-add">➕ Thêm thể loại</button>
        <button class="btn-add">➕ Thêm NXB</button>
        <button class="btn-add">➕ Thêm loại độc giả</button>
      </div>

      <div class="config-box">
        <h3>⚙️ Cấu hình Quy định</h3>
        <form>
          <label>Số ngày mượn tối đa</label>
          <input type="number" value="14" />

          <label>Phí phạt trễ hạn (VND/ngày)</label>
          <input type="number" value="5000" />

          <button class="btn-search">💾 Lưu thay đổi</button>
        </form>
      </div>
    </main>
  </div>
</body>
</html>