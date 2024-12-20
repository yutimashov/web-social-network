<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<c:set var="sessionAccountId" value="${sessionScope.account.id}"/>
<c:set var="pageAccountId" value="${param.id}"/>
<html>
<head>
    <title>Account page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl mt-4" id="account-info">
    <div class="row">
        <!-- Account info -->
        <div class="col-xl-4">
            <div class="card mb-4 mb-xl-0">
                <!-- Account picture -->
                <div class="card-body text-center">
                    <c:choose>
                        <c:when test="${not empty requestScope.account.avatar}">
                            <img src="${rootUrl}/account/avatar?id=${pageAccountId}" alt="Profile avatar" width="250px"
                                 height="250px">
                        </c:when>
                        <c:otherwise>
                            <img src="${rootUrl}/static/img/img-coming-soon-placeholder.png" alt="Profile avatar">
                        </c:otherwise>
                    </c:choose>
                </div>
                <!-- Account data -->
                <div class="card mb-1">
                    <div class="card-header">${requestScope.account.firstName} ${requestScope.account.lastName}</div>
                    <c:if test="${sessionAccountId ne pageAccountId and empty(requestScope.alreadySentFriendRequest)}">
                        <a href="${rootUrl}/friends/send-request?id=${pageAccountId}" role="button"
                           class="btn btn-warning">Send friend request</a>
                    </c:if>
                    <c:if test="${sessionAccountId ne pageAccountId}">
                        <a href="${rootUrl}account/messages/dialog?id=${pageAccountId}" role="button"
                           class="btn btn-primary">Send message</a>
                    </c:if>
                    <div class="card-body">
                        <div>
                            <!-- Birthdate -->
                            <c:if test="${not empty requestScope.account.birthDate}">
                                <div class="mb-2">
                                    <p><i class="fa-solid fa-cake-candles"></i>&nbsp;${requestScope.account.birthDate}
                                    </p>
                                </div>
                            </c:if>
                            <!-- Personal address -->
                            <c:if test="${not empty requestScope.account.personalAddress}">
                                <div class="mb-2">
                                    <p><i class="fa-solid fa-house"></i>&nbsp;${requestScope.account.personalAddress}
                                    </p>
                                </div>
                            </c:if>
                            <!-- Email -->
                            <c:if test="${not empty requestScope.account.email}">
                                <div class="mb-2">
                                    <p><i class="fa-solid fa-envelope"></i>&nbsp;${requestScope.account.email}</p>
                                </div>
                            </c:if>
                            <!-- ICQ -->
                            <c:if test="${not empty requestScope.account.icq}">
                                <div class="mb-2">
                                    <p>ICQ: ${requestScope.account.icq}</p>
                                </div>
                            </c:if>
                            <!-- Skype -->
                            <c:if test="${not empty requestScope.account.skype}">
                                <div class="mb-2">
                                    <p><i class="fa-brands fa-skype"></i>&nbsp;${requestScope.account.skype}</p>
                                </div>
                            </c:if>
                            <!-- Additional info -->
                            <c:if test="${not empty requestScope.account.additionalInfo}">
                                <div class="mb-2">
                                    <p>
                                        <i class="fa-solid fa-circle-info"></i>&nbsp;${requestScope.account.additionalInfo}
                                    </p>
                                </div>
                            </c:if>
                        </div>
                        <!-- Phones -->
                        <div class="row gx-3">
                            <!-- Personal phones -->
                            <c:if test="${not empty requestScope.personalPhones}">
                                <div class="col-md-6">
                                    <p>Personal phones:</p>
                                    <c:forEach var="phone" items="${requestScope.personalPhones}">
                                        <p><i class="fa-solid fa-phone"></i>&nbsp;&nbsp;${phone}</p>
                                    </c:forEach>
                                </div>
                            </c:if>
                            <!-- Working phones -->
                            <c:if test="${not empty requestScope.workingPhones}">
                                <div class="col-md-6">
                                    <p>Working phones:</p>
                                    <c:forEach var="phone" items="${requestScope.workingPhones}">
                                        <p><i class="fa-solid fa-phone"></i>&nbsp;&nbsp;${phone}</p>
                                    </c:forEach>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
                <!-- Account's friends -->
                <div class="card mb-1">
                    <div class="card-header">Friends</div>
                    <div class="card-body row gx-3">
                        <div class="col-md-6">
                            <a class="link-underline-dark" href="${rootUrl}/friends?id=${pageAccountId}"><i
                                    class="fa-solid fa-user-group"></i>&nbsp;Friends</a>
                        </div>
                        <div class="col-md-6">
                            <c:if test="${sessionAccountId eq pageAccountId}">
                                <a class="link-underline-dark" href="${rootUrl}/friends/requests"><i
                                        class="fa-solid fa-bell"></i>&nbsp;Requests</a><br>
                            </c:if>
                        </div>
                    </div>
                </div>
                <!-- Account's messages -->
                <c:if test="${sessionAccountId eq pageAccountId}">
                    <div class="card mb-1">
                        <div class="card-header">Messages</div>
                        <div class="card-body row gx-3">
                            <a href="${rootUrl}/account/messages?id=${pageAccountId}"><i
                                    class="fa-solid fa-envelope"></i>&nbsp;My messages</a>
                        </div>
                    </div>
                    <div class="card mb-1">
                        <div class="card-header">Groups</div>
                        <div class="card-body row gx-3">
                            <a href="${rootUrl}/group/create"><i
                                    class="fa-solid fa-people-group"></i>&nbsp;Create group</a>
                        </div>
                    </div>
                </c:if>
                <!-- Account management buttons -->
                <div class="m-2">
<%--                    <c:if test="${sessionAccountId eq param.id or sessionScope.account.role eq 'ADMIN'}">--%>
                        <a href="${rootUrl}/account/edit?id=${pageAccountId}" class="btn btn-warning"
                           role="button">Edit account</a>&nbsp;&nbsp;
                        <a href="${rootUrl}/account/delete?id=${pageAccountId}" class="btn btn-danger"
                           role="button">Delete account</a>
<%--                    </c:if>--%>
                    <c:if test="${sessionScope.account.role eq 'ADMIN' and requestScope.account.role eq 'REGULAR'}">
                        <a href="${rootUrl}/account/make-admin?id=${pageAccountId}">
                            <button>Make admin</button>
                        </a><br>
                    </c:if>
                </div>
            </div>
        </div>
        <!-- account wall message -->
        <div class="col-xl-8">
            <c:if test="${sessionAccountId eq pageAccountId}">
                <div>
                    <form action="${rootUrl}/account-wall/message/create" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="accountReceiverId" value="${pageAccountId}">
                        <div class="mb-3">
                            <label for="text" class="form-label">New post:</label>
                            <textarea class="form-control" id="text" name="text" rows="3"
                                      placeholder="Enter post message"></textarea>
                            <label for="photo" class="form-label">Add image (optional):</label>
                            <input class="form-control form-control-sm" name="photo" id="photo" type="file"/>
                        </div>
                        <button type="submit" class="btn btn-warning">Create post</button>
                    </form>
                    <hr class="hr">
                </div>
            </c:if>
            <c:if test="${requestScope.wallPosts ne null}">
                <div>
                    <c:forEach items="${requestScope.wallPosts}" var="post">
                        <div class="account-wall-msg">
                            <hr>
                            <div style="display: flex; justify-content: space-between; align-items: center;">
                                <span>Created: ${post.creationDate}</span>
                                <button type="button" class="close btn-delete-account-wall-msg" aria-label="Close"
                                        style="background: none; border: none; cursor: pointer;">
                                    <span aria-hidden="true" style="font-size: 20px;">&times;</span>
                                </button>
                            </div>
                            <p>Author:
                                <a href="${rootUrl}/account?id=${post.accountAuthorId}">
                                        ${requestScope.accountService.getById(post.accountAuthorId).get().firstName}
                                        ${requestScope.accountService.getById(post.accountAuthorId).get().lastName}
                                </a>
                            </p>
                            <p>${post.text}</p>
                            <c:if test="${post.photo ne null}">
                                <img src="${rootUrl}/account-wall/image?id=${post.id}" alt="Message photo" width="150px"
                                     height="150px">
                            </c:if>
                            <hr>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
<script src="${rootUrl}/static/js/account.js"></script>
</body>
</html>
