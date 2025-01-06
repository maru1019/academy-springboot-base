document.addEventListener("DOMContentLoaded", function() {
    // 現在の月 (0が1月なので+1)
    const currentMonth = new Date().getMonth() + 1;

    // 月の値とoptionタグのvalueをマッピング
    const monthMap = {
        1: "jan",
        2: "feb",
        3: "mar",
        4: "apr",
        5: "may",
        6: "jun",
        7: "jul",
        8: "aug",
        9: "sep",
        10: "oct",
        11: "nov",
        12: "dec",
    };

    // 過去3ヶ月分を計算
    const monthsToShow = [];
    for (let i = 0; i < 3; i++) {
        // 月を逆算 (1月を超えた場合12月に戻る)
        const month = currentMonth - i <= 0 ? currentMonth - i + 12 : currentMonth - i;
        monthsToShow.push(month);
    }

    // select要素を取得
    const monthSelect = document.getElementById("month");

    // selectタグ内をクリア
    monthSelect.innerHTML = "";

    // 過去3ヶ月分のoptionタグを動的に生成
    monthsToShow.forEach((month) => {
        const option = document.createElement("option");
        option.value = monthMap[month]; // value属性に設定
        option.textContent = `${month}月`; // 表示用テキスト
        monthSelect.appendChild(option);
    });

    // 現在の月を選択
    monthSelect.value = monthMap[currentMonth];
});

