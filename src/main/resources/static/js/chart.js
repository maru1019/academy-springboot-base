document.addEventListener("DOMContentLoaded", function() {
    const ctx = document.getElementById("studyChart").getContext("2d");

    if (!userId) {
        console.error("userId が取得できませんでした。");
        return;
    }
  
    fetch(`/learningData/${userId}/chart`)
      .then(response => response.json())
      .then(data => {
            const labels = data.labels;
            const backendData = labels.map(month => data.categoryData.backend[month] || 0);
            const frontendData = labels.map(month => data.categoryData.frontend[month] || 0);
            const infraData = labels.map(month => data.categoryData.infra[month] || 0);

            new Chart(ctx, {
            type: "bar",
            data: {
                labels: labels,
                datasets: [
                    {
                        label: "Backend",
                        data: backendData,
                        backgroundColor: "rgba(255, 99, 132, 0.5)",
                    },
                    {
                        label: "Frontend",
                        data: frontendData,
                        backgroundColor: "rgba(54, 162, 235, 0.5)",
                    },
                    {
                        label: "Infra",
                        data: infraData,
                        backgroundColor: "rgba(75, 192, 192, 0.5)",
                    },
                ],
            },
            options: {
                responsive: true, 
                plugins: {
                    legend: { position: "top" } 
                }
            }
        });
    })
    .catch(error => console.error("データ取得エラー:", error));
});
  