/* ==========================================
   FILE: view/script.js
   ========================================== */

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
            if (dateContainer) { dateContainer.style.display = "none"; }
        }
        var h2 = modal.querySelector("h2");
        if (h2) h2.innerText = titlePrefix; 
        modal.style.display = "block";
    }

    // --- PHẦN 3: ĐÓNG MODAL ---
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
                didOpen: () => { Swal.showLoading(); }
            });
            window.location.href = contextPath + "/" + controllerName + "?action=delete&id=" + id;
        }
    });
}

// --- PHẦN 5: HÀM KHỞI TẠO TRANG MƯỢN TRẢ ---
function initMuonTraPage(limitBooks, baoLoi, contextPath) {
    // 1. Cấu hình Select2
    $(document).ready(function() {
        $('.select2-docgia').select2({ 
            placeholder: "Chọn độc giả...", 
            allowClear: true, 
            dropdownParent: $('#modalLapPhieu') 
        });
        
        $('.select2-sach').select2({ 
            placeholder: "Chọn sách cần mượn...", 
            allowClear: true, 
            maximumSelectionLength: limitBooks, 
            dropdownParent: $('#modalLapPhieu') 
        });
    });

    // 2. Xử lý nút mở Modal Lập Phiếu
    var btnLapPhieu = document.getElementById("btnLapPhieu");
    var modalLapPhieu = document.getElementById("modalLapPhieu");
    
    if(btnLapPhieu && modalLapPhieu) {
        btnLapPhieu.onclick = function(e) {
            e.preventDefault();
            modalLapPhieu.style.display = "block";
        }
    }

    // 3. Hiển thị lỗi nếu có
    if (baoLoi && baoLoi.trim() !== "") {
        Swal.fire({ icon: 'error', title: 'Thông báo', text: baoLoi });
    }
}

// --- PHẦN 6: HÀM TRẢ SÁCH (CÓ NHẬP LIỆU) ---
// --- CẬP NHẬT HÀM TRẢ SÁCH (CÓ KHÓA Ô TIỀN PHẠT) ---
function xacNhanTraSach(maPhieu, maSach, tenSach) {
    if (typeof contextPath === 'undefined') {
        alert("Lỗi: Chưa khai báo contextPath!");
        return;
    }

    Swal.fire({
        title: 'Trả sách: ' + tenSach,
        html: `
            <div style="text-align: left; font-size: 14px;">
                <label style="font-weight: bold; display: block; margin-bottom: 5px;">1. Tình trạng sách:</label>
                <div style="margin-bottom: 15px;">
                    <input type="radio" id="st_normal" name="swal_status" value="0" checked onchange="toggleFineInput(false)">
                    <label for="st_normal" style="margin-right: 15px; cursor: pointer;">✅ Bình thường</label>
                    
                    <input type="radio" id="st_damaged" name="swal_status" value="1" onchange="toggleFineInput(true)">
                    <label for="st_damaged" style="margin-right: 15px; cursor: pointer;">⚠️ Hư hỏng</label>
                    
                    <input type="radio" id="st_lost" name="swal_status" value="2" onchange="toggleFineInput(true)">
                    <label for="st_lost" style="cursor: pointer;">❌ Mất sách</label>
                </div>

                <label style="font-weight: bold; display: block; margin-bottom: 5px;">2. Phí phạt (VNĐ):</label>
                <input type="number" id="swal_fine" class="swal2-input" value="0" min="0" disabled 
                       style="margin: 0; width: 100%; background-color: #f9f9f9;">
                
            </div>
        `,
        showCancelButton: true,
        confirmButtonText: 'Xác nhận trả',
        cancelButtonText: 'Hủy',
        focusConfirm: false,
        preConfirm: () => {
            const status = document.querySelector('input[name="swal_status"]:checked').value;
            const fine = document.getElementById('swal_fine').value;
            return { status: status, fine: fine };
        }
    }).then((result) => {
        if (result.isConfirmed) {
            const status = result.value.status;
            // Nếu chọn bình thường thì ép tiền phạt về 0 luôn cho chắc chắn
            const fine = (status == 0) ? 0 : result.value.fine;
            
            window.location.href = contextPath + "/MuonTra?action=return&maPhieu=" + maPhieu + 
                                   "&maSach=" + maSach + 
                                   "&tinhTrang=" + status + 
                                   "&tienPhat=" + fine;
        }
    });
}

// --- HÀM PHỤ TRỢ: BẬT/TẮT Ô NHẬP TIỀN ---
function toggleFineInput(isBad) {
    const input = document.getElementById('swal_fine');
    if (isBad) {
        // Nếu sách Hỏng/Mất -> Cho phép nhập
        input.disabled = false; 
        input.style.backgroundColor = "#fff"; // Màu nền trắng
        if (input.value == 0) input.value = ""; 
        input.focus();
    } else {
        // Nếu sách Bình thường -> Khóa lại và reset về 0
        input.value = 0; 
        input.disabled = true; 
        input.style.backgroundColor = "#f9f9f9"; // Màu nền xám để biết là bị khóa
    }
}

function dongModal(id) { 
    var modal = document.getElementById(id);
    if(modal) modal.style.display = "none"; 
}