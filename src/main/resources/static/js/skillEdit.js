document.addEventListener("DOMContentLoaded", function() {
  const open = document.getElementById('modal-open')
  const modalBg = document.getElementById('modal-bg');
  const container = document.getElementById('modal-container');
  const close = document.getElementById('modal-close');

  open.addEventListener('click', () => {
    container.classList.add('active');
    modalBg.classList.add('active');
  });

  close.addEventListener('click', () => {
    container.classList.remove('active');
    modalBg.classList.remove('active');
    window.location.reload();
  });

  modalBg.addEventListener('click', () => {
    container.classList.remove('active');
    modalBg.classList.remove('active');
  });
  
});
