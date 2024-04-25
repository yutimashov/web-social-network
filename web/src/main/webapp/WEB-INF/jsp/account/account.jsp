<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Account page</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div>
    <h4>Friends</h4>
    <a href="${pageContext.request.contextPath}/friends?id=${param.id}">Friends list</a><br>
    <c:if test="${sessionScope.account.id == param.id}">
        <a href="${pageContext.request.contextPath}/friends/requests">Friend requests</a><br>
    </c:if>
    <c:if test="${sessionScope.account.id != param.id && empty(requestScope.alreadySentFriendRequest)}">
        <a href="${pageContext.request.contextPath}/friends/send-request?id=${param.id}">
            <button>Send friend request</button>
        </a>
    </c:if>
    <hr>
</div>
<c:if test="${sessionScope.account.id == param.id}">
    <div>
        <h4>Messages</h4>
        <!-- переход на страницу со списком аккаунтов, с которыми есть диалогиы -->
        <!-- при переходе на аккаунт - диалог с этим пользователем -->
        <a href="${pageContext.request.contextPath}/account/messages?id=${param.id}">All messages</a><br>
        <hr>
    </div>
    <div>
        <h4>Groups</h4>
        <a href="${pageContext.request.contextPath}/group/all">All groups</a><br>
        <a href="${pageContext.request.contextPath}/group/create">
            <button>Create group</button>
        </a>
        <hr>
    </div>
</c:if>
<div>
    <c:if test="${requestScope.avatarInputStream != null}">
        <img src="${pageContext.request.contextPath}/avatar?id=${requestScope.account.id}" alt="Profile avatar"
             width="200px" height="200px">
    </c:if><br>
    <span>First name: ${requestScope.account.firstName}</span><br>
    <span>Last name: ${requestScope.account.lastName}</span><br>
    <span>Middle name: ${requestScope.account.middleName}</span><br>
    <span>Birthdate: ${requestScope.account.birthDate}</span><br>
    <span>Personal phones:</span><br>
    <c:forEach var="phone" items="${requestScope.account.personalPhoneNumber}">
        <span>${phone.number}</span><br>
    </c:forEach>
    <span>Working phones:</span><br>
    <c:forEach var="phone" items="${requestScope.account.workPhoneNumber}">
        <span>${phone.number}</span><br>
    </c:forEach>
    <span>Personal address: ${requestScope.account.personalAddress}</span><br>
    <span>Email: ${requestScope.account.email}</span><br>
    <span>ICQ: ${requestScope.account.icq}</span><br>
    <span>Skype: ${requestScope.account.skype}</span><br>
    <span>Other information: ${requestScope.account.additionalInfo}</span><br>
    <c:if test="${sessionScope.account.id eq param.id || sessionScope.account.role eq 'ADMIN'}">
        <a href="${pageContext.request.contextPath}/account/edit?id=${param.id}">
            <button>Edit account</button>
        </a>&nbsp;&nbsp;
        <a href="${pageContext.request.contextPath}/account/delete?id=${param.id}">
            <button>Delete account</button>
        </a>&nbsp;&nbsp;
    </c:if>
    <c:if test="${sessionScope.account.role eq 'ADMIN' && requestScope.account.role eq 'REGULAR'}">
        <a href="${pageContext.request.contextPath}/make-admin?id=${param.id}">
            <button>Make admin</button>
        </a><br>
    </c:if>
</div>
<c:if test="${sessionScope.account.id == param.id}">
    <div>
        <form action="${pageContext.request.contextPath}/account-wall/message/create" method="POST"
              enctype="multipart/form-data">
            <input type="hidden" name="accountReceiverId" value="${param.id}">
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
    <c:forEach items="${requestScope.wallPosts}" var="post">
        <hr>
        <span>Created: ${post.creationDate}</span><br>
        <c:set var="accountAuthor"
               value="${requestScope.accountService.getAccountById(post.accountAuthorId).get()}"/>
        <p>${post.text}</p>
        <c:if test="${!empty(post.photo)}">
            <img src="${pageContext.request.contextPath}/account-wall/image?id=${post.id}"
                 alt="Message photo"
                 width="250px" height="250px">
        </c:if>
        <hr>
    </c:forEach>
</div>
</body>
</html>
