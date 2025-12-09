package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import dao.NhaXuatBanDAO;
import dao.TheLoaiDAO;
import dao.ThuThuDAO;

/**
 * Servlet implementation class TrangChuQuanTriVien
 */
@WebServlet("/TrangChuQuanTriVien")
public class TrangChuQuanTriVien extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrangChuQuanTriVien() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		if (session.getAttribute("admin_account") == null) {
			response.sendRedirect(request.getContextPath() + "/view/dangnhap.jsp");
			return;
		}
        TheLoaiDAO tldao= new TheLoaiDAO();
        NhaXuatBanDAO nxbdao=new NhaXuatBanDAO();
        ThuThuDAO ttdao=new ThuThuDAO();
		int slTheLoai = tldao.layDanhSachTheLoai().size();
		int slNXB = nxbdao.layDanhSachNhaXuatBan().size();
		int slThuThu = ttdao.layDanhSachThuThu().size(); 
		dao.QuyDinhDAO qdDao = new dao.QuyDinhDAO();
	    model.QuyDinh qdNgay = qdDao.timKiemQuyDinh("QD001"); 
	    model.QuyDinh qdPhat = qdDao.timKiemQuyDinh("QD002");
	    
	    request.setAttribute("qdNgay", qdNgay);
	    request.setAttribute("qdPhat", qdPhat);
		request.setAttribute("slTheLoai", slTheLoai);
		request.setAttribute("slNXB", slNXB);
		request.setAttribute("slThuThu", slThuThu);
		request.getRequestDispatcher("/view/trangchuadmin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action != null && action.equals("update_config")) {
	        try {
	            String giaTriNgay = request.getParameter("giaTriNgay");
	            String giaTriPhat = request.getParameter("giaTriPhat");
	            
	            dao.QuyDinhDAO qdDao = new dao.QuyDinhDAO();
	            model.QuyDinh qdNgay = qdDao.timKiemQuyDinh("QD001");
	            if (qdNgay != null) {
	                qdNgay.setGiaTri(giaTriNgay);
	                qdNgay.setNgayCapNhat(new java.util.Date());
	                model.QuanTriVien admin = (model.QuanTriVien)
	                request.getSession().getAttribute("admin_account");
	                qdNgay.setQuanTriVien(admin);
	                
	                qdDao.capNhatQuyDinh(qdNgay);
	            }
	            model.QuyDinh qdPhat = qdDao.timKiemQuyDinh("QD002");
	            if (qdPhat != null) {
	                qdPhat.setGiaTri(giaTriPhat);
	                qdPhat.setNgayCapNhat(new java.util.Date());
	                model.QuanTriVien admin = (model.QuanTriVien) 
	                request.getSession().getAttribute("admin_account");
	                qdPhat.setQuanTriVien(admin);
	                qdDao.capNhatQuyDinh(qdPhat);
	            }
	            request.setAttribute("thongBaoConfig", "Cập nhật cấu hình thành công!");
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            request.setAttribute("thongBaoConfig", "Lỗi: " + e.getMessage());
	        }
	        doGet(request, response);
	    } 
	    else {
	        doGet(request, response);
	    }
	}

}
