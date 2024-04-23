<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Group</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<h2>Group: ${requestScope.group.groupName}</h2>
<p>Profile avatar:</p>
<c:if test="${requestScope.avatarInputStream != null}">
    <img src="${pageContext.request.contextPath}/group-avatar?id=${requestScope.group.id}" alt="Group avatar"
         width="250px" height="250px">
</c:if>
<hr>
<p>Description: ${requestScope.group.description}</p>
<hr>
<c:if test="${empty(requestScope.isSubscriber) && empty(requestScope.isMember)}">
    <a href="${pageContext.request.contextPath}/group/send-request?id=${requestScope.group.id}">
        <button>Send join request</button>
    </a>
</c:if>
<c:if test="${!empty(requestScope.isSubscriber)}">
    <span>Already subscribed!</span>
</c:if>
<c:if test="${!empty(requestScope.isAccountAdmin)}">
    <p>Welcome, admin!</p>
    <a href="${pageContext.request.contextPath}/group/requests?id=${requestScope.group.id}">
        <button>Account requests</button>
    </a>&nbsp;&nbsp;
    <a href="${pageContext.request.contextPath}/group/members?id=${requestScope.group.id}">
        <button>Account members</button>
    </a>
    <hr>
</c:if>
<c:if test="${!empty(requestScope.isMember)} && ${empty(requestScope.isAccountAdmin)}">
    <span>Welcome, group member!</span>
</c:if>
<c:if test="${!empty(requestScope.isMember)}">
    <div>
        <form action="${pageContext.request.contextPath}/message/create" method="POST"
              enctype="multipart/form-data">
            <label for="messageText">Create new post:</label><br>
            <textarea id="messageText" name="messageText" rows="5" cols="33"
                      placeholder="Enter post message"></textarea>
            <br>
            <label for="messageImage">Add post image (optional):<br>
                <input type="file" id="messageImage" name="messageImage">
            </label>
            <br><br>
            <button type="submit">Create post</button>
        </form>
        <hr>
    </div>
    <div>
        <c:forEach items="${requestScope.groupPosts}" var="post">
            <p>Author: <span>${post.accountAuthorId}</span></p>
            <p>Text: <span>${post.text}</span></p>
            <hr>
        </c:forEach>
    </div>
</c:if>
</body>
</html>
