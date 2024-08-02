<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="${rootUrl}/static/css/auth-form.css">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<h4>New account registration</h4>
<form action="${rootUrl}/register" method="POST" enctype="multipart/form-data" id="registerForm">
    <input type="hidden" id="personalPhones" name="personalPhones">
    <input type="hidden" id="workingPhones" name="workingPhones">
    <div class="mb-3">
        <div class="row g-2 align-items-center">
            <div class="col-auto">
                <label for="email" class="form-label">Email<span style="color:red;">*</span> :</label>
            </div>
            <div class="col-auto">
                <input type="email" name="email" id="email" class="form-control form-control-sm" required>
            </div>
        </div>
        <div class="row g-2 align-items-center">
            <div class="col-auto">
                <label for="password" class="form-label">Password<span style="color:red;">*</span> :</label>
            </div>
            <div class="col-auto">
                <input type="password" name="password" id="password" class="form-control form-control-sm" required>
            </div>
        </div>
        <div class="row g-2 align-items-center">
            <div class="col-auto">
                <label for="name" class="form-label">First name<span style="color:red;">*</span> :</label>
            </div>
            <div class="col-auto">
                <input type="text" name="name" id="name" class="form-control form-control-sm" required>
            </div>
        </div>
        <div class="row g-2 align-items-center">
            <div class="col-auto">
                <label for="lastName" class="form-label">Last name<span style="color:red;">*</span> :</label>
            </div>
            <div class="col-auto">
                <input type="text" name="lastName" id="lastName" class="form-control form-control-sm" required>
            </div>
        </div>
        <div class="row g-2 align-items-center">
            <div class="col-auto">
                <label for="middleName" class="form-label">Middle name:</label>
            </div>
            <div class="col-auto">
                <input type="text" name="middleName" id="middleName" class="form-control form-control-sm">
            </div>
        </div>
        <div class="row g-2 align-items-center">
            <div class="col-auto">
                <label for="avatar" class="form-label">Profile avatar:</label>
            </div>
            <div class="col-auto">
                <input type="file" name="avatar" id="avatar" class="form-control form-control-sm">
            </div>
        </div>
        <div class="row g-2 align-items-center">
            <div class="col-auto">
                <label for="skype" class="form-label">Skype:</label>
            </div>
            <div class="col-auto">
                <input type="text" name="skype" id="skype" class="form-control form-control-sm">
            </div>
        </div>
        <div class="row g-2 align-items-center">
            <div class="col-auto">
                <label for="icq" class="form-label">ICQ:</label>
            </div>
            <div class="col-auto">
                <input type="text" name="icq" id="icq" class="form-control form-control-sm">
            </div>
        </div>
        <div class="row g-2 align-items-center">
            <div class="col-auto">
                <label for="personalPhoneNumberInput" class="form-label">Personal phone number:</label>
            </div>
            <div class="col-auto">
                <div class="input-group mb-3" id="personalPhoneInputGroup">
                    <input type="tel" class="form-control" name="personalPhoneNumber" id="personalPhoneNumberInput">
                    <div class="input-group-append">
                        <button class="btn btn-success" id="validatePersonalNumberBtn" type="button">Add</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-text" id="personal-phone-numbers-generated-inputs">Add more personal phone numbers
            <button type="button" class="btn btn-primary btn-sm" id="addPersonalNumberBtn">&plus;</button>
        </div>
        <div class="personal-phone-container"></div>
        <div class="row g-2 align-items-center">
            <div class="col-auto">
                <label for="workingPhoneNumberInput" class="form-label">Working phone number:</label>
            </div>
            <div class="col-auto">
                <div class="input-group mb-3" id="workPhoneInputGroup">
                    <input type="tel" class="form-control" name="workPhoneNumber" id="workingPhoneNumberInput">
                    <div class="input-group-append">
                        <button class="btn btn-success" id="validateWorkingNumberBtn" type="button">Add</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-text" id="working-phone-numbers-generated-inputs">Add more working phone numbers
            <button type="button" class="btn btn-primary btn-sm" id="addWorkingNumberBtn">&plus;</button>
        </div>
        <div class="working-phone-container"></div>
        <button type="submit">Register</button>
    </div>
</form>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script src="${rootUrl}/static/js/register-account.js"></script>
</body>
</html>
