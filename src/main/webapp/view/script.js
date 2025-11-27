document.addEventListener("DOMContentLoaded", function() {

    // --- 1. HÀM DÙNG CHUNG: MỞ MODAL & RESET FORM (QUAN TRỌNG) ---
    // Hàm này sẽ tự động tìm form, xóa dữ liệu cũ, mở khóa ô mã...
    function setupModalLogic(btnId, modalId, titleText) {
        var btn = document.getElementById(btnId);
        var modal = document.getElementById(modalId);

        if (btn && modal) {
            btn.onclick = function() {
                var form = modal.querySelector("form");
                if (form) {
                    // a. Reset form về mặc định
                    form.reset();

                    // b. Xóa sạch dữ liệu text (đề phòng dính dữ liệu cũ)
                    var inputs = form.querySelectorAll("input[type='text']");
                    inputs.forEach(input => input.value = "");

                    // c. Mở khóa các ô bị readonly (quan trọng cho trường hợp vừa bấm Sửa xong)
                    var readOnlyInputs = form.querySelectorAll("input[readonly]");
                    readOnlyInputs.forEach(input => {
                        input.readOnly = false;
                        input.style.backgroundColor = "white";
                    });

                    // d. Đặt lại action về 'insert'
                    var actionInput = form.querySelector("input[name='action']");
                    if (actionInput) actionInput.value = "insert";

                    // e. Reset Tiêu đề và Nút bấm về trạng thái Thêm mới
                    var h2 = modal.querySelector("h2");
                    // Nếu có truyền titleText thì dùng, không thì lấy mặc định
                    if (h2 && titleText) h2.innerText = "➕ " + titleText;
                    
                    var btnSave = form.querySelector(".btn-save");
                    if (btnSave) btnSave.innerText = "💾 Lưu lại";
                    
                    // f. Ẩn thông báo lỗi cũ
                    var errorMsg = modal.querySelector(".alert-error");
                    if(errorMsg) errorMsg.style.display = 'none';
                }
                // Hiện modal
                modal.style.display = "block";
            }
        }
    }

    // --- 2. CẤU HÌNH CHO TRANG DASHBOARD (Hành động nhanh) ---
    // (Dùng ID riêng của Dashboard)
    setupModalLogic("btnQuickTheLoai", "modalQuickTheLoai", "Thêm nhanh Thể Loại");
    setupModalLogic("btnQuickNXB", "modalQuickNXB", "Thêm nhanh NXB");
    setupModalLogic("btnQuickDocGia", "modalQuickDocGia", "Thêm nhanh Loại Độc Giả");


    // --- 3. CẤU HÌNH CHO CÁC TRANG QUẢN LÝ ---
    var mainModals = [
        { id: "modalThem", title: "Thêm mới Thủ Thư" },           // Trang Thủ Thư
        { id: "modalThemQuyDinh", title: "Thêm mới Quy Định" },    // Trang Quy Định
        { id: "modalThemTheLoai", title: "Thêm mới Thể Loại" },    // Trang Thể Loại
        { id: "modalThemNhaXuatBan", title: "Thêm mới NXB" },      // Trang NXB
        { id: "modalThemLoaiDocGia", title: "Thêm mới Loại ĐG" }   // Trang Loại ĐG
    ];

    mainModals.forEach(function(item) {
        setupModalLogic("btnThemMoi", item.id, item.title);
        setupModalLogic("btnThem", item.id, item.title);
    });


    // --- 4. XỬ LÝ ĐÓNG MODAL (Dấu X và Click ra ngoài) ---
    
    var closeBtns = document.querySelectorAll(".close");
    closeBtns.forEach(function(span) {
        span.onclick = function() {
            var parentModal = span.closest(".modal");
            if(parentModal) {
                parentModal.style.display = "none";
            }
            if (window.location.search.includes("action=edit")) {
                 window.history.replaceState(null, null, window.location.pathname);
            }
        }
    });
    window.onclick = function(event) {
        if (event.target.classList.contains("modal")) {
            event.target.style.display = "none";
        }
    }
});


/**
 * HÀM XÓA CHUNG 
 */
function xacNhanXoa(id, ten, controllerName) {
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
            window.location.href = contextPath + "/" + controllerName + "?action=delete&id=" + id;
        }
    })
}