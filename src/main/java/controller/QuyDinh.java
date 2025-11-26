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
		String action = request.getParameter("action");
	    String maQuyDinh = request.getParameter("id"); 
	    if (action != null && action.equals("delete") && maQuyDinh != null) {
	        boolean kq = qddao.xoaQuyDinh(maQuyDinh);
	        if (kq) {
	            response.sendRedirect(request.getContextPath() + "/QuyDinh");
	            return; 
	        } else {
	            request.setAttribute("baoLoi", "Không thể xóa quy định này (có thể do đang được sử dụng ở nơi khác)!");
	        }
	    }
	    if (action != null && action.equals("edit") && maQuyDinh != null) {
	        model.QuyDinh qd = qddao.timKiemQuyDinh(maQuyDinh);
	        if (qd != null) {
	            request.setAttribute("suaQuyDinh", qd); 
	            request.setAttribute("moFormThem", true); 
	        }
	    }
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
		                if (qddao.timKiemQuyDinh(maQuyDinh) != null) {
		                    request.setAttribute("baoLoi", "Lỗi: Mã quy định '" + maQuyDinh + "' đã tồn tại!");
		                    request.setAttribute("maQuyDinh", maQuyDinh); 
		                    request.setAttribute("tenQuyDinh", tenQuyDinh);
		                    request.setAttribute("giaTri", giaTri);
		                    request.setAttribute("donViTinh", donViTinh);
		                    
		                    request.setAttribute("moFormThem", true); 
		                    doGet(request, response);
		                    return;
		                } 
		                else {
		                    List<model.QuyDinh> listCheckTen = qddao.timKiemTheoTenQuyDinh(tenQuyDinh);
		                    if (listCheckTen != null && !listCheckTen.isEmpty()) {
		                        request.setAttribute("baoLoi", "Lỗi: Tên quy định '" + tenQuyDinh + "' đã tồn tại!");
		                        request.setAttribute("maQuyDinh", maQuyDinh);
		                        request.setAttribute("tenQuyDinh", tenQuyDinh);
		                        request.setAttribute("giaTri", giaTri);
		                        request.setAttribute("donViTinh", donViTinh);
		                        
		                        request.setAttribute("moFormThem", true);
		                        doGet(request, response);
		                        return;
		                    } 
		                    else {
		                        ketQua = qddao.themQuyDinh(qd);
		                    }
		                }
		            } else if(action.equals("update")) { 
		            	    if (qddao.timKiemQuyDinh(maQuyDinh) == null) {
		                    request.setAttribute("baoLoi", "Lỗi: Không tìm thấy quy định để sửa!");
		                    doGet(request, response);
		                    return;
		            	    }
		                    ketQua = qddao.capNhatQuyDinh(qd); 
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
