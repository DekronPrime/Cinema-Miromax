import createNotification from "./toast.js";
const modalOverlay = document.querySelector("#modal-overlay");
const modalWindows = document.querySelectorAll(".modal");
const modalCloseBtn = document.querySelector("#modal-overlay .icon-close");

document.addEventListener("DOMContentLoaded", async () => {
  modalOverlay.addEventListener("click", handleOverlayClick);
  modalCloseBtn.addEventListener("click", closeModal);
  /* openModal("modal-filter"); */
});

function handleOverlayClick(event) {
  if (event.target.dataset.close) {
    closeModal();
  }
}

function openModal(modalId) {
  let modalWindow = document.querySelector(`#${modalId}`);
  setTimeout(() => {
    modalOverlay.style.display = "flex";
    modalOverlay.dataset.close = false;
    modalWindow.style.display = "block";
    document.body.style.overflow = "hidden";
  }, 100);
}

function closeModal() {
  setTimeout(() => {
    Array.from(document.forms).forEach((form) => {
      form.reset();
    });
    modalOverlay.style.display = "none";
    modalOverlay.dataset.close = true;
    modalWindows.forEach((modalWindow) => {
      modalWindow.style.display = "none";
    });
    document.body.style.overflow = "auto";
  }, 100);
}

export { openModal, closeModal };
