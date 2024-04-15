<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Group</title>
</head>
<body>
<h2>Group: ${requestScope.group.groupName}</h2>
<p>Profile avatar:</p>
<c:if test="${requestScope.avatarInputStream != null}">
    <img src="${pageContext.request.contextPath}/group-avatar?id=${requestScope.group.id}" alt="Group avatar"
         width="250px" height="250px">
</c:if>
<hr>
<p>Description: ${requestScope.group.description}</p>
<hr>
<c:if test="${empty(requestScope.isSubscriber)} && ${empty(requestScope.isMember)}">
    <a href="${pageContext.request.contextPath}/group/send-request?id=${requestScope.group.id}">
        <button>Send join request</button>
    </a>
</c:if>
<c:if test="${!empty(requestScope.isSubscriber)}">
    <span>Already subscribed!</span>
</c:if>
<c:if test="${!empty(requestScope.isMember)}">
    <span>You are a group member!</span>
</c:if>
<hr>
<c:if test="${!empty(requestScope.isAccountAdmin)}">
    <a href="${pageContext.request.contextPath}/group/requests?id=${requestScope.group.id}">
        <button>Account requests</button>
    </a>
    <hr>
</c:if>
</body>
</html>
