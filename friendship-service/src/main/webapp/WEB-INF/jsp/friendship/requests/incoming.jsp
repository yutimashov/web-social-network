<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Incoming friend requests</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl mt-4" id="followers">
    <!-- hidden field for storing lastId for ajax-queries -->
    <input type="hidden" id="last-id" value="${lastId}">
    <c:choose>
        <c:when test="${empty requestScope.friendRequests}">
            <div class="alert alert-warning" role="alert">
                No incoming friend requests
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach items="${requestScope.friendRequests}" var="account">
                <div class="row col-md-6 m-2">
                    <div class="col-md-1 col-sm-2">
                        <c:if test="${not empty account.avatar}">
                            <img src="${rootUrl}/account/avatar?id=${account.id}" alt="user" class="profile-photo-lg" width="50px"
                                 height="50px">
                        </c:if>
                        <c:if test="${empty account.avatar}">
                            <img src="${rootUrl}/static/img/img-coming-soon-placeholder.png" alt="user"
                                 class="profile-photo-lg" width="50px" height="50px">
                        </c:if>
                    </div>
                    <div class="col-md-4 col-sm-2">
                        <a href="${rootUrl}/account?id=${account.id}">${account.firstName} ${account.lastName}</a>
                    </div>
                    <div class="col-md-3 col-sm-10">
                        <a href="${rootUrl}/friends/accept-request?id=${account.id}">
                            <button class="btn btn-success btn-sm">Accept request</button>
                        </a>
                    </div>
                    <div class="col-md-3 col-sm-10">
                        <a href="${rootUrl}/friends/delete?id=${account.id}">
                            <button class="btn btn-danger btn-sm">Decline request</button>
                        </a>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
<script src="<c:url value="/static/js/friendship/followers-ajax.js" />"></script>
</body>
</html>
