<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:forEach items="${requestScope.friendRequests}" var="account">
    <a href="${pageContext.request.contextPath}/account?id=${account.id}">${account.firstName} ${account.lastName}</a>
    <span>&nbsp;&nbsp;</span>
    <a href="${pageContext.request.contextPath}/friends/accept-request?id=${account.id}">
        <button class="btn btn-success">Accept request</button>
    </a>
    <span>&nbsp;&nbsp;</span>
    <a href="${pageContext.request.contextPath}/friends/delete?id=${account.id}">
        <button class="btn btn-danger btn-sm">Decline request</button>
    </a>
</c:forEach>
<!-- new value of lastId -->
<div id="new-last-id" data-last-id="${lastId}" style="display: none;"></div>