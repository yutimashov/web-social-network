<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<c:forEach var="account" items="${requestScope.accounts}">
  <div class="row">
    <div class="col-md-2 col-sm-2">
      <c:if test="${not empty account.avatar}">
        <img src="${rootUrl}/account/avatar?id=${account.id}" alt="user" class="profile-photo-lg"
             width="100px" height="100px">
      </c:if>
      <c:if test="${empty account.avatar}">
        <img src="${rootUrl}/static/img/img-coming-soon-placeholder.png" alt="user"
             class="profile-photo-lg" width="100px" height="100px">
      </c:if>
    </div>
    <div class="col-md-10 col-sm-10">
      <h5><a href="${rootUrl}/account?id=${account.id}">${account.firstName} ${account.lastName}</a></h5>
    </div>
  </div>
</c:forEach>
<c:forEach var="group" items="${requestScope.groups}">
  <div class="row">
    <div class="col-md-2 col-sm-2">
      <c:if test="${not empty group.avatar}">
        <img src="${rootUrl}/avatar?id=${group.id}" alt="user" class="profile-photo-lg" width="100px"
             height="100px">
      </c:if>
      <c:if test="${empty group.avatar}">
        <img src="${rootUrl}/static/img/img-coming-soon-placeholder.png" alt="user"
             class="profile-photo-lg" width="100px" height="100px">
      </c:if>
    </div>
    <div class="col-md-10 col-sm-10">
      <h5><a href="${rootUrl}/group?id=${group.id}">${group.name}</a></h5>
    </div>
  </div>
</c:forEach>