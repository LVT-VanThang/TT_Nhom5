document.addEventListener("DOMContentLoaded", function() {

    // --- PHẦN 1: XỬ LÝ NÚT THÊM GENERIC ---
    var btnThem = document.getElementById("btnThem");
    var modalThem = document.getElementById("modalThem");
    
    if (btnThem && modalThem) {
        btnThem.onclick = function() {
            openModalAndReset(modalThem, "Thêm mới");
        }
    }

    // --- PHẦN 2: XỬ LÝ 3 NÚT THÊM NHANH  ---
    var btnQuickTL = document.getElementById("btnQuickTheLoai");
    var modalQuickTL = document.getElementById("modalQuickTheLoai");
    if (btnQuickTL && modalQuickTL) {
        btnQuickTL.onclick = function() { openModalAndReset(modalQuickTL, "Thêm nhanh Thể Loại"); }
    }

    var btnQuickNXB = document.getElementById("btnQuickNXB");
    var modalQuickNXB = document.getElementById("modalQuickNXB");
    if (btnQuickNXB && modalQuickNXB) {
        btnQuickNXB.onclick = function() { openModalAndReset(modalQuickNXB, "Thêm nhanh NXB"); }
    }

    var btnQuickDocGia = document.getElementById("btnQuickDocGia");
    var modalQuickDocGia = document.getElementById("modalQuickDocGia");
    if (btnQuickDocGia && modalQuickDocGia) {
        btnQuickDocGia.onclick = function() { openModalAndReset(modalQuickDocGia, "Thêm nhanh Loại Độc Giả"); }
    }

    // --- HÀM HỖ TRỢ MỞ VÀ RESET MODAL  ---
    function openModalAndReset(modal, titlePrefix) {
        var form = modal.querySelector("form");
        if (form) {
            form.reset();
            
            var actionInput = form.querySelector("input[name='action']");
            if (actionInput) actionInput.value = "insert";
            
            var inputs = form.querySelectorAll("input:not([type='hidden']), textarea");
            inputs.forEach(input => input.value = "");

            var readOnlyInputs = form.querySelectorAll("input[readonly]");
            readOnlyInputs.forEach(input => {
                 input.readOnly = false;
                 input.style.backgroundColor = "white"; 
            });
            var btnSave = form.querySelector(".btn-save");
            if(btnSave) btnSave.innerText = "💾 Lưu lại";
            
            var dateContainer = form.querySelector("#dateContainer");
            if (dateContainer) {
                dateContainer.style.display = "none";
            }
            
        }
        
        // Reset tiêu đề Modal
        var h2 = modal.querySelector("h2");
        if (h2) h2.innerText = titlePrefix; 

        modal.style.display = "block";
    }

    // --- PHẦN 3 & 4: ĐÓNG MODAL VÀ XÓA  ---
    var closeSpans = document.querySelectorAll(".close");
    closeSpans.forEach(function(span) {
        span.onclick = function() { closeModal(span.closest(".modal")); }
    });

    window.onclick = function(event) {
        if (event.target.classList.contains("modal")) { closeModal(event.target); }
    }

    function closeModal(modalElement) {
        if (modalElement) modalElement.style.display = "none";
        if (window.location.search.includes("action=edit")) {
             var cleanUrl = window.location.pathname;
             window.history.replaceState(null, null, cleanUrl);
        }
    }
});
// --- PHẦN 4: HÀM XÓA ---
function xacNhanXoa(id, ten, controllerName) {
    if (typeof contextPath === 'undefined') {
        alert("Lỗi: Chưa khai báo biến contextPath ở file JSP!");
        return;
    }

    Swal.fire({
        title: 'Bạn có chắc chắn?',
        text: "Bạn muốn xóa mục: " + ten + "?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6', 
        cancelButtonColor: '#d33',    
        confirmButtonText: 'Vâng, xóa nó!',
        cancelButtonText: 'Huỷ bỏ'
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                title: 'Đang xóa...',
                text: 'Vui lòng chờ trong giây lát',
                allowOutsideClick: false,
                didOpen: () => {
                    Swal.showLoading();
                }
            });
            window.location.href = contextPath + "/" + controllerName + "?action=delete&id=" + id;
        }
    });
}