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
<nav>
    <ul>
        <c:if test="${requestScope.currentPage != 1}">
            <li>
                <a href="${rootUrl}/search?searchType=${requestScope.searchType}&searchQuery=${requestScope.searchQuery}&currentPage=${requestScope.currentPage - 1}">
                    Previous
                </a>
            </li>
        </c:if>
        <c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
            <c:choose>
                <c:when test="${requestScope.currentPage eq i}">
                    <li>
                        <a href="">${i} <span>(current)</span></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="${rootUrl}/search?searchType=${requestScope.searchType}&searchQuery=${requestScope.searchQuery}&currentPage=${i}">
                                ${i}
                        </a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${requestScope.currentPage lt requestScope.numberOfPages}">
            <li>
                <a href="${rootUrl}/search?searchType=${requestScope.searchType}&searchQuery=${requestScope.searchQuery}&currentPage=${requestScope.currentPage + 1}">
                    Next
                </a>
            </li>
        </c:if>
    </ul>
</nav>
</body>
</html>
