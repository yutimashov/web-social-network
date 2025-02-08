const accountsContainer = document.getElementById('accounts-result');
const searchQuery = new URLSearchParams(new URL(window.location.href).search).get('searchQuery');
const searchType = new URLSearchParams(new URL(window.location.href).search).get('searchType');
let lastFirstName;
let lastLastName;

window.addEventListener('scroll', () => {
    if (Math.ceil(window.scrollY + window.innerHeight) >= document.documentElement.scrollHeight) {
        appendSearchResultsDynamically();
    }
});

function appendSearchResultsDynamically() {
    lastFirstName = document.getElementById('last-first-name').value;
    lastLastName = document.getElementById('last-last-name').value;
    makeRequest(handleAppendSearchResultsResponse);
}

function handleAppendSearchResultsResponse(responseText) {
    accountsContainer.insertAdjacentHTML("beforeend", responseText);
    // update lastId
    const newLastFirstNameElement = document.getElementById('new-last-id');
    const newLastLastNameElement = document.getElementById('new-last-id');
    if (newLastFirstNameElement && newLastLastNameElement) {
        document.getElementById('last-first-name').value = newLastFirstNameElement.getAttribute('data-last-firstname');
        document.getElementById('last-last-name').value = newLastLastNameElement.getAttribute('data-last-lastname');
        // remove temp element
        newLastFirstNameElement.remove();
        newLastLastNameElement.remove();
    }
}

function makeRequest(callback) {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", `/search?searchType=${searchType}&searchQuery=${searchQuery}&lastLastName=${lastLastName}&lastFirstName=${lastFirstName}&isAjax=true`, true);
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
