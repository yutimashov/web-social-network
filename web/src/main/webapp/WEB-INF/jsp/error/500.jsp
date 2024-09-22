<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>404 error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="d-flex align-items-center justify-content-center vh-100">
    <div class="text-center">
        <h1 class="display-1 fw-bold">500</h1>
        <p class="fs-3"><span class="text-danger">Opps!</span> Something on the server is broken.</p>
        <p class="lead">
            We are currently working on resolving the issue.
        </p>
    </div>
</div>
</body>
</html>
