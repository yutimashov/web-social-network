<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:forEach items="${requestScope.friends}" var="friend">
  <div class="row">
    <div class="col-md-2 col-sm-2">
      <c:if test="${not empty friend.avatar}">
        <a href="${pageContext.request.contextPath}/account?id=${friend.id}">
          <img src="${rootUrl}/account/avatar?id=${friend.id}" alt="user" class="profile-photo-lg"
               width="100px"
               height="100px">
        </a>
      </c:if>
      <c:if test="${empty friend.avatar}">
        <a href="${pageContext.request.contextPath}/account?id=${friend.id}">
          <img src="${rootUrl}/static/img/img-coming-soon-placeholder.png" alt="user"
               class="profile-photo-lg" width="100px" height="100px">
        </a>
      </c:if>
    </div>
    <div class="col-md-10 col-sm-10">
      <h5>
        <a href="${pageContext.request.contextPath}/account?id=${friend.id}">${friend.firstName} ${friend.lastName}</a>
      </h5>
      <a href="${pageContext.request.contextPath}/friends/delete?id=${friend.id}"
         class="btn btn-danger"
         role="button">Delete friend</a>
    </div>
  </div>
</c:forEach>