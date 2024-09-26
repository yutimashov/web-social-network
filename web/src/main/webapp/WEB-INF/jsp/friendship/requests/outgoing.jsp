<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Outgoing friend requests</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl mt-4">
    <p>Account has ${requestScope.outgoingFriendRequests.size()} outgoing friend requests</p>
    <c:forEach items="${requestScope.outgoingFriendRequests}" var="account">
        <a href="${pageContext.request.contextPath}/account?id=${account.id}">${account.firstName} ${account.lastName}</a>
        <span>&nbsp;&nbsp;</span>
        <a href="${pageContext.request.contextPath}/friends/delete?id=${account.id}">
            <button class="btn btn-danger">Revoke request</button>
        </a>
    </c:forEach>
</div>
</body>
</html>
