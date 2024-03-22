<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Account page</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div>
    <a href="${pageContext.request.contextPath}/account-friends?id=${param.id}">
        <button>Friends list</button>
    </a>
    <p>Profile avatar:</p>
    <c:if test="${requestScope.avatarInputStream != null}">
        <img src="${pageContext.request.contextPath}/avatar?id=${requestScope.account.id}" alt="Profile avatar"
        width="250px" height="250px">
    </c:if>
    <hr>
    <p>First name: ${requestScope.account.firstName}</p>
    <hr>
    <p>Last name: ${requestScope.account.lastName}</p>
    <hr>
    <p>Middle name: ${requestScope.account.middleName}</p>
    <hr>
    <p>Birthdate: ${requestScope.account.birthDate}</p>
    <hr>
    <p>Private phones:</p>
    <c:forEach var="phone" items="${requestScope.account.personalPhoneNumber}">
        <p>${phone.number}</p>
    </c:forEach>
    <hr>
    <p>Working phones:</p>
    <c:forEach var="phone" items="${requestScope.account.workPhoneNumber}">
        <p>${phone.number}</p>
    </c:forEach>
    <hr>
    <p>Private address: ${requestScope.account.personalAddress}</p>
    <hr>
    <p>Email: ${requestScope.account.email}</p>
    <hr>
    <p>ICQ: ${requestScope.account.icq}</p>
    <hr>
    <p>Skype: ${requestScope.account.skype}</p>
    <hr>
    <p>Other information: ${requestScope.account.additionalInfo}</p>
    <hr>
    <c:if test="${sessionScope.account.id == param.id || sessionScope.account.role == 'ADMIN'}">
        <a href="${pageContext.request.contextPath}/edit-account?id=${param.id}">
            <button>Edit account</button>
        </a>
        <br>
        <a href="${pageContext.request.contextPath}/delete-account?id=${param.id}">
            <button>Delete account</button>
        </a>
    </c:if>
    <c:if test="${sessionScope.account.role == 'ADMIN' && requestScope.account.role == 'REGULAR'}">
        <a href="${pageContext.request.contextPath}/make-admin?id=${param.id}">
            <button>Make admin</button>
        </a>
    </c:if>
</div>
</body>
</html>
