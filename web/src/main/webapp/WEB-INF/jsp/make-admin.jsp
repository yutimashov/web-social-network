<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Make admin</title>
</head>
<body>
<p>Account with id = ${param.id} now is Admin!</p>
<div>
    <a href="${pageContext.request.contextPath}/account?id=${param.id}">
        <button>New admin page</button>
    </a>
</div>
<div>
    <a href="${pageContext.request.contextPath}/account?id=${sessionScope.account.id}">
        <button>My page</button>
    </a>
</div>
</body>
</html>
