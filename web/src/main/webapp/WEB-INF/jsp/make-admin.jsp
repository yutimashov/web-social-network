<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Make admin</title>
</head>
<body>
<p>Account with id = ${param.id} now is Admin!</p>
<a href="${pageContext.request.contextPath}/account?id=${param.id}">New admin page</a>
<a href="${pageContext.request.contextPath}/account?id=${sessionScope.account.id}">My page</a>
</body>
</html>
