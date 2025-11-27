package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LoaiDocGiaDAO;

/**
 * Servlet implementation class LoaiDocGia
 */
@WebServlet("/LoaiDocGia")
public class LoaiDocGia extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoaiDocGia() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		LoaiDocGiaDAO ldgdao= new LoaiDocGiaDAO();
		List<model.LoaiDocGia> dsldg= null;
		String action = request.getParameter("action");
	    String maLoaiDocGia = request.getParameter("id");
	    String tuKhoa = request.getParameter("tuKhoa");
		if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
			String tuKhoaChuan = tuKhoa.trim();
			dsldg = ldgdao.timKiemTheoTenLoaiDocGia(tuKhoaChuan);
		} else {
			dsldg = ldgdao.layDanhSachLoaiDocGia();
		}
	    if (action != null && action.equals("delete") && maLoaiDocGia != null) {
	        boolean kq = ldgdao.xoaLoaiDocGia(maLoaiDocGia);
	        if (kq) {
	            response.sendRedirect(request.getContextPath() + "/LoaiDocGia");
	            return; 
	        } else {
	            request.setAttribute("baoLoi", "Không thể xóa thể loại này (có thể do đang được sử dụng ở nơi khác)!");
	        }
	    }
	    if ("edit".equals(action) && maLoaiDocGia != null) {
	        model.LoaiDocGia ldg = ldgdao.timKiemLoaiDocGia(maLoaiDocGia);
	        if (ldg != null) {
	            request.setAttribute("suaLoaiDocGia", ldg);
	            request.setAttribute("moFormThem", true); 
	        }
	    }
		request.setAttribute("dsldg", dsldg);
		request.getRequestDispatcher("/view/loaidocgia.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    
	    LoaiDocGiaDAO ldgdao=new LoaiDocGiaDAO();
	    String action = request.getParameter("action");	
	    if(action != null && (action.equals("insert") || action.equals("update"))) {
	    	try {
				String maLoaiDocGia=request.getParameter("maLoaiDocGia");
				String tenLoaiDocGia=request.getParameter("tenLoaiDocGia");
				model.LoaiDocGia ldg=new model.LoaiDocGia();
				if(maLoaiDocGia == null || maLoaiDocGia.trim().isEmpty()) {
	                throw new Exception("Mã quy định không được để trống!");
	            }
                ldg.setMaLoaiDocGia(maLoaiDocGia.trim());
                ldg.setTenLoaiDocGia(tenLoaiDocGia.trim());
                boolean ketQua = false;
	            if (action.equals("insert")) {
	                if (ldgdao.timKiemLoaiDocGia(maLoaiDocGia) != null) {
	                    request.setAttribute("baoLoi", "Lỗi: Mã loại độc giả '" + maLoaiDocGia + "' đã tồn tại!");
	                    request.setAttribute("maLoaiDocGia", maLoaiDocGia); 
	                    request.setAttribute("tenLoaiDocGia", tenLoaiDocGia);
	                    
	                    request.setAttribute("moFormThem", true); 
	                    doGet(request, response);
	                    return;
	                 }else {
	                	   List<model.LoaiDocGia> listCheckTen = ldgdao.timKiemTheoTenLoaiDocGia(tenLoaiDocGia);
	                       if (listCheckTen != null && !listCheckTen.isEmpty()) {
	                           request.setAttribute("baoLoi", "Lỗi: Tên loại độc giả '" + tenLoaiDocGia + "' đã tồn tại!");
	                           request.setAttribute("maLoaiDocGia", maLoaiDocGia);
	                           request.setAttribute("tenLoaiDocGia", tenLoaiDocGia);
	                           request.setAttribute("moFormThem", true);
	                           doGet(request, response);
	                           return;
	                        }else {
		                          ketQua = ldgdao.themLoaiDocGia(ldg);
		                    }
	                        
	                 } 
	            }else if(action.equals("update")) { 
		            	    if (ldgdao.timKiemLoaiDocGia(maLoaiDocGia) == null) {
		                         request.setAttribute("baoLoi", "Lỗi: Không tìm thấy loại độc giả để sửa!");
		                         doGet(request, response);
		                         return;
		            	    }
		                    ketQua = ldgdao.capNhatLoaiDocGia(ldg);
	                  }if (ketQua) {
		                    response.sendRedirect(request.getContextPath() + "/LoaiDocGia");
		                } else {
		                         throw new Exception("Lưu dữ liệu thất bại!");
		                }
	              } catch (Exception e) {
	            	e.printStackTrace();
		            request.setAttribute("baoLoi", e.getMessage());
		            request.setAttribute("maLoaiDocGia", request.getParameter("maLoaiDocGia")); 
	                request.setAttribute("tenLoaiDocGia", request.getParameter("tenLoaiDocGia"));
		            request.setAttribute("moFormThem", true); 
		            doGet(request, response);
			    }
	    }else {
	        doGet(request, response);
	    }
	}

}
