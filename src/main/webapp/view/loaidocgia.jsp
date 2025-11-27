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
<title>Quản lý Loại độc giả</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/view/style.css?v=1" />
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
				<li><a href="${pageContext.request.contextPath}/LoaiDocGia">🧑‍🏫 Loại Độc Giả</a></li>
                <li><a href="${pageContext.request.contextPath}/DangXuat">🚪 Đăng xuất</a></li>
			</ul>
		</aside>

		<main class="main-content">
			<h1>🧑‍🏫 Quản lý Loại độc giả</h1>

			<div class="search-bar">
				<form action="${pageContext.request.contextPath}/LoaiDocGia"
					method="get" style="display: flex; gap: 10px; flex: 1;">
					<input type="text" name="tuKhoa" value="${param.tuKhoa}"
						placeholder="Tìm kiếm theo tên thể loại..." style="flex: 1;" />
					<button type="submit" class="btn-search">🔍 Tìm kiếm</button>
					<c:if test="${not empty param.tuKhoa}">
						<a href="${pageContext.request.contextPath}/LoaiDocGia"
							class="btn-delete"
							style="text-decoration: none; display: flex; align-items: center;">Hủy
							tìm</a>
					</c:if>
				</form>
				<button id="btnThemMoi" class="btn-add">➕ Thêm mới</button>
			</div>

			<table class="data-table">
				<thead>
					<tr>
						<th>STT</th>
						<th>Mã Thể loại</th>
						<th>Tên thể loại</th>
						<th>Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty dsldg}">
						<c:forEach var="ldg" items="${dsldg}" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td>${ldg.maLoaiDocGia}</td>
								<td>${ldg.tenLoaiDocGia}</td>
								<td><a href="LoaiDocGia?action=edit&id=${ldg.maLoaiDocGia}"
									class="btn-edit">Sửa</a> <a href="#" class="btn-delete"
									onclick="xacNhanXoa('${ldg.maLoaiDocGia}', '${ldg.tenLoaiDocGia}', 'LoaiDocGia')">
										Xóa </a></td>
							</tr>
						</c:forEach>
					</c:if>

					<c:if test="${empty dsldg}">
						<tr>
							<td colspan="8" style="text-align: center;">Không có dữ liệu
								thể loại nào.</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</main>
	</div>
	<div id="modalThemLoaiDocGia" class="modal">
		<div class="modal-content">
			<span class="close">&times;</span>

			<h2>
				<c:choose>
					<c:when test="${not empty suaLoaiDocGia}">✏️ Cập nhật Thể Loại</c:when>
					<c:otherwise>➕ Thêm mới Thể Loại</c:otherwise>
				</c:choose>
			</h2>

			<form action="${pageContext.request.contextPath}/LoaiDocGia"
				method="post">

				<input type="hidden" name="action"
					value="${not empty suaLoaiDocGia ? 'update' : 'insert'}">

				<c:if test="${not empty baoLoi}">
					<div class="alert-error">⚠️ ${baoLoi}</div>
				</c:if>

				<div class="form-group">
					<label>Mã Thể Loại (*):</label> <input type="text" name="maLoaiDocGia"
						value="${not empty suaLoaiDocGia ? suaLoaiDocGia.maLoaiDocGia : param.maLoaiDocGia}"
						${not empty suaLoaiDocGia ? 'readonly style="background-color:#e9ecef"' : ''}
						required placeholder="Nhập mã (VD: LDG001)">
				</div>

				<div class="form-group">
					<label>Tên Quy định (*):</label> <input type="text"
						name="tenLoaiDocGia"
						value="${not empty suaLoaiDocGia ? suaLoaiDocGia.tenLoaiDocGia : param.tenLoaiDocGia}"
						required placeholder="Nhập tên loại độc giả">
				</div>



				<button type="submit" class="btn-save" style="margin-top: 20px;">
					<c:choose>
						<c:when test="${not empty suaLoaiDocGia}">💾 Cập nhật</c:when>
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