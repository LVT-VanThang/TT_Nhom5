package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ThongKeDAO;
import model.PhieuMuon;

/**
 * Servlet implementation class TrangChuThuThu
 */
@WebServlet("/TrangChuThuThu")
public class TrangChuThuThu extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ThongKeDAO tkDAO = new ThongKeDAO();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrangChuThuThu() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        // 1. Kiểm tra đăng nhập (Bảo mật 2 lớp: cả Servlet và JSP)
        HttpSession session = request.getSession();
        if (session.getAttribute("thuthu_account") == null) {
            response.sendRedirect(request.getContextPath() + "/view/dangnhap.jsp");
            return;
        }

        try {
            // 2. Lấy các số liệu thống kê tổng quan (4 ô màu)
            request.setAttribute("slSach", tkDAO.demTongSoSach());
            request.setAttribute("slDocGia", tkDAO.demTongDocGia());
            request.setAttribute("slDangMuon", tkDAO.demPhieuDangMuon());
            request.setAttribute("slTreHan", tkDAO.demDocGiaTreHan());

            // 3. Lấy danh sách cảnh báo (Phiếu trễ hạn)
            List<PhieuMuon> dsTreHan = tkDAO.layDanhSachPhieuTreHan();
            request.setAttribute("alertList", dsTreHan);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4. Chuyển hướng về trang giao diện
        request.getRequestDispatcher("/view/trangchuthuthu.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
