package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the doc_gia database table.
 * */
@Entity
@Table(name="doc_gia")
@NamedQuery(name="DocGia.findAll", query="SELECT d FROM DocGia d")
public class DocGia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="MaDocGia")
	private String maDocGia;

	@Column(name="Email")
	private String email;

	@Column(name="HoTen")
	private String hoTen;

	@Temporal(TemporalType.DATE)
	@Column(name="NgayHetHan")
	private Date ngayHetHan;

	@Temporal(TemporalType.DATE)
	@Column(name="NgayLapThe")
	private Date ngayLapThe;

	@Column(name="SoDienThoai")
	private String soDienThoai;

	@Column(name="TrangThaiThe")
	private String trangThaiThe;

	

	// bi-directional many-to-one association to PhieuMuon
	@OneToMany(mappedBy="docGia")
	private List<PhieuMuon> phieuMuons;

	public DocGia() {
	}

	public String getMaDocGia() {
		return this.maDocGia;
	}

	public void setMaDocGia(String maDocGia) {
		this.maDocGia = maDocGia;
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

	public Date getNgayHetHan() {
		return this.ngayHetHan;
	}

	public void setNgayHetHan(Date ngayHetHan) {
		this.ngayHetHan = ngayHetHan;
	}

	public Date getNgayLapThe() {
		return this.ngayLapThe;
	}

	public void setNgayLapThe(Date ngayLapThe) {
		this.ngayLapThe = ngayLapThe;
	}

	public String getSoDienThoai() {
		return this.soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	// --- [GETTER/SETTER MỚI CHO STRING] ---
	public String getTrangThaiThe() {
		return this.trangThaiThe;
	}

	public void setTrangThaiThe(String trangThaiThe) {
		this.trangThaiThe = trangThaiThe;
	}
	// --------------------------------------



	public List<PhieuMuon> getPhieuMuons() {
		return this.phieuMuons;
	}

	public void setPhieuMuons(List<PhieuMuon> phieuMuons) {
		this.phieuMuons = phieuMuons;
	}

	public PhieuMuon addPhieuMuon(PhieuMuon phieuMuon) {
		getPhieuMuons().add(phieuMuon);
		phieuMuon.setDocGia(this);

		return phieuMuon;
	}

	public PhieuMuon removePhieuMuon(PhieuMuon phieuMuon) {
		getPhieuMuons().remove(phieuMuon);
		phieuMuon.setDocGia(null);

		return phieuMuon;
	}

}