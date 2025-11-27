package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.TheLoai;
import util.HibernateUtil;

public class TheLoaiDAO {
	public List<TheLoai> layDanhSachTheLoai(){
		   EntityManager em=HibernateUtil.getEMF().createEntityManager();
	 	   List<TheLoai> dstl=em.createQuery("from TheLoai").getResultList();
	 	   em.close();
	 	   return dstl;
		}
	public TheLoai timKiemTheLoai(String maTheLoai){
		   EntityManager em=HibernateUtil.getEMF().createEntityManager();
		   TheLoai tl=em.find(TheLoai.class, maTheLoai);
		   em.close();
		   return tl;
		   
	 }
	public boolean themTheLoai(TheLoai tl) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin(); 
	        em.persist(tl);             
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
	public List<TheLoai> timKiemTheoTenTheLoai(String tenTheLoai) {
		EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        String jpql = "SELECT t FROM TheLoai t WHERE t.tenTheLoai LIKE :key";
	        TypedQuery<TheLoai> query = em.createQuery(jpql, TheLoai.class);
	        query.setParameter("key", "%" + tenTheLoai + "%");
	        return query.getResultList();
	    } finally {
	        em.close();
	    }
	}
	public boolean capNhatTheLoai(TheLoai tl) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin();
	        em.merge(tl);
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
	public boolean xoaTheLoai(String maTheLoai) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin();
	        TheLoai tl = em.find(TheLoai.class, maTheLoai);
	        if (tl != null) {
	            em.remove(tl);
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
