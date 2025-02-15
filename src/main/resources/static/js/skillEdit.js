document.addEventListener("DOMContentLoaded", function() {
  const modalBg = document.getElementById("modal-bg");
  const container = document.getElementById("modal-container");
  const close = document.getElementById("modal-close");
  const modalMessage = document.getElementById("modal-message"); // モーダルのメッセージ要素

  document.querySelectorAll(".save").forEach(button => {
    button.addEventListener("click", function() {

      const form = this.closest(".edit-form");
      const formData = new FormData(form);
      const userId = form.dataset.userId;
      const skillName = form.closest("tr").querySelector(".subject-item").textContent.trim();

      fetch(`/learningData/${userId}/edit`, {
        method: "POST",
        body: formData
      })
      .then(response => response.json())
      .then(data => {
        if (data.success) {
          container.classList.add("active");
          modalBg.classList.add("active");

          modalMessage.textContent = `${skillName} の学習時間を保存しました！`;

        } else {
          alert("更新に失敗しました");
        }
      })
      .catch(error => console.error("エラー:", error));
    });
  });

  close.addEventListener("click", () => {
    container.classList.remove("active");
    modalBg.classList.remove("active");
    window.location.reload();
  });

  modalBg.addEventListener("click", () => {
    container.classList.remove("active");
    modalBg.classList.remove("active");
  });
});
