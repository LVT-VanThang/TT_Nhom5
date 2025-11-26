package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the loai_doc_gia database table.
 * 
 */
@Entity
@Table(name="loai_doc_gia")
@NamedQuery(name="LoaiDocGia.findAll", query="SELECT l FROM LoaiDocGia l")
public class LoaiDocGia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String maLoaiDocGia;

	private String tenLoaiDocGia;

	//bi-directional many-to-one association to DocGia
	@OneToMany(mappedBy="loaiDocGia")
	private List<DocGia> docGias;

	public LoaiDocGia() {
	}

	public String getMaLoaiDocGia() {
		return this.maLoaiDocGia;
	}

	public void setMaLoaiDocGia(String maLoaiDocGia) {
		this.maLoaiDocGia = maLoaiDocGia;
	}

	public String getTenLoaiDocGia() {
		return this.tenLoaiDocGia;
	}

	public void setTenLoaiDocGia(String tenLoaiDocGia) {
		this.tenLoaiDocGia = tenLoaiDocGia;
	}

	public List<DocGia> getDocGias() {
		return this.docGias;
	}

	public void setDocGias(List<DocGia> docGias) {
		this.docGias = docGias;
	}

	public DocGia addDocGia(DocGia docGia) {
		getDocGias().add(docGia);
		docGia.setLoaiDocGia(this);

		return docGia;
	}

	public DocGia removeDocGia(DocGia docGia) {
		getDocGias().remove(docGia);
		docGia.setLoaiDocGia(null);

		return docGia;
	}

}