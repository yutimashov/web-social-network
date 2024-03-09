<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<h1>Регистрация нового аккаунта</h1>
<p>Введите данные для регистрации</p>
<form action="${pageContext.request.contextPath}/register" method="POST" enctype="multipart/form-data">
    <label for="name">Имя:
        <input type="text" name="name" id="name">
    </label>
    <br>
    <label for="lastName">Фамилия:
        <input type="text" name="lastName" id="lastName">
    </label>
    <br>
    <label for="middleName">Отчество:
        <input type="text" name="middleName" id="middleName">
    </label>
    <br>
    <label for="birthDate">Дата рождения:
        <input type="date" name="birthDate" id="birthDate">
    </label>
    <br>
    <label for="avatar">Фото профиля:
        <input type="file" name="avatar" id="avatar">
    </label>
    <br>
    <label for="email">Email* :
        <input type="email" name="email" id="email" required>
    </label>
    <br>
    <label for="skype">Skype:
        <input type="text" name="skype" id="skype">
    </label>
    <br>
    <label for="icq">ICQ:
        <input type="text" name="icq" id="icq">
    </label>
    <br>
    <label for="personalPhoneNumber">Телефон домашний:
        <input type="tel" name="personalPhoneNumber" id="personalPhoneNumber">
    </label>
    <br>
    <label for="workPhoneNumber">Телефон рабочий:
        <input type="tel" name="workPhoneNumber" id="workPhoneNumber">
    </label>
    <br>
    <label for="password">Пароль* :
        <input type="password" name="password" id="password" required>
    </label>
    <br>
    <button type="submit">Зарегистрироваться</button>
</form>
</body>
</html>
