<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
  // Bảo vệ trang (Giữ nguyên code của bạn)
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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/view/style.css?v=18" />
  
  <style>
  .alert-container {
    max-height: 400px; /* Giới hạn chiều cao */
    overflow-y: auto;  /* Hiện thanh cuộn nếu danh sách quá dài */
    border: 1px solid #eee;
    padding: 10px;
    border-radius: 5px;
}
      /* CSS bổ sung cho Dashboard */
      .alert-item {
          border-bottom: 1px solid #eee;
          padding: 10px 0;
      }
      .alert-item:last-child { border-bottom: none; }
      .alert-red { color: #dc3545; font-weight: bold; }
  </style>
</head>
<body>
  <div class="container">
    <aside class="sidebar">
      <h2>📘 Thư viện ABC</h2>
      <ul>
        <li><a href="${pageContext.request.contextPath}/TrangChuThuThu" class="active">🏠 Trang chủ</a></li>
        <li><a href="${pageContext.request.contextPath}/Sach">📚Quản Lý Sách</a></li>
        <li><a href="${pageContext.request.contextPath}/TacGia">✍️Quản Lý Tác giả</a></li>
        <li><a href="${pageContext.request.contextPath}/DocGia">🧑‍💼Quản Lý Độc giả</a></li>
        <li><a href="${pageContext.request.contextPath}/MuonTra">🔄Quản Lý Mượn/Trả</a></li>
        <li><a href="${pageContext.request.contextPath}/ThongKe">📊 Thống kê</a></li>
        <li><a href="${pageContext.request.contextPath}/DangXuat">🚪 Đăng xuất</a></li>
      </ul>
    </aside>

    <main class="main-content">
      <h1>Xin chào, <span style="color: #0d6efd;">${sessionScope.thuthu_account.hoTen}</span> 👋</h1>

      <div class="stats">
        <div class="stat-box green">
          <span style="font-size: 24px;">📚</span><br>Tổng số sách<br>
          <strong>${slSach}</strong>
        </div>
        <div class="stat-box blue">
          <span style="font-size: 24px;">🧑‍💼</span><br>Tổng số độc giả<br>
          <strong>${slDocGia}</strong>
        </div>
        <div class="stat-box orange">
          <span style="font-size: 24px;">🔄</span><br>Phiếu đang mượn<br>
          <strong>${slDangMuon}</strong>
        </div>
        <div class="stat-box red">
          <span style="font-size: 24px;">⚠️</span><br>Độc giả trễ hạn<br>
          <strong>${slTreHan}</strong>
        </div>
      </div>
      <div class="alert-list">
    <h3>📌 Cần chú ý: Danh sách Phiếu mượn quá hạn</h3>

    <div class="alert-container"> <c:if test="${not empty alertList}">
            <c:forEach var="pm" items="${alertList}">
                <div class="alert-item">
                    <p><strong>Phiếu:</strong> <a href="${pageContext.request.contextPath}/MuonTra?action=detail&maPhieu=${pm.maPhieuMuon}">${pm.maPhieuMuon}</a></p>
                    <p><strong>Độc giả:</strong> ${pm.docGia.hoTen} (${pm.docGia.maDocGia})</p>
                    <p>
                        <strong>Hạn trả:</strong> 
                        <span class="alert-red">
                            <fmt:formatDate value="${pm.ngayHenTra}" pattern="dd/MM/yyyy"/>
                        </span>
                        (Trễ: <strong style="color:red">${pm.soNgayTreHan}</strong> ngày)
                    </p>
                </div>
            </c:forEach>
        </c:if>

        <c:if test="${empty alertList}">
            <p style="color: green; text-align: center; margin-top: 10px;">🎉 Hiện tại không có phiếu nào quá hạn!</p>
        </c:if>
    </div>
  </div>
    </main>
  </div>
</body>
</html>