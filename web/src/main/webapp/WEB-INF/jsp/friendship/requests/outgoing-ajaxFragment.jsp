<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:forEach items="${requestScope.outgoingFriendRequests}" var="account">
    <a href="${pageContext.request.contextPath}/account?id=${account.id}">${account.firstName} ${account.lastName}</a>
    <span>&nbsp;&nbsp;</span>
    <a href="${pageContext.request.contextPath}/friends/delete?id=${account.id}">
        <button class="btn btn-danger btn-sm">Revoke request</button>
    </a>
    <br />
</c:forEach>
<!-- new value of lastId -->
<div id="new-last-id" data-last-id="${lastId}" style="display: none;"></div>