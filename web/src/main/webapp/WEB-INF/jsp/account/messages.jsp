<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Personal messages</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<c:forEach items="${requestScope.accounts}" var="account">
  <a href="${pageContext.request.contextPath}/account?id=${account.id}">${account.firstName} ${account.lastName}</a>&nbsp;&nbsp;
  <a href="${pageContext.request.contextPath}/account/messages/dialog?id=${account.id}">Open dialog</a>
  <hr>
</c:forEach>
</body>
</html>
