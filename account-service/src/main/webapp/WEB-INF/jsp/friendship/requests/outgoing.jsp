<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Outgoing friend requests</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl mt-4" id="followings">
    <!-- hidden field for storing lastId for ajax-queries -->
    <input type="hidden" id="last-id" value="${lastId}">
    <c:choose>
        <c:when test="${empty requestScope.outgoingFriendRequests}">
            <div class="alert alert-warning" role="alert">
                No outgoing friend requests
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach items="${requestScope.outgoingFriendRequests}" var="account">
                <div class="row col-md-6 m-2">
                    <div class="col-md-1 col-sm-2">
                        <c:if test="${not empty account.avatar}">
                            <img src="${rootUrl}/account/avatar?id=${account.id}" alt="user" class="profile-photo-lg"
                                 width="50px"
                                 height="50px">
                        </c:if>
                        <c:if test="${empty account.avatar}">
                            <img src="${rootUrl}/static/img/img-coming-soon-placeholder.png" alt="user"
                                 class="profile-photo-lg" width="50px" height="50px">
                        </c:if>
                    </div>
                    <div class="col-md-4 col-sm-2">
                        <a href="${pageContext.request.contextPath}/account?id=${account.id}">${account.firstName} ${account.lastName}</a>
                    </div>
                    <div class="col-md-3 col-sm-10">
                        <a href="${pageContext.request.contextPath}/friends/delete?id=${account.id}">
                            <button class="btn btn-danger btn-sm">Revoke request</button>
                        </a>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script src="<c:url value="/static/js/friendship/following-ajax.js" />"></script>
</body>
</html>
