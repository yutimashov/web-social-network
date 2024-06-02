<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="POST">
    <div>
        <c:choose>
            <c:when test="${param.error eq 'auth_data'}"><span
                    style="color:red">Incorrect email or password</span></c:when>
            <c:when test="${param.error eq 'authorization'}"><span
                    style="color:red">You are not authorized!</span></c:when>
            <c:when test="${param.error eq 'reg'}"><span
                    style="color:red">Account has not been registered!</span></c:when>
        </c:choose>
    </div>
    <label for="email">Email:
        <input type="email" id="email" name="email" required>
    </label><br>
    <label for="password">Password:
        <input type="password" id="password" name="password" required>
    </label><br>
    <label for="rememberMe">Remember me
        <input type="checkbox" id="rememberMe" name="rememberMe" value="true">
    </label><br>
    <button type="submit">Login</button>
    <a href="${rootUrl}/register">
        <button type="button">Register</button>
    </a>
</form>
</body>
</html>
