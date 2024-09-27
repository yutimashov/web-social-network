const dropdownContainer = document.getElementById('dropdown-container');
let searchPortion = 1;
let allResultsLoaded = false;
let itemsPerRequest = 0;

document.getElementById('searchQuery').addEventListener('input', loadSearchTips);

function loadSearchTips() {
    const searchQuery = document.getElementById('searchQuery').value;
    const searchType = document.getElementById('searchType').value;
    if (searchQuery.length > 0) {
        makeRequest(searchQuery, searchType, searchPortion, handleSearchTipsResponse);
    }
}

function makeRequest(searchQuery, searchType, currentPage, callback) {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", `/search_ajax?searchQuery=${encodeURIComponent(searchQuery)}&searchType=${encodeURIComponent(searchType)}&currentPage=${currentPage}`, true);
    xhr.timeout = 6000;
    xhr.onload = function () {
        if (xhr.status === 200) {
            callback(xhr.responseText);
        } else {
            console.log("ERROR:", xhr.statusText);
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

function handleSearchTipsResponse(responseText) {
    allResultsLoaded = false;
    if (responseText.trim() === '') {
        dropdownContainer.classList.remove('show');
    } else {
        searchPortion++;
        dropdownContainer.classList.add('show');
        dropdownContainer.innerHTML = responseText;
        itemsPerRequest = document.querySelectorAll('.dropdown-item').length;
    }
}

// Hide tips when click is outside tips block
document.addEventListener('click', function (e) {
    if (!dropdownContainer.contains(e.target)) {
        dropdownContainer.classList.remove('show');
        searchPortion = 1;
    }
});

// Dynamic loading search results
dropdownContainer.addEventListener('scroll', () => {
    if (!allResultsLoaded && dropdownContainer.clientHeight + dropdownContainer.scrollTop >= dropdownContainer.scrollHeight) {
        appendSearchResultsDynamically();
    }
});

function appendSearchResultsDynamically() {
    const searchQuery = document.getElementById('searchQuery').value;
    const searchType = document.getElementById('searchType').value;
    makeRequest(searchQuery, searchType, searchPortion, handleAppendSearchResultsResponse);
}

function handleAppendSearchResultsResponse(responseText) {
    dropdownContainer.insertAdjacentHTML("beforeend", responseText);
    searchPortion++;
    // check if the latest request was the last needed
    if (document.querySelectorAll('.dropdown-item').length % itemsPerRequest !== 0) {
        allResultsLoaded = true;
    }
}
