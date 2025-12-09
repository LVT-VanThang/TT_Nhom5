package controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DocGiaDAO;

@WebServlet("/DocGia")
public class DocGia extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DocGiaDAO dgdao = new DocGiaDAO();

	public DocGia() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");
		String maDocGia = request.getParameter("id");
		if (action != null && action.equals("delete") && maDocGia != null) {
			boolean kq = dgdao.xoaDocGia(maDocGia);
			if (kq) {
				response.sendRedirect(request.getContextPath() + "/DocGia");
				return;
			} else {
				request.setAttribute("baoLoi", "Không thể xóa độc giả này (có thể do ràng buộc dữ liệu)!");
			}
		}
		if (action != null && action.equals("edit") && maDocGia != null) {
			model.DocGia dg = dgdao.timKiemDocGia(maDocGia);
			if (dg != null) {
				request.setAttribute("suaDocGia", dg);
				request.setAttribute("moFormThem", true);
				request.setAttribute("isEdit", true);
			}
		}
		List<model.DocGia> dsdg = null;
		String tuKhoa = request.getParameter("tuKhoa");
		if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
			dsdg = dgdao.timKiemTheoTongHop(tuKhoa.trim());
		} else {
			dsdg = dgdao.layDanhSachDocGia();
		}
		request.setAttribute("dsdg", dsdg);
		request.getRequestDispatcher("/view/docgia.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");
		String maDocGia = request.getParameter("maDocGia");
		String hoTen = request.getParameter("hoTen");
		String email = request.getParameter("email");
		String soDienThoai = request.getParameter("soDienThoai");
		String trangThaiThe = request.getParameter("trangThaiThe");
		
		model.DocGia dg = new model.DocGia();
		dg.setMaDocGia(maDocGia);
		dg.setHoTen(hoTen);
		dg.setEmail(email);
		dg.setSoDienThoai(soDienThoai);
		dg.setTrangThaiThe(trangThaiThe);

		try {
			if (maDocGia == null || maDocGia.trim().isEmpty()) {
				throw new Exception("Mã độc giả không được để trống!");
			}
			if (hoTen == null || hoTen.trim().isEmpty()) {
				throw new Exception("Họ tên không được để trống!");
			}
			String idNgoaiTru = "";
			if (action.equals("update")) {
				idNgoaiTru = maDocGia;
			}
			if (action.equals("insert")) {
				if (dgdao.timKiemDocGia(maDocGia) != null) {
					throw new Exception("Mã độc giả '" + maDocGia + "' đã tồn tại!");
				}
			}
			if (email != null && !email.trim().isEmpty()) {
				if (dgdao.kiemTraEmail(email.trim(), idNgoaiTru)) {
					throw new Exception("Email '" + email + "' đã được sử dụng bởi độc giả khác!");
				}
			}
			if (soDienThoai != null && !soDienThoai.trim().isEmpty()) {
				if (dgdao.kiemTraSDT(soDienThoai.trim(), idNgoaiTru)) {
					throw new Exception("Số điện thoại '" + soDienThoai + "' đã tồn tại trong hệ thống!");
				}
			}
			boolean ketQua = false;

			if (action.equals("insert")) {
				LocalDate today = LocalDate.now();
				LocalDate nextYear = today.plusYears(1);
				dg.setNgayLapThe(Date.valueOf(today));
				dg.setNgayHetHan(Date.valueOf(nextYear));
				if(trangThaiThe == null) dg.setTrangThaiThe("Còn Hạn");

				ketQua = dgdao.themDocGia(dg);
			} 
			else if (action.equals("update")) {
				String ngayLapStr = request.getParameter("ngayLapThe");
				String ngayHetHanStr = request.getParameter("ngayHetHan");
				
				if (ngayLapStr != null && !ngayLapStr.isEmpty())
					dg.setNgayLapThe(Date.valueOf(ngayLapStr));
				
				if (ngayHetHanStr != null && !ngayHetHanStr.isEmpty())
					dg.setNgayHetHan(Date.valueOf(ngayHetHanStr));

				ketQua = dgdao.capNhatDocGia(dg);
			}

			if (ketQua) {
				response.sendRedirect(request.getContextPath() + "/DocGia");
			} else {
				throw new Exception("Lỗi hệ thống khi lưu dữ liệu (Database Error)!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("baoLoi", e.getMessage()); 
			boolean isUpdateMode = "update".equals(action);
			request.setAttribute("isEdit", isUpdateMode);
			request.setAttribute("suaDocGia", dg);
			request.setAttribute("moFormThem", true);
			doGet(request, response);
		}
	}
}