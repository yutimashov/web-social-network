<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Account group requests</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<p>Group has ${requestScope.groupRequests.size()} incoming requests from accounts</p>
<c:forEach items="${requestScope.groupRequests}" var="account">
  <a href="${pageContext.request.contextPath}/account?id=${account.id}">${account.firstName} ${account.lastName}</a>
  <span>&nbsp;&nbsp;</span>
<%--  ${pageContext.request.contextPath}/accept-group-request?id=${account.id}--%>
  <a href="#">
    <button>Accept request</button>
  </a>
  <span>&nbsp;&nbsp;</span>
<%--  ${pageContext.request.contextPath}/delete-group-request?id=${account.id}--%>
  <a href="#">
    <button>Decline request</button>
  </a>
</c:forEach>
</body>
</html>
