package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the thu_thu database table.
 * 
 */
@Entity
@Table(name="thu_thu")
@NamedQuery(name="ThuThu.findAll", query="SELECT t FROM ThuThu t")
public class ThuThu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String maThuThu;

	private String email;

	private String hoTen;

	private String matKhau;

	private String tenDangNhap;

	private byte trangThai;

	//bi-directional many-to-one association to PhieuMuon
	@OneToMany(mappedBy="thuThu")
	private List<PhieuMuon> phieuMuons;

	public ThuThu() {
	}

	public String getMaThuThu() {
		return this.maThuThu;
	}

	public void setMaThuThu(String maThuThu) {
		this.maThuThu = maThuThu;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHoTen() {
		return this.hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
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

	public byte getTrangThai() {
		return this.trangThai;
	}

	public void setTrangThai(byte trangThai) {
		this.trangThai = trangThai;
	}

	public List<PhieuMuon> getPhieuMuons() {
		return this.phieuMuons;
	}

	public void setPhieuMuons(List<PhieuMuon> phieuMuons) {
		this.phieuMuons = phieuMuons;
	}

	public PhieuMuon addPhieuMuon(PhieuMuon phieuMuon) {
		getPhieuMuons().add(phieuMuon);
		phieuMuon.setThuThu(this);

		return phieuMuon;
	}

	public PhieuMuon removePhieuMuon(PhieuMuon phieuMuon) {
		getPhieuMuons().remove(phieuMuon);
		phieuMuon.setThuThu(null);

		return phieuMuon;
	}

}