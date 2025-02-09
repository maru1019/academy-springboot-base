document.addEventListener("DOMContentLoaded", function() {
  const form = document.querySelector(".study-form");
  const modalBg = document.getElementById('modal-bg');
  const container = document.getElementById('modal-container');
  const close = document.getElementById('modal-close');

  form.addEventListener("submit", function(event) {
    event.preventDefault();  // フォームのデフォルト送信を防止

    // バリデーションをサーバーで実行し、エラーがなければモーダル表示
    saveData();
  });

  //必要なデータをサーバーに送信するための変数定義
  function saveData() {
    const formData = new FormData(form);
    const actionUrl = form.getAttribute("action");

    // フォームデータをサーバーに送信
    fetch(actionUrl, {
        method: "POST",
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`サーバーエラー: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        if (data.errors) {
            displayErrors(data.errors);  // バリデーションエラー表示
        } else if (data.success) {
            showModal(data);  // 成功時モーダル表示
        }
    })
    .catch(error => {
        console.error("フェッチエラー:", error);
        alert("サーバーに接続できませんでした。後でもう一度試してください。");
    });
  }
  // エラーメッセージを表示
  function displayErrors(errors) {
    // 既存のエラーメッセージをクリア
    document.querySelectorAll('.error-message').forEach(el => el.textContent = '');

    // サーバーから返されたエラーを表示
    if (errors.name) {
      document.querySelector('p.name-error').textContent = errors.name;  // クラス名で指定
    }
    if (errors.studyTime) {
      document.querySelector('p.studyTime-error').textContent = errors.studyTime;  // クラス名で指定
    }
  }

  //エラーがなければモーダルを表示
  function showModal(data) {
    const categoryName = container.getAttribute("data-category-name");
    const itemName = form.querySelector('input[name="name"]').value;
    const studyTime = form.querySelector('input[name="studyTime"]').value;

    container.classList.add('active');
    modalBg.classList.add('active');

    document.getElementById("modal-message-1").textContent = `${categoryName} に ${itemName} を`;
    document.getElementById("modal-message-2").textContent = `${studyTime}分 で追加しました！`;
  }

  // モーダルを閉じて編集ページに戻る処理
  close.addEventListener('click', () => {
    modalBg.classList.remove('active');
    container.classList.remove('active');

    const userId = form.getAttribute("data-user-id");
    window.location.href = `/learningData/${userId}/skill`;
  });
});
