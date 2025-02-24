document.addEventListener("DOMContentLoaded", function() {
    const ctx = document.getElementById("studyChart").getContext("2d");

    if (!userId) {
        console.error("userId が取得できませんでした。");
        return;
    }
  
    fetch(`/learningData/${userId}/chart`)
      .then(response => response.json())
      .then(data => {
            let rawLabels = data.labels.reverse(); // 月の順序を逆にする
                
            // 月のラベルを「今月・先月・先々月」に変換
            let currentMonthIndex = 0; // 今月を0番目とする
            let labels = rawLabels.map((_, index) => {
                if (index === currentMonthIndex) return "今月";
                if (index === currentMonthIndex + 1) return "先月";
                if (index === currentMonthIndex + 2) return "先々月";
                return "";
            });

            let backendData = rawLabels.map(month => data.categoryData.backend[month] || 0);
            let frontendData = rawLabels.map(month => data.categoryData.frontend[month] || 0);
            let infraData = rawLabels.map(month => data.categoryData.infra[month] || 0);


            new Chart(ctx, {
            type: "bar",
            data: {
                labels: labels,
                datasets: [
                    {
                        label: "バックエンド",
                        data: backendData,
                        backgroundColor: "rgba(255, 105, 180, 0.5)",
                    },
                    {
                        label: "フロントエンド",
                        data: frontendData,
                        backgroundColor: "rgba(255, 165, 0, 0.5)",
                    },
                    {
                        label: "インフラ",
                        data: infraData,
                        backgroundColor: "rgba(255, 215, 0, 0.5)",
                    },
                ],
            },
            options: {
                responsive: true, 
                plugins: {
                    legend: { position: "top" } 
                },
                scales: {
                    x: {
                        reverse: true, // X軸の順序を逆にする
                    }
                }
            }
        });
    })
    .catch(error => console.error("データ取得エラー:", error));
});
  