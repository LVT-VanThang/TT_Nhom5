<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/view/style.css" />
</head>
<body>
	<div class="container">
		<aside class="sidebar">
			<h2>📘 Thư viện ABC</h2>
			<ul>
				<li><a href="${pageContext.request.contextPath}/TrangChuQuanTriVien" class="active">🏠 Trang chủ</a></li>
				<li><a href="${pageContext.request.contextPath}/ThuThu">👤 Tài khoản Thủ thư</a></li>
				<li><a href="${pageContext.request.contextPath}/QuyDinh">⚙️ Cấu Hình Quy Định</a></li>
				<li><a href="${pageContext.request.contextPath}/TheLoai">📂 Quản Lý Thể loại</a></li>
				<li><a href="${pageContext.request.contextPath}/NhaXuatBan">🏢 Nhà xuất bản</a></li>
				<li><a href="${pageContext.request.contextPath}/LoaiDocGia">🧑‍🏫 Loại Độc Giả</a></li>
                <li><a href="${pageContext.request.contextPath}/DangXuat">🚪 Đăng xuất</a></li>
			</ul>
		</aside>

		<main class="main-content">

			<h1>Xin chào, ${sessionScope.admin_account.tenDangNhap} 👋</h1>

			<div class="stats">
				<div class="stat-box blue">
					📂<br>Thể loại sách<br>
					<strong>${slTheLoai}</strong>
				</div>
				<div class="stat-box green">
					🏢<br>Nhà xuất bản<br>
					<strong>${slNXB}</strong>
				</div>
				<div class="stat-box orange">
					🧑‍🏫<br>Loại độc giả<br>
					<strong>${slLoaiDocGia}</strong>
				</div>
				<div class="stat-box gray">
					👤<br>Tài khoản thủ thư<br>
					<strong>${slThuThu}</strong>
				</div>
			</div>

	 <div class="quick-actions">
    <h3>⚡ Hành động nhanh</h3>
    <button id="btnQuickTheLoai" class="btn-add">➕ Thêm thể loại</button>
    <button id="btnQuickNXB" class="btn-add">➕ Thêm NXB</button>
    <button id="btnQuickDocGia" class="btn-add">➕ Thêm loại độc giả</button>
</div>

      <div class="config-box">
    <h3>⚙️ Cấu hình Quy định</h3>
    
    <form action="${pageContext.request.contextPath}/TrangChuQuanTriVien" method="post">
        
        <input type="hidden" name="action" value="update_config">

        <label>Số ngày mượn tối đa</label>
        <input type="number" name="giaTriNgay" value="${qdNgay.giaTri}" required />

        <label>Phí phạt trễ hạn (VND/ngày)</label>
        <input type="number" name="giaTriPhat" value="${qdPhat.giaTri}" required />

        <button type="submit" class="btn-search">💾 Lưu thay đổi</button>
    </form>

    <c:if test="${not empty thongBaoConfig}">
        <div style="color: green; margin-top: 10px; font-weight: bold;">
            ✅ ${thongBaoConfig}
        </div>
    </c:if>
</div>
		</main>
	</div>
	<div id="modalQuickTheLoai" class="modal">
    <div class="modal-content">
        <span class="close" data-modal="modalQuickTheLoai">&times;</span>
        <h2>➕ Thêm nhanh Thể Loại</h2>
        <form action="${pageContext.request.contextPath}/TheLoai" method="post">
            <input type="hidden" name="action" value="insert">
            
            <div class="form-group">
                <label>Mã Thể Loại (*):</label>
                <input type="text" name="maTheLoai" required placeholder="VD: TL001">
            </div>
            <div class="form-group">
                <label>Tên Thể Loại (*):</label>
                <input type="text" name="tenTheLoai" required placeholder="VD: Khoa học">
            </div>
            <button type="submit" class="btn-save">💾 Lưu lại</button>
        </form>
    </div>
</div>

<div id="modalQuickNXB" class="modal">
    <div class="modal-content">
        <span class="close" data-modal="modalQuickNXB">&times;</span>
        <h2>➕ Thêm nhanh Nhà Xuất Bản</h2>
        <form action="${pageContext.request.contextPath}/NhaXuatBan" method="post">
            <input type="hidden" name="action" value="insert">
            
            <div class="form-group">
                <label>Mã NXB (*):</label>
                <input type="text" name="maNhaXuatBan" required placeholder="VD: NXB001">
            </div>
            <div class="form-group">
                <label>Tên NXB (*):</label>
                <input type="text" name="tenNhaXuatBan" required placeholder="VD: Kim Đồng">
            </div>
            <button type="submit" class="btn-save">💾 Lưu lại</button>
        </form>
    </div>
</div>

<div id="modalQuickDocGia" class="modal">
    <div class="modal-content">
        <span class="close" data-modal="modalQuickDocGia">&times;</span>
        <h2>➕ Thêm nhanh Loại Độc Giả</h2>
        <form action="${pageContext.request.contextPath}/LoaiDocGia" method="post">
            <input type="hidden" name="action" value="insert">
            
            <div class="form-group">
                <label>Mã Loại Độc Giả (*):</label>
                <input type="text" name="maLoaiDocGia" required placeholder="VD: LDG001">
            </div>
            <div class="form-group">
                <label>Tên Loại Độc Giả (*):</label>
                <input type="text" name="tenLoaiDocGia" required placeholder="VD: Sinh viên">
            </div>
            <button type="submit" class="btn-save">💾 Lưu lại</button>
        </form>
    </div>
</div>
<script>
    var contextPath = "${pageContext.request.contextPath}";
</script>

<script src="${pageContext.request.contextPath}/view/script.js?v=5"></script>
<c:if test="${not empty baoLoi or not empty moFormThem}">
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            var modal = document.querySelector(".modal");
            if(modal) {
                modal.style.display = "block";
            }
        });
    </script>
</c:if>
</body>
</html>