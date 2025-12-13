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
<title>Quản lý Tài khoản Thủ thư</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/view/style.css?v=2" />
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

</head>
<body>
	<div class="container">
		<aside class="sidebar">
			<h2>🛠️ Quản trị viên</h2>
			<ul>
				<li><a href="${pageContext.request.contextPath}/TrangChuQuanTriVien">🏠 Trang chủ</a></li>
				<li><a href="${pageContext.request.contextPath}/ThuThu">👤 Tài khoản Thủ thư</a></li>
				<li><a href="${pageContext.request.contextPath}/QuyDinh"class="active">⚙️ Cấu Hình Quy Định</a></li>
				<li><a href="${pageContext.request.contextPath}/TheLoai">📂 Quản Lý Thể loại</a></li>
				<li><a href="${pageContext.request.contextPath}/NhaXuatBan">🏢 Nhà xuất bản</a></li>
                <li><a href="${pageContext.request.contextPath}/DangXuat">🚪 Đăng xuất</a></li>
			</ul>
		</aside>

		<main class="main-content">
			<h1>👤 Quản lý Tài khoản Thủ thư</h1>

			<div class="search-bar">
				<form action="${pageContext.request.contextPath}/ThuThu"
					method="get" style="display: flex; gap: 10px; flex: 1;">
					<input type="text" name="tuKhoa" value="${tuKhoa}"
						placeholder="Tìm kiếm theo tên hoặc tên đăng nhập..."
						style="flex: 1;" />
					<button type="submit" class="btn-search">🔍 Tìm kiếm</button>
					<c:if test="${not empty tuKhoa}">
						<a href="${pageContext.request.contextPath}/ThuThu"
							class="btn-delete"
							style="text-decoration: none; display: flex; align-items: center;">Hủy
							tìm</a>
					</c:if>
				</form>
				<button id="btnThem" class="btn-add">➕ Thêm mới</button>
			</div>
            <div class="table-container">
			<table class="data-table">
				<thead>
					<tr>

						<th>Mã Thủ thư</th>
						<th>Họ tên</th>
						<th>Email</th>
						<th>Tên đăng nhập</th>
						<th>Trạng thái</th>
						<th>Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty dstt}">
						<c:forEach var="tt" items="${dstt}" varStatus="status">
							<tr>

								<td>${tt.maThuThu}</td>
								<td>${tt.hoTen}</td>
								<td>${tt.email}</td>
								<td>${tt.tenDangNhap}</td>
								<td><c:choose>
										<c:when test="${tt.trangThai == 1}">Hoạt động</c:when>
										<c:otherwise>Đã khóa</c:otherwise>
									</c:choose></td>

								<td><a href="ThuThu?action=edit&id=${tt.maThuThu}"
									class="btn-edit">Sửa</a> <a href="#" class="btn-delete"
									onclick="xacNhanXoa('${tt.maThuThu}', '${tt.hoTen}', 'ThuThu')">
										Xóa </a></td>
							</tr>
						</c:forEach>
					</c:if>

					<c:if test="${empty dstt}">
						<tr>
							<td colspan="7" style="text-align: center;">Không có dữ liệu
								thủ thư nào.</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			</div>
		</main>
	</div>
	<div id="modalThem" class="modal">
		<div class="modal-content">
			<span class="close">&times;</span>

			<h2>
				<c:choose>
					<c:when test="${not empty suaThuThu}">✏️ Cập nhật Thủ thư</c:when>
					<c:otherwise>➕ Thêm mới Thủ thư</c:otherwise>
				</c:choose>
			</h2>

			<form action="${pageContext.request.contextPath}/ThuThu"
				method="post">

				<input type="hidden" name="action"
					value="${not empty suaThuThu ? 'update' : 'insert'}">

				<c:if test="${not empty baoLoi}">
					<div class="alert-error">⚠️ ${baoLoi}</div>
				</c:if>

				<div class="form-group">
					<label>Mã Thủ thư (*):</label> <input type="text" name="maThuThu"
						value="${not empty suaThuThu ? suaThuThu.maThuThu : param.maThuThu}"
						${not empty suaThuThu ? 'readonly style="background-color:#e9ecef"' : ''}
						required placeholder="Nhập mã (VD: TT005)">
				</div>

				<div class="form-group">
					<label>Họ và tên (*):</label> <input type="text" name="hoTen"
						value="${not empty suaThuThu ? suaThuThu.hoTen : param.hoTen}"
						required placeholder="Nhập họ tên đầy đủ">
				</div>

				<div class="form-group">
					<label>Email:</label> <input type="email" name="email"
						value="${not empty suaThuThu ? suaThuThu.email : param.email}"
						required placeholder="email@example.com">
				</div>

				<div class="form-group">
					<label>Tên đăng nhập (*):</label> <input type="text"
						name="tenDangNhap"
						value="${not empty suaThuThu ? suaThuThu.tenDangNhap : param.tenDangNhap}"
						required>
				</div>

				<div class="form-group">
					<label>Mật khẩu (*):</label> <input type="password" name="matKhau"
						value="${not empty suaThuThu ? suaThuThu.matKhau : ''}" required>
				</div>

				<div class="form-group">
					<label>Trạng thái:</label> <select name="trangThai">
						<option value="1" ${suaThuThu.trangThai == 1 ? 'selected' : ''}>Hoạt
							động</option>
						<option value="0" ${suaThuThu.trangThai == 0 ? 'selected' : ''}>Đã
							khóa</option>
					</select>
				</div>

				<button type="submit" class="btn-save">
					<c:choose>
						<c:when test="${not empty suaThuThu}">💾 Cập nhật</c:when>
						<c:otherwise>💾 Lưu lại</c:otherwise>
					</c:choose>
				</button>
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