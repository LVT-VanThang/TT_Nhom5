package controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DocGiaDAO; 

@WebServlet("/DocGia")
public class DocGia extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DocGiaDAO dgdao = new DocGiaDAO();

    public DocGia() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String maDocGia = request.getParameter("id");

        // --- XỬ LÝ KHÓA (XÓA MỀM) ---
        if ("delete".equals(action) && maDocGia != null) {
            // Hàm xoaDocGia bây giờ thực chất là update trạng thái về 0
            boolean kq = dgdao.xoaDocGia(maDocGia);
            if (kq) {
                response.sendRedirect(request.getContextPath() + "/DocGia");
                return;
            } else {
                request.setAttribute("baoLoi", "Không thể xử lý yêu cầu này!");
            }
        }

        // --- XỬ LÝ SỬA (LOAD FORM) ---
        if ("edit".equals(action) && maDocGia != null) {
            model.DocGia dg = dgdao.timKiemDocGia(maDocGia);
            if (dg != null) {
                request.setAttribute("suaDocGia", dg);
                request.setAttribute("moFormThem", true);
                request.setAttribute("isEdit", true);
            }
        }

        // --- LOAD DANH SÁCH ---
        List<model.DocGia> dsdg = null;
        String tuKhoa = request.getParameter("tuKhoa");
        if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
            dsdg = dgdao.timKiemTheoTongHop(tuKhoa.trim());
        } else {
            dsdg = dgdao.layDanhSachDocGia();
        }
        request.setAttribute("dsdg", dsdg);
        request.getRequestDispatcher("/view/docgia.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String maDocGia = request.getParameter("maDocGia");
        String hoTen = request.getParameter("hoTen");
        String email = request.getParameter("email");
        String soDienThoai = request.getParameter("soDienThoai");
        
        // Lấy trạng thái dạng chuỗi
        String trangThaiTheStr = request.getParameter("trangThaiThe");

        model.DocGia dg = new model.DocGia();
        dg.setMaDocGia(maDocGia);
        dg.setHoTen(hoTen);
        dg.setEmail(email);
        dg.setSoDienThoai(soDienThoai);

        // --- XỬ LÝ ÉP KIỂU INT CHO TRẠNG THÁI ---
        int trangThai = 1; // Mặc định là 1 (Hoạt động)
        if (trangThaiTheStr != null && !trangThaiTheStr.isEmpty()) {
            try {
                trangThai = Integer.parseInt(trangThaiTheStr);
            } catch (NumberFormatException e) {
                trangThai = 1; // Nếu lỗi format thì về mặc định
            }
        }
        dg.setTrangThaiThe(trangThai); 

        try {
            // --- VALIDATION ---
            if (maDocGia == null || maDocGia.trim().isEmpty()) {
                throw new Exception("Mã độc giả không được để trống!");
            }
            if (hoTen == null || hoTen.trim().isEmpty()) {
                throw new Exception("Họ tên không được để trống!");
            }

            String idNgoaiTru = "update".equals(action) ? maDocGia : "";

            if ("insert".equals(action)) {
                if (dgdao.timKiemDocGia(maDocGia) != null) {
                    throw new Exception("Mã độc giả '" + maDocGia + "' đã tồn tại!");
                }
            }
            if (email != null && !email.trim().isEmpty()) {
                if (dgdao.kiemTraEmail(email.trim(), idNgoaiTru)) {
                    throw new Exception("Email '" + email + "' đã được sử dụng!");
                }
            }
            if (soDienThoai != null && !soDienThoai.trim().isEmpty()) {
                if (dgdao.kiemTraSDT(soDienThoai.trim(), idNgoaiTru)) {
                    throw new Exception("SĐT '" + soDienThoai + "' đã tồn tại!");
                }
            }

            boolean ketQua = false;

            // --- XỬ LÝ THÊM MỚI ---
            if ("insert".equals(action)) {
                LocalDate today = LocalDate.now();
                LocalDate nextYear = today.plusYears(1);
                dg.setNgayLapThe(Date.valueOf(today));
                dg.setNgayHetHan(Date.valueOf(nextYear));
                
                // QUY ĐỊNH: Thêm mới thì mặc định là Hoạt động (1)
                dg.setTrangThaiThe(1); 

                ketQua = dgdao.themDocGia(dg);
            } 
            // --- XỬ LÝ CẬP NHẬT ---
            else if ("update".equals(action)) {
                String ngayLapStr = request.getParameter("ngayLapThe");
                String ngayHetHanStr = request.getParameter("ngayHetHan");
                
                if (ngayLapStr != null && !ngayLapStr.isEmpty())
                    dg.setNgayLapThe(Date.valueOf(ngayLapStr));
                
                if (ngayHetHanStr != null && !ngayHetHanStr.isEmpty())
                    dg.setNgayHetHan(Date.valueOf(ngayHetHanStr));

                // Trạng thái đã được set ở trên (dòng 85)
                ketQua = dgdao.capNhatDocGia(dg);
            }

            if (ketQua) {
                response.sendRedirect(request.getContextPath() + "/DocGia");
            } else {
                throw new Exception("Lỗi hệ thống khi lưu dữ liệu!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("baoLoi", e.getMessage());
            boolean isUpdateMode = "update".equals(action);
            request.setAttribute("isEdit", isUpdateMode);
            request.setAttribute("suaDocGia", dg);
            request.setAttribute("moFormThem", true);
            doGet(request, response);
        }
    }
}