'use strict';

document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.page-link').forEach(function (link) {
        link.addEventListener('click', function (e) {
            // remove previously active link
            let element = document.querySelector('.current-search-link');
            if (element !== null) {
                element.classList.remove('current-search-link');
            }
            e.preventDefault();
            e.currentTarget.classList.add('current-search-link');
            loadPages(this.getAttribute('href'));
        });
    });
});

function loadPages(url) {
    fetch(url)
        .then(function (result) {
            return result.text();
        }).then(function (data) {
        document.getElementById('search-results').innerHTML = data;
    }).catch((err) => alert(err))
}
