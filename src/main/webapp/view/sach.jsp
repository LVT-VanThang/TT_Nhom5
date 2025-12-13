<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <title>Quản lý Sách</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/view/style.css?v=10" />
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
   <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
  <div class="container">
    <aside class="sidebar">
      <h2>📘 Thủ thư</h2>
      <ul>
        <li><a href="${pageContext.request.contextPath}/TrangChuThuThu">🏠 Trang chủ</a></li>
        <li><a href="${pageContext.request.contextPath}/Sach">📚Quản Lý Sách</a></li>
        <li><a href="${pageContext.request.contextPath}/TacGia">✍️Quản Lý Tác giả</a></li>
        <li><a href="${pageContext.request.contextPath}/DocGia" class="active">🧑‍💼Quản Lý Độc giả</a></li>
        <li><a href="${pageContext.request.contextPath}/MuonTra">🔄Quản Lý Mượn/Trả</a></li>
        <li><a href="${pageContext.request.contextPath}/ThongKe">📊 Thống kê</a></li>
        <li><a href="${pageContext.request.contextPath}/DangXuat">🚪 Đăng xuất</a></li>
      </ul>
    </aside>

    <main class="main-content">
      <h1>📚 Quản lý Sách</h1>

      <div class="search-bar">
        <form action="${pageContext.request.contextPath}/Sach" method="get" style="display:flex; width:85%;">
            <input type="hidden" name="action" value="search">
            <input type="text" name="keyword" placeholder="Tìm kiếm sách theo tên hoặc mã..." value="${param.keyword}"/>
            <button type="submit" class="btn-search">🔍 Tìm kiếm</button>
					<c:if test="${not empty param.keyword}">
						<a href="${pageContext.request.contextPath}/Sach"
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

            <th>Mã sách</th>
            <th>Tên sách</th>
            <th>Tác giả</th>
            <th>Thể loại</th>
            <th>Vị Trí Kệ</th>
            <th>NXB</th>
            <th>Năm XB</th>
            <th>Số lượng</th>
            <th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          <c:if test="${not empty dss}">
            <c:forEach var="s" items="${dss}" varStatus="status">
              <tr>

                <td>${s.maSach}</td>
                <td>${s.tenSach}</td>
                <td>${s.tacGia.tenTacGia}</td>
                <td>${s.theLoai.tenTheLoai}</td>
                <td>${s.theLoai.viTriKe}</td>
                <td>${s.nhaXuatBan.tenNXB}</td>
                <td>${s.namXuatBan}</td>
                <td>${s.soLuongTonKho}</td>
                <td>
                  <a href="${pageContext.request.contextPath}/Sach?action=edit&id=${s.maSach}" class="btn-edit">Sửa</a> 
                  <a href="#" class="btn-delete" onclick="xacNhanXoa('${s.maSach}', '${s.tenSach}', 'Sach')">Xóa</a>
                </td>
              </tr>
            </c:forEach>
          </c:if>
          <c:if test="${empty dss}">
            <tr><td colspan="9" style="text-align: center;">Không có dữ liệu sách nào.</td></tr>
          </c:if>
        </tbody>
      </table>
      </div>
    </main>
  </div>

  <div id="modalThem" class="modal">
    <div class="modal-content">
      <span class="close">&times;</span>

      <h2 data-title="Sách">
        <c:choose>
          <c:when test="${not empty suaSach}">✏️ Cập nhật Sách</c:when>
          <c:otherwise>➕ Thêm mới Sách</c:otherwise>
        </c:choose>
      </h2>

      <form action="${pageContext.request.contextPath}/Sach" method="post">
        <input type="hidden" name="action" value="${not empty suaSach ? 'update' : 'insert'}">

        <c:if test="${not empty baoLoi}">
          <div class="alert-error">⚠️ ${baoLoi}</div>
        </c:if>

        <div class="form-group">
          <label>Mã Sách (*):</label> 
          <input type="text" name="maSach"
            value="${not empty suaSach ? suaSach.maSach : param.maSach}"
            ${not empty suaSach ? 'readonly style="background-color:#e9ecef"' : ''}
            required placeholder="Nhập mã (VD: S001)">
        </div>

        <div class="form-group">
          <label>Tên Sách (*):</label> 
          <input type="text" name="tenSach"
            value="${not empty suaSach ? suaSach.tenSach : param.tenSach}"
            required placeholder="Nhập tên sách">
        </div>

        <div class="form-group">
          <label>Tác giả:</label>
          <select name="maTacGia" required>
            <option value="">-- Chọn Tác giả --</option>
            <c:forEach var="tg" items="${dstg}">
                <option value="${tg.maTacGia}" ${suaSach.tacGia.maTacGia == tg.maTacGia ? 'selected' : ''}>
                    ${tg.tenTacGia}
                </option>
            </c:forEach>
          </select>
        </div>

        <div class="form-group">
            <label>Thể loại:</label>
            <select name="maTheLoai" required>
              <option value="">-- Chọn Thể loại --</option>
              <c:forEach var="tl" items="${dstl}">
                  <option value="${tl.maTheLoai}" ${suaSach.theLoai.maTheLoai == tl.maTheLoai ? 'selected' : ''}>
                      ${tl.tenTheLoai}
                  </option>
              </c:forEach>
            </select>
        </div>
  
        <div class="form-group">
            <label>Nhà xuất bản:</label>
            <select name="maNXB" required>
              <option value="">-- Chọn NXB --</option>
              <c:forEach var="nxb" items="${dsnxb}">
                  <option value="${nxb.maNXB}" ${suaSach.nhaXuatBan.maNXB == nxb.maNXB ? 'selected' : ''}>
                      ${nxb.tenNXB}
                  </option>
              </c:forEach>
            </select>
        </div>

        <div class="form-group">
          <label>Năm Xuất Bản:</label> 
          <input type="number" name="namXuatBan"
            value="${not empty suaSach ? suaSach.namXuatBan : param.namXuatBan}"
            required placeholder="VD: 2023">
        </div>

        <div class="form-group">
          <label>Số lượng tồn:</label> 
          <input type="number" name="soLuongTonKho"
            value="${not empty suaSach ? suaSach.soLuongTonKho : param.soLuongTonKho}"
            required min="0">
        </div>

        <button type="submit" class="btn-save" style="margin-top: 20px;">
          <c:choose>
            <c:when test="${not empty suaSach}">💾 Cập nhật</c:when>
            <c:otherwise>💾 Lưu lại</c:otherwise>
          </c:choose>
        </button>
      </form>
    </div>
  </div>

  <script>
    var contextPath = "${pageContext.request.contextPath}";
  </script>

  <script src="${pageContext.request.contextPath}/view/script.js?v=8"></script>
  
  <c:if test="${not empty baoLoi or not empty moFormThem or not empty suaSach}">
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            // [CHUẨN HÓA 4]: Tìm đúng ID modalThem
            var modal = document.getElementById("modalThem");
            if(modal) {
                modal.style.display = "block";
            }
        });
    </script>
  </c:if>
</body>
</html>