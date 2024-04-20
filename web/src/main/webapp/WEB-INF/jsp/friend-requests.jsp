<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Friends requests</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<a href="${pageContext.request.contextPath}/incoming-friend-requests">Incoming requests</a>
<br>
<a href="${pageContext.request.contextPath}/outgoing-friend-requests">Outgoing requests</a>
</body>
</html>
