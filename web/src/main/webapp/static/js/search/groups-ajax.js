const accountsContainer = document.getElementById('groups-result');
const searchQuery = new URLSearchParams(new URL(window.location.href).search).get('searchQuery');
const searchType = new URLSearchParams(new URL(window.location.href).search).get('searchType');
let lastGroupName;

window.addEventListener('scroll', () => {
    if (Math.ceil(window.scrollY + window.innerHeight) >= document.documentElement.scrollHeight) {
        appendSearchResultsDynamically();
    }
});

function appendSearchResultsDynamically() {
    lastGroupName = document.getElementById('last-groupName').value;
    makeRequest(handleAppendSearchResultsResponse);
}

function handleAppendSearchResultsResponse(responseText) {
    accountsContainer.insertAdjacentHTML("beforeend", responseText);
    // update lastId
    const newLastGroupName = document.getElementById('new-last-groupName');
    if (newLastGroupName) {
        document.getElementById('last-groupName').value = newLastGroupName.getAttribute('data-last-groupName');
        // remove temp element
        newLastGroupName.remove();
    }
}

function makeRequest(callback) {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", `/search?searchType=${searchType}&searchQuery=${searchQuery}&lastGroupName=${lastGroupName}&isAjax=true`, true);
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
