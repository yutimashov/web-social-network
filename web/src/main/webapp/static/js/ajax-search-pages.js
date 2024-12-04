'use strict';

Array.from(document.getElementsByClassName('page-link')).forEach(function(element) {
    element.addEventListener('click', loadPages);
});

// Values for searchType and searchQuery are gotten from header search form
function loadPages() {
    makeRequest(handleSearchTipsResponse);
}

function makeRequest(callback) {
    fetch(`/search_ajax_pages?searchQuery=t&searchType=group&currentPage=1`)
        .then(function (result) {
            return result.text();
        }).then(function (data) {
        callback(data);
    }).catch((err) => alert(err))
}

function handleSearchTipsResponse(responseText) {
    document.getElementById('account-info').insertAdjacentHTML("beforeend", responseText);
}