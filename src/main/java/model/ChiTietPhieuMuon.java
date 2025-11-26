package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the chi_tiet_phieu_muon database table.
 * 
 */
@Entity
@Table(name="chi_tiet_phieu_muon")
@NamedQuery(name="ChiTietPhieuMuon.findAll", query="SELECT c FROM ChiTietPhieuMuon c")
public class ChiTietPhieuMuon implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ChiTietPhieuMuonPK id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date ngayTraThucTe;

	private BigDecimal tienPhat;

	private byte trangThaiSach;

	//bi-directional many-to-one association to PhieuMuon
	@ManyToOne
	@JoinColumn(name="MaPhieuMuon")
	private PhieuMuon phieuMuon;

	//bi-directional many-to-one association to Sach
	@ManyToOne
	@JoinColumn(name="MaSach")
	private Sach sach;

	public ChiTietPhieuMuon() {
	}

	public ChiTietPhieuMuonPK getId() {
		return this.id;
	}

	public void setId(ChiTietPhieuMuonPK id) {
		this.id = id;
	}

	public Date getNgayTraThucTe() {
		return this.ngayTraThucTe;
	}

	public void setNgayTraThucTe(Date ngayTraThucTe) {
		this.ngayTraThucTe = ngayTraThucTe;
	}

	public BigDecimal getTienPhat() {
		return this.tienPhat;
	}

	public void setTienPhat(BigDecimal tienPhat) {
		this.tienPhat = tienPhat;
	}

	public byte getTrangThaiSach() {
		return this.trangThaiSach;
	}

	public void setTrangThaiSach(byte trangThaiSach) {
		this.trangThaiSach = trangThaiSach;
	}

	public PhieuMuon getPhieuMuon() {
		return this.phieuMuon;
	}

	public void setPhieuMuon(PhieuMuon phieuMuon) {
		this.phieuMuon = phieuMuon;
	}

	public Sach getSach() {
		return this.sach;
	}

	public void setSach(Sach sach) {
		this.sach = sach;
	}

}