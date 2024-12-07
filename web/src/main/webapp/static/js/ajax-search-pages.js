'use strict';

const urlObj = new URL(window.location.href);
const searchType = urlObj.searchParams.get('searchType');
const searchQuery = urlObj.searchParams.get('searchQuery');
const totalPages = parseInt(document.getElementById('search-results').getAttribute('data-page-result-amount'));

let currentActivePage = 1;

document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.page-link').forEach(function (link) {
        link.addEventListener('click', function (e) {
            e.preventDefault();
            loadPages(this.getAttribute('href'));
        });
    });
});

function loadPages(url) {
    fetch(url)
        .then((response) => {
            currentActivePage = new URL(response.url).searchParams.get('currentPage');
            return response.text();
        })
        .then((data) => {
            updateActivePageLinkStyle();
            updateNavLinks();
            document.getElementById('search-results').innerHTML = data;
        })
        .catch(err => displayError(err));
}

function updateActivePageLinkStyle() {
    const outdatedLink = document.querySelector('.current-search-link');
    if (outdatedLink) {
        outdatedLink.classList.remove('current-search-link', 'disabled');
    }
    const newActiveLink = document.querySelector(`.page-link[data-current-page="${currentActivePage}"]`);
    if (newActiveLink) {
        newActiveLink.classList.add('current-search-link', 'disabled');
    }
}

function updateNavLinks() {
    updatePageLink('prevPageLink', -1);
    updatePageLink('nextPageLink', 1);
}

function updatePageLink(linkId, increment) {
    const link = document.getElementById(linkId);
    const newPage = parseInt(currentActivePage) + increment;
    if ((linkId === 'prevPageLink' && newPage > 0) || (linkId === 'nextPageLink' && newPage <= totalPages)) {
        link.parentElement.style.display = 'inline-block';
        link.setAttribute('href',
            `/search_ajax_pages?searchType=${searchType}&searchQuery=${searchQuery}&currentPage=${newPage}`);
        link.classList.remove('current-search-link');
    } else {
        link.parentElement.style.display = 'none';
    }
}

function displayError(err) {
    console.error(err);
    alert('An error occurred while loading the page. Please try again later.');
}
