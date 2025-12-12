<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <title>Thống Kê Thư Viện</title>
  
  <link rel="stylesheet" href="${pageContext.request.contextPath}/view/style.css?v=17" />
  
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
  <div class="container">
    <aside class="sidebar">
      <h2>📘 Thủ thư</h2>
      <ul>
        <li><a href="${pageContext.request.contextPath}/TrangChu">🏠 Trang chủ</a></li>
        <li><a href="${pageContext.request.contextPath}/Sach">📚Quản Lý Sách</a></li>
        <li><a href="${pageContext.request.contextPath}/TacGia">✍️Quản Lý Tác giả</a></li>
        <li><a href="${pageContext.request.contextPath}/DocGia">🧑‍💼Quản Lý Độc giả</a></li>
        <li><a href="${pageContext.request.contextPath}/MuonTra">🔄Quản Lý Mượn/Trả</a></li>
        <li><a href="${pageContext.request.contextPath}/TraCuu">🔍 Tra cứu</a></li>
        <li><a href="${pageContext.request.contextPath}/ThongKe" class="active">📊 Thống kê</a></li>
        <li><a href="${pageContext.request.contextPath}/DangXuat">🚪 Đăng xuất</a></li>
      </ul>
    </aside>

    <main class="main-content">
      <h1>📊 Bảng Thống Kê Hoạt Động</h1>

      <div class="stats-grid">
          <div class="stat-card" style="border-bottom: 4px solid #0d6efd;">
              <span class="icon-stat">📚</span>
              <h3>${slSach}</h3>
              <p>Tổng Đầu Sách</p>
          </div>
          <div class="stat-card" style="border-bottom: 4px solid #198754;">
              <span class="icon-stat">🧑‍💼</span>
              <h3>${slDocGia}</h3>
              <p>Tổng Độc Giả</p>
          </div>
          <div class="stat-card" style="border-bottom: 4px solid #ffc107;">
              <span class="icon-stat">🔄</span>
              <h3>${slDangMuon}</h3>
              <p>Phiếu Đang Mượn</p>
          </div>
          <div class="stat-card" style="border-bottom: 4px solid #dc3545;">
              <span class="icon-stat">💰</span>
              <h3><fmt:formatNumber value="${tongPhat}" type="number" maxFractionDigits="0"/></h3>
              <p>Doanh Thu Phạt (VNĐ)</p>
          </div>
      </div>

      <div class="chart-wrapper">
          <div class="chart-container">
              <h3>📈 Top 5 Sách Mượn Nhiều Nhất</h3>
              <div class="chart-box">
                  <canvas id="topBookChart"></canvas>
              </div>
          </div>

          <div class="chart-container">
              <h3>🏆 Xếp Hạng</h3>
              <table class="data-table" style="margin-top: 10px;">
                  <thead>
                      <tr><th>Tên Sách</th><th>Lượt</th></tr>
                  </thead>
                  <tbody>
                      <c:forEach var="item" items="${topSach}">
                          <tr>
                              <td>${item[0]}</td>
                              <td style="text-align: center; font-weight: bold; color: #0d6efd;">${item[1]}</td>
                          </tr>
                      </c:forEach>
                  </tbody>
              </table>
          </div>
      </div>

      <div class="chart-container" style="margin-top: 30px;">
          <h3 style="color: #fd7e14; display: flex; align-items: center; gap: 10px;">
              ⚠️ Danh Sách Độc Giả Trễ Hạn
          </h3>
          <table class="data-table" style="margin-top: 15px;">
              <thead>
                  <tr><th>Mã Độc Giả</th><th>Họ Tên</th><th>Số Điện Thoại</th><th>Hành Động</th></tr>
              </thead>
              <tbody>
                  <c:if test="${not empty dsTreHan}">
                      <c:forEach var="dg" items="${dsTreHan}">
                          <tr>
                              <td><strong>${dg.maDocGia}</strong></td>
                              <td>${dg.hoTen}</td>
                              <td>${dg.soDienThoai}</td>
                              <td>
                                  <a href="${pageContext.request.contextPath}/MuonTra?tuKhoa=${dg.maDocGia}" 
                                     class="btn-edit" style="text-decoration: none; font-size: 13px;">🔍 Xem phiếu nợ</a>
                              </td>
                          </tr>
                      </c:forEach>
                  </c:if>
                  <c:if test="${empty dsTreHan}">
                      <tr><td colspan="4" style="text-align: center;">Không có độc giả nào trễ hạn.</td></tr>
                  </c:if>
              </tbody>
          </table>
      </div>	
    </main>
  </div>

  <script src="${pageContext.request.contextPath}/view/script.js?v=7"></script>

  <script>
   var tenSachArr = [];
    var soLuotArr = [];

    <c:forEach var="item" items="${topSach}">
        tenSachArr.push("${item[0]}"); 
        soLuotArr.push(${item[1]});    
    </c:forEach>
    document.addEventListener("DOMContentLoaded", function() {
        // [QUAN TRỌNG] Bạn chưa gọi hàm này trong code cũ của bạn
        if (typeof veBieuDoTopSach === "function") {
             veBieuDoTopSach(tenSachArr, soLuotArr);
        } else {
             console.error("Hàm veBieuDoTopSach chưa được định nghĩa!");
        }
    });
  </script>
</body>
</html>