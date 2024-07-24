<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="${rootUrl}/css/styles.css">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<h4>New account registration</h4>
<form action="${rootUrl}/register" method="POST" enctype="multipart/form-data">
    <hr>
    <label for="email">Email<span style="color:red;">*</span> :
        <input type="email" name="email" id="email" required>
    </label><br>
    <label for="password">Password<span style="color:red;">*</span> :
        <input type="password" name="password" id="password" required>
    </label><br>
    <label for="name">First name<span style="color:red;">*</span> :
        <input type="text" name="name" id="name">
    </label><br>
    <label for="lastName">Last name<span style="color:red;">*</span> :
        <input type="text" name="lastName" id="lastName">
    </label><br>
    <label for="middleName">Middle name:
        <input type="text" name="middleName" id="middleName">
    </label><br>
    <label for="avatar">Profile avatar:
        <input type="file" name="avatar" id="avatar">
    </label><br>
    <label for="skype">Skype:
        <input type="text" name="skype" id="skype">
    </label><br>
    <label for="icq">ICQ:
        <input type="text" name="icq" id="icq">
    </label><br>
    <h3>Personal phone number</h3>
    <a href="#" class="add">&plus;</a>
    <label for="personalPhoneNumber">
        <div class="inp-group">
            <input type="tel" name="personalPhoneNumber" id="personalPhoneNumber">
        </div>
    </label><br>
    <label for="workPhoneNumber">Working phone number:
        <input type="tel" name="workPhoneNumber" id="workPhoneNumber">
    </label><br>
    <button type="submit">Register</button>
</form>
<script src="${rootUrl}/js/index.js"></script>
</body>
</html>
