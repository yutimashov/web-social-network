<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Accounts</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<p>There are ${requestScope.accounts.size()} accounts</p>
<c:forEach items="${requestScope.accounts}" var="account">
    <a href="${rootUrl}/account?id=${account.id}">${account.firstName} ${account.lastName}</a>
    <hr>
</c:forEach>
</body>
</html>
