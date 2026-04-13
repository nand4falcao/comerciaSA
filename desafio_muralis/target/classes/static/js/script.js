const form = document.querySelector("form");
const overlay = document.querySelector(".overlay");
const modal = document.querySelector(".modal-box");
const closeBtn = document.querySelector(".close-btn");

function hideModal() {
    if (overlay) overlay.classList.remove("active");
    if (modal) modal.classList.remove("active");
}

if (closeBtn) {
    closeBtn.addEventListener("click", hideModal);
}

if (overlay) {
    overlay.addEventListener("click", hideModal);
}
    