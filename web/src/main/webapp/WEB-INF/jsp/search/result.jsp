<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Search result</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<h4>Search result for query: ${requestScope.searchQuery}</h4>
<table style="border:1px solid black;padding:5px;">
    <tr>
        <th>Name</th>
    </tr>
    <c:forEach var="account" items="${requestScope.accounts}">
        <tr>
            <td><a href="${rootUrl}/account?id=${account.id}">${account.firstName} ${account.lastName}</a></td>
        </tr>
    </c:forEach>
    <c:forEach var="group" items="${requestScope.groups}">
        <tr>
            <td><a href="${rootUrl}/group?id=${group.id}">${group.groupName}</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
