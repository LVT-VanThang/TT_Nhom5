package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.DocGia;
import util.HibernateUtil;

public class DocGiaDAO {
	public List<model.DocGia> layDanhSachDocGia() {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {

	        String jpql = "SELECT d FROM DocGia d ORDER BY d.maDocGia ASC";
	        TypedQuery<model.DocGia> query = em.createQuery(jpql, model.DocGia.class);
	        return query.getResultList();
	        
	    } finally {
	        em.close();
	    }
	}
	public DocGia timKiemDocGia(String maDocGia){
		   EntityManager em=HibernateUtil.getEMF().createEntityManager();
		   DocGia dg=em.find(DocGia.class, maDocGia);
		   em.close();
		   return dg;
		   
	 }
	public List<DocGia> timKiemTheoTenDocGia(String tenDocGia) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        String jpql = "SELECT d FROM DocGia d WHERE d.hoTen LIKE :key";
	        TypedQuery<DocGia> query = em.createQuery(jpql, DocGia.class);
	        query.setParameter("key", "%" + tenDocGia + "%");
	        return query.getResultList();
	    } finally {
	        em.close();
	    }
	}
	public List<DocGia> timKiemTheoTongHop(String tenDocGia) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        String jpql = "SELECT d FROM DocGia d WHERE d.hoTen LIKE :key OR d.maDocGia LIKE :key ";
	        TypedQuery<DocGia> query = em.createQuery(jpql, DocGia.class);
	        query.setParameter("key", "%" + tenDocGia + "%");
	        return query.getResultList();
	    } finally {
	        em.close();
	    }
	}
	public boolean themDocGia(DocGia dg) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin(); 
	        em.persist(dg);             
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
	public boolean capNhatDocGia(DocGia dg) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin();
	        em.merge(dg);
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
	public boolean xoaDocGia(String maDocGia) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin();
	        DocGia dg = em.find(DocGia.class, maDocGia);
	        if (dg != null) {
	            em.remove(dg);
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
