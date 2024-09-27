const dropdownContainer = document.getElementById('dropdown-container');
let searchPortion = 1;
let allResultsLoaded = false;
let itemsPerRequest = 0;

document.getElementById('searchQuery').addEventListener('input', loadSearchTips);

function loadSearchTips() {
    const searchQuery = document.getElementById('searchQuery').value;
    const searchType = document.getElementById('searchType').value;

    if (searchQuery.length > 0) {
        const xhr = new XMLHttpRequest();
        xhr.open("GET",
            `/search_ajax?searchQuery=${encodeURIComponent(searchQuery)}&searchType=${encodeURIComponent(searchType)}&currentPage=${searchPortion}`,
            true);
        xhr.timeout = 6000;
        xhr.onload = function () {
            if (xhr.status === 200) {
                allResultsLoaded = false;
                const searchResult = xhr.responseText;
                if (searchResult.trim() === '') {
                    dropdownContainer.classList.remove('show');
                } else {
                    searchPortion++;
                    dropdownContainer.classList.add('show');
                    dropdownContainer.innerHTML = searchResult;
                    itemsPerRequest = document.querySelectorAll('.dropdown-item').length;
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
        searchPortion = 1;
    }
});

// dynamic loading search results
dropdownContainer.addEventListener('scroll', () => {
    if (!allResultsLoaded && dropdownContainer.clientHeight + dropdownContainer.scrollTop >= dropdownContainer.scrollHeight) {
        appendSearchResultsDynamically();
    }
})

function appendSearchResultsDynamically() {
    const searchQuery = document.getElementById('searchQuery').value;
    const searchType = document.getElementById('searchType').value;

    const xhr = new XMLHttpRequest();
    xhr.open("GET",
        `/search_ajax?searchQuery=${encodeURIComponent(searchQuery)}&searchType=${encodeURIComponent(searchType)}&currentPage=${searchPortion}`,
        true);
    xhr.timeout = 6000;
    xhr.onload = function () {
        if (xhr.status === 200) {
            const searchResult = xhr.responseText;
            dropdownContainer.insertAdjacentHTML("beforeend", searchResult);
            searchPortion++;
            // if latest query returned less than elements contained in 1 portion - it was the last query
            if (document.querySelectorAll('.dropdown-item').length % itemsPerRequest !== 0) {
                allResultsLoaded = true;
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
