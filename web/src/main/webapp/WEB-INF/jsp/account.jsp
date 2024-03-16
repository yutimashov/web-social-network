<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div>
    <hr>
    <p>Аватар пользователя:</p>
    <c:if test="${not empty requestScope.avatarInputStream}">
        <img src="${pageContext.request.contextPath}/avatar?id=${requestScope.account.id}" alt="Аватар пользователя">
    </c:if>
    <p>Имя: ${requestScope.account.firstName}</p>
    <p>Фамилия: ${requestScope.account.lastName}</p>
    <p>Отчество: ${requestScope.account.middleName}</p>
    <p>Дата рождения: ${requestScope.account.birthDate}</p>
    <p>Личный номер телефона:</p>
    <c:forEach var="phone" items="${requestScope.account.personalPhoneNumber}">
        <p>${phone.number}</p>
    </c:forEach>
    <p>Рабочий номер телефона:</p>
    <c:forEach var="phone" items="${requestScope.account.workPhoneNumber}">
        <p>${phone.number}</p>
    </c:forEach>
    <p>Домашний адрес: ${requestScope.account.personalAddress}</p>
    <p>Email: ${requestScope.account.email}</p>
    <p>ICQ: ${requestScope.account.icq}</p>
    <p>Skype: ${requestScope.account.skype}</p>
    <p>Прочая информация: ${requestScope.account.additionalInfo}</p>
    <c:if test="${sessionScope.account.id == param.id}">
        <a href="${pageContext.request.contextPath}/edit-account?id=${param.id}">
            <button>Редактировать аккаунт</button>
        </a>
    </c:if>
</div>
</body>
</html>
