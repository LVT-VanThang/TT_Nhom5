var modalLoaiDocGia = document.getElementById("modalThemLoaiDocGia");
    var btnThemLoaiDocGia = document.getElementById("btnThemMoi");
    var closeLoaiDocGia = modalLoaiDocGia ? modalLoaiDocGia.querySelector(".close") : null;

    if (btnThemLoaiDocGia && modalLoaiDocGia) {
        btnThemLoaiDocGia.onclick = function() {
        modalLoaiDocGia.style.display = "block";
        }
    }
    if (closeLoaiDocGia && modalLoaiDocGia) {
        closeLoaiDocGia.onclick = function() {
            modalLoaiDocGia.style.display = "none";
            if (window.location.search.includes("action=edit")) {
               modalLoaiDocGia.style.display = "none";
            }
        }
    }
    
    if (modalLoaiDocGia && event.target == modalLoaiDocGia) {
            modalLoaiDocGia.style.display = "none";
        }
 /**
 * 
 */