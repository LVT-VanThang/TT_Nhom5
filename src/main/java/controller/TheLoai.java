package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.TheLoaiDAO;

/**
 * Servlet implementation class TheLoai
 */
@WebServlet("/TheLoai")
public class TheLoai extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TheLoai() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		TheLoaiDAO tldao= new TheLoaiDAO();
		List<model.TheLoai> dstl= null;
		String action = request.getParameter("action");
	    String maTheLoai = request.getParameter("id");
	    String tuKhoa = request.getParameter("tuKhoa");
		if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
			String tuKhoaChuan = tuKhoa.trim();
			dstl = tldao.timKiemTheoTenTheLoai(tuKhoaChuan);
		} else {
			dstl = tldao.layDanhSachTheLoai();
		}
	    if (action != null && action.equals("delete") && maTheLoai != null) {
	        boolean kq = tldao.xoaTheLoai(maTheLoai);
	        if (kq) {
	            response.sendRedirect(request.getContextPath() + "/TheLoai");
	            return; 
	        } else {
	            request.setAttribute("baoLoi", "Không thể xóa thể loại này (có thể do đang được sử dụng ở nơi khác)!");
	        }
	    }
	    if ("edit".equals(action) && maTheLoai != null) {
	        model.TheLoai tl = tldao.timKiemTheLoai(maTheLoai);
	        if (tl != null) {
	            request.setAttribute("suaTheLoai", tl);
	            request.setAttribute("moFormThem", true); 
	        }
	    }
		request.setAttribute("dstl", dstl);
		request.getRequestDispatcher("/view/theloai.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    
	    TheLoaiDAO tldao=new TheLoaiDAO();
	    String action = request.getParameter("action");	
	    if(action != null && (action.equals("insert") || action.equals("update"))) {
	    	try {
				String maTheLoai=request.getParameter("maTheLoai");
				String tenTheLoai=request.getParameter("tenTheLoai");
				String viTriKe=request.getParameter("viTriKe");
				model.TheLoai tl=new model.TheLoai();
				if(maTheLoai == null || maTheLoai.trim().isEmpty()) {
	                throw new Exception("Mã quy định không được để trống!");
	            }
                tl.setMaTheLoai(maTheLoai.trim());
                tl.setTenTheLoai(tenTheLoai.trim());
                tl.setViTriKe(viTriKe.trim());
                boolean ketQua = false;
	            if (action.equals("insert")) {
	                if (tldao.timKiemTheLoai(maTheLoai) != null) {
	                    request.setAttribute("baoLoi", "Lỗi: Mã thể loại '" + maTheLoai + "' đã tồn tại!");
	                    request.setAttribute("maTheLoai", maTheLoai); 
	                    request.setAttribute("tenTheLoai", tenTheLoai);
	                    request.setAttribute("viTriKe", viTriKe);
	                    
	                    request.setAttribute("moFormThem", true); 
	                    doGet(request, response);
	                    return;
	                 }else {
	                	   List<model.TheLoai> listCheckTen = tldao.timKiemTheoTenTheLoai(tenTheLoai);
	                       if (listCheckTen != null && !listCheckTen.isEmpty()) {
	                           request.setAttribute("baoLoi", "Lỗi: Tên thể loại '" + tenTheLoai + "' đã tồn tại!");
	                           request.setAttribute("maTheLoai", maTheLoai);
	                           request.setAttribute("tenTheLoai", tenTheLoai);
	                           request.setAttribute("viTriKe", viTriKe);
	                           request.setAttribute("moFormThem", true);
	                           doGet(request, response);
	                           return;
	                        }else {
		                          ketQua = tldao.themTheLoai(tl);
		                    }
	                        
	                 } 
	            }else if(action.equals("update")) { 
		            	    if (tldao.timKiemTheLoai(maTheLoai) == null) {
		                         request.setAttribute("baoLoi", "Lỗi: Không tìm thấy thể loại để sửa!");
		                         doGet(request, response);
		                         return;
		            	    }
		                    ketQua = tldao.capNhatTheLoai(tl);
	                  }if (ketQua) {
		                    response.sendRedirect(request.getContextPath() + "/TheLoai");
		                } else {
		                         throw new Exception("Lưu dữ liệu thất bại!");
		                }
	              } catch (Exception e) {
	            	e.printStackTrace();
		            request.setAttribute("baoLoi", e.getMessage());
		            request.setAttribute("maTheLoai", request.getParameter("maTheLoai")); 
	                request.setAttribute("tenTheLoai", request.getParameter("tenTheLoai"));
		            request.setAttribute("moFormThem", true); 
		            doGet(request, response);
			    }
	    }else {
	        doGet(request, response);
	    }
	    
	}

}
