<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Search result</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<p>Search results for query: <em>${requestScope.searchQuery}</em></p>
<table style="border:1px solid black;padding:5px;border-spacing:10px;">
    <tr>
        <th>Name</th>
        <th>Type</th>
    </tr>
    <c:forEach var="account" items="${requestScope.accounts}">
        <tr>
            <td><a href="${rootUrl}/account?id=${account.id}">${account.firstName} ${account.lastName}</a></td>
            <td>Account</td>
        </tr>
    </c:forEach>
    <c:forEach var="group" items="${requestScope.groups}">
        <tr>
            <td><a href="${rootUrl}/group?id=${group.id}">${group.groupName}</a></td>
            <td>Group</td>
        </tr>
    </c:forEach>
</table>
<div class="pagination">
    <c:if test="${not empty requestScope.accounts}">
        <c:set var="currentPage" value="${param.page != null ? param.page : 1}"/>
        <c:set var="totalPages" value="${(requestScope.accounts.size() + 4) / 5}"/>

        <c:if test="${currentPage > 1}">
            <a href="?page=${currentPage - 1}&searchQuery=${requestScope.searchQuery}">&lt; Previous</a>
        </c:if>

        <c:forEach begin="1" end="${totalPages}" varStatus="status">
            <c:choose>
                <c:when test="${status.index + 1 == currentPage}">
                    <strong>${status.index + 1}</strong>
                </c:when>
                <c:otherwise>
                    <a href="?page=${status.index + 1}&searchQuery=${requestScope.searchQuery}">${status.index + 1}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${currentPage < totalPages}">
            <a href="?page=${currentPage + 1}&searchQuery=${requestScope.searchQuery}">Next &gt;</a>
        </c:if>
    </c:if>
</div>

</body>
</html>
