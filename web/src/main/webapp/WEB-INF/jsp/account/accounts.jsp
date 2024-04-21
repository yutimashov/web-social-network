<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Accounts</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<p>There are ${requestScope.accounts.size()} accounts</p>
<c:forEach items="${requestScope.accounts}" var="account">
    <a href="${pageContext.request.contextPath}/account?id=${account.id}">${account.firstName} ${account.lastName}</a>
    <hr>
</c:forEach>
</body>
</html>
