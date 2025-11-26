package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AdminDAO;
import dao.ThuThuDAO;

/**
 * Servlet implementation class DangNhap
 */
@WebServlet("/DangNhap")
public class DangNhap extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DangNhap() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/view/dangnhap.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String tenDangNhap = request.getParameter("user");
		String matKhau = request.getParameter("pass");
		
		AdminDAO daoadmin = new AdminDAO();
		ThuThuDAO daothuthu = new ThuThuDAO();
		model.QuanTriVien taiKhoanAdmin = daoadmin.kiemTraDangNhap(tenDangNhap, matKhau);
		
		
		if(taiKhoanAdmin != null) {
			HttpSession session = request.getSession();
			session.setAttribute("admin_account", taiKhoanAdmin);
			response.sendRedirect(request.getContextPath() + "/view/trangchuadmin.jsp"); 
			
		} else {
			model.ThuThu taiKhoanThuThu = daothuthu.kiemTraDangNhap(tenDangNhap, matKhau);
			if (taiKhoanThuThu != null) {
				if (taiKhoanThuThu.getTrangThai() == 1) {
		        HttpSession session = request.getSession();
		        session.setAttribute("thuthu_account", taiKhoanThuThu);
		        response.sendRedirect(request.getContextPath() + "/view/trangchuthuthu.jsp"); 
				}else {
					request.setAttribute("baoLoi", "Tài khoản của bạn đã bị KHÓA! Vui lòng liên hệ Admin.");
                    request.getRequestDispatcher("/view/dangnhap.jsp").forward(request, response);
				}
		} else {
		        request.setAttribute("baoLoi", "Sai tên đăng nhập hoặc mật khẩu!");
		        request.getRequestDispatcher("/view/dangnhap.jsp").forward(request, response);
		    }
		}
	}

}
