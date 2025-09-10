<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<div class="container-xl mt-4" id="groups-result">
    <!-- hidden fields for storing lastFirstName and lastLastName for ajax-queries -->
    <input type="hidden" id="last-group-name" value="${lastGroupName}">
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
</div>
<!-- new value of firstName and lastName -->
<div id="new-last-groupName" data-last-firstname="${lastGroupName}" style="display: none;"></div>