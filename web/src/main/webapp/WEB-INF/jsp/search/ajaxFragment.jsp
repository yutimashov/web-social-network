<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="searchResult">
    <c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
    <c:forEach var="account" items="${requestScope.accounts}">
        <a class="dropdown-item" href="${rootUrl}/account?id=${account.id}">${account.firstName} ${account.lastName}</a>
    </c:forEach>
    <c:forEach var="group" items="${requestScope.groups}">
        <a class="dropdown-item" href="${rootUrl}/group?id=${group.id}">${group.name}</a>
    </c:forEach>
</div>
