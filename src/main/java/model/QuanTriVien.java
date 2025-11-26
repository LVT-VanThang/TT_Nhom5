package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the quan_tri_vien database table.
 * 
 */
@Entity
@Table(name="quan_tri_vien")
@NamedQuery(name="QuanTriVien.findAll", query="SELECT q FROM QuanTriVien q")
public class QuanTriVien implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String maAdmin;

	private String matKhau;

	private String tenDangNhap;

	//bi-directional many-to-one association to QuyDinh
	@OneToMany(mappedBy="quanTriVien")
	private List<QuyDinh> quyDinhs;

	public QuanTriVien() {
	}

	public String getMaAdmin() {
		return this.maAdmin;
	}

	public void setMaAdmin(String maAdmin) {
		this.maAdmin = maAdmin;
	}

	public String getMatKhau() {
		return this.matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public String getTenDangNhap() {
		return this.tenDangNhap;
	}

	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}

	public List<QuyDinh> getQuyDinhs() {
		return this.quyDinhs;
	}

	public void setQuyDinhs(List<QuyDinh> quyDinhs) {
		this.quyDinhs = quyDinhs;
	}

	public QuyDinh addQuyDinh(QuyDinh quyDinh) {
		getQuyDinhs().add(quyDinh);
		quyDinh.setQuanTriVien(this);

		return quyDinh;
	}

	public QuyDinh removeQuyDinh(QuyDinh quyDinh) {
		getQuyDinhs().remove(quyDinh);
		quyDinh.setQuanTriVien(null);

		return quyDinh;
	}

}