package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DocGiaDAO;
import dao.PhieuMuonDAO;
import dao.QuyDinhDAO;
import dao.SachDAO;
import model.DocGia;
import model.PhieuMuon;
import model.ThuThu;

@WebServlet("/MuonTra")
public class MuonTra extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private PhieuMuonDAO pmDAO = new PhieuMuonDAO();
    private DocGiaDAO dgDAO = new DocGiaDAO();
    private SachDAO sachDAO = new SachDAO();
    private QuyDinhDAO qdDAO = new QuyDinhDAO();

    public MuonTra() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        String tuKhoa = request.getParameter("tuKhoa");

        try {
            // TRẢ SÁCH 
        	if ("return".equals(action)) {
                String maPhieu = request.getParameter("maPhieu");
                String maSach = request.getParameter("maSach");
                
                int tinhTrang = 0;
                try { tinhTrang = Integer.parseInt(request.getParameter("tinhTrang")); } catch(Exception e) {}
                
                BigDecimal tienPhat = BigDecimal.ZERO;
                try { tienPhat = new BigDecimal(request.getParameter("tienPhat")); } catch(Exception e) {}
                
                java.util.Map<String, Object> ketQua = pmDAO.traSach(maPhieu, maSach, tinhTrang, tienPhat);            
                
                if (ketQua != null) {
                    action = "detail"; 
                    String status = (String) ketQua.get("status");
                    
                    if ("finished".equals(status)) {
                        BigDecimal total = (BigDecimal) ketQua.get("total");
                        BigDecimal damage = (BigDecimal) ketQua.get("damage");
                        BigDecimal late = (BigDecimal) ketQua.get("late");
                        long daysLate = (long) ketQua.get("daysLate");
                        
                        String strTotal = String.format("%,.0f", total);
                        String strDamage = String.format("%,.0f", damage);
                        String strLate = String.format("%,.0f", late);
                        
                        String msg = "Đã trả hết sách!" +
                                     "Tổng phạt: " + strTotal + " VNĐ" +
                                     "(Hư/Mất: " + strDamage + " - Trễ hạn " + daysLate + " ngày: " + strLate + ")";
                        
                        request.setAttribute("thongBao", msg);
                    } else {
                        request.setAttribute("thongBao", "Trả sách thành công!");
                    }
                } else {
                    request.setAttribute("baoLoi", "Lỗi khi trả sách!");
                }
            }

            // XEM CHI TIẾT 
            if ("detail".equals(action)) {
                String maPhieu = request.getParameter("maPhieu");
                PhieuMuon pmChiTiet = pmDAO.layPhieuMuonTheoMa(maPhieu);
                request.setAttribute("pmChiTiet", pmChiTiet);
                request.setAttribute("moModalChiTiet", true); 
            }

            //LOAD DANH SÁCH
            if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
                request.setAttribute("dsPhieu", pmDAO.timKiemPhieuMuon(tuKhoa.trim()));
            } else {
                request.setAttribute("dsPhieu", pmDAO.layDanhSachPhieuMuon());
            }
            request.setAttribute("dsDocGia", dgDAO.layDanhSachDocGia());
            request.setAttribute("dsSach", sachDAO.layDanhSachSach());
            int maxNgay = qdDAO.layGiaTriQuyDinh("QD002");
            if (maxNgay <= 0) maxNgay = 7; 
            int maxSach = qdDAO.layGiaTriQuyDinh("QD003");
            if(maxSach<=0) maxSach=5;
            
            request.setAttribute("maxNgay", maxNgay);
            request.setAttribute("maxSach", maxSach);
            
            LocalDate homNay = LocalDate.now();
            LocalDate ngayTra = homNay.plusDays(maxNgay);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            request.setAttribute("hienThiNgayMuon", homNay.format(fmt));       
            request.setAttribute("hienThiHanTra", ngayTra.format(fmt));

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("baoLoi", "Lỗi hệ thống: " + e.getMessage());
        }

        request.getRequestDispatcher("/view/muontra.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        try {
            if (action != null && "insert".equals(action)) {
                String maDocGia = request.getParameter("maDocGia");
                String[] maSachArr = request.getParameterValues("maSach");

                if (maDocGia == null || maDocGia.isEmpty())
                    throw new Exception("Vui lòng chọn Độc giả!");
                if (pmDAO.kiemTraDocGiaDangMuon(maDocGia)) {
                    throw new Exception("Độc giả này đang có phiếu mượn chưa trả xong!");
                }
                if (maSachArr == null || maSachArr.length == 0) 
                    throw new Exception("Vui lòng chọn ít nhất 1 cuốn sách!");

                String maPhieuTuDong = pmDAO.taoMaPhieuTuDong();
                PhieuMuon pm = new PhieuMuon();
                pm.setMaPhieuMuon(maPhieuTuDong); 
                
                DocGia dg = new DocGia();
                dg.setMaDocGia(maDocGia);
                pm.setDocGia(dg);

                HttpSession session = request.getSession();
                ThuThu thuThuHienTai = (ThuThu) session.getAttribute("thuthu_account");
                
                if (thuThuHienTai != null) {
                    pm.setThuThu(thuThuHienTai);
                } else {
                    throw new Exception("Phiên đăng nhập hết hạn! Vui lòng đăng nhập lại.");
                }
                
                LocalDate ngayHienTai = LocalDate.now();
                pm.setNgayMuon(Date.valueOf(ngayHienTai));

                int soNgayDuocMuon = qdDAO.layGiaTriQuyDinh("QD002");
                if (soNgayDuocMuon <= 0) 
                    soNgayDuocMuon = 7; 
                LocalDate ngayHetHan = ngayHienTai.plusDays(soNgayDuocMuon);
                pm.setNgayHenTra(Date.valueOf(ngayHetHan));

                pm.setTrangThaiPhieu((byte) 0); 

                List<String> listMaSach = Arrays.asList(maSachArr);
                boolean ketQua = pmDAO.lapPhieuMuon(pm, listMaSach);

                if (ketQua) {
                    response.sendRedirect(request.getContextPath() + "/MuonTra");
                    return;
                } else {
                    throw new Exception("Lập phiếu thất bại!");
                }
            } 
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("baoLoi", e.getMessage());
            doGet(request, response);
        }
    }
}