<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Редактирование аккаунта</title>
</head>
<body>
<h2>Редактирование аккаунта: ${requestScope.account.firstName} ${requestScope.account.lastName}</h2>
<hr>
<form action="/account-edit" method="POST">
    <label for="name">Имя<br>
        Старое значение: <strong>${requestScope.account.firstName}</strong><br>
        Новое значение: <input type="text" name="name" id="name">
    </label>
    <hr>
    <label for="lastName">Фамилия<br>
        Старое значение: <strong>${requestScope.account.lastName}</strong><br>
        Новое значение: <input type="text" name="lastName" id="lastName">
    </label>
    <hr>
    <label for="middleName">Отчество<br>
        Старое значение: <strong>${requestScope.account.middleName}</strong><br>
        Новое значение: <input type="text" name="middleName" id="middleName">
    </label>
    <hr>
    <label for="birthDate">Дата рождения<br>
        Старое значение: <strong>${requestScope.account.birthDate}</strong><br>
        Новое значение: <input type="date" name="birthDate" id="birthDate">
    </label>
    <hr>
    <label for="skype">Skype<br>
        Старое значение: <strong>${requestScope.account.skype}</strong><br>
        Новое значение: <input type="text" name="skype" id="skype">
    </label>
    <hr>
    <label for="icq">ICQ<br>
        Старое значение: <strong>${requestScope.account.icq}</strong><br>
        Новое значение: <input type="text" name="icq" id="icq">
    </label>
    <hr>
    <label for="email">Email<br>
        Старое значение: <strong>${requestScope.account.email}</strong><br>
        Новое значение: <input type="email" id="email" name="email">
    </label>
    <hr>
    <label for="email">Пароль<br>
        Новое значение: <input type="password" id="password" name="password">
    </label>
    <hr>
    <button type="submit">Сохранить изменения</button>
</form>
</body>
</html>
