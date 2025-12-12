package model;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class ChiTietPhieuMuonPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name="MaPhieuMuon") 
    private String maPhieuMuon;

    @Column(name="MaSach")      
    private String maSach;

    public ChiTietPhieuMuonPK() {
    }
    public String getMaPhieuMuon() {
        return this.maPhieuMuon;
    }
    public void setMaPhieuMuon(String maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }
    public String getMaSach() {
        return this.maSach;
    }
    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ChiTietPhieuMuonPK)) {
            return false;
        }
        ChiTietPhieuMuonPK castOther = (ChiTietPhieuMuonPK)other;
        return 
            this.maPhieuMuon.equals(castOther.maPhieuMuon)
            && this.maSach.equals(castOther.maSach);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.maPhieuMuon.hashCode();
        hash = hash * prime + this.maSach.hashCode();
        
        return hash;
    }
}