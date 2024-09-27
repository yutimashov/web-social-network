const dropdownContainer = document.getElementById('dropdown-container');
let searchResultPortion = 1;
let allResultsLoaded = false;

document.getElementById('searchQuery').addEventListener('input', loadSearchTips);

function loadSearchTips() {
    const searchQuery = document.getElementById('searchQuery').value;
    const searchType = document.getElementById('searchType').value;
    if (searchQuery.length > 0) {
        const xhr = new XMLHttpRequest();
        xhr.open("GET", "/search_ajax?searchQuery=" + encodeURIComponent(searchQuery)
            + "&searchType=" + encodeURIComponent(searchType) + "&currentPage=" + searchResultPortion, true);
        xhr.timeout = 6000;
        xhr.onload = function () {
            if (xhr.status === 200) {
                const searchResult = xhr.responseText;
                if (searchResult.trim() === '') {
                    dropdownContainer.classList.remove('show');
                } else {
                    searchResultPortion++;
                    dropdownContainer.classList.add('show');
                    dropdownContainer.innerHTML = searchResult;
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

// hide tips when click is outside tips block
document.addEventListener('click', function (e) {
    const isClickInside = dropdownContainer.contains(e.target);
    if (!isClickInside) {
        dropdownContainer.classList.remove('show');
        searchResultPortion = 1;
    }
});

// dynamic loading search results
dropdownContainer.addEventListener('scroll', () => {
    appendSearchResultsDynamically();
})

function appendSearchResultsDynamically() {
    if (allResultsLoaded) {
        return;
    }
    if (dropdownContainer.clientHeight + dropdownContainer.scrollTop > dropdownContainer.scrollHeight) {
        const clientSearchQuery = document.getElementById('searchQuery').value;
        const searchType = document.getElementById('searchType').value;
        const xhr = new XMLHttpRequest();
        xhr.open("GET", "/search_ajax?searchQuery=" + encodeURIComponent(clientSearchQuery)
            + "&searchType=" + encodeURIComponent(searchType) + "&currentPage="
            + encodeURIComponent(searchResultPortion), true);
        xhr.timeout = 6000;
        xhr.onload = function () {
            if (xhr.status === 200) {
                if (xhr.responseText.trim() === "") {
                    allResultsLoaded = true;
                } else {
                    searchResultPortion++;
                    dropdownContainer.insertAdjacentHTML("beforeend", xhr.responseText);
                    // if latest query returned less than 5 elements - it was the last query
                    if (document.querySelectorAll('.dropdown-item') % 5 !== 0) {
                        allResultsLoaded = true;
                    }
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
