<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<title>Cấu hình Quy định</title>
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
                <li><a href="${pageContext.request.contextPath}/DangXuat">🚪 Đăng xuất</a></li>
			</ul>
		</aside>

		<main class="main-content">
			<h1>⚙️ Cấu hình Quy định</h1>

			<div class="search-bar">
				<form action="${pageContext.request.contextPath}/QuyDinh"
					method="get" style="display: flex; gap: 10px; flex: 1;">
					<input type="text" name="tuKhoa" value="${param.tuKhoa}"
						placeholder="Tìm kiếm theo tên quy định..." style="flex: 1;" />
					<button type="submit" class="btn-search">🔍 Tìm kiếm</button>
					<c:if test="${not empty param.tuKhoa}">
						<a href="${pageContext.request.contextPath}/QuyDinh"
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
						<th>STT</th>
						<th>Mã QĐ</th>
						<th>Tên quy định</th>
						<th>Giá trị</th>
						<th>Đơn vị</th>
						<th>Ngày cập nhật</th>
						<th>Người thực hiện</th>
						<th>Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty dsqd}">
						<c:forEach var="qd" items="${dsqd}" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td>${qd.maQuyDinh}</td>
								<td>${qd.tenQuyDinh}</td>
								<td>${qd.giaTri}</td>
								<td>${qd.donViTinh}</td>
								<td><fmt:formatDate value="${qd.ngayCapNhat}"
										pattern="dd/MM/yyyy HH:mm" /></td>
								<td>${qd.quanTriVien.maAdmin}</td>
								<td><a href="QuyDinh?action=edit&id=${qd.maQuyDinh}"
									 class="btn-edit">Sửa
									</a> 
								</td>
							</tr>
						</c:forEach>
					</c:if>

					<c:if test="${empty dsqd}">
						<tr>
							<td colspan="8" style="text-align: center;">Không có dữ liệu
								quy định nào.</td>
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
					<c:when test="${not empty suaQuyDinh}">✏️ Cập nhật Quy định</c:when>
					<c:otherwise>➕ Thêm mới Quy định</c:otherwise>
				</c:choose>
			</h2>

			<form action="${pageContext.request.contextPath}/QuyDinh"
				method="post">

				<input type="hidden" name="action"
					value="${not empty suaQuyDinh ? 'update' : 'insert'}">

				<c:if test="${not empty baoLoi}">
					<div class="alert-error">⚠️ ${baoLoi}</div>
				</c:if>

				<div class="form-group">
					<label>Mã Quy định (*):</label> <input type="text" name="maQuyDinh"
						value="${not empty suaQuyDinh ? suaQuyDinh.maQuyDinh : param.maQuyDinh}"
						${not empty suaQuyDinh ? 'readonly style="background-color:#e9ecef"' : ''}
						required placeholder="Nhập mã (VD: QD01)">
				</div>

				<div class="form-group">
					<label>Tên Quy định (*):</label> <input type="text"
						name="tenQuyDinh"
						value="${not empty suaQuyDinh ? suaQuyDinh.tenQuyDinh : param.tenQuyDinh}"
						${not empty suaQuyDinh ? 'readonly style="background-color:#e9ecef"' : ''}
						required placeholder="Nhập tên quy định">
				</div>

				<div class="form-group">
					<label>Giá trị (*):</label> <input type="text" name="giaTri"
						value="${not empty suaQuyDinh ? suaQuyDinh.giaTri : param.giaTri}"
						required placeholder="Nhập giá trị">
				</div>

				<div class="form-group">
					<label>Đơn vị tính:</label> <input type="text" name="donViTinh"
						value="${not empty suaQuyDinh ? suaQuyDinh.donViTinh : param.donViTinh}"
						required placeholder="VD: Đồng, Ngày, Quyển...">
				</div>


				<button type="submit" class="btn-save" style="margin-top: 20px;">
					<c:choose>
						<c:when test="${not empty suaQuyDinh}">💾 Cập nhật</c:when>
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