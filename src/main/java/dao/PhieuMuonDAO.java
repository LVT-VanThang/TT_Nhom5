package dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import model.ChiTietPhieuMuon;
import model.ChiTietPhieuMuonPK;
import model.PhieuMuon;
import model.Sach;
import util.HibernateUtil;

public class PhieuMuonDAO {
    public List<PhieuMuon> layDanhSachPhieuMuon() {
        EntityManager em = HibernateUtil.getEMF().createEntityManager();
        try {
            String jpql = "SELECT p FROM PhieuMuon p ORDER BY p.maPhieuMuon DESC";
            TypedQuery<PhieuMuon> query = em.createQuery(jpql, PhieuMuon.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public String taoMaPhieuTuDong() {
        EntityManager em = HibernateUtil.getEMF().createEntityManager();
        try {
            String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyy"));
            String prefix = "PM" + datePart;
            String jpql = "SELECT p.maPhieuMuon FROM PhieuMuon p WHERE p.maPhieuMuon LIKE :prefix ORDER BY p.maPhieuMuon DESC";
            List<String> result = em.createQuery(jpql, String.class)
                                    .setParameter("prefix", prefix + "%")
                                    .setMaxResults(1)
                                    .getResultList();

            int nextId = 1;
            if (!result.isEmpty()) {
                String lastId = result.get(0); 
                String suffix = lastId.substring(lastId.length() - 3);
                nextId = Integer.parseInt(suffix) + 1;
            }
            return prefix + String.format("%03d", nextId);
        } finally {
            em.close();
        }
    }
    public boolean lapPhieuMuon(PhieuMuon pm, List<String> danhSachMaSach) {
        EntityManager em = HibernateUtil.getEMF().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.persist(pm);

            for (String maSach : danhSachMaSach) {
                Sach sach = em.find(Sach.class, maSach);
                if (sach == null) 
                    throw new Exception("Sách mã " + maSach + " không tồn tại!");
                if (sach.getSoLuongTonKho() < 1) 
                    throw new Exception("Sách '" + sach.getTenSach() + "' đã hết hàng!");
                sach.setSoLuongTonKho(sach.getSoLuongTonKho() - 1);
                em.merge(sach); 
                ChiTietPhieuMuon ctpm = new ChiTietPhieuMuon();
                ChiTietPhieuMuonPK pk = new ChiTietPhieuMuonPK();
                pk.setMaPhieuMuon(pm.getMaPhieuMuon());
                pk.setMaSach(maSach);
                
                ctpm.setId(pk);
                ctpm.setPhieuMuon(pm);
                ctpm.setSach(sach);
                ctpm.setTrangThaiSach((byte) 0); 
                ctpm.setTienPhat(BigDecimal.ZERO);
                ctpm.setNgayTraThucTe(null); 

                em.persist(ctpm);
            }
            trans.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) trans.rollback(); 
            return false;
        } finally {
            em.close();
        }
    }
    
    public List<PhieuMuon> timKiemPhieuMuon(String tuKhoa) {
        EntityManager em = HibernateUtil.getEMF().createEntityManager();
        try {
            String jpql = "SELECT p FROM PhieuMuon p " +
                          "WHERE LOWER(p.maPhieuMuon) LIKE LOWER(:tuKhoa) " +
                          "OR LOWER(p.docGia.hoTen) LIKE LOWER(:tuKhoa) " +
                          "OR LOWER(p.docGia.maDocGia) LIKE LOWER(:tuKhoa) " +
                          "ORDER BY p.maPhieuMuon DESC";
                          
            TypedQuery<PhieuMuon> query = em.createQuery(jpql, PhieuMuon.class);
            query.setParameter("tuKhoa", "%" + tuKhoa + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    public PhieuMuon layPhieuMuonTheoMa(String maPhieu) {
        EntityManager em = HibernateUtil.getEMF().createEntityManager();
        try {
            PhieuMuon pm = em.find(PhieuMuon.class, maPhieu);
            if (pm != null) {
                pm.getChiTietPhieuMuons().size(); 
            }
            return pm;
        } finally {
            em.close();
        }
    }

    public java.util.Map<String, Object> traSach(String maPhieu, String maSach, int tinhTrang, BigDecimal tienPhatNhapVao) {
        EntityManager em = HibernateUtil.getEMF().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        java.util.Map<String, Object> ketQuaMap = new java.util.HashMap<>();
        ketQuaMap.put("status", "running"); 

        try {
            trans.begin();

            ChiTietPhieuMuonPK pk = new ChiTietPhieuMuonPK();
            pk.setMaPhieuMuon(maPhieu);
            pk.setMaSach(maSach);
            ChiTietPhieuMuon ctpm = em.find(ChiTietPhieuMuon.class, pk);
            
            if (ctpm != null && ctpm.getNgayTraThucTe() == null) { 
                PhieuMuon pm = ctpm.getPhieuMuon(); 
                java.util.Date ngayHenTra = pm.getNgayHenTra();
                java.util.Date homNay = new java.util.Date();
                long diff = homNay.getTime() - ngayHenTra.getTime();
                long soNgayTre = diff / (24 * 60 * 60 * 1000); 
                
                BigDecimal tienPhatTreHanCuaPhieu = BigDecimal.ZERO;
                
                if (soNgayTre > 0) {
                    dao.QuyDinhDAO qdDAO = new dao.QuyDinhDAO(); 
                    int phiPhatMoiNgay = qdDAO.layGiaTriQuyDinh("QD001");
                    if (phiPhatMoiNgay > 0) {
                        tienPhatTreHanCuaPhieu = BigDecimal.valueOf(soNgayTre * phiPhatMoiNgay);
                    }
                }
                ctpm.setNgayTraThucTe(new java.sql.Date(homNay.getTime()));
                ctpm.setTrangThaiSach((byte) tinhTrang);
                ctpm.setTienPhat(tienPhatNhapVao);
                em.merge(ctpm);

                if (tinhTrang != 2) { 
                    Sach sach = em.find(Sach.class, maSach);
                    sach.setSoLuongTonKho(sach.getSoLuongTonKho() + 1);
                    em.merge(sach);
                }
                String sqlSum = "SELECT SUM(c.tienPhat) FROM ChiTietPhieuMuon c WHERE c.phieuMuon.maPhieuMuon = :maPhieu";
                BigDecimal tongTienHong = em.createQuery(sqlSum, BigDecimal.class)
                                         .setParameter("maPhieu", maPhieu)
                                         .getSingleResult();
                if (tongTienHong == null) tongTienHong = BigDecimal.ZERO;
                
                BigDecimal tongTienCuoiCung = tongTienHong.add(tienPhatTreHanCuaPhieu);
                pm.setTongTienPhat(tongTienCuoiCung); 
                String jpqlCount = "SELECT COUNT(c) FROM ChiTietPhieuMuon c WHERE c.phieuMuon.maPhieuMuon = :maPhieu AND c.ngayTraThucTe IS NULL";
                Long conLai = em.createQuery(jpqlCount, Long.class)
                                .setParameter("maPhieu", maPhieu)
                                .getSingleResult();

                if (conLai == 0) {
                    pm.setTrangThaiPhieu((byte) 1); 
                    ketQuaMap.put("status", "finished"); 
                    ketQuaMap.put("total", tongTienCuoiCung);
                    ketQuaMap.put("damage", tongTienHong); 
                    ketQuaMap.put("late", tienPhatTreHanCuaPhieu); 
                    ketQuaMap.put("daysLate", soNgayTre > 0 ? soNgayTre : 0); 
                }
                em.merge(pm);
            }
            
            trans.commit();
            return ketQuaMap;

        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) trans.rollback();
            return null;
        } finally {
            em.close();
        }
    }

    public boolean kiemTraDocGiaDangMuon(String maDocGia) {
        EntityManager em = HibernateUtil.getEMF().createEntityManager();
        try {

            String jpql = "SELECT COUNT(p) FROM PhieuMuon p WHERE p.docGia.maDocGia = :maDocGia AND p.trangThaiPhieu = 0";
            Long count = em.createQuery(jpql, Long.class)
                           .setParameter("maDocGia", maDocGia)
                           .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    
}