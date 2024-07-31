<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="${rootUrl}/static/css/auth-form.css">
</head>
<body>
<div class="full-width d-flex justify-content-center and align-items-center">
    <form class="rounded p-4 p-sm" action="${pageContext.request.contextPath}/login" method="POST">
        <div class="mb-3">
            <c:choose>
                <c:when test="${param.error eq 'auth_data'}"><span
                        style="color:red">Incorrect email or password</span></c:when>
                <c:when test="${param.error eq 'authorization'}"><span
                        style="color:red">You are not authorized!</span></c:when>
                <c:when test="${param.error eq 'reg'}"><span
                        style="color:red">Account has not been registered!</span></c:when>
                <c:when test="${param.reg eq 'success'}"><span
                        style="color:cadetblue">Account has been registered!</span></c:when>
            </c:choose>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email:</label>
            <input type="email" id="email" name="email" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password:</label>
            <input type="password" id="password" name="password" class="form-control" required>
        </div>
        <div class="mb-3 form-check">
            <input type="checkbox" id="rememberMe" name="rememberMe" value="true" class="form-check-input">
            <label for="rememberMe" class="form-check-label">Remember me</label>
        </div>
        <div>
            <button type="submit" class="btn btn-primary">Login</button>
            <a href="${rootUrl}/register">
                <button type="button" class="btn btn-secondary">Register</button>
            </a>
        </div>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
