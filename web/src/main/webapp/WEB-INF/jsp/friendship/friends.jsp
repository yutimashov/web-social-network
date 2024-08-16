<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Friends</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl mt-4">
    <c:choose>
        <c:when test="${requestScope.friends.size() == 0}">
            <div class="alert alert-primary" role="alert">
                You have no friends yet. You can <a href="${rootUrl}/accounts" class="alert-link">send request</a> to any user.
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach items="${requestScope.friends}" var="friend">
                <div class="row">
                    <div class="col-md-2 col-sm-2">
                        <c:if test="${not empty friend.avatar}">
                            <a href="${pageContext.request.contextPath}/account?id=${friend.id}">
                                <img src="${rootUrl}/avatar?id=${friend.id}" alt="user" class="profile-photo-lg"
                                     width="100px"
                                     height="100px">
                            </a>
                        </c:if>
                        <c:if test="${empty friend.avatar}">
                            <a href="${pageContext.request.contextPath}/account?id=${friend.id}">
                                <img src="${rootUrl}/static/img/img-coming-soon-placeholder.png" alt="user"
                                     class="profile-photo-lg" width="100px" height="100px">
                            </a>
                        </c:if>
                    </div>
                    <div class="col-md-10 col-sm-10">
                        <h5>
                            <a href="${pageContext.request.contextPath}/account?id=${friend.id}">${friend.firstName} ${friend.lastName}</a>
                        </h5>
                        <a href="${pageContext.request.contextPath}/friends/delete?id=${friend.id}"
                           class="btn btn-danger"
                           role="button">Delete friend</a>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
