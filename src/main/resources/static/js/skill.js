document.addEventListener("DOMContentLoaded", function() {
    // 当月のデータを取得
    const today = new Date();
    const currentMonth = today.getMonth() + 1; // JavaScriptの月は0始まりなので+1

    // 過去3ヶ月分のリストを作成
    const months = [];
    for (let i = 0; i < 3; i++) {
        const month = currentMonth - i > 0 ? currentMonth - i : currentMonth - i + 12;
        months.push(month);
    }

    // プルダウンメニューを動的に生成
    const monthSelect = document.getElementById("createMonth");
    months.forEach(month => {
        const option = document.createElement("option");
        option.value = month;
        option.textContent = `${month}月`;
        if (month === currentMonth) {
            option.selected = true; // 初期選択
        }
        monthSelect.appendChild(option);
    });

    // プルダウン選択時の処理
    createMonthSelect.addEventListener("change", () => {
        const selectedMonth = createMonthSelect.value;
        const userId = document.querySelector('input[name="userId"]').value;
        // サーバーにリクエストを送信
        window.location.href = `/learningData/${userId}/skill?createMonth=${selectedMonth}`;
    });
});
