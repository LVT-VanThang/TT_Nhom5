<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8" />
<title>Quản lý Độc giả</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/view/style.css?v=2" />

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
	<div class="container">
		<aside class="sidebar">
			<h2>📘 Thủ thư</h2>
			<ul>
				<li><a href="#">🏠 Trang chủ</a></li>
				<li><a href="${pageContext.request.contextPath}/Sach">📚Quản
						Lý Sách</a></li>
				<li><a href="${pageContext.request.contextPath}/TacGia">✍️Quản
						Lý Tác giả</a></li>
				<li><a href="${pageContext.request.contextPath}/DocGia"
					class="active">🧑‍💼Quản Lý Độc giả</a></li>
				<li><a href="${pageContext.request.contextPath}/MuonTra">🔄Quản
						Lý Mượn/Trả</a></li>
				<li><a href="${pageContext.request.contextPath}/TraCuu">🔍
						Tra cứu</a></li>
				<li><a href="${pageContext.request.contextPath}/ThongKe">📊
						Thống kê</a></li>
				<li><a href="${pageContext.request.contextPath}/DangXuat">🚪
						Đăng xuất</a></li>
			</ul>
		</aside>

		<main class="main-content">
			<h1>🧑‍💼 Quản lý Độc giả</h1>

			<div class="search-bar">
				<form action="${pageContext.request.contextPath}/DocGia"
					method="get" style="display: flex; width: 86%;">
					<input type="text" name="tuKhoa"
						placeholder="Tìm tên hoặc mã độc giả..." value="${param.tuKhoa}"
						style="flex: 1; margin-right: 10px;" />
					<button type="submit" class="btn-search">🔍 Tìm kiếm</button>

					<c:if test="${not empty param.tuKhoa}">
						<a href="${pageContext.request.contextPath}/DocGia"
							class="btn-delete"
							style="text-decoration: none; display: flex; align-items: center; margin-left: 5px;">Hủy
							tìm</a>
					</c:if>
				</form>

				<button class="btn-add" id="btnThem">➕ Thêm mới</button>
			</div>
            <div class="table-container">
			<table class="data-table">
				<thead>
					<tr>
						<th>STT</th>
						<th>Mã ĐG</th>
						<th>Họ tên</th>
						<th>Email</th>
						<th>SĐT</th>
						<th>Ngày lập</th>
						<th>Hết hạn</th>
						<th>Trạng thái</th>
						<th>Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty dsdg}">
						<c:forEach var="dg" items="${dsdg}" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td>${dg.maDocGia}</td>
								<td>${dg.hoTen}</td>
								<td>${dg.email}</td>
								<td>${dg.soDienThoai}</td>
								<td><fmt:formatDate value="${dg.ngayLapThe}"
										pattern="dd/MM/yyyy" /></td>
								<td><fmt:formatDate value="${dg.ngayHetHan}"
										pattern="dd/MM/yyyy" /></td>

								<td><span
									style="color: ${dg.trangThaiThe == 'Hoạt động' ? 'green' : 'red'}; font-weight:bold;">
										${dg.trangThaiThe} </span></td>
								<td><a
									href="${pageContext.request.contextPath}/DocGia?action=edit&id=${dg.maDocGia}"
									class="btn-edit">Sửa</a> <a href="#" class="btn-delete"
									onclick="xacNhanXoa('${dg.maDocGia}', '${dg.hoTen}', 'DocGia')">Xóa</a>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty dsdg}">
						<tr>
							<td colspan="9" style="text-align: center;">Không có dữ liệu
								độc giả nào.</td>
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
                <c:when test="${isEdit}">✏️ Cập nhật Độc giả</c:when>
                <c:otherwise>➕ Thêm mới Độc giả</c:otherwise>
            </c:choose>
        </h2>

        <form action="${pageContext.request.contextPath}/DocGia" method="post" id="formDocGia">
            <input type="hidden" name="action" value="${isEdit ? 'update' : 'insert'}">

            <c:if test="${not empty baoLoi}">
                <div class="alert-error" style="color: red; background-color: #f8d7da; padding: 10px; margin-bottom: 10px; border-radius: 5px; text-align: center;">
                    ⚠️ ${baoLoi}
                </div>
            </c:if>

            <div class="form-group">
                <label>Mã Độc giả (*):</label> 
                <input type="text" name="maDocGia"
                    value="${not empty suaDocGia ? suaDocGia.maDocGia : param.maDocGia}"
                    ${isEdit ? 'readonly style="background-color:#e9ecef"' : ''}
                    required placeholder="Nhập mã (VD: DG001)">
            </div>

            <div class="form-group">
                <label>Họ và Tên (*):</label> 
                <input type="text" name="hoTen"
                    value="${not empty suaDocGia ? suaDocGia.hoTen : param.hoTen}"
                    required placeholder="Nhập họ tên đầy đủ">
            </div>

            <div class="form-group">
                <label>Email:</label> 
                <input type="email" name="email"
                    value="${not empty suaDocGia ? suaDocGia.email : param.email}"
                    placeholder="example@gmail.com">
            </div>
            
            <div class="form-group">
                <label>Số điện thoại:</label> 
                <input type="text" name="soDienThoai"
                    value="${not empty suaDocGia ? suaDocGia.soDienThoai : param.soDienThoai}"
                    placeholder="Nhập số điện thoại">
            </div>
            <div id="dateContainer" style="display: ${isEdit ? 'contents' : 'none'};">
                <div class="form-group">
                    <label>Ngày lập thẻ:</label> 
                    <input type="date" name="ngayLapThe"
                        value="<fmt:formatDate value='${suaDocGia.ngayLapThe}' pattern='yyyy-MM-dd'/>">
                </div>

                <div class="form-group">
                    <label>Ngày hết hạn:</label> 
                    <input type="date" name="ngayHetHan"
                        value="<fmt:formatDate value='${suaDocGia.ngayHetHan}' pattern='yyyy-MM-dd'/>">
                </div>
            </div>

            <div class="form-group">
                <label>Trạng thái thẻ:</label> 
                <select name="trangThaiThe" required>
                    <option value="Còn Hạn">Hoạt động (Còn Hạn)</option>
                    <option value="Hết Hạn" ${suaDocGia.trangThaiThe == 'Hết Hạn' ? 'selected' : ''}>Đã khóa (Hết Hạn)</option>
                </select>
            </div>

            <button type="submit" class="btn-save" style="margin-top: 20px;">
                <c:choose>
                    <c:when test="${isEdit}">💾 Cập nhật</c:when>
                    <c:otherwise>💾 Lưu lại</c:otherwise>
                </c:choose>
            </button>
        </form>
    </div>
</div>

	<script>
    var contextPath = "${pageContext.request.contextPath}";
  </script>

	<script src="${pageContext.request.contextPath}/view/script.js?v=11"></script>

	<c:if
		test="${not empty baoLoi or not empty moFormThem or not empty suaDocGia}">
		<script>
        document.addEventListener("DOMContentLoaded", function() {
            var modal = document.getElementById("modalThem");
            if(modal) modal.style.display = "block";
        });
    </script>
	</c:if>
</body>
</html>