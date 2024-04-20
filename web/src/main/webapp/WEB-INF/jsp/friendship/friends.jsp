<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Friends</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<p>Account has ${requestScope.friends.size()} friends</p>
<c:forEach items="${requestScope.friends}" var="friend">
    <a href="${pageContext.request.contextPath}/account?id=${friend.id}">${friend.firstName} ${friend.lastName}</a>
    <a href="${pageContext.request.contextPath}/delete-friend?id=${friend.id}">
        <button>Delete friendship</button>
    </a>
    <hr>
</c:forEach>
</body>
</html>
