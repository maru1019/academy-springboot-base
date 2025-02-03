document.addEventListener("DOMContentLoaded", function() {
  const open = document.getElementById('modal-open');
  const modalBg = document.getElementById('modal-bg');
  const container = document.getElementById('modal-container');
  const close = document.getElementById('modal-close');
  const form = document.querySelector(".study-form");
  const userId = form.getAttribute("data-user-id"); // フォームに userId を埋め込んでおく

  open.addEventListener("click", function(event) {
      event.preventDefault(); // デフォルトのフォーム送信を防ぐ

      const formData = new FormData(form);

      fetch(form.action, {
          method: "POST",
          body: formData
      })
      .then(response => {
          if (!response.ok) {
              throw new Error("登録に失敗しました");
          }
          return response.text(); // HTML を取得
      })
      .then(html => {
          // フォーム送信成功後にモーダルを表示
          container.classList.add('active');
          modalBg.classList.add('active');
      })
      .catch(error => {
          console.error("エラー:", error);
      });
  });

  close.addEventListener('click', () => {
      container.classList.remove('active');
      modalBg.classList.remove('active');

      // **正しいパスに遷移**
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
