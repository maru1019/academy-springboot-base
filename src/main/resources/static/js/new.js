document.addEventListener("DOMContentLoaded", function() {
  const open = document.getElementById('modal-open');
  const modalBg = document.getElementById('modal-bg');
  const container = document.getElementById('modal-container');
  const close = document.getElementById('modal-close');
  const form = document.querySelector(".study-form");
  const userId = form.getAttribute("data-user-id");
   // modal-container の data-* 属性を取得
  const categoryName = container.getAttribute("data-category-name") || "カテゴリー名";
  const itemName = container.getAttribute("data-item-name") || "項目名";
  const studyTime = container.getAttribute("data-study-time") || "学習時間";
  
  // モーダルの表示処理
  if (container.classList.contains("active")) {
        modalBg.classList.add('active');

        // モーダルのテキストを動的に設定
        document.getElementById("modal-message-1").textContent = `${categoryName} に ${itemName} を`;
        document.getElementById("modal-message-2").textContent = `(${studyTime}分) で追加しました！`;
    }

  // モーダルを閉じる処理
  close.addEventListener('click', () => {
    container.classList.remove('active');
    modalBg.classList.remove('active');

    // 編集ページにリダイレクト
    if (userId) {
        window.location.href = `/learningData/${userId}/skill`;
    } else {
        window.location.href = "/learningData"; // userId 取得に失敗した場合のデフォルト
    }
  });
});