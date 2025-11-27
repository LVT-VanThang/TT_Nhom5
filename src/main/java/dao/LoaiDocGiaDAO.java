package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.LoaiDocGia;
import util.HibernateUtil;

public class LoaiDocGiaDAO {
	public List<LoaiDocGia> layDanhSachLoaiDocGia(){
		   EntityManager em=HibernateUtil.getEMF().createEntityManager();
	 	   List<LoaiDocGia> dsdg=em.createQuery("from LoaiDocGia").getResultList();
	 	   em.close();
	 	   return dsdg;
		}
	public LoaiDocGia timKiemLoaiDocGia(String maLoaiDocGia){
		   EntityManager em=HibernateUtil.getEMF().createEntityManager();
		   LoaiDocGia ldg=em.find(LoaiDocGia.class, maLoaiDocGia);
		   em.close();
		   return ldg;
		   
	 }
	public boolean themLoaiDocGia(LoaiDocGia ldg) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin(); 
	        em.persist(ldg);             
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
	public List<LoaiDocGia> timKiemTheoTenLoaiDocGia(String tenLoaiDocGia) {
		EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        String jpql = "SELECT t FROM LoaiDocGia t WHERE t.tenLoaiDocGia LIKE :key";
	        TypedQuery<LoaiDocGia> query = em.createQuery(jpql, LoaiDocGia.class);
	        query.setParameter("key", "%" + tenLoaiDocGia + "%");
	        return query.getResultList();
	    } finally {
	        em.close();
	    }
	}
	public boolean capNhatLoaiDocGia(LoaiDocGia ldg) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin();
	        em.merge(ldg);
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
	public boolean xoaLoaiDocGia(String maLoaiDocGia) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin();
	        LoaiDocGia ldg = em.find(LoaiDocGia.class, maLoaiDocGia);
	        if (ldg != null) {
	            em.remove(ldg);
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
