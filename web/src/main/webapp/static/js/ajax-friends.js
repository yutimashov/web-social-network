const accountsContainer = document.getElementById('friends');
let pageNumber = 1;
const id = new URLSearchParams(new URL(window.location.href).search).get('id');

window.addEventListener('scroll', () => {
    if (Math.ceil(window.scrollY + window.innerHeight) >= document.documentElement.scrollHeight) {
        appendSearchResultsDynamically();
    }
});

function appendSearchResultsDynamically() {
    makeRequest(pageNumber, handleAppendSearchResultsResponse);
}

function handleAppendSearchResultsResponse(responseText) {
    accountsContainer.insertAdjacentHTML("beforeend", responseText);
    pageNumber++;
}

function makeRequest(pageNumber, callback) {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", `/friends/all-friends?id=${id}&pageNumber=${pageNumber}`, true);
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
