<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Incoming friend requests</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<p>Account has ${requestScope.friendRequests.size()} incoming friend requests</p>
<c:forEach items="${requestScope.friendRequests}" var="account">
  <a href="${pageContext.request.contextPath}/account?id=${account.id}">${account.firstName} ${account.lastName}</a>
  <span>&nbsp;&nbsp;</span>
  <a href="${pageContext.request.contextPath}/accept-friend-request?id=${account.id}">
    <button>Accept request</button>
  </a>
  <span>&nbsp;&nbsp;</span>
  <a href="${pageContext.request.contextPath}/delete-friend?id=${account.id}">
    <button>Decline request</button>
  </a>
</c:forEach>
</body>
</html>
