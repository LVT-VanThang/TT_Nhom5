package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the nha_xuat_ban database table.
 * 
 */
@Entity
@Table(name="nha_xuat_ban")
@NamedQuery(name="NhaXuatBan.findAll", query="SELECT n FROM NhaXuatBan n")
public class NhaXuatBan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String maNXB;

	private String tenNXB;

	//bi-directional many-to-one association to Sach
	@OneToMany(mappedBy="nhaXuatBan")
	private List<Sach> saches;

	public NhaXuatBan() {
	}

	public String getMaNXB() {
		return this.maNXB;
	}

	public void setMaNXB(String maNXB) {
		this.maNXB = maNXB;
	}

	public String getTenNXB() {
		return this.tenNXB;
	}

	public void setTenNXB(String tenNXB) {
		this.tenNXB = tenNXB;
	}

	public List<Sach> getSaches() {
		return this.saches;
	}

	public void setSaches(List<Sach> saches) {
		this.saches = saches;
	}

	public Sach addSach(Sach sach) {
		getSaches().add(sach);
		sach.setNhaXuatBan(this);

		return sach;
	}

	public Sach removeSach(Sach sach) {
		getSaches().remove(sach);
		sach.setNhaXuatBan(null);

		return sach;
	}

}