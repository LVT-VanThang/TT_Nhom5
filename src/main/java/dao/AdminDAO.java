package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.QuanTriVien;
import util.HibernateUtil;

public class AdminDAO {
    public QuanTriVien kiemTraDangNhap(String tenDangNhap, String matKhau) {
        EntityManager em = HibernateUtil.getEMF().createEntityManager();
        try {
            String jpql = "SELECT q FROM QuanTriVien q WHERE q.tenDangNhap = :user AND q.matKhau = :pass";
            TypedQuery<QuanTriVien> lenh = em.createQuery(jpql, QuanTriVien.class);
            lenh.setParameter("user", tenDangNhap);
            lenh.setParameter("pass", matKhau);
            List<QuanTriVien> ketQua = lenh.getResultList(); 
            if (ketQua != null && !ketQua.isEmpty()) {
                return ketQua.get(0); 
            }
            return null; 
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
