<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Group members</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<p>There are ${requestScope.groupMembers.size()} regular (not admins) members of the group</p>
<c:forEach items="${requestScope.groupMembers}" var="groupMember">
    <a href="${pageContext.request.contextPath}/account?id=${groupMember.id}">${groupMember.firstName}
            ${groupMember.lastName}</a><br>
    <a href="${pageContext.request.contextPath}/group/delete-member?groupId=${param.id}&accountId=${groupMember.id}">
        <button>Delete member</button>
    </a><br>
    <a href="${pageContext.request.contextPath}/group/make-admin?groupId=${param.id}&accountId=${groupMember.id}">
        <button>Make admin</button>
    </a><br>
    <hr>
</c:forEach>
</body>
</html>
