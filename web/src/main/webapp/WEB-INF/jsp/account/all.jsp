<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Accounts</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl mt-4">
    <c:forEach items="${requestScope.accounts}" var="account">
        <div class="row">
            <div class="col-md-1 col-sm-2">
                <c:if test="${not empty account.avatar}">
                    <img src="${rootUrl}/account/avatar?id=${account.id}" alt="user" class="profile-photo-lg" width="50px"
                         height="50px">
                </c:if>
                <c:if test="${empty account.avatar}">
                    <img src="${rootUrl}/static/img/img-coming-soon-placeholder.png" alt="user"
                         class="profile-photo-lg" width="50px" height="50px">
                </c:if>
            </div>
            <div class="col-md-11 col-sm-10">
                <h5><a href="${rootUrl}/account?id=${account.id}">${account.firstName} ${account.lastName}</a></h5>
            </div>
        </div>
    </c:forEach>
</div>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
