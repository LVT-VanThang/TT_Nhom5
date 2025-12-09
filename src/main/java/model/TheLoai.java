package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the the_loai database table.
 * 
 */
@Entity
@Table(name="the_loai")
@NamedQuery(name="TheLoai.findAll", query="SELECT t FROM TheLoai t")
public class TheLoai implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String maTheLoai;

	private String tenTheLoai;
	
	private String viTriKe;

	//bi-directional many-to-one association to Sach
	@OneToMany(mappedBy="theLoai")
	private List<Sach> saches;

	public TheLoai() {
	}

	public String getMaTheLoai() {
		return this.maTheLoai;
	}

	public void setMaTheLoai(String maTheLoai) {
		this.maTheLoai = maTheLoai;
	}

	public String getTenTheLoai() {
		return this.tenTheLoai;
	}

	public void setTenTheLoai(String tenTheLoai) {
		this.tenTheLoai = tenTheLoai;
	}
	public String getViTriKe() {
        return viTriKe;
    }

    public void setViTriKe(String viTriKe) {
        this.viTriKe = viTriKe;
    }
	public List<Sach> getSaches() {
		return this.saches;
	}

	public void setSaches(List<Sach> saches) {
		this.saches = saches;
	}

	public Sach addSach(Sach sach) {
		getSaches().add(sach);
		sach.setTheLoai(this);

		return sach;
	}

	public Sach removeSach(Sach sach) {
		getSaches().remove(sach);
		sach.setTheLoai(null);

		return sach;
	}

}