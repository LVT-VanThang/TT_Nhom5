package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NhaXuatBanDAO;
import dao.SachDAO;
import dao.TacGiaDAO;
import dao.TheLoaiDAO;
import model.NhaXuatBan;
import model.TacGia;
import model.TheLoai;

/**
 * Servlet implementation class Sach
 */
@WebServlet("/Sach")
public class Sach extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sach() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");	
		
		SachDAO sdao = new SachDAO();
		TacGiaDAO tgdao = new TacGiaDAO();
		TheLoaiDAO tldao = new TheLoaiDAO();
		NhaXuatBanDAO nxbdao = new NhaXuatBanDAO();
		
		List<TacGia> dstg = tgdao.layDanhTacGiaTacGia();
		List<TheLoai> dstl = tldao.layDanhSachTheLoai();
		List<NhaXuatBan> dsnxb = nxbdao.layDanhSachNhaXuatBan();
		
		request.setAttribute("dstg", dstg);
		request.setAttribute("dstl", dstl);
		request.setAttribute("dsnxb", dsnxb);
		
		String action = request.getParameter("action");
		String maSach = request.getParameter("id");
		
		if (action != null && action.equals("delete") && maSach != null) {
			boolean kq = sdao.xoaSach(maSach);
			if (kq) {
				response.sendRedirect(request.getContextPath() + "/Sach");
				return; 
			} else {
				request.setAttribute("baoLoi", "Không thể xóa sách này (có thể do đang có phiếu mượn liên quan)!");
			}
		}
		
		if (action != null && action.equals("edit") && maSach != null) {
			model.Sach s = sdao.timKiemSach(maSach);
			if (s != null) {
				request.setAttribute("suaSach", s); 
				request.setAttribute("moFormThem", true); 
			}
		}
		
		List<model.Sach> dss = null;
		String tuKhoa = request.getParameter("keyword"); 
		
		if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
			String tuKhoaChuan = tuKhoa.trim();
			dss = sdao.timKiemTongHop(tuKhoaChuan);
		} else {
			dss = sdao.layDanhSachSach();
		}
		request.setAttribute("dss", dss);
		request.getRequestDispatcher("/view/sach.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		SachDAO sdao = new SachDAO();
		String action = request.getParameter("action");
		
		if(action != null && (action.equals("insert") || action.equals("update"))) {
			try {
				String maSach = request.getParameter("maSach");
				String tenSach = request.getParameter("tenSach");
				String maTacGia = request.getParameter("maTacGia");
				String maTheLoai = request.getParameter("maTheLoai");
				String maNXB = request.getParameter("maNXB");
				String namXBStr = request.getParameter("namXuatBan");
				String soLuongStr = request.getParameter("soLuongTonKho");
				
				if(maSach == null || maSach.trim().isEmpty()) {
					throw new Exception("Mã sách không được để trống!");
				}
				
				model.Sach s = new model.Sach();
				s.setMaSach(maSach.trim());
				s.setTenSach(tenSach.trim());
				s.setNamXuatBan(Integer.parseInt(namXBStr));
				s.setSoLuongTonKho(Integer.parseInt(soLuongStr));
				
				TacGia tg = new TacGia();
				tg.setMaTacGia(maTacGia);
				s.setTacGia(tg);
				TheLoai tl = new TheLoai();
				tl.setMaTheLoai(maTheLoai);
				s.setTheLoai(tl);
				NhaXuatBan nxb = new NhaXuatBan();
				nxb.setMaNXB(maNXB);
				s.setNhaXuatBan(nxb);
				
				boolean ketQua = false;
				if (action.equals("insert")) {
					if (sdao.timKiemSach(maSach) != null) {
						request.setAttribute("baoLoi", "Lỗi: Mã sách '" + maSach + "' đã tồn tại!");
						request.setAttribute("param.maSach", maSach); 
						request.setAttribute("param.tenSach", tenSach);
						request.setAttribute("param.namXuatBan", namXBStr);
						request.setAttribute("param.soLuongTonKho", soLuongStr);
						
						request.setAttribute("suaSach", s); 
						request.setAttribute("moFormThem", true); 
						doGet(request, response);
						return;
					} 
					else {
						
						
						ketQua = sdao.themSach(s);
					}
				} 
				else if(action.equals("update")) { 
					if (sdao.timKiemSach(maSach) == null) {
						request.setAttribute("baoLoi", "Lỗi: Không tìm thấy sách để sửa!");
						doGet(request, response);
						return;
					}
					ketQua = sdao.capNhatSach(s); 
				}

				if (ketQua) {
					response.sendRedirect(request.getContextPath() + "/Sach");
				} else {
					throw new Exception("Lưu dữ liệu thất bại!");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("baoLoi", "Lỗi hệ thống: " + e.getMessage());
				request.setAttribute("moFormThem", true); 
				doGet(request, response);
			}
		} else {
			doGet(request, response);
		}
	}
}