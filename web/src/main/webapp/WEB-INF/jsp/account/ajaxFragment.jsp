<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:forEach items="${requestScope.accounts}" var="account">
  <div class="row">
    <div class="col-md-1 col-sm-2">
      <c:if test="${not empty account.avatar}">
        <img src="${rootUrl}/account/avatar?id=${account.id}" alt="user" class="profile-photo-lg" width="50px"
             height="50px">
      </c:if>
      <c:if test="${empty account.avatar}">
        <img src="${rootUrl}/static/img/img-coming-soon-placeholder.png" alt="user"
             class="profile-photo-lg" width="50px" height="50px">
      </c:if>
    </div>
    <div class="col-md-11 col-sm-10">
      <h5><a href="${rootUrl}/account?id=${account.id}">${account.firstName} ${account.lastName}</a></h5>
    </div>
  </div>
</c:forEach>