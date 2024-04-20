<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<h2>New account registration</h2>
<p>Enter necessary data:</p>
<form action="${pageContext.request.contextPath}/register" method="POST" enctype="multipart/form-data">
    <hr>
    <label for="email">Email<span style="color:red;">*</span> :
        <input type="email" name="email" id="email" required>
    </label>
    <br>
    <hr>
    <label for="password">Password<span style="color:red;">*</span> :
        <input type="password" name="password" id="password" required>
    </label>
    <br>
    <hr>
    <label for="name">First name<span style="color:red;">*</span> :
        <input type="text" name="name" id="name">
    </label>
    <br>
    <hr>
    <label for="lastName">Last name<span style="color:red;">*</span> :
        <input type="text" name="lastName" id="lastName">
    </label>
    <br>
    <hr>
    <label for="middleName">Middle name:
        <input type="text" name="middleName" id="middleName">
    </label>
    <br>
    <hr>
    <label for="avatar">Profile avatar:
        <input type="file" name="avatar" id="avatar">
    </label>
    <br>
    <hr>
    <label for="skype">Skype:
        <input type="text" name="skype" id="skype">
    </label>
    <br>
    <hr>
    <label for="icq">ICQ:
        <input type="text" name="icq" id="icq">
    </label>
    <br>
    <hr>
    <label for="personalPhoneNumber">Personal phone number (if there are several numbers - separate them with commas):
        <input type="tel" name="personalPhoneNumber" id="personalPhoneNumber">
    </label>
    <br>
    <hr>
    <label for="workPhoneNumber">Working phone number (if there are several numbers - separate them with commas):
        <input type="tel" name="workPhoneNumber" id="workPhoneNumber">
    </label>
    <br>
    <hr>
    <button type="submit">Register</button>
</form>
</body>
</html>
