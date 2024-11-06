<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Group</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl mt-4">
    <h2>${requestScope.group.name}</h2>
    <c:if test="${not empty requestScope.group.avatar}">
        <img src="${rootUrl}/group/avatar?id=${requestScope.group.id}" alt="Group avatar" width="250px" height="250px">
    </c:if>
    <c:if test="${empty requestScope.group.avatar}">
        <img src="${rootUrl}/static/img/img-coming-soon-placeholder.png" alt="Group avatar placeholder">
    </c:if><br>
    <hr>
    <c:if test="${requestScope.isSubscriber ne true and requestScope.isMember ne true}">
        <a href="${rootUrl}/group/send-request?id=${requestScope.group.id}">
            <button type="button" class="btn btn-success">Send join request</button>
        </a>
    </c:if>
    <c:if test="${requestScope.isAdmin eq true}">
        <p>Welcome, admin!</p>
        <a href="${rootUrl}/group/requests?id=${requestScope.group.id}">
            <button type="button" class="btn btn-primary">Account requests</button>
        </a>&nbsp;&nbsp;
        <a href="${rootUrl}/group/members?id=${requestScope.group.id}">
            <button type="button" class="btn btn-secondary">Account members</button>
        </a>
        <hr>
    </c:if>
    <p>${requestScope.group.id}</p>
    <c:if test="${requestScope.isMember eq true}">
        <c:if test="${requestScope.isAdmin eq true}">
            <%-- group message --%>
            <div>
                <form action="${rootUrl}/group/message/create" method="POST" enctype="multipart/form-data">
                    <input type="hidden" name="groupId" value="${requestScope.group.id}">
                    <label for="text" class="form-label">New post:</label>
                    <textarea class="form-control" id="text" name="text" rows="3"
                              placeholder="Enter post message"></textarea>
                    <label for="photo" class="form-label">Add image (optional):</label>
                    <input class="form-control form-control-sm" name="photo" id="photo" type="file"/>
                    <button type="submit" class="btn btn-dark">Create post</button>
                </form>
                <hr>
            </div>
        </c:if>
        <div>
            <c:forEach items="${requestScope.groupPosts}" var="post">
                <hr>
                <span>Created: ${post.creationDate}</span><br>
                <c:set var="accountAuthor"
                       value="${requestScope.accountService.getById(post.accountAuthorId).get()}"/>
                <span>Author:
                <a href="${rootUrl}/account?id=${accountAuthor.id}">${accountAuthor.firstName} ${accountAuthor.lastName}
                </a>
            </span>
                <p>${post.text}</p>
                <c:if test="${post.photo ne null}">
                    <img src="${rootUrl}/group-message/image?id=${post.id}" alt="Message photo" width="250px"
                         height="250px">
                </c:if>
                <hr>
            </c:forEach>
        </div>
    </c:if>
</div>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
</body>
</html>
