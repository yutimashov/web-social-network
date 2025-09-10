<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Group members</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl mt-4">
    <p>There are ${requestScope.groupAdmins.size()} admins of the group</p>
    <p>Admins:</p>
    <c:forEach items="${requestScope.groupAdmins}" var="admin">
        <a href="${rootUrl}/account?id=${admin.id}">${admin.firstName}
                ${admin.lastName}</a>&nbsp;&nbsp;<span style="color: yellow;background-color: black">admin</span><br>
    </c:forEach>
    <hr>
    <p>There are ${requestScope.groupMembers.size()} regular members of the group</p>
    <c:if test="${requestScope.groupMembers.size() != 0}">
        <p>Regular members:</p>
    </c:if>
    <c:forEach items="${requestScope.groupMembers}" var="groupMember">
        <a href="${rootUrl}/account?id=${groupMember.id}">${groupMember.firstName}
                ${groupMember.lastName}</a>&nbsp;&nbsp;
        <a href="${rootUrl}/group/delete-member?groupId=${param.id}&accountId=${groupMember.id}">
            <button>Delete member</button>
        </a>&nbsp;&nbsp;
        <a href="${rootUrl}/group/make-admin?groupId=${param.id}&accountId=${groupMember.id}">
            <button>Make admin</button>
        </a><br>
        <hr>
    </c:forEach>
</div>
</body>
</html>
