// Đợi trang web tải xong mới chạy code
document.addEventListener("DOMContentLoaded", function() {

    // --- PHẦN 1: XỬ LÝ MODAL THỦ THƯ ---
    var modalThuThu = document.getElementById("modalThem");
    var btnThemThuThu = document.getElementById("btnThem");
    var closeThuThu = modalThuThu ? modalThuThu.querySelector(".close") : null;

    if (btnThemThuThu && modalThuThu) {
        btnThemThuThu.onclick = function() {
            modalThuThu.style.display = "block";
        }
    }
    if (closeThuThu && modalThuThu) {
        closeThuThu.onclick = function() {
            modalThuThu.style.display = "none";
        }
    }

    // --- PHẦN 2: XỬ LÝ MODAL QUY ĐỊNH ---
    var modalQuyDinh = document.getElementById("modalThemQuyDinh");
    var btnThemQuyDinh = document.getElementById("btnThemMoi");
    var closeQuyDinh = modalQuyDinh ? modalQuyDinh.querySelector(".close") : null;

    if (btnThemQuyDinh && modalQuyDinh) {
        btnThemQuyDinh.onclick = function() {
        modalQuyDinh.style.display = "block";
        }
    }
    if (closeQuyDinh && modalQuyDinh) {
        closeQuyDinh.onclick = function() {
            modalQuyDinh.style.display = "none";
            if (window.location.search.includes("action=edit")) {
               modalQuyDinh.style.display = "none";
            }
        }
    }

    // --- PHẦN 3: XỬ LÝ CLICK RA NGOÀI (GỘP CHUNG) ---
    window.onclick = function(event) {
        // Nếu click vào vùng đen của modal Thủ thư
        if (modalThuThu && event.target == modalThuThu) {
            modalThuThu.style.display = "none";
        }
        // Nếu click vào vùng đen của modal Quy định
        if (modalQuyDinh && event.target == modalQuyDinh) {
            modalQuyDinh.style.display = "none";
        }
    }
});

/**
 * HÀM XÓA CHUNG (Dùng cho tất cả các trang)
 * @param {string} id - Mã đối tượng (VD: TT01, QD01)
 * @param {string} ten - Tên hiển thị thông báo
 * @param {string} controllerName - Tên Controller xử lý (VD: 'ThuThu', 'QuyDinh')
 */
function xacNhanXoa(id, ten, controllerName) {
    // Kiểm tra biến contextPath (được khai báo ở file JSP)
    if (typeof contextPath === 'undefined') {
        alert("Lỗi: Chưa khai báo biến contextPath ở file JSP!");
        return;
    }

    Swal.fire({
        title: 'Bạn có chắc chắn?',
        text: "Bạn sắp xóa: " + ten,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Xóa ngay!',
        cancelButtonText: 'Hủy'
    }).then((result) => {
        if (result.isConfirmed) {
            // Tạo đường dẫn động dựa trên controllerName truyền vào
            window.location.href = contextPath + "/" + controllerName + "?action=delete&id=" + id;
        }
    })
}