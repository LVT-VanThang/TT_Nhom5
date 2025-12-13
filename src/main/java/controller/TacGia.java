package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TacGiaDAO;

/**
 * Servlet implementation class TacGia
 */
@WebServlet("/TacGia")
public class TacGia extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TacGia() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");	
		
		TacGiaDAO tgdao = new TacGiaDAO();
		String action = request.getParameter("action");
		String maTacGia = request.getParameter("id");
		
		if (action != null && action.equals("delete") && maTacGia != null) {
			boolean kq = tgdao.xoaTacGia(maTacGia);
			if (kq) {
				response.sendRedirect(request.getContextPath() + "/TacGia");
				return; 
			} else {
				request.setAttribute("baoLoi", "Không thể xóa tác giả này (có thể do đang có sách liên quan)!");
			}
		}
		if (action != null && action.equals("edit") && maTacGia != null) {
			model.TacGia tg = tgdao.timKiemTacGia(maTacGia);
			if (tg != null) {
				request.setAttribute("suaTacGia", tg); 
				request.setAttribute("moFormThem", true); 
			}
		}
		List<model.TacGia> dstg = null;
		String tuKhoa = request.getParameter("keyword"); 
		
		if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
			String tuKhoaChuan = tuKhoa.trim();
			dstg = tgdao.timKiemTheoTenTacGia(tuKhoaChuan);
		} else {
			dstg = tgdao.layDanhSachTacGia();
		}
		request.setAttribute("dstg", dstg);
		request.getRequestDispatcher("/view/tacgia.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		TacGiaDAO tgdao = new TacGiaDAO();
		String action = request.getParameter("action");
		
		if(action != null && (action.equals("insert") || action.equals("update"))) {
			try {
				String maTacGia = request.getParameter("maTacGia");
				String tenTacGia = request.getParameter("tenTacGia");
				String ghiChu = request.getParameter("ghiChu");
				
				if(maTacGia == null || maTacGia.trim().isEmpty()) {
					throw new Exception("Mã tác giả không được để trống!");
				}
				
				model.TacGia tg = new model.TacGia();
				tg.setMaTacGia(maTacGia.trim());
				tg.setTenTacGia(tenTacGia.trim());
				tg.setGhiChu(ghiChu);
				
				boolean ketQua = false;
				if (action.equals("insert")) {
					if (tgdao.timKiemTacGia(maTacGia) != null) {
						request.setAttribute("baoLoi", "Lỗi: Mã tác giả '" + maTacGia + "' đã tồn tại!");
						request.setAttribute("param.maTacGia", maTacGia); 
						request.setAttribute("param.tenTacGia", tenTacGia);
						request.setAttribute("param.ghiChu", ghiChu);
						request.setAttribute("suaTacGia", tg); 
						request.setAttribute("moFormThem", true); 
						doGet(request, response);
						return;
					} 
					else {
						ketQua = tgdao.themTacGia(tg);
					}
				} 
				else if(action.equals("update")) { 
					if (tgdao.timKiemTacGia(maTacGia) == null) {
						request.setAttribute("baoLoi", "Lỗi: Không tìm thấy tác giả để sửa!");
						doGet(request, response);
						return;
					}
					ketQua = tgdao.capNhatTacGia(tg); 
				}

				if (ketQua) {
					response.sendRedirect(request.getContextPath() + "/TacGia");
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