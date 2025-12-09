package dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.ThuThu;
import util.HibernateUtil;

public class ThuThuDAO {
	public ThuThu kiemTraDangNhap(String tenDangNhap, String matKhau) {
        EntityManager em = HibernateUtil.getEMF().createEntityManager();
        try {
            String jpql = "SELECT q FROM ThuThu q WHERE q.tenDangNhap = :user AND q.matKhau = :pass";
            TypedQuery<ThuThu> lenh = em.createQuery(jpql, ThuThu.class);
            lenh.setParameter("user", tenDangNhap);
            lenh.setParameter("pass", matKhau);
            List<ThuThu> ketQua = lenh.getResultList(); 
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
	public List<model.ThuThu> layDanhSachThuThu() {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {

	        String jpql = "SELECT t FROM ThuThu t ORDER BY t.maThuThu ASC";
	        TypedQuery<model.ThuThu> query = em.createQuery(jpql, model.ThuThu.class);
	        return query.getResultList();
	        
	    } finally {
	        em.close();
	    }
	}
	public ThuThu timKiemThuThu(String maThuThu){
 	   EntityManager em=HibernateUtil.getEMF().createEntityManager();
 	   ThuThu tt=em.find(ThuThu.class, maThuThu);
 	   em.close();
 	   return tt;
 	   
    }
	public boolean kiemTraTenDangNhap(String tenDangNhap) {
		EntityManager em = HibernateUtil.getEMF().createEntityManager();
		try {
			Long count = em.createQuery("SELECT COUNT(t) FROM ThuThu t WHERE t.tenDangNhap = :user", Long.class)
					       .setParameter("user", tenDangNhap)
					       .getSingleResult();
			return count > 0;
		} catch (Exception e) {
			return false;
		} finally {
			em.close();
		}
	}
	public boolean themThuThu(ThuThu tt) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin(); 
	        
	        em.persist(tt);              
	        
	        em.getTransaction().commit(); 
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        em.getTransaction().rollback(); 
	        return false;
	    } finally {
	        em.close(); 
	    }
	}
	public boolean capNhatThuThu(ThuThu tt) {
        EntityManager em = HibernateUtil.getEMF().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(tt);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }
	public boolean xoaThuThu(String maThuThu) {
	 	   EntityManager em = HibernateUtil.getEMF().createEntityManager();
	 	   try {
	 		   em.getTransaction().begin();
	 		   
	 		   ThuThu tt = em.find(ThuThu.class, maThuThu);
	 		   if (tt != null) {
	 			   em.remove(tt); 
	 			   em.getTransaction().commit(); 
	 			   return true;
	 		   } else {
	 			   return false; 
	 		   }
	 	   } catch (Exception e) {
	 		   e.printStackTrace();
	 		   if(em.getTransaction().isActive())
	 			   em.getTransaction().rollback();
	 		   return false;
	 	   } finally {
	 		   em.close(); 
	 	   }
	 }
	public List<ThuThu> timKiemTongHop(String tuKhoa) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        String jpql = "SELECT t FROM ThuThu t WHERE t.hoTen LIKE :key OR t.tenDangNhap LIKE :key";
	        TypedQuery<ThuThu> query = em.createQuery(jpql, ThuThu.class);
	        query.setParameter("key", "%" + tuKhoa + "%");
	        return query.getResultList();
	    } finally {
	        em.close();
	    }
	}
	public boolean kiemTraEmail(String email) {
        EntityManager em = HibernateUtil.getEMF().createEntityManager();
        try {
            Long count = em.createQuery("SELECT COUNT(t) FROM ThuThu t WHERE t.email = :email", Long.class)
                           .setParameter("email", email)
                           .getSingleResult();
            return count > 0; 
        } catch (Exception e) {
            return false;
        } finally {
            em.close();
        }
    }
 }

