document.addEventListener("DOMContentLoaded", function() {
  // ✅ 1. HTML の <canvas> を取得
  const ctx = document.getElementById("studyChart").getContext("2d");

  // ✅ 2. 学習時間データ（サンプル）
  const labels = ["先々月", "先月", "今月"]; 
  const backendData = [30, 50, 80]; 
  const frontendData = [20, 40, 60]; 
  const infraData = [10, 30, 50]; 

  // ✅ 3. Chart.js で棒グラフを作成
  new Chart(ctx, {
      type: "bar",
      data: {
          labels: labels,
          datasets: [
              { label: "バックエンド", data: backendData, backgroundColor: "pink" },
              { label: "フロントエンド", data: frontendData, backgroundColor: "yellow" },
              { label: "インフラ", data: infraData, backgroundColor: "orange" }
          ]
      },
      options: {
          responsive: true, 
          plugins: {
              legend: { position: "top" } 
          }
      }
  });
});
