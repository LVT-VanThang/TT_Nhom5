package dao;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;

import model.DocGia;
import util.HibernateUtil;

public class ThongKeDAO {

    public long demTongSoSach() {
        EntityManager em = HibernateUtil.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(s) FROM Sach s", Long.class).getSingleResult();
        } finally { em.close(); }
    }
    public long demTongDocGia() {
        EntityManager em = HibernateUtil.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(d) FROM DocGia d", Long.class).getSingleResult();
        } finally { em.close(); }
    }
    public long demPhieuDangMuon() {
        EntityManager em = HibernateUtil.getEMF().createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(p) FROM PhieuMuon p WHERE p.trangThaiPhieu = 0", Long.class).getSingleResult();
        } finally { em.close(); }
    }
    public BigDecimal tinhTongTienPhat() {
        EntityManager em = HibernateUtil.getEMF().createEntityManager();
        try {
            BigDecimal tong = em.createQuery("SELECT SUM(c.tienPhat) FROM ChiTietPhieuMuon c", BigDecimal.class).getSingleResult();
            return (tong == null) ? BigDecimal.ZERO : tong;
        } finally { em.close(); }
    }
    public List<Object[]> layTopSachMuon() {
        EntityManager em = HibernateUtil.getEMF().createEntityManager();
        try {
            String hql = "SELECT ct.sach.tenSach, COUNT(ct) as soLuot " +
                         "FROM ChiTietPhieuMuon ct " +
                         "GROUP BY ct.sach.tenSach " +
                         "ORDER BY soLuot DESC";
            return em.createQuery(hql).setMaxResults(5).getResultList();
        } finally { em.close(); }
    }
    public long demDocGiaTreHan() {
        EntityManager em = HibernateUtil.getEMF().createEntityManager();
        try {
            String jpql = "SELECT COUNT(DISTINCT p.docGia) FROM PhieuMuon p " +
                          "WHERE p.trangThaiPhieu = 0 AND p.ngayHenTra < CURRENT_DATE";
            
            return em.createQuery(jpql, Long.class).getSingleResult();
        } finally { 
            em.close(); 
        }
    }
    public List<DocGia> layDanhSachDocGiaTreHan() {
        EntityManager em = HibernateUtil.getEMF().createEntityManager();
        try {
            String jpql = "SELECT DISTINCT p.docGia FROM PhieuMuon p " +
                          "WHERE p.trangThaiPhieu = 0 AND p.ngayHenTra < CURRENT_DATE";
            
            return em.createQuery(jpql, DocGia.class).getResultList();
        } finally { 
            em.close(); 
        }
    }
}