<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <h2>Пользователь: ${sessionScope.account.firstName}</h2>
    <hr>
    <p>Имя: ${sessionScope.account.firstName}</p>
    <p>Фамилия: ${sessionScope.account.lastName}</p>
    <p>Отчество: ${sessionScope.account.middleName}</p>
    <p>Дата рождения: ${sessionScope.account.birthDate}</p>
    <p>Личный номер телефона:</p>
    <c:forEach var="phone" items="${sessionScope.account.personalPhoneNumber}">
        <p>${phone.number}</p>
    </c:forEach>
    <p>Рабочий номер телефона:</p>
    <c:forEach var="phone" items="${sessionScope.account.workPhoneNumber}">
        <p>${phone.number}</p>
    </c:forEach>
    <p>Домашний адрес: ${sessionScope.account.personalAddress}</p>
    <p>Email: ${sessionScope.account.email}</p>
    <p>ICQ: ${sessionScope.account.icq}</p>
    <p>Skype: ${sessionScope.account.skype}</p>
    <p>Прочая информация: ${sessionScope.account.additionalInfo}</p>
</div>
</body>
</html>
