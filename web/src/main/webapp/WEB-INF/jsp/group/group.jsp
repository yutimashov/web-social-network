<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Group</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<h2>${requestScope.group.groupName}</h2>
<p>${requestScope.group.description}</p>
<c:if test="${requestScope.avatarInputStream != null}">
    <img src="${baseUrl}/group-avatar?id=${requestScope.group.id}" alt="Group avatar"
         width="250px" height="250px">
</c:if>
<hr>
<c:if test="${requestScope.isSubscriber ne true && requestScope.isMember ne true}">
    <a href="${baseUrl}/group/send-request?id=${requestScope.group.id}">
        <button>Send join request</button>
    </a>
</c:if>
<c:if test="${requestScope.isAdmin ne true}">
    <p>Welcome, admin!</p>
    <a href="${baseUrl}/group/requests?id=${requestScope.group.id}">
        <button>Account requests</button>
    </a>&nbsp;&nbsp;
    <a href="${baseUrl}/group/members?id=${requestScope.group.id}">
        <button>Account members</button>
    </a>
    <hr>
</c:if>
<c:if test="${requestScope.isMember eq true}">
    <c:if test="${requestScope.isAdmin eq true}">
        <div>
            <form action="${baseUrl}/group/message/create" method="POST"
                  enctype="multipart/form-data">
                <input type="hidden" name="groupId" value="${requestScope.group.id}">
                <label for="text">New post:</label><br>
                <textarea id="text" name="text" rows="10" cols="40"
                          placeholder="Enter post message"></textarea>
                <br><br>
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
            <span>Created: ${post.creationDate}</span><br>
            <c:set var="accountAuthor"
                   value="${requestScope.accountService.getAccountById(post.accountAuthorId).get()}"/>
            <span>Author:
                <a href="${baseUrl}/account?id=${accountAuthor.id}">${accountAuthor.firstName} ${accountAuthor.lastName}
                </a>
            </span>
            <p>${post.text}</p>
            <c:if test="${!empty(post.photo)}">
                <img src="${baseUrl}/message/image?id=${post.id}"
                     alt="Message photo"
                     width="250px" height="250px">
            </c:if>
            <hr>
        </c:forEach>
    </div>
</c:if>
</body>
</html>
