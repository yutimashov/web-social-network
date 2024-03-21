<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<form action="${pageContext.request.contextPath}/login" method="POST">
    <c:if test="${param.error == 'auth-data'}">
        <div>
            <span style="color:red">Incorrect email or password</span>
        </div>
    </c:if>
    <c:if test="${param.error == 'authorization'}">
        <div>
            <span style="color:red">You are not authorized!</span>
        </div>
    </c:if>
    <label for="email">Email:
        <input type="email" id="email" value="${param.email}" name="email" required>
    </label>
    <br>
    <label for="password">Password:
        <input type="password" id="password" name="password" required>
    </label>
    <br>
    <label for="rememberMe">Remember me
        <input type="checkbox" id="rememberMe" name="rememberMe" value="true">
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
