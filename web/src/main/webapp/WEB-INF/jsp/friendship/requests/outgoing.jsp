<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Outgoing friend requests</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
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
                <a href="${pageContext.request.contextPath}/account?id=${account.id}">${account.firstName} ${account.lastName}</a>
                <span>&nbsp;&nbsp;</span>
                <a href="${pageContext.request.contextPath}/friends/delete?id=${account.id}">
                    <button class="btn btn-danger btn-sm">Revoke request</button>
                </a>
                <br />
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script src="<c:url value="/static/js/ajax-following-accounts.js" />"></script>
</body>
</html>
