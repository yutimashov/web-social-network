<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Account group requests</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl mt-4">
  <p>Group has ${requestScope.groupRequests.size()} incoming requests from accounts</p>
  <c:forEach items="${requestScope.groupRequests}" var="account">
    <a href="${rootUrl}/account?id=${account.id}">${account.firstName} ${account.lastName}</a>
    <span>&nbsp;&nbsp;</span>
    <a href="${rootUrl}/group/accept-request?groupId=${param.id}&accountId=${account.id}">
      <button>Accept request</button>
    </a>
    <span>&nbsp;&nbsp;</span>
    <a href="${rootUrl}/group/decline-request?groupId=${param.id}&accountId=${account.id}">
      <button>Decline request</button>
    </a>
  </c:forEach>
</div>
</body>
</html>
