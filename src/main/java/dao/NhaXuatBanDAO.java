package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.NhaXuatBan;
import util.HibernateUtil;

public class NhaXuatBanDAO {
	public List<model.NhaXuatBan> layDanhSachNhaXuatBan() {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {

	        String jpql = "SELECT n FROM NhaXuatBan n ORDER BY n.maNXB ASC";
	        TypedQuery<model.NhaXuatBan> query = em.createQuery(jpql, model.NhaXuatBan.class);
	        return query.getResultList();
	        
	    } finally {	
	        em.close();
	    }
	}
	public NhaXuatBan timKiemNhaXuatBan(String maNhaXuatBan){
		   EntityManager em=HibernateUtil.getEMF().createEntityManager();
		   NhaXuatBan nxb=em.find(NhaXuatBan.class, maNhaXuatBan);
		   em.close();
		   return nxb;
		   
	 }
	public boolean themNhaXuatBan(NhaXuatBan nxb) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin(); 
	        em.persist(nxb);             
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
	public List<NhaXuatBan> timKiemTheoTenNhaXuatBan(String tenNhaXuatBan) {
		EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        String jpql = "SELECT n FROM NhaXuatBan n WHERE n.tenNXB LIKE :key";
	        TypedQuery<NhaXuatBan> query = em.createQuery(jpql, NhaXuatBan.class);
	        query.setParameter("key", "%" + tenNhaXuatBan + "%");
	        return query.getResultList();
	    } finally {
	        em.close();
	    }
	}
	public boolean capNhatNhaXuatBan(NhaXuatBan nxb) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin();
	        em.merge(nxb);
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
	public boolean xoaNhaXuatBan(String maNhaXuatBan) {
	    EntityManager em = HibernateUtil.getEMF().createEntityManager();
	    try {
	        em.getTransaction().begin();
	        NhaXuatBan nxb = em.find(NhaXuatBan.class, maNhaXuatBan);
	        if (nxb != null) {
	            em.remove(nxb);
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
