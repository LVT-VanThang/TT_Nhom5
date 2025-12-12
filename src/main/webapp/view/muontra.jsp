<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <title>Quản Lý Mượn Trả</title>
  
  <link rel="stylesheet" href="${pageContext.request.contextPath}/view/style.css?v=14" />
  
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
  <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
  
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
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
        <li><a href="${pageContext.request.contextPath}/MuonTra" class="active">🔄Quản Lý Mượn/Trả</a></li>
        <li><a href="${pageContext.request.contextPath}/TraCuu">🔍 Tra cứu</a></li>
        <li><a href="${pageContext.request.contextPath}/ThongKe">📊 Thống kê</a></li>
        <li><a href="${pageContext.request.contextPath}/DangXuat">🚪 Đăng xuất</a></li>
      </ul>
    </aside>

    <main class="main-content">
      <h1>🔄 Quản Lý Phiếu Mượn</h1>

      <div class="search-bar">
        <form action="${pageContext.request.contextPath}/MuonTra" method="get" style="display: flex; width: 80%;">
            <input type="text" name="tuKhoa" value="${param.tuKhoa}" placeholder="Nhập mã phiếu hoặc tên độc giả...">
            <button type="submit" class="btn-search">🔍 Tìm kiếm</button>
            
            <c:if test="${not empty param.tuKhoa}">
                 <a href="${pageContext.request.contextPath}/MuonTra" class="btn-delete" 
                 style="text-decoration: none; display: flex; align-items: center; margin-left: 5px;">Hủy Tìm</a>
            </c:if>
        </form>

        <button class="btn-add" id="btnLapPhieu">➕ Lập Phiếu Mượn Mới</button>
      </div>

      <div class="table-container">
          <table class="data-table">
            <thead>
              <tr>
                <th>Mã Phiếu</th>
                <th>Mã ĐG</th>
                <th>Độc Giả</th>
                <th>Ngày Mượn</th>
                <th>Hạn Trả</th>
                <th>Người Lập</th>
                <th>Tổng Phạt</th>
                <th>Trạng Thái</th>
                <th>Hành động</th>
              </tr>
            </thead>
            <tbody>
              <c:if test="${not empty dsPhieu}">
                <c:forEach var="pm" items="${dsPhieu}">
                  <tr>
                    <td><strong>${pm.maPhieuMuon}</strong></td>
                    <td>${pm.docGia.maDocGia}</td>
                    <td>${pm.docGia.hoTen}</td>
                    <td><fmt:formatDate value="${pm.ngayMuon}" pattern="dd/MM/yyyy"/></td>
                    <td><fmt:formatDate value="${pm.ngayHenTra}" pattern="dd/MM/yyyy"/></td>
                    <td>${pm.thuThu.hoTen}</td>
                    <td>
                        <c:if test="${not empty pm.tongTienPhat and pm.tongTienPhat > 0}">
                            <span style="color: #d32f2f; font-weight: bold;">
                                <fmt:formatNumber value="${pm.tongTienPhat}" type="number" maxFractionDigits="0"/> VNĐ
                            </span>
                        </c:if>
                        <c:if test="${empty pm.tongTienPhat or pm.tongTienPhat == 0}">
                            <span style="color: #ccc;">0 VNĐ</span>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${pm.trangThaiPhieu == 0}"><span class="status-tag status-borrowing">Đang mượn</span></c:if>
                        <c:if test="${pm.trangThaiPhieu == 1}"><span class="status-tag status-returned">Đã trả</span></c:if>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/MuonTra?action=detail&maPhieu=${pm.maPhieuMuon}"
                        class="btn-edit"
                        style="text-decoration: none; font-size: 12px; background-color: #17a2b8;">Xem chi tiết </a>
                    </td>
                  </tr>
                </c:forEach>
              </c:if>
              <c:if test="${empty dsPhieu}">
                <tr><td colspan="7" style="text-align: center;">Chưa có phiếu mượn nào.</td></tr>
              </c:if>
            </tbody>
          </table>
      </div>
    </main>
  </div>

  <div id="modalLapPhieu" class="modal">
    <div class="modal-content" style="width: 800px; margin-top: 50px;"> 
      <span class="close" onclick="dongModal('modalLapPhieu')">&times;</span>
      <h2>📝 Lập Phiếu Mượn Mới</h2>
      
      <form action="${pageContext.request.contextPath}/MuonTra" method="post">
        <input type="hidden" name="action" value="insert">
        
        <div class="form-group">
            <label>Chọn Độc Giả (*):</label>
            <select name="maDocGia" class="select2-docgia" style="width: 100%;" required>
                <option value="">-- Tìm tên hoặc mã độc giả --</option>
                <c:forEach var="dg" items="${dsDocGia}">
                    <option value="${dg.maDocGia}">[${dg.maDocGia}] ${dg.hoTen} - SĐT: ${dg.soDienThoai}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>Chọn Sách (Tối đa ${maxSach != null ? maxSach : 5} cuốn) (*):</label>
            <select name="maSach" class="select2-sach" multiple="multiple" style="width: 100%;" required>
                <c:forEach var="s" items="${dsSach}">
                    <c:if test="${s.soLuongTonKho > 0}">
                        <option value="${s.maSach}">[${s.maSach}] ${s.tenSach} (Còn: ${s.soLuongTonKho})</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>

        <div class="form-group" style="grid-column: 1 / -1; width: 100%;">
            <label style="display: block; margin-bottom: 5px;">Thông tin thời gian:</label>
            <div style="display: flex; gap: 50px; align-items: center; width: 100%;">
                <span>
                    📅 Ngày mượn: <strong style="color: #0d6efd; margin-left: 5px;">${hienThiNgayMuon}</strong>
                </span>
                <span>
                    ⏳ Hạn trả: <strong style="color: #dc3545; margin-left: 5px;">${hienThiHanTra}</strong>
                </span>
            </div>
        </div>

        <button type="submit" class="btn-save" style="margin-top: 20px;">💾 Lưu Phiếu Mượn</button>
      </form>
    </div>
  </div>
  
  <div id="modalChiTiet" class="modal">
    <div class="modal-content" style="width: 800px; margin-top: 50px;"> 
      <span class="close" onclick="dongModal('modalChiTiet')">&times;</span>
      
      <c:if test="${not empty pmChiTiet}">
          <h2 style="background-color: #17a2b8;">📖 Chi Tiết Phiếu: ${pmChiTiet.maPhieuMuon}</h2>
          
          <div style="padding: 20px;">
              <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px; margin-bottom: 20px; background: #f8f9fa; padding: 15px; border-radius: 5px;">
                  <div><strong>👤 Độc giả:</strong> ${pmChiTiet.docGia.hoTen}</div>
                  <div><strong>🔢 Mã ĐG:</strong> ${pmChiTiet.docGia.maDocGia}</div>
                  <div><strong>📅 Ngày mượn:</strong> <fmt:formatDate value="${pmChiTiet.ngayMuon}" pattern="dd/MM/yyyy"/></div>
                  <div><strong>⏳ Hạn trả:</strong> <fmt:formatDate value="${pmChiTiet.ngayHenTra}" pattern="dd/MM/yyyy"/></div>
              </div>
              
              <h3>📚 Danh sách sách đã mượn</h3>
              
              <table class="data-table" style="width: 100%; margin-top: 10px;">
                  <thead>
                      <tr>
                          <th>Mã Sách</th>
                          <th>Tên Sách</th>
                          <th>Trạng thái & Phạt</th>
                          <th>Hành động</th>
                      </tr>
                  </thead>
                  <tbody>
                  <c:forEach var="ct" items="${pmChiTiet.chiTietPhieuMuons}">
                      <tr>
                          <td>${ct.sach.maSach}</td>
                          <td>${ct.sach.tenSach}</td>
									<td>
    <c:choose>
        <c:when test="${empty ct.ngayTraThucTe}">
            <span style="color: #d32f2f; font-weight: bold;">Đang mượn</span>
            
            <c:if test="${pmChiTiet.soNgayTreHan > 0}">
                <br>
                <span style="color: #dc3545; font-size: 11px; background: #ffe6e6; padding: 2px 5px; border-radius: 4px; border: 1px solid #f5c6cb;">
                    ⚠️ Quá hạn ${pmChiTiet.soNgayTreHan} ngày
                </span>
            </c:if>
        </c:when>

        <c:otherwise>
            <span style="color: #2e7d32; font-weight: bold;">
              Đã trả: <fmt:formatDate value="${ct.ngayTraThucTe}" pattern="dd/MM/yyyy"/>
            </span>
            <br>
            <small>
                <c:if test="${ct.trangThaiSach == 0}">✅ Bình thường</c:if>
                <c:if test="${ct.trangThaiSach == 1}"><span style="color: orange; font-weight: bold;">⚠️ Hỏng</span></c:if>
                <c:if test="${ct.trangThaiSach == 2}"><span style="color: red; font-weight: bold;">❌ Mất</span></c:if>
                
                <c:if test="${ct.tienPhat > 0}">
                    | Phạt: <span style="color: red;"><fmt:formatNumber value="${ct.tienPhat}" type="number" maxFractionDigits="0"/> VNĐ</span>
                </c:if>
            </small>
        </c:otherwise>
    </c:choose>
</td>
									<td style="text-align: center;">
                              <c:if test="${empty ct.ngayTraThucTe}">
                                  <button type="button" 
                                     onclick="xacNhanTraSach('${pmChiTiet.maPhieuMuon}', '${ct.sach.maSach}', '${ct.sach.tenSach}')"
                                     class="btn-add" 
                                     style="background-color: #28a745; padding: 5px 10px; font-size: 12px; width: auto; border:none;">
                                     ↩️ Trả sách
                                  </button>
                              </c:if>
                          </td>
                      </tr>
                  </c:forEach>
              </tbody>
              </table>
          </div>
      </c:if>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/view/script.js?v=6"></script>

  <script>
      var maxSach = ${maxSach != null ? maxSach : 5};
      var baoLoi = '${baoLoi}';
      var contextPath = '${pageContext.request.contextPath}';
      initMuonTraPage(maxSach, baoLoi, contextPath);

      <c:if test="${moModalChiTiet == true}">
          document.addEventListener("DOMContentLoaded", function() {
              var modalCT = document.getElementById("modalChiTiet");
              if(modalCT) {
                  modalCT.style.display = "block";
              }
          });
      </c:if>

      <c:if test="${not empty thongBao}">
          Swal.fire({ icon: 'success', title: 'Thành công', text: '${thongBao}' });
      </c:if>
  </script>
</body>
</html>