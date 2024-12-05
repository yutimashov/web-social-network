'use strict';

// all pagination links
const paginationLinks = document.querySelectorAll('.page-link');

document.addEventListener('DOMContentLoaded', function() {
    paginationLinks.forEach(function(link) {
        // for each pagination link add event listener on click event
        link.addEventListener('click', function(e) {
            e.preventDefault();
            // get url from href
            let url = this.getAttribute('href');
            console.log(url);
            loadPages(url);
        });
    });
});

function loadPages(url) {
    // Values for searchType and searchQuery are gotten from header search form
    fetch(url)
        .then(function (result) {
            return result.text();
        }).then(function (data) {
        document.getElementById('search-result-container').insertAdjacentHTML("beforeend", data);
    }).catch((err) => alert(err))
}
