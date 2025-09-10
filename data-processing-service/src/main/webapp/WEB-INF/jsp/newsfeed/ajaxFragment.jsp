<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:forEach items="${requestScope.newsfeed}" var="post">
    <div class="account-wall-msg">
        <hr>
        <div style="display: flex; justify-content: space-between; align-items: center;">
            <span>Created: ${post.creationDate}</span>
        </div>
        <p>Author:
            <a href="${rootUrl}/account?id=${post.accountAuthorId}">
                    ${requestScope.accountService.getById(post.accountAuthorId).get().firstName}
                    ${requestScope.accountService.getById(post.accountAuthorId).get().lastName}
            </a>
        </p>
        <p>${post.text}</p>
        <c:if test="${post.photo ne null}">
            <img src="${rootUrl}/account-wall/image?id=${post.id}" alt="Message photo" width="150px"
                 height="150px">
        </c:if>
        <hr>
    </div>
</c:forEach>
<!-- new value of lastId -->
<div id="new-last-post-id" data-last-id="${lastPostId}" style="display: none;"></div>
<div id="new-cache-start-range" data-cache-range="${cacheStartRange}" style="display: none;"></div>