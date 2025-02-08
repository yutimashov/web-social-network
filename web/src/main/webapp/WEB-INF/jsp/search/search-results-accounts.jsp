<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<div class="container-xl mt-4" id="accounts-result">
  <!-- hidden fields for storing lastAccountId for ajax-queries -->
  <input type="hidden" id="last-account-id" value="${lastAccountId}">
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
</div>
<!-- new value of lastAccountId -->
<div id="new-last-account-id" data-last-account-id="${lastAccountId}" style="display: none;"></div>