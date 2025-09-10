<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<c:set var="sessionAccountId" value="${sessionScope.account.id}"/>
<html>
<head>
    <title>News feed</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl mt-4" id="newsfeed">
    <!-- hidden field for storing data for ajax-queries -->
    <input type="hidden" id="last-post-id" value="${lastPostId}">
    <input type="hidden" id="cache-start-range" value="${cacheStartRange}">
    <input type="hidden" id="page-size" value="${limit}">
    <!-- account wall message -->
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
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script src="<c:url value="/static/js/newsfeed/newsfeed-ajax.js" />"></script>
</body>
</html>
