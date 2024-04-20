<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Outgoing friend requests</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<p>Account has ${requestScope.outgoingFriendRequests.size()} outgoing friend requests</p>
<c:forEach items="${requestScope.outgoingFriendRequests}" var="account">
  <a href="${pageContext.request.contextPath}/account?id=${account.id}">${account.firstName} ${account.lastName}</a>
  <span>&nbsp;&nbsp;</span>
  <a href="${pageContext.request.contextPath}/delete-friend?id=${account.id}">
    <button>Revoke request</button>
  </a>
</c:forEach>
</body>
</html>
