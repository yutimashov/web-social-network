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
    <%--  ${pageContext.request.contextPath}/group?id=${group.id}  --%>
    <a href="#">${group.groupName}</a>
    <span>${group.description}</span>
    <hr>
</c:forEach>
</body>
</html>
