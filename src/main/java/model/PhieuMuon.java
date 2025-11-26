package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the phieu_muon database table.
 * 
 */
@Entity
@Table(name="phieu_muon")
@NamedQuery(name="PhieuMuon.findAll", query="SELECT p FROM PhieuMuon p")
public class PhieuMuon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String maPhieuMuon;

	@Temporal(TemporalType.DATE)
	private Date ngayHenTra;

	@Temporal(TemporalType.TIMESTAMP)
	private Date ngayMuon;

	private byte trangThaiPhieu;

	//bi-directional many-to-one association to ChiTietPhieuMuon
	@OneToMany(mappedBy="phieuMuon")
	private List<ChiTietPhieuMuon> chiTietPhieuMuons;

	//bi-directional many-to-one association to DocGia
	@ManyToOne
	@JoinColumn(name="MaDocGia")
	private DocGia docGia;

	//bi-directional many-to-one association to ThuThu
	@ManyToOne
	@JoinColumn(name="MaThuThu")
	private ThuThu thuThu;

	public PhieuMuon() {
	}

	public String getMaPhieuMuon() {
		return this.maPhieuMuon;
	}

	public void setMaPhieuMuon(String maPhieuMuon) {
		this.maPhieuMuon = maPhieuMuon;
	}

	public Date getNgayHenTra() {
		return this.ngayHenTra;
	}

	public void setNgayHenTra(Date ngayHenTra) {
		this.ngayHenTra = ngayHenTra;
	}

	public Date getNgayMuon() {
		return this.ngayMuon;
	}

	public void setNgayMuon(Date ngayMuon) {
		this.ngayMuon = ngayMuon;
	}

	public byte getTrangThaiPhieu() {
		return this.trangThaiPhieu;
	}

	public void setTrangThaiPhieu(byte trangThaiPhieu) {
		this.trangThaiPhieu = trangThaiPhieu;
	}

	public List<ChiTietPhieuMuon> getChiTietPhieuMuons() {
		return this.chiTietPhieuMuons;
	}

	public void setChiTietPhieuMuons(List<ChiTietPhieuMuon> chiTietPhieuMuons) {
		this.chiTietPhieuMuons = chiTietPhieuMuons;
	}

	public ChiTietPhieuMuon addChiTietPhieuMuon(ChiTietPhieuMuon chiTietPhieuMuon) {
		getChiTietPhieuMuons().add(chiTietPhieuMuon);
		chiTietPhieuMuon.setPhieuMuon(this);

		return chiTietPhieuMuon;
	}

	public ChiTietPhieuMuon removeChiTietPhieuMuon(ChiTietPhieuMuon chiTietPhieuMuon) {
		getChiTietPhieuMuons().remove(chiTietPhieuMuon);
		chiTietPhieuMuon.setPhieuMuon(null);

		return chiTietPhieuMuon;
	}

	public DocGia getDocGia() {
		return this.docGia;
	}

	public void setDocGia(DocGia docGia) {
		this.docGia = docGia;
	}

	public ThuThu getThuThu() {
		return this.thuThu;
	}

	public void setThuThu(ThuThu thuThu) {
		this.thuThu = thuThu;
	}

}