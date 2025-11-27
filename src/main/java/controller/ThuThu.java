package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.ThuThuDAO;



/**
 * Servlet implementation class ThuThu
 */
@WebServlet("/ThuThu")
public class ThuThu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThuThu() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
		ThuThuDAO ttdao = new ThuThuDAO();
		List<model.ThuThu> dstt = null; 
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        if (action != null && action.equals("new")) {
			request.setAttribute("moFormThem", true);
		}
        if (action != null && action.equals("delete")) {
			
			boolean xoaThanhCong = ttdao.xoaThuThu(id);
			if (xoaThanhCong) {
				response.sendRedirect(request.getContextPath() + "/ThuThu");
				return; 
			} else {
				request.setAttribute("baoLoi", "Không thể xóa! Thủ thư '" + id + "' đã lập phiếu mượn hoặc có dữ liệu liên quan.");
			}
		}
        if (action != null && action.equals("edit")) {
            model.ThuThu ttCanSua = ttdao.timKiemThuThu(id);
            
            if (ttCanSua != null) {
                request.setAttribute("suaThuThu", ttCanSua);    
                request.setAttribute("moFormThem", true); 
            }
        }
		String tuKhoa = request.getParameter("tuKhoa");
		if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
			String tuKhoaChuan = tuKhoa.trim();
			dstt = ttdao.timKiemTongHop(tuKhoaChuan);
		} else {
			dstt = ttdao.layDanhSachThuThu();
		}
		request.setAttribute("dstt", dstt);
		request.setAttribute("tuKhoa", tuKhoa);
		request.getRequestDispatcher("/view/taikhoanthuthu.jsp").forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				ThuThuDAO ttdao=new ThuThuDAO();
				String action = request.getParameter("action");
				
				if (action != null && action.equals("update")) {
					String maThuThu = request.getParameter("maThuThu");
					String hoTen = request.getParameter("hoTen");
					String email = request.getParameter("email");
					String tenDangNhap = request.getParameter("tenDangNhap");
					String matKhau = request.getParameter("matKhau");
					byte trangThai = Byte.parseByte(request.getParameter("trangThai"));
					
					model.ThuThu tt = new model.ThuThu();
					tt.setMaThuThu(maThuThu);
					tt.setHoTen(hoTen);
					tt.setEmail(email);
					tt.setTenDangNhap(tenDangNhap);
					tt.setMatKhau(matKhau);
					tt.setTrangThai(trangThai);
				
					boolean ketQua = ttdao.capNhatThuThu(tt);
					
					if (ketQua) {
						response.sendRedirect(request.getContextPath() + "/ThuThu");
					} else {
						request.setAttribute("baoLoi", "Cập nhật thất bại!");
						doGet(request, response);
					}
				  }else if(action != null && action.equals("insert")) {
						String maThuThu = request.getParameter("maThuThu").trim();
						String hoTen = request.getParameter("hoTen").trim();
						String email = request.getParameter("email").trim();
						String tenDangNhap = request.getParameter("tenDangNhap").trim();
						String matKhau = request.getParameter("matKhau");
						byte trangThai = Byte.parseByte(request.getParameter("trangThai"));
						if (ttdao.timKiemThuThu(maThuThu) != null) {
							request.setAttribute("baoLoi", "Lỗi: Mã thủ thư '" + maThuThu + "' đã tồn tại!");
							doGet(request, response);
							return; 
						}
						if (ttdao.kiemTraTenDangNhap(tenDangNhap)) {
							request.setAttribute("baoLoi", "Lỗi: Tên đăng nhập '" + tenDangNhap + "' đã có người sử dụng!");
							doGet(request, response);
							return;
						}
						if (ttdao.kiemTraEmail(email)) {
                            request.setAttribute("baoLoi", "Lỗi: Email '" + email + "' đã được sử dụng bởi tài khoản khác!");
                            doGet(request, response);
                            return;
                        }
						model.ThuThu tt=new model.ThuThu();
						tt.setMaThuThu(maThuThu);
						tt.setHoTen(hoTen);
						tt.setEmail(email);
						tt.setTenDangNhap(tenDangNhap);
						tt.setMatKhau(matKhau);
						tt.setTrangThai(trangThai);
						
						boolean ketQua = ttdao.themThuThu(tt);
						if (ketQua) {
							response.sendRedirect(request.getContextPath() + "/ThuThu");
						} else {
							request.setAttribute("baoLoi", "Thêm thất bại!");
							doGet(request, response); 
						}
				} else {

					doGet(request, response);
				}
	}
}
