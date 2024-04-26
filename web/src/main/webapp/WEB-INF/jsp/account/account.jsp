<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<c:set var="sessionAccountId" value="${sessionScope.account.id}"/>
<c:set var="pageAccountId" value="${param.id}"/>
<html>
<head>
    <title>Account page</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div>
    <h4>Friends</h4>
    <a href="${rootUrl}/friends?id=${pageAccountId}">Friends list</a><br>
    <c:if test="${sessionAccountId eq pageAccountId}">
        <a href="${rootUrl}/friends/requests">Friend requests</a><br>
    </c:if>
    <c:if test="${sessionAccountId ne pageAccountId and empty(requestScope.alreadySentFriendRequest)}">
        <a href="${rootUrl}/friends/send-request?id=${pageAccountId}">
            <button>Send friend request</button>
        </a>
    </c:if>
    <hr>
</div>
<c:if test="${sessionAccountId eq pageAccountId}">
    <div>
        <h4>Messages</h4>
        <a href="${rootUrl}/account/messages?id=${pageAccountId}">All messages</a><br>
        <hr>
    </div>
    <div>
        <h4>Groups</h4>

        <a href="${rootUrl}/group/create">
            <button>Create group</button>
        </a>
        <hr>
    </div>
</c:if>
<c:if test="${sessionAccountId ne pageAccountId}">
    <div>
        <h4>Messages</h4>
        <a href="${rootUrl}account/messages/dialog?id=${pageAccountId}">Send message</a>
        <hr>
    </div>
</c:if>
<div>
    <c:if test="${requestScope.avatarInputStream ne null}">
        <img src="${rootUrl}/avatar?id=${pageAccountId}" alt="Profile avatar" width="100px" height="100px">
    </c:if><br>
    <span>First name: ${requestScope.account.firstName}</span><br>
    <span>Last name: ${requestScope.account.lastName}</span><br>
    <span>Middle name: ${requestScope.account.middleName}</span><br>
    <span>Birthdate: ${requestScope.account.birthDate}</span><br>
    <span>Personal phones:</span><br>
    <c:forEach var="phone" items="${requestScope.account.personalPhoneNumber}">
        &nbsp;&nbsp;<span>${phone.number}</span><br>
    </c:forEach>
    <span>Working phones:</span><br>
    <c:forEach var="phone" items="${requestScope.account.workPhoneNumber}">
        &nbsp;&nbsp;<span>${phone.number}</span><br>
    </c:forEach>
    <span>Personal address: ${requestScope.account.personalAddress}</span><br>
    <span>Email: ${requestScope.account.email}</span><br>
    <span>ICQ: ${requestScope.account.icq}</span><br>
    <span>Skype: ${requestScope.account.skype}</span><br>
    <span>Other information: ${requestScope.account.additionalInfo}</span><br>
    <c:if test="${sessionAccountId eq param.id or sessionScope.account.role eq 'ADMIN'}">
        <a href="${rootUrl}/account/edit?id=${pageAccountId}">
            <button>Edit account</button>
        </a>&nbsp;&nbsp;
        <a href="${rootUrl}/account/delete?id=${pageAccountId}">
            <button>Delete account</button>
        </a>&nbsp;&nbsp;
    </c:if>
    <c:if test="${sessionScope.account.role eq 'ADMIN' and requestScope.account.role eq 'REGULAR'}">
        <a href="${rootUrl}/make-admin?id=${pageAccountId}">
            <button>Make admin</button>
        </a><br>
    </c:if>
</div>
<c:if test="${sessionAccountId eq pageAccountId}">
    <div>
        <form action="${rootUrl}/account-wall/message/create" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="accountReceiverId" value="${pageAccountId}">
            <label for="text">New post:</label><br>
            <textarea id="text" name="text" rows="10" cols="40" placeholder="Enter post message"></textarea>
            <br><br>
            <label for="photo">Add post photo (optional):<br><input type="file" id="photo" name="photo"></label>
            <br><br>
            <button type="submit">Create post</button>
        </form>
        <hr>
    </div>
</c:if>
<div>
    <c:forEach items="${requestScope.wallPosts}" var="post">
        <hr>
        <span>Created: ${post.creationDate}</span><br>
        <c:set var="accountAuthor"
               value="${requestScope.accountService.getAccountById(post.accountAuthorId).get()}"/>
        <p>${post.text}</p>
        <c:if test="${post.photo ne null}">
            <img src="${rootUrl}/account-wall/image?id=${post.id}" alt="Message photo" width="150px" height="150px">
        </c:if>
        <hr>
    </c:forEach>
</div>
</body>
</html>
