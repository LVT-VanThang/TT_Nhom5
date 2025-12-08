<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <title>Quản lý Tác giả</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/view/style.css?v=2" />
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
  <div class="container">
    <aside class="sidebar">
      <h2>📘 Thủ thư</h2>
      <ul>
        <li><a href="#">🏠 Trang chủ</a></li>
        <li><a href="${pageContext.request.contextPath}/Sach">📚 Sách</a></li>
        <li><a href="${pageContext.request.contextPath}/TacGia">✍️ Tác giả</a></li>
        <li><a href="#">🧑‍💼 Độc giả</a></li>
        <li><a href="#">🔄 Mượn/Trả</a></li>
        <li><a href="#">🔍 Tra cứu</a></li>
        <li><a href="#">📊 Thống kê</a></li>
        <li><a href="#">🚪 Đăng xuất</a></li>
      </ul>
    </aside>

    <main class="main-content">
      <h1>✍️ Quản lý Tác giả</h1>

      <div class="search-bar">
        <form action="${pageContext.request.contextPath}/TacGia" method="get" style="display:flex; width:85%;">
            <input type="hidden" name="action" value="search">
            <input type="text" name="keyword" placeholder="Tìm kiếm tác giả..." value="${param.keyword}"/>
            <button type="submit" class="btn-search">🔍 Tìm kiếm</button>
        </form>
        
        <button class="btn-add" id="btnThem">➕ Thêm mới</button>
      </div>

      <table class="data-table">
        <thead>
          <tr>
            <th>STT</th>
            <th>Mã tác giả</th>
            <th>Tên tác giả</th>
            <th>Ghi chú</th>
            <th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          <c:if test="${not empty dstg}">
            <c:forEach var="tg" items="${dstg}" varStatus="status">
              <tr>
                <td>${status.count}</td>
                <td>${tg.maTacGia}</td>
                <td>${tg.tenTacGia}</td>
                <td>${tg.ghiChu}</td>
                <td>
                  <a href="${pageContext.request.contextPath}/TacGia?action=edit&id=${tg.maTacGia}" class="btn-edit">Sửa</a> 
                  <a href="#" class="btn-delete" onclick="xacNhanXoa('${tg.maTacGia}', '${tg.tenTacGia}', 'TacGia')">Xóa</a>
                </td>
              </tr>
            </c:forEach>
          </c:if>
          <c:if test="${empty dstg}">
            <tr><td colspan="5" style="text-align: center;">Không có dữ liệu tác giả nào.</td></tr>
          </c:if>
        </tbody>
      </table>
    </main>
  </div>

  <div id="modalThem" class="modal">
    <div class="modal-content">
      <span class="close">&times;</span>

      <h2 data-title="Tác giả">
        <c:choose>
          <c:when test="${not empty suaTacGia}">✏️ Cập nhật Tác giả</c:when>
          <c:otherwise>➕ Thêm mới Tác giả</c:otherwise>
        </c:choose>
      </h2>

      <form action="${pageContext.request.contextPath}/TacGia" method="post">
        
        <input type="hidden" name="action" value="${not empty suaTacGia ? 'update' : 'insert'}">

        <c:if test="${not empty baoLoi}">
          <div class="alert-error">⚠️ ${baoLoi}</div>
        </c:if>

        <div class="form-group">
          <label>Mã Tác giả (*):</label> 
          <input type="text" name="maTacGia"
            value="${not empty suaTacGia ? suaTacGia.maTacGia : param.maTacGia}"
            ${not empty suaTacGia ? 'readonly style="background-color:#e9ecef"' : ''}
            required placeholder="Nhập mã (VD: TG01)">
        </div>

        <div class="form-group">
          <label>Tên Tác giả (*):</label> 
          <input type="text" name="tenTacGia"
            value="${not empty suaTacGia ? suaTacGia.tenTacGia : param.tenTacGia}"
            required placeholder="Nhập tên tác giả">
        </div>

        <div class="form-group">
          <label>Ghi chú:</label> 
          <textarea name="ghiChu" rows="3" placeholder="Thông tin thêm...">${not empty suaTacGia ? suaTacGia.ghiChu : param.ghiChu}</textarea>
        </div>

        <button type="submit" class="btn-save" style="margin-top: 20px;">
          <c:choose>
            <c:when test="${not empty suaTacGia}">💾 Cập nhật</c:when>
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
  
  <c:if test="${not empty baoLoi or not empty moFormThem or not empty suaTacGia}">
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            var modal = document.getElementById("modalThem");
            if(modal) {
                modal.style.display = "block";
            }
        });
    </script>
  </c:if>
</body>
</html>