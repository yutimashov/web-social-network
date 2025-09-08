<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<c:forEach items="${requestScope.outgoingFriendRequests}" var="account">
    <div class="row col-md-6 m-2">
        <div class="col-md-1 col-sm-2">
            <c:if test="${not empty account.avatar}">
                <img src="${rootUrl}/account/avatar?id=${account.id}" alt="user" class="profile-photo-lg"
                     width="50px"
                     height="50px">
            </c:if>
            <c:if test="${empty account.avatar}">
                <img src="${rootUrl}/static/img/img-coming-soon-placeholder.png" alt="user"
                     class="profile-photo-lg" width="50px" height="50px">
            </c:if>
        </div>
        <div class="col-md-4 col-sm-2">
            <a href="${rootUrl}/account?id=${account.id}">${account.firstName} ${account.lastName}</a>
        </div>
        <div class="col-md-3 col-sm-10">
            <a href="${rootUrl}/friends/delete?id=${account.id}">
                <button class="btn btn-danger btn-sm">Revoke request</button>
            </a>
        </div>
    </div>
</c:forEach>
<!-- new value of lastId -->
<div id="new-last-id" data-last-id="${lastId}" style="display: none;"></div>