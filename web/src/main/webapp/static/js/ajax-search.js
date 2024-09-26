document.getElementById('searchQuery').addEventListener('keyup', dropDown);

function dropDown() {
    const clientSearchQuery = document.getElementById("searchQuery").value;
    const searchType = document.getElementById("searchType").value;
    if (clientSearchQuery.length > 1) {
        const xhr = new XMLHttpRequest();
        xhr.open("GET", "/search_ajax?searchQuery=" + encodeURIComponent(clientSearchQuery) + "&searchType="
            + encodeURIComponent(searchType), true);
        xhr.timeout = 6000;
        xhr.onload = function () {
            if (xhr.status === 200) {
                console.log("hello")
                const data = xhr.responseText;
                const dropdownContainer = document.getElementById("dropdown-container");
                dropdownContainer.innerHTML = ''; // Очистить контейнер
                dropdownContainer.innerHTML = data; // Добавить новые данные
                // Эмулировать поведение dropdown-toggle
                const dropdownToggle = document.querySelector('.dropdown-toggle');
                if (dropdownToggle) {
                    dropdownToggle.classList.add('show');
                    dropdownContainer.classList.add('show');
                }
            } else {
                console.log("ERROR : ", xhr.statusText);
            }
        };
        xhr.ontimeout = function () {
            console.log("ERROR: Request timed out");
        };
        xhr.onerror = function () {
            console.log("ERROR: Network error");
        };
        xhr.send();
    }
}