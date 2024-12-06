'use strict';

const urlObj = new URL(window.location.href);
const searchType = urlObj.searchParams.get('searchType');
const searchQuery = urlObj.searchParams.get('searchQuery');

let currentActivePage = 1;

document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.page-link').forEach(function (link) {
        link.addEventListener('click', function (e) {
            e.preventDefault();
            loadPages(this.getAttribute('href'));
        });
    });
});

function loadPages(url) {
    fetch(url)
        .then(function (result) {
            currentActivePage = new URL(result.url).searchParams.get('currentPage');
            return result.text();
        })
        .then(function (data) {
            updateOutdatedActivePageLinkStyle();
            updatePrevPageLink();
            updateNextPageLink();
           document.querySelector(`.page-link[data-current-page="${currentActivePage}"]`).classList.add('current-search-link', 'disabled');
            document.getElementById('search-results').innerHTML = data;
        })
        .catch((err) => alert(err))
}

function updateOutdatedActivePageLinkStyle() {
    let outdatedActivePage = document.querySelector('.current-search-link');
    if (outdatedActivePage !== null) {
        outdatedActivePage.classList.remove('current-search-link', 'disabled');
    }
}

function updatePrevPageLink() {
    const prevLink = document.getElementById('prevPageLink');
    if (currentActivePage > 1) {
        prevLink.parentElement.style.display = 'inline-block';
        prevLink.setAttribute('href', `/search_ajax_pages?searchType=${searchType}&searchQuery=${searchQuery}&currentPage=${parseInt(currentActivePage) - 1}`);
        prevLink.classList.remove('current-search-link');
        // document.getElementsByClassName(`[data-current-page="${currentActivePage}"]`)[0].classList.add('disabled');
    } else {
        prevLink.parentElement.style.display = 'none';
    }
}

function updateNextPageLink() {
    const nextLink = document.getElementById('nextPageLink');
    if (currentActivePage < document.getElementById('search-results').getAttribute('data-page-result-amount')) {
        nextLink.parentElement.style.display = 'inline-block';
        nextLink.setAttribute('href', `/search_ajax_pages?searchType=${searchType}&searchQuery=${searchQuery}&currentPage=${parseInt(currentActivePage) + 1}`);
        nextLink.classList.remove('current-search-link');
    } else {
        nextLink.parentElement.style.display = 'none';
    }
}
