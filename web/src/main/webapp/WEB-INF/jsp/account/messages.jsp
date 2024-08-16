<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Personal messages</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl mt-4">
    <c:forEach items="${requestScope.accounts}" var="account">
        <c:choose>
            <c:when test="${not empty account.avatar}">
                <a href="${rootUrl}/account?id=${account.id}"><img src="${rootUrl}/avatar?id=${account.id}"
                                                                   alt="Profile avatar" width="150px"
                                                                   height="150px"></a>
            </c:when>
            <c:otherwise>
                <a href="${rootUrl}/account?id=${account.id}"><img
                        src="${rootUrl}/static/img/img-coming-soon-placeholder.png" alt="Profile avatar"
                        width="150px" height="150px"></a>
            </c:otherwise>
        </c:choose>
        <h5><a href="${rootUrl}/account?id=${account.id}">${account.firstName} ${account.lastName}</a></h5>
        <a href="${rootUrl}/account/messages/dialog?id=${account.id}" role="button" class="btn btn-sm btn-warning">Open
            dialog</a>
        <hr>
    </c:forEach>
</div>
</body>
</html>
