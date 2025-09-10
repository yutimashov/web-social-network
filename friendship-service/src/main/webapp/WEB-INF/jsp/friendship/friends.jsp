<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<c:set var="sessionAccountId" value="${sessionScope.account.id}"/>
<c:set var="pageAccountId" value="${param.id}"/>
<html>
<head>
    <title>Friends</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl mt-4" id="friends">
    <!-- hidden field for storing lastId for ajax-queries -->
    <input type="hidden" id="last-id" value="${lastId}">
    <c:choose>
        <c:when test="${requestScope.friends.size() == 0}">
            <div class="alert alert-primary" role="alert">
                You have no friends yet. You can <a href="${rootUrl}/account/all" class="alert-link">send request</a> to
                any user.
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach items="${requestScope.friends}" var="friend">
                <div class="row">
                    <div class="col-md-2 col-sm-2">
                        <c:if test="${not empty friend.avatar}">
                            <a href="${rootUrl}/account?id=${friend.id}">
                                <img src="${rootUrl}/account/avatar?id=${friend.id}" alt="user" class="profile-photo-lg"
                                     width="100px"
                                     height="100px">
                            </a>
                        </c:if>
                        <c:if test="${empty friend.avatar}">
                            <a href="${rootUrl}/account?id=${friend.id}">
                                <img src="${rootUrl}/static/img/img-coming-soon-placeholder.png" alt="user"
                                     class="profile-photo-lg" width="100px" height="100px">
                            </a>
                        </c:if>
                    </div>
                    <div class="col-md-10 col-sm-10">
                        <h5>
                            <a href="${rootUrl}/account?id=${friend.id}">${friend.firstName} ${friend.lastName}</a>
                        </h5>
                        <c:if test="${sessionAccountId eq pageAccountId}">
                            <a href="${rootUrl}/friends/delete?id=${friend.id}"
                               class="btn btn-danger btn-sm"
                               role="button">Delete friend</a>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script src="<c:url value="/static/js/friendship/friends-ajax.js" />"></script>
</body>
</html>
