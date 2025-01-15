document.addEventListener("DOMContentLoaded", function() {
    const currentMonth = parseInt(document.getElementById("createMonth").dataset.currentMonth, 10);
    const selectedMonth = parseInt(document.getElementById("createMonth").value, 10) || currentMonth; // 選択中の月
    const monthSelect = document.getElementById("createMonth");

    // 常に「当月」「1ヶ月前」「2ヶ月前」のリストを生成
    monthSelect.innerHTML = ""; // 現在の選択肢をクリア
    for (let i = 0; i < 3; i++) {
        let month = (currentMonth - i + 12) % 12 || 12; // 過去3ヶ月を計算
        const option = document.createElement("option");
        option.value = month;
        option.textContent = `${month}月`;
        if (month === selectedMonth) {
            option.selected = true; // 選択された月を反映
        }
        monthSelect.appendChild(option);
    }

    // プルダウン変更時にフォームを送信
    monthSelect.addEventListener("change", function() {
        this.form.submit();
    });
});
