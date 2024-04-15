<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Groups catalog</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<p>There are ${requestScope.groups.size()} groups</p>
<c:forEach items="${requestScope.groups}" var="group">
    <a href="${pageContext.request.contextPath}/group?id=${group.id}">${group.groupName}</a>
    <span>&nbsp;&nbsp;${group.description}&nbsp;&nbsp;</span>
    <hr>
</c:forEach>
</body>
</html>
