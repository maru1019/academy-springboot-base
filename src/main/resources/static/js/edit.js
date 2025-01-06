function updateFileName() {
  const fileInput = document.getElementById('form-image'); // ファイル入力要素
  const fileNameSpan = document.getElementById('file-name'); // ファイル名を表示する要素
  const fileName = fileInput.files.length > 0 ? fileInput.files[0].name : 'ファイルが選択されていません';
  fileNameSpan.textContent = fileName; // 選択したファイル名を設定
}
