<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>400 error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="d-flex align-items-center justify-content-center vh-100">
    <div class="text-center">
        <h1 class="display-1 fw-bold">400</h1>
        <p class="fs-3"><span class="text-danger">Opps!</span> Probably, your request some errors.</p>
        <p class="lead">
            Please, check entered data one more time and send them again.
        </p>
        <p class="lead">
            If this action doesn't help, please <a href="mailto:someone@example.com" style="color: cornflowerblue">contact
            our support</a>.
        </p>
    </div>
</div>
</body>
</html>
