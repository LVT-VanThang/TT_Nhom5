package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tac_gia database table.
 * 
 */
@Entity
@Table(name="tac_gia")
@NamedQuery(name="TacGia.findAll", query="SELECT t FROM TacGia t")
public class TacGia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String maTacGia;

	@Lob
	private String ghiChu;

	private String tenTacGia;

	//bi-directional many-to-one association to Sach
	@OneToMany(mappedBy="tacGia")
	private List<Sach> saches;

	public TacGia() {
	}

	public String getMaTacGia() {
		return this.maTacGia;
	}

	public void setMaTacGia(String maTacGia) {
		this.maTacGia = maTacGia;
	}

	public String getGhiChu() {
		return this.ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public String getTenTacGia() {
		return this.tenTacGia;
	}

	public void setTenTacGia(String tenTacGia) {
		this.tenTacGia = tenTacGia;
	}

	public List<Sach> getSaches() {
		return this.saches;
	}

	public void setSaches(List<Sach> saches) {
		this.saches = saches;
	}

	public Sach addSach(Sach sach) {
		getSaches().add(sach);
		sach.setTacGia(this);

		return sach;
	}

	public Sach removeSach(Sach sach) {
		getSaches().remove(sach);
		sach.setTacGia(null);

		return sach;
	}

}