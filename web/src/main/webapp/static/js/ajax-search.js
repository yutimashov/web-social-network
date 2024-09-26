document.getElementById('searchQuery').addEventListener('input', dropDown);

function dropDown() {
    const clientSearchQuery = document.getElementById("searchQuery").value;
    const searchType = document.getElementById("searchType").value;
    if (clientSearchQuery.length > 0) {
        const xhr = new XMLHttpRequest();
        xhr.open("GET", "/search_ajax?searchQuery=" + encodeURIComponent(clientSearchQuery)
            + "&searchType=" + encodeURIComponent(searchType), true);
        xhr.timeout = 6000;
        xhr.onload = function () {
            if (xhr.status === 200) {
                const data = xhr.responseText;
                const dropdownContainer = document.getElementById("dropdown-container");
                const dropdownToggle = document.querySelector('.dropdown-toggle');
                if (dropdownToggle) {
                    dropdownToggle.classList.add('show');
                    dropdownContainer.classList.add('show');
                }
                dropdownContainer.innerHTML = '';
                dropdownContainer.innerHTML = data;
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