package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Sach;
import model.Sach;
import util.HibernateUtil;

public class SachDAO {
	public List<model.Sach> layDanhSachSach(){
		   EntityManager em=HibernateUtil.getEMF().createEntityManager();
		   try {

		        String jpql = "SELECT s FROM Sach s ORDER BY s.maSach DESC";
		        TypedQuery<model.Sach> query = em.createQuery(jpql, model.Sach.class);
		        return query.getResultList();
		        
		    } finally {
		        em.close();
		    }
		}
	
	public Sach timKiemSach(String maSach){
		   EntityManager em=HibernateUtil.getEMF().createEntityManager();
		   Sach s=em.find(Sach.class, maSach);
		   em.close();
		   return s;
		   
	 }
	public List<Sach> timKiemTheoTenSach(String tenSach) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        String jpql = "SELECT s FROM Sach s WHERE s.tenSach LIKE :key";
	        TypedQuery<Sach> query = em.createQuery(jpql, Sach.class);
	        query.setParameter("key", "%" + tenSach + "%");
	        return query.getResultList();
	    } finally {
	        em.close();
	    }
	}
	public List<model.Sach> timKiemTongHop(String tuKhoa) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        String jpql = "SELECT s FROM Sach s WHERE " +
	                      "s.tenSach LIKE :key OR " +
	                      "s.tacGia.tenTacGia LIKE :key OR " +
	                      "s.theLoai.tenTheLoai LIKE :key";
	                      
	        TypedQuery<model.Sach> query = em.createQuery(jpql, model.Sach.class);
	        query.setParameter("key", "%" + tuKhoa + "%");
	        
	        return query.getResultList();
	    } finally {
	        em.close();
	    }
	}
	public boolean themSach(Sach s) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin(); 
	        em.persist(s);             
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
	public boolean capNhatSach(Sach s) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin();
	        em.merge(s);
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
	public boolean xoaSach(String maSach) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin();
	        Sach s = em.find(Sach.class, maSach);
	        if (s != null) {
	            em.remove(s);
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
