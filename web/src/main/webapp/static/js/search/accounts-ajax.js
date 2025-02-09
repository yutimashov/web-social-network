const accountsContainer = document.getElementById('accounts-result');
const searchQuery = new URLSearchParams(new URL(window.location.href).search).get('searchQuery');
const searchType = new URLSearchParams(new URL(window.location.href).search).get('searchType');
let lastAccountId;

window.addEventListener('scroll', () => {
    if (Math.ceil(window.scrollY + window.innerHeight) >= document.documentElement.scrollHeight) {
        appendSearchResultsDynamically();
    }
});

function appendSearchResultsDynamically() {
    lastAccountId = document.getElementById('last-account-id').value;
    makeRequest(handleAppendSearchResultsResponse);
}

function handleAppendSearchResultsResponse(responseText) {
    accountsContainer.insertAdjacentHTML("beforeend", responseText);
    // update lastId
    const newLastAccountId = document.getElementById('new-last-account-id');
    if (newLastAccountId) {
        document.getElementById('last-account-id').value = newLastAccountId.getAttribute('data-last-account-id');
        // remove temp element
        newLastAccountId.remove();
    }
}

function makeRequest(callback) {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", `/search?searchType=${searchType}&searchQuery=${searchQuery}&lastAccountId=${lastAccountId}&isAjax=true`, true);
    xhr.timeout = 6000;
    xhr.onload = function () {
        if (xhr.status === 200) {
            callback(xhr.responseText);
        } else {
            displayError(xhr);
        }
    };
    xhr.ontimeout = function () {
        displayError(xhr);
    };
    xhr.onerror = function () {
        displayError(xhr);
    };
    xhr.send();
}

function displayError(xhr) {
    console.error("ERROR:", xhr.statusText);
    alert('An error occurred while loading the page. Please try again later.');
}
