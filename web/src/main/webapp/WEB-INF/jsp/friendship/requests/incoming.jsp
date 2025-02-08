<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Incoming friend requests</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
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
                    <div class="col-md-4 col-sm-2">
                        <a href="${pageContext.request.contextPath}/account?id=${account.id}">${account.firstName} ${account.lastName}</a>
                    </div>
                    <div class="col-md-3 col-sm-10">
                        <a href="${pageContext.request.contextPath}/friends/accept-request?id=${account.id}">
                            <button class="btn btn-success btn-sm">Accept request</button>
                        </a>
                    </div>
                    <div class="col-md-3 col-sm-10">
                        <a href="${pageContext.request.contextPath}/friends/delete?id=${account.id}">
                            <button class="btn btn-danger btn-sm">Decline request</button>
                        </a>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
<script src="<c:url value="/static/js/ajax-follower-accounts.js" />"></script>
</body>
</html>
