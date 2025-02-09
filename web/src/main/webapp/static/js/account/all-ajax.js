const accountsContainer = document.getElementById('accounts');
let accountId;

window.addEventListener('scroll', () => {
    if (Math.ceil(window.scrollY + window.innerHeight) >= document.documentElement.scrollHeight) {
        appendSearchResultsDynamically();
    }
});

function appendSearchResultsDynamically() {
    accountId = document.getElementById('last-id').value;
    makeRequest(handleAppendSearchResultsResponse);
}

function handleAppendSearchResultsResponse(responseText) {
    accountsContainer.insertAdjacentHTML("beforeend", responseText);
    // update lastId
    const newLastIdElement = document.getElementById('new-last-id');
    if (newLastIdElement) {
        document.getElementById('last-id').value = newLastIdElement.getAttribute('data-last-id');
        // remove temp element
        newLastIdElement.remove();
    }
}

function makeRequest(callback) {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", `/account/all?lastId=${accountId}&isAjax=true`, true);
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
