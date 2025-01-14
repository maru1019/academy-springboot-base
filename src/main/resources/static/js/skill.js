document.addEventListener("DOMContentLoaded", function() {
    const monthSelect = document.getElementById("createMonth");
   
        monthSelect.addEventListener("change", function() {
            this.form.submit();
    });
});