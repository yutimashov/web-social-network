<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Groups catalog</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl mt-4">
    <c:forEach items="${requestScope.groups}" var="group">
        <div class="row">
            <div class="col-md-2 col-sm-2">
                <c:choose>
                    <c:when test="${not empty group.avatar}">
                        <img src="${rootUrl}/group/avatar?id=${group.id}" alt="user" class="profile-photo-lg" width="100px"
                             height="100px">
                    </c:when>
                    <c:otherwise>
                        <img src="${rootUrl}/static/img/img-coming-soon-placeholder.png" alt="user"
                             class="profile-photo-lg" width="100px" height="100px">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-md-10 col-sm-10">
                <h5><a href="${pageContext.request.contextPath}/group?id=${group.id}">${group.groupName}</a></h5>
                <p>${group.description}</p>
            </div>
        </div>
    </c:forEach>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
