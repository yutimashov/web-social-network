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
<c:if test="${requestScope.isSubscriber ne true && requestScope.isMember ne true}">
    <a href="${pageContext.request.contextPath}/group/send-request?id=${requestScope.group.id}">
        <button>Send join request</button>
    </a>
</c:if>
<c:if test="${requestScope.isAdmin ne true}">
    <p>Welcome, admin!</p>
    <a href="${pageContext.request.contextPath}/group/requests?id=${requestScope.group.id}">
        <button>Account requests</button>
    </a>&nbsp;&nbsp;
    <a href="${pageContext.request.contextPath}/group/members?id=${requestScope.group.id}">
        <button>Account members</button>
    </a>
    <hr>
</c:if>
<c:if test="${requestScope.isMember eq true}">
    <c:if test="${requestScope.isAdmin eq true}">
        <div>
            <form action="${pageContext.request.contextPath}/group/message/create" method="POST"
                  enctype="multipart/form-data">
                <input type="hidden" name="groupId" value="${requestScope.group.id}">
                <label for="text">Create new post:</label><br>
                <textarea id="text" name="text" rows="5" cols="33"
                          placeholder="Enter post message"></textarea>
                <br>
                <label for="photo">Add post photo (optional):<br>
                    <input type="file" id="photo" name="photo">
                </label>
                <br><br>
                <button type="submit">Create post</button>
            </form>
            <hr>
        </div>
    </c:if>
    <div>
        <c:forEach items="${requestScope.groupPosts}" var="post">
            <hr>
            <span>Created: ${post.creationDate}</span>
            <span>Author: ${requestScope.accountService.getAccountById(post.accountAuthorId).get().firstName}
                    ${requestScope.accountService.getAccountById(post.accountAuthorId).get().lastName}</span>
            <p>Text: ${post.text}</p>
            <c:if test="${!empty(post.photo)}">
                <img src="${pageContext.request.contextPath}/message/image?id=${post.id}"
                     alt="Message photo"
                     width="250px" height="250px">
            </c:if>
            <hr>
        </c:forEach>
    </div>
</c:if>
</body>
</html>
