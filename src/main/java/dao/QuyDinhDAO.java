package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.QuyDinh;
import util.HibernateUtil;

public class QuyDinhDAO {
	public List<QuyDinh> layDanhSachQuyDinh(){
		   EntityManager em=HibernateUtil.getEMF().createEntityManager();
	 	   List<QuyDinh> dsqd=em.createQuery("from QuyDinh").getResultList();
	 	   em.close();
	 	   return dsqd;
		}
	public QuyDinh timKiemQuyDinh(String maQuyDinh){
		   EntityManager em=HibernateUtil.getEMF().createEntityManager();
		   QuyDinh qd=em.find(QuyDinh.class, maQuyDinh);
		   em.close();
		   return qd;
		   
	 }
	public List<QuyDinh> timKiemTheoTenQuyDinh(String tenQuyDinh) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        String jpql = "SELECT q FROM QuyDinh q WHERE q.tenQuyDinh LIKE :key";
	        TypedQuery<QuyDinh> query = em.createQuery(jpql, QuyDinh.class);
	        query.setParameter("key", "%" + tenQuyDinh + "%");
	        return query.getResultList();
	    } finally {
	        em.close();
	    }
	}
	public boolean themQuyDinh(QuyDinh qd) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin(); 
	        em.persist(qd);             
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
	public boolean capNhatQuyDinh(QuyDinh qd) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin();
	        em.merge(qd);
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
	public boolean xoaQuyDinh(String maQuyDinh) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin();
	        QuyDinh qd = em.find(QuyDinh.class, maQuyDinh);
	        if (qd != null) {
	            em.remove(qd);
	            em.getTransaction().commit();
	            return true;
	        } else {
	            return false; 
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        em.getTransaction().rollback();
	        return false;
	    } finally {
	        em.close();
	    }
	}
}   
