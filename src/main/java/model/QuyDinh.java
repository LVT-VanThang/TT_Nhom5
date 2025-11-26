package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the quy_dinh database table.
 * 
 */
@Entity
@Table(name="quy_dinh")
@NamedQuery(name="QuyDinh.findAll", query="SELECT q FROM QuyDinh q")
public class QuyDinh implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String maQuyDinh;

	private String donViTinh;

	private String giaTri;

	@Temporal(TemporalType.TIMESTAMP)
	private Date ngayCapNhat;

	private String tenQuyDinh;

	//bi-directional many-to-one association to QuanTriVien
	@ManyToOne
	@JoinColumn(name="MaAdmin")
	private QuanTriVien quanTriVien;

	public QuyDinh() {
	}

	public String getMaQuyDinh() {
		return this.maQuyDinh;
	}

	public void setMaQuyDinh(String maQuyDinh) {
		this.maQuyDinh = maQuyDinh;
	}

	public String getDonViTinh() {
		return this.donViTinh;
	}

	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}

	public String getGiaTri() {
		return this.giaTri;
	}

	public void setGiaTri(String giaTri) {
		this.giaTri = giaTri;
	}

	public Date getNgayCapNhat() {
		return this.ngayCapNhat;
	}

	public void setNgayCapNhat(Date ngayCapNhat) {
		this.ngayCapNhat = ngayCapNhat;
	}

	public String getTenQuyDinh() {
		return this.tenQuyDinh;
	}

	public void setTenQuyDinh(String tenQuyDinh) {
		this.tenQuyDinh = tenQuyDinh;
	}

	public QuanTriVien getQuanTriVien() {
		return this.quanTriVien;
	}

	public void setQuanTriVien(QuanTriVien quanTriVien) {
		this.quanTriVien = quanTriVien;
	}

}