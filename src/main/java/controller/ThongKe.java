package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ThongKeDAO;

/**
 * Servlet implementation class ThongKe
 */
@WebServlet("/ThongKe")
public class ThongKe extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ThongKeDAO tkDAO = new ThongKeDAO();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThongKe() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        try {
            request.setAttribute("slSach", tkDAO.demTongSoSach());
            request.setAttribute("slDocGia", tkDAO.demTongDocGia());
            request.setAttribute("slDangMuon", tkDAO.demPhieuDangMuon());
            request.setAttribute("tongPhat", tkDAO.tinhTongTienPhat());
            request.setAttribute("slTreHan", tkDAO.demDocGiaTreHan());
            request.setAttribute("dsTreHan", tkDAO.layDanhSachDocGiaTreHan());

            List<Object[]> topSach = tkDAO.layTopSachMuon();
            request.setAttribute("topSach", topSach);

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/view/thongke.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
