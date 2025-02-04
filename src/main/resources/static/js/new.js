document.addEventListener("DOMContentLoaded", function() {
  const open = document.getElementById('modal-open');
  const modalBg = document.getElementById('modal-bg');
  const container = document.getElementById('modal-container');
  const close = document.getElementById('modal-close');
  const form = document.querySelector(".study-form");
  const userId = form.getAttribute("data-user-id"); // フォームに userId を埋め込んでおく

  // サーバーから返された isSaved フラグが true の場合、モーダルを表示
  if (document.querySelector('[name="isSaved"]')) {
      container.classList.add('active');
      modalBg.classList.add('active');
  }

  open.addEventListener("click", function(event) {
      event.preventDefault(); // デフォルトのフォーム送信を防ぐ
      form.submit();  // 通常のフォーム送信を行う
  });

  close.addEventListener('click', () => {
      container.classList.remove('active');
      modalBg.classList.remove('active');

      // フォーム送信後、遷移先のページにリダイレクト
      if (userId) {
          window.location.href = `/learningData/${userId}/skill`;
      } else {
          window.location.href = "/learningData"; // userId 取得に失敗した場合のデフォルト
      }
  });

  modalBg.addEventListener('click', () => {
      container.classList.remove('active');
      modalBg.classList.remove('active');
  });
});
