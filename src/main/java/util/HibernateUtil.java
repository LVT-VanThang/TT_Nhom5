package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
	  public static EntityManagerFactory emf=null; 
	  public static EntityManagerFactory getEMF() {
		  if(emf==null) {
			  emf=Persistence.createEntityManagerFactory("QuanLyThuVien");
		  }
		  return emf;
	  }
}
