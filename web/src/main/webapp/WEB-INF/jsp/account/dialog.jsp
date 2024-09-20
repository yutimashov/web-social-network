<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Dialog</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<section>
    <div class="container py-5">
        <div class="row d-flex justify-content-center">
            <div class="col-md-10 col-lg-10 col-xl-10">
                <div class="card" id="chat1" style="border-radius: 15px;">
                    <div class="card-body">
                        <c:forEach items="${requestScope.messages}" var="message">
                            <c:set var="senderId" value="${message.accountAuthorId}"/>
                            <c:set var="destinationId" value="${message.destinationId}"/>
                            <c:choose>
                                <%-- sender is session account -> outcoming message --%>
                                <c:when test="${senderId eq sessionScope.account.id}">
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
                                                         alt="Message photo"
                                                         width="150px" height="150px">
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
                        <div>
                            <form action="${pageContext.request.contextPath}/account/messages/create" method="POST"
                                  enctype="multipart/form-data">
                                <input type="hidden" name="accountReceiverId" value="${param.id}">
                                <textarea class="form-control bg-body-tertiary" id="text" name="text"
                                          rows="4"></textarea>
                                <label class="form-label" for="text"></label>
                                <input class="my-2" type="file" id="photo" name="photo" style="display:none;">
                                <div class="d-flex flex-row-reverse mb-2">
                                    <label for="photo" class="input-file-upload">Add photo</label>
                                </div>
                                <div class="d-flex justify-content-end">
                                    <button type="submit" class="btn btn-success btn-lg">Send message</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
