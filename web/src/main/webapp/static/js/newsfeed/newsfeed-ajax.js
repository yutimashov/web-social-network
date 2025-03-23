const newsFeedContainer = document.getElementById('newsfeed');
const pageSize = document.getElementById('page-size').value;
// variables for storing changing after each request data
let lastPostId;
let cacheStartRange;

// send ajax request when user scroll till end of viewport
window.addEventListener('scroll', () => {
    if (Math.ceil(window.scrollY + window.innerHeight) >= document.documentElement.scrollHeight) {
        appendSearchResultsDynamically();
    }
});

// implement ajax request
function appendSearchResultsDynamically() {
    lastPostId = document.getElementById('last-post-id').value;
    cacheStartRange = document.getElementById('cache-start-range').value;
    makeRequest(handleAppendNewsFeedResultsResponse);
}

function handleAppendNewsFeedResultsResponse(responseText) {
    newsFeedContainer.insertAdjacentHTML("beforeend", responseText);
    // update tmp data
    const newLastIdElement = document.getElementById('new-last-post-id');
    const newCacheStartRange = document.getElementById('new-cache-start-range');
    if (newLastIdElement) {
        document.getElementById('last-post-id').value = newLastIdElement.getAttribute('data-last-id');
        document.getElementById('cache-start-range').value = newCacheStartRange.getAttribute('data-cache-range')
            + pageSize;
        // remove temp element
        newLastIdElement.remove();
        newCacheStartRange.remove();
    }
}

function makeRequest(callback) {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", `/newsfeed?lastPostId=${lastPostId}&cacheStartRange=${cacheStartRange}&isAjax=true`, true);
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
