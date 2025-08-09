const accountsContainer = document.getElementById('followings');
const id = new URLSearchParams(new URL(window.location.href).search).get('id');
let lastFriendId;

window.addEventListener('scroll', () => {
    if (Math.ceil(window.scrollY + window.innerHeight) >= document.documentElement.scrollHeight) {
        appendSearchResultsDynamically();
    }
});

function appendSearchResultsDynamically() {
    lastFriendId = document.getElementById('last-id').value;
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
    xhr.open("GET", `/friends/requests/outgoing?id=${id}&lastId=${lastFriendId}&isAjax=true`, true);
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
