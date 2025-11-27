package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NhaXuatBanDAO;

/**
 * Servlet implementation class NhaXuatBan
 */
@WebServlet("/NhaXuatBan")
public class NhaXuatBan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NhaXuatBan() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		NhaXuatBanDAO nxbdao= new NhaXuatBanDAO();
		List<model.NhaXuatBan> dsnxb= null;
		String action = request.getParameter("action");
	    String maNhaXuatBan = request.getParameter("id");
	    String tuKhoa = request.getParameter("tuKhoa");
		if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
			String tuKhoaChuan = tuKhoa.trim();
			dsnxb = nxbdao.timKiemTheoTenNhaXuatBan(tuKhoaChuan);
		} else {
			dsnxb = nxbdao.layDanhSachNhaXuatBan();
		}
	    if (action != null && action.equals("delete") && maNhaXuatBan != null) {
	        boolean kq = nxbdao.xoaNhaXuatBan(maNhaXuatBan);
	        if (kq) {
	            response.sendRedirect(request.getContextPath() + "/NhaXuatBan");
	            return; 
	        } else {
	            request.setAttribute("baoLoi", "Không thể xóa nhà xuất bản này (có thể do đang được sử dụng ở nơi khác)!");
	        }
	    }
	    if ("edit".equals(action) && maNhaXuatBan != null) {
	        model.NhaXuatBan nxb = nxbdao.timKiemNhaXuatBan(maNhaXuatBan);
	        if (nxb != null) {
	            request.setAttribute("suaNhaXuatBan", nxb);
	            request.setAttribute("moFormThem", true); 
	        }
	    }
		request.setAttribute("dsnxb", dsnxb);
		request.getRequestDispatcher("/view/nhaxuatban.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    
	    NhaXuatBanDAO nxbdao=new NhaXuatBanDAO();
	    String action = request.getParameter("action");	
	    if(action != null && (action.equals("insert") || action.equals("update"))) {
	    	try {
				String maNhaXuatBan=request.getParameter("maNhaXuatBan");
				String tenNhaXuatBan=request.getParameter("tenNhaXuatBan");
				model.NhaXuatBan nxb=new model.NhaXuatBan();
				if(maNhaXuatBan == null || maNhaXuatBan.trim().isEmpty()) {
	                throw new Exception("Mã quy định không được để trống!");
	            }
               nxb.setMaNXB(maNhaXuatBan);
               nxb.setTenNXB(tenNhaXuatBan);
                boolean ketQua = false;
	            if (action.equals("insert")) {
	                if (nxbdao.timKiemNhaXuatBan(maNhaXuatBan) != null) {
	                    request.setAttribute("baoLoi", "Lỗi: Mã thể loại '" + maNhaXuatBan + "' đã tồn tại!");
	                    request.setAttribute("maNhaXuatBan", maNhaXuatBan); 
	                    request.setAttribute("tenNhaXuatBan", tenNhaXuatBan);
	                    
	                    request.setAttribute("moFormThem", true); 
	                    doGet(request, response);
	                    return;
	                 }else {
	                	   List<model.NhaXuatBan> listCheckTen = nxbdao.timKiemTheoTenNhaXuatBan(tenNhaXuatBan);
	                       if (listCheckTen != null && !listCheckTen.isEmpty()) {
	                           request.setAttribute("baoLoi", "Lỗi: Tên tên nhà xuất bản '" + tenNhaXuatBan + "' đã tồn tại!");
	                           request.setAttribute("maNhaXuatBan", maNhaXuatBan);
	                           request.setAttribute("tenNhaXuatBan", tenNhaXuatBan);
	                           request.setAttribute("moFormThem", true);
	                           doGet(request, response);
	                           return;
	                        }else {
		                          ketQua = nxbdao.themNhaXuatBan(nxb);
		                    }
	                        
	                 } 
	            }else if(action.equals("update")) { 
		            	    if (nxbdao.timKiemNhaXuatBan(maNhaXuatBan) == null) {
		                         request.setAttribute("baoLoi", "Lỗi: Không tìm thấy nhà xuất bản để sửa!");
		                         doGet(request, response);
		                         return;
		            	    }
		                    ketQua = nxbdao.capNhatNhaXuatBan(nxb);
	                  }if (ketQua) {
		                    response.sendRedirect(request.getContextPath() + "/NhaXuatBan");
		                } else {
		                         throw new Exception("Lưu dữ liệu thất bại!");
		                }
	              } catch (Exception e) {
	            	e.printStackTrace();
		            request.setAttribute("baoLoi", e.getMessage());
		            request.setAttribute("maNhaXuatBan", request.getParameter("maNhaXuatBan")); 
	                request.setAttribute("tenNhaXuatBan", request.getParameter("tenNhaXuatBan"));
		            request.setAttribute("moFormThem", true); 
		            doGet(request, response);
			    }
	    }else {
	        doGet(request, response);
	    }
	    
	}
	

}
