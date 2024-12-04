'use strict';

let searchPortion = document.getElementsByClassName('current-search-link')[0].value;
console.log(searchPortion);

Array.from(document.getElementsByClassName('page-link')).forEach(function(element) {
    element.addEventListener('click', loadPages);
});

// Values for searchType and searchQuery are gotten from header search form
function loadPages() {
    const searchQuery = document.getElementById('searchQuery').value;
    const searchType = document.getElementById('searchType').value;
    makeRequest(searchQuery, searchType, searchPortion, handleSearchTipsResponse);
}

function makeRequest(searchQuery, searchType, currentPage, callback) {
    fetch(`/search_ajax_pages?searchQuery=${encodeURIComponent(searchQuery)}&searchType=${encodeURIComponent(searchType)}&currentPage=${currentPage}`)
        .then(function (result) {
            return result.text();
        }).then(function (data) {
        callback(data);
    }).catch((err) => alert(err))
}

function handleSearchTipsResponse(responseText) {
    console.log(responseText);
    document.getElementById('searchResult').replaceWith(responseText);
    searchPortion = document.getElementsByClassName('current-search-link')[0].value;
}