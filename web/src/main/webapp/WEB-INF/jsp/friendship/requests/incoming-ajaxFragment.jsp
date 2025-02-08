<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:forEach items="${requestScope.friendRequests}" var="account">
    <div class="row col-md-6 m-2">
        <div class="col-md-4 col-sm-2">
            <a href="${pageContext.request.contextPath}/account?id=${account.id}">${account.firstName} ${account.lastName}</a>
        </div>
        <div class="col-md-3 col-sm-10">
            <a href="${pageContext.request.contextPath}/friends/accept-request?id=${account.id}">
                <button class="btn btn-success btn-sm">Accept request</button>
            </a>
        </div>
        <div class="col-md-3 col-sm-10">
            <a href="${pageContext.request.contextPath}/friends/delete?id=${account.id}">
                <button class="btn btn-danger btn-sm">Decline request</button>
            </a>
        </div>
    </div>
</c:forEach>
<!-- new value of lastId -->
<div id="new-last-id" data-last-id="${lastId}" style="display: none;"></div>