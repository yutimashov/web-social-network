<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="POST">
    <label for="email">Email:
        <input type="email" id="email" name="email" required>
    </label>
    <br>
    <label for="password">Password:
        <input type="password" id="password" name="password" required>
    </label>
    <br>
    <button type="submit">Login</button>
    <br>
    <a href="${pageContext.request.contextPath}/register">
        <button type="button">Register</button>
    </a>
</form>
</body>
</html>
