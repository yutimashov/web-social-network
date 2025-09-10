<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Dialog</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body data-current-user-id="${accountSender.id}" data-receiver-user-id="${accountReceiverId}">
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<section>
    <div class="container py-5">
        <div class="row d-flex justify-content-center">
            <div class="col-md-10 col-lg-10 col-xl-10">
                <div class="card" id="chat1" style="border-radius: 15px;">
                    <div class="card-body">
                        <div class="messages">
                            <c:forEach items="${messages}" var="message">
                                <c:set var="senderId" value="${message.accountAuthorId}"/>
                                <c:set var="destinationId" value="${message.destinationId}"/>
                                <c:choose>
                                    <%-- sender is session account -> outcoming message --%>
                                    <c:when test="${message.destinationId ne accountSender.id}">
                                        <div class="d-flex flex-row justify-content-end mb-4">
                                            <a href="${rootUrl}/account?id=${senderId}">
                                                <img src="${rootUrl}/account/avatar?id=${senderId}" alt="Profile avatar"
                                                     style="width: 45px; border-radius: 50%;">
                                            </a>
                                            <div class="p-3 ms-3"
                                                 style="border-radius: 15px; background-color: rgba(57, 192, 237,.2);">
                                                <p class="small mb-0">${message.text}</p>
                                                <c:if test="${message.photo ne null}">
                                                    <div>
                                                        <img src="${rootUrl}/personal-message/image?id=${message.id}"
                                                             alt="Message photo" width="150px" height="150px">
                                                    </div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </c:when>
                                    <%-- incoming message --%>
                                    <c:otherwise>
                                        <div class="d-flex flex-row justify-content-start mb-4">
                                            <div class="p-3 me-3 border bg-body-tertiary" style="border-radius: 15px;">
                                                <p class="small mb-0">${message.text}</p>
                                                <c:if test="${message.photo ne null}">
                                                    <div>
                                                        <img src="${rootUrl}/personal-message/image?id=${message.id}"
                                                             alt="Message photo"
                                                             width="150px" height="150px">
                                                    </div>
                                                </c:if>
                                            </div>
                                            <a href="${rootUrl}/account?id=${senderId}">
                                                <img src="${rootUrl}/account/avatar?id=${senderId}" alt="Profile avatar"
                                                     style="width: 45px; border-radius: 50%;">
                                            </a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                        <%-- message input --%>
                        <div>
                            <textarea class="form-control bg-body-tertiary" id="text" name="text" rows="4"></textarea>
                            <label class="form-label" for="text"></label>
                            <input class="my-2" type="file" id="photo" name="photo" style="display:none;">
                            <div class="d-flex flex-row-reverse mb-2">
                                <label for="photo" class="input-file-upload">Add photo</label>
                            </div>
                            <div class="d-flex justify-content-end">
                                <button onclick="sendMessage()" class="btn btn-success btn-lg">Send message</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.1/sockjs.min.js"
        integrity="sha512-1QvjE7BtotQjkq8PxLeF6P46gEpBRXuskzIVgjFpekzFVF4yjRgrQvTG1MTOJ3yQgvTteKAcO7DSZI92+u/yZw=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"
        integrity="sha512-iKDtgDyTHjAitUDdLljGhenhPwrbBfqTKWO1mkhSFH3A7blITC9MhYon6SjnMhp4o0rADGw9yAC6EW4t5a4K3g=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="${pageContext.request.contextPath}/static/js/account/chat.js"></script>
</body>
</html>
