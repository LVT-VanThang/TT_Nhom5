package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.QuyDinhDAO;
import model.QuanTriVien;

/**
 * Servlet implementation class QuyDinh
 */
@WebServlet("/QuyDinh")
public class QuyDinh extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuyDinh() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		QuyDinhDAO qddao = new QuyDinhDAO();
		List<model.QuyDinh> dsqd=null;
		String tuKhoa = request.getParameter("tuKhoa");
		if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
			String tuKhoaChuan = tuKhoa.trim();
			dsqd = qddao.timKiemTheoTenQuyDinh(tuKhoaChuan);
		} else {
			dsqd = qddao.layDanhSachQuyDinh();
		}
		request.setAttribute("dsqd", dsqd);
		request.getRequestDispatcher("/view/cauhinhquydinh.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		 
		    QuyDinhDAO qddao = new QuyDinhDAO();
		    String action = request.getParameter("action");	    
		    if(action != null && (action.equals("insert") || action.equals("update"))) {
		        try {
		            String maQuyDinh = request.getParameter("maQuyDinh");
		            String tenQuyDinh = request.getParameter("tenQuyDinh");
		            String giaTri = request.getParameter("giaTri");
		            String donViTinh = request.getParameter("donViTinh");
		            if(maQuyDinh == null || maQuyDinh.trim().isEmpty()) {
		                throw new Exception("Mã quy định không được để trống!");
		            }
		            model.QuyDinh qd = new model.QuyDinh();
		            qd.setMaQuyDinh(maQuyDinh.trim());
		            qd.setTenQuyDinh(tenQuyDinh.trim());
		            qd.setGiaTri(giaTri.trim());
		            qd.setDonViTinh(donViTinh.trim());
		            qd.setNgayCapNhat(new java.util.Date());
		            
		            HttpSession session = request.getSession();
		            model.QuanTriVien adminLog = (model.QuanTriVien) session.getAttribute("admin_account");
		            
		            if(adminLog == null) {
		                adminLog = new model.QuanTriVien();
		                adminLog.setMaAdmin("ADMTEST"); 
		            }
		            qd.setQuanTriVien(adminLog);
		            
		            boolean ketQua = false;

		            if (action.equals("insert")) {
		                
		                // 1. Kiểm tra trùng Mã Quy Định
		                if (qddao.timKiemQuyDinh(maQuyDinh) != null) {
		                    request.setAttribute("baoLoi", "Lỗi: Mã quy định '" + maQuyDinh + "' đã tồn tại!");
		                    
		                    // ❌ XÓA DÒNG NÀY ĐI: request.setAttribute("suaQuyDinh", qd); 
		                    // Lý do: Nếu để dòng này, JSP sẽ tưởng là đang Cập nhật (Update) nên hiện tiêu đề sai.
		                    
		                    // ✅ THÊM CÁC DÒNG NÀY (Để giữ lại dữ liệu người dùng vừa nhập)
		                    request.setAttribute("maQuyDinh", maQuyDinh); // Gửi về dưới dạng attribute thường
		                    request.setAttribute("tenQuyDinh", tenQuyDinh);
		                    request.setAttribute("giaTri", giaTri);
		                    request.setAttribute("donViTinh", donViTinh);
		                    
		                    request.setAttribute("moFormThem", true); // Mở lại modal
		                    doGet(request, response);
		                    return;
		                } 
		                // 2. Kiểm tra trùng Tên Quy Định (Lưu ý: hàm tìm tên trả về List)
		                else {
		                    List<model.QuyDinh> listCheckTen = qddao.timKiemTheoTenQuyDinh(tenQuyDinh);
		                    // Phải kiểm tra !isEmpty() vì List rỗng không phải là null
		                    if (listCheckTen != null && !listCheckTen.isEmpty()) {
		                        request.setAttribute("baoLoi", "Lỗi: Tên quy định '" + tenQuyDinh + "' đã tồn tại!");
		                        
		                        // Giữ lại dữ liệu đã nhập
		                        request.setAttribute("maQuyDinh", maQuyDinh);
		                        request.setAttribute("tenQuyDinh", tenQuyDinh);
		                        request.setAttribute("giaTri", giaTri);
		                        request.setAttribute("donViTinh", donViTinh);
		                        
		                        request.setAttribute("moFormThem", true);
		                        doGet(request, response);
		                        return;
		                    } 
		                    // 3. Nếu không trùng gì cả thì Thêm mới
		                    else {
		                        ketQua = qddao.themQuyDinh(qd);
		                    }
		                }
		            } else { // Chức năng Sửa (Update)
		                // Code update của bạn ở đây...
		                ketQua = true; 
		            }

		            if (ketQua) {
		                response.sendRedirect(request.getContextPath() + "/QuyDinh");
		            } else {
		                throw new Exception("Lưu dữ liệu thất bại!");
		            }
		            
		        } catch (Exception e) {
		            e.printStackTrace();
		            request.setAttribute("baoLoi", e.getMessage());
		            request.setAttribute("moFormThem", true); 
		            doGet(request, response);
		        }
		    } else {
		        doGet(request, response);
		    }
		}
}
