<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Friends requests</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl mt-4">
    <a href="${pageContext.request.contextPath}/friends/requests/incoming" class="btn btn-warning"
       role="button">Incoming requests</a>
    <br>
    <a href="${pageContext.request.contextPath}/friends/requests/outgoing" class="btn btn-warning"
       role="button">Outgoing requests</a>
</div>
</body>
</html>
