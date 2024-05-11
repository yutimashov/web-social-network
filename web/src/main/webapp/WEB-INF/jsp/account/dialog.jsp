<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Dialog</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div>
    <div>
        <p>Messages with:
            <a href="${pageContext.request.contextPath}/account?id=${account.id}">
                <img src="${pageContext.request.contextPath}/avatar?id=${requestScope.account.id}" alt="Profile avatar"
                     width="100px" height="100px">
                ${requestScope.account.firstName} ${requestScope.account.lastName}
            </a>
        </p>
        <hr>
    </div>
    <div>
        <form action="${pageContext.request.contextPath}/account/messages/create" method="POST"
              enctype="multipart/form-data">
            <input type="hidden" name="accountReceiverId" value="${param.id}">
            <label for="text">New message:</label><br>
            <textarea id="text" name="text" rows="10" cols="40"
                      placeholder="Enter post message"></textarea>
            <br><br>
            <label for="photo">Add message photo (optional):<br>
                <input type="file" id="photo" name="photo">
            </label>
            <br><br>
            <button type="submit">Send message</button>
        </form>
        <hr>
    </div>
    <div>
        <c:forEach items="${requestScope.messages}" var="message">
            <c:set var="senderId" value="${message.accountAuthorId}"/>
            <c:set var="destinationId" value="${message.destinationId}"/>
            <hr>
            <span>Created: ${message.creationDate}</span><br>
            <span>From:
                <a href="${rootUrl}/account?id=${senderId}">
                    <img src="${pageContext.request.contextPath}/avatar?id=${senderId}"
                         alt="Profile avatar"
                         width="50px" height="50px">
                        ${requestScope.accountService.getById(senderId).get().firstName}
                        ${requestScope.accountService.getById(senderId).get().lastName}
                </a>
            </span><br>
            <span>To:
                    <a href="${rootUrl}/account?id=${destinationId}">
                        <img src="${pageContext.request.contextPath}/avatar?id=${destinationId}"
                             alt="Profile avatar"
                             width="50px" height="50px">
                        ${requestScope.accountService.getById(destinationId).get().firstName}
                        ${requestScope.accountService.getById(destinationId).get().lastName}
                </a>
            </span><br>
            <span>Message:</span><br>
            <span>${message.text}</span>
            <c:if test="${message.photo ne null}">
                <div>
                    <img src="${pageContext.request.contextPath}/personal-message/image?id=${message.id}"
                         alt="Message photo"
                         width="150px" height="150px">
                </div>
            </c:if>
            <hr>
        </c:forEach>
    </div>
</div>
</body>
</html>
