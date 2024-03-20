<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit account</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<h2>Edit account: ${requestScope.account.firstName} ${requestScope.account.lastName}</h2>
<hr>
<form action="${pageContext.request.contextPath}/edit-account?id=${requestScope.account.id}" method="POST"
      enctype="multipart/form-data">
    <label for="avatar">Profile avatar<br>
        Current account avatar:
        <c:if test="${requestScope.avatarInputStream != null}">
            <img src="${pageContext.request.contextPath}/avatar?id=${requestScope.account.id}"
                 alt="Profile avatar"
                 width="250px"
                 height="250px"
            >
        </c:if>
        <br>
        New account avatar: <input type="file" name="avatar" id="avatar">
    </label>
    <hr>
    <label for="name">First name<br>
        Current: <strong>${requestScope.account.firstName}</strong><br>
        New: <input type="text" name="name" id="name">
    </label>
    <hr>
    <label for="lastName">Last name<br>
        Current: <strong>${requestScope.account.lastName}</strong><br>
        New: <input type="text" name="lastName" id="lastName">
    </label>
    <hr>
    <label for="middleName">Middle name<br>
        Current: <strong>${requestScope.account.middleName}</strong><br>
        New: <input type="text" name="middleName" id="middleName">
    </label>
    <hr>
    <label for="birthDate">Birthdate<br>
        Current: <strong>${requestScope.account.birthDate}</strong><br>
        New: <input type="date" name="birthDate" id="birthDate">
    </label>
    <hr>
    <label for="skype">Skype<br>
        Current: <strong>${requestScope.account.skype}</strong><br>
        New: <input type="text" name="skype" id="skype">
    </label>
    <hr>
    <label for="icq">ICQ<br>
        Current: <strong>${requestScope.account.icq}</strong><br>
        New: <input type="text" name="icq" id="icq">
    </label>
    <hr>
    <label for="email">Email<br>
        Current: <strong>${requestScope.account.email}</strong><br>
        New: <input type="email" id="email" name="email">
    </label>
    <hr>
    <label for="email">Password<br>
        New: <input type="password" id="password" name="password">
    </label>
    <hr>
    <button type="submit">Apply changes</button>
</form>
</body>
</html>
