<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>500 error</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="d-flex align-items-center justify-content-center vh-100">
    <div class="text-center">
        <h1 class="display-1 fw-bold">500</h1>
        <p class="fs-3"><span class="text-danger">Oops!</span> ${errorMessage}</p>
        <p class="lead">
            We are currently working on resolving the issue.
        </p>
    </div>
</div>
</body>
</html>
