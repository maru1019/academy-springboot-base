document.addEventListener("DOMContentLoaded", function () {
    // 現在の月 (0が1月なので+1)
    const currentMonth = new Date().getMonth() + 1;

    // 過去3ヶ月分を計算
    const monthsToShow = [];
    for (let i = 0; i < 3; i++) {
        const month = currentMonth - i <= 0 ? currentMonth - i + 12 : currentMonth - i;
        monthsToShow.push(month);
    }

    // select要素を取得
    const monthSelect = document.getElementById("month");

    // 過去3ヶ月分のoptionタグを動的に生成
    monthsToShow.forEach((month) => {
        const option = document.createElement("option");
        option.value = month; // value属性に数値の月を設定
        option.textContent = `${month}月`; // 表示用テキスト
        if (month === currentMonth) {
            option.selected = true; // 現在の月をデフォルトで選択
        }
        monthSelect.appendChild(option);
    });
});

