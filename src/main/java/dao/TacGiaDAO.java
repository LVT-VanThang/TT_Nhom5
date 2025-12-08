package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.TacGia;

import util.HibernateUtil;

public class TacGiaDAO {
	public List<TacGia> layDanhTacGiaTacGia(){
		   EntityManager em=HibernateUtil.getEMF().createEntityManager();
	 	   List<TacGia> dstg=em.createQuery("from TacGia").getResultList();
	 	   em.close();
	 	   return dstg;
		}
	public TacGia timKiemTacGia(String maTacGia){
		   EntityManager em=HibernateUtil.getEMF().createEntityManager();
		   TacGia tg=em.find(TacGia.class, maTacGia);
		   em.close();
		   return tg;
		   
	 }
	public List<TacGia> timKiemTheoTenTacGia(String tenTacGia) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        String jpql = "SELECT t FROM TacGia t WHERE t.tenTacGia LIKE :key";
	        TypedQuery<TacGia> query = em.createQuery(jpql, TacGia.class);
	        query.setParameter("key", "%" + tenTacGia + "%");
	        return query.getResultList();
	    } finally {
	        em.close();
	    }
	}
	public boolean themTacGia(TacGia tg) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin(); 
	        em.persist(tg);             
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
	public boolean capNhatTacGia(TacGia tg) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin();
	        em.merge(tg);
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
	public boolean xoaTacGia(String maTacGia) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin();
	        TacGia tg = em.find(TacGia.class, maTacGia);
	        if (tg != null) {
	            em.remove(tg);
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
