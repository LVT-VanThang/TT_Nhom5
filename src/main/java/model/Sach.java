package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sach database table.
 * 
 */
@Entity
@NamedQuery(name="Sach.findAll", query="SELECT s FROM Sach s")
public class Sach implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String maSach;

	private int namXuatBan;

	private int soLuongTonKho;

	private String tenSach;

	//bi-directional many-to-one association to ChiTietPhieuMuon
	@OneToMany(mappedBy="sach")
	private List<ChiTietPhieuMuon> chiTietPhieuMuons;

	//bi-directional many-to-one association to NhaXuatBan
	@ManyToOne
	@JoinColumn(name="MaNXB")
	private NhaXuatBan nhaXuatBan;

	//bi-directional many-to-one association to TacGia
	@ManyToOne
	@JoinColumn(name="MaTacGia")
	private TacGia tacGia;

	//bi-directional many-to-one association to TheLoai
	@ManyToOne
	@JoinColumn(name="MaTheLoai")
	private TheLoai theLoai;

	public Sach() {
	}

	public String getMaSach() {
		return this.maSach;
	}

	public void setMaSach(String maSach) {
		this.maSach = maSach;
	}

	public int getNamXuatBan() {
		return this.namXuatBan;
	}

	public void setNamXuatBan(int namXuatBan) {
		this.namXuatBan = namXuatBan;
	}

	public int getSoLuongTonKho() {
		return this.soLuongTonKho;
	}

	public void setSoLuongTonKho(int soLuongTonKho) {
		this.soLuongTonKho = soLuongTonKho;
	}

	public String getTenSach() {
		return this.tenSach;
	}

	public void setTenSach(String tenSach) {
		this.tenSach = tenSach;
	}

	public List<ChiTietPhieuMuon> getChiTietPhieuMuons() {
		return this.chiTietPhieuMuons;
	}

	public void setChiTietPhieuMuons(List<ChiTietPhieuMuon> chiTietPhieuMuons) {
		this.chiTietPhieuMuons = chiTietPhieuMuons;
	}

	public ChiTietPhieuMuon addChiTietPhieuMuon(ChiTietPhieuMuon chiTietPhieuMuon) {
		getChiTietPhieuMuons().add(chiTietPhieuMuon);
		chiTietPhieuMuon.setSach(this);

		return chiTietPhieuMuon;
	}

	public ChiTietPhieuMuon removeChiTietPhieuMuon(ChiTietPhieuMuon chiTietPhieuMuon) {
		getChiTietPhieuMuons().remove(chiTietPhieuMuon);
		chiTietPhieuMuon.setSach(null);

		return chiTietPhieuMuon;
	}

	public NhaXuatBan getNhaXuatBan() {
		return this.nhaXuatBan;
	}

	public void setNhaXuatBan(NhaXuatBan nhaXuatBan) {
		this.nhaXuatBan = nhaXuatBan;
	}

	public TacGia getTacGia() {
		return this.tacGia;
	}

	public void setTacGia(TacGia tacGia) {
		this.tacGia = tacGia;
	}

	public TheLoai getTheLoai() {
		return this.theLoai;
	}

	public void setTheLoai(TheLoai theLoai) {
		this.theLoai = theLoai;
	}

}