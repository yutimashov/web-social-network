<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Edit account</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<h2>Edit account: ${requestScope.account.firstName} ${requestScope.account.lastName}</h2>
<hr>
<form action="${pageContext.request.contextPath}/account/edit?id=${requestScope.account.id}" method="POST"
      enctype="multipart/form-data" id="editAccountForm">
    <label for="avatar">Current avatar:<br>
        <c:if test="${requestScope.avatarInputStream != null}">
            <img src="${rootUrl}/avatar?id=${requestScope.account.id}" alt="Profile avatar"
                 width="250px" height="250px">
        </c:if>
        <br>
        New avatar: <input type="file" name="avatar" id="avatar">
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
    <label>Personal phones<br>
        <input type="hidden" id="personalPhoneValue" name="personalPhoneValue">
        <input type="hidden" id="personalPhoneId" name="personalPhoneId">
        <c:forEach items="${requestScope.personalPhones}" var="phone">
            <span>&nbsp;&nbsp;Current:&nbsp;&nbsp;${phone.number}&nbsp;&nbsp;
                New:&nbsp;&nbsp;<input type="tel" name="personalPhoneValue" placeholder="Enter new phone number" data-personal-phone-id="${phone.id}">
                <button class="btn btn-success btn-sm validate-phone-btn" type="button">Change</button>
            </span><br>
        </c:forEach>
        <br>
    </label>
    <hr>
    <label>Working phones<br>
        <input type="hidden" id="workingPhoneValue" name="workingPhoneValue">
        <input type="hidden" id="workingPhoneId" name="workingPhoneId">
        <c:forEach items="${requestScope.workingPhones}" var="phone">
            <span>&nbsp;&nbsp;Current:&nbsp;&nbsp;${phone.number}&nbsp;&nbsp;
                New:&nbsp;&nbsp;<input type="tel" name="workingPhoneValue" placeholder="Enter new phone number" data-working-phone-id="${phone.id}">
                <button class="btn btn-success btn-sm validate-phone-btn" type="button">Change</button>
            </span><br>
        </c:forEach>
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
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editAccountModal">
        Apply changes
    </button>
    <!-- Modal -->
    <div class="modal fade" id="editAccountModal" tabindex="-1" aria-labelledby="editAccountModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="editAccountModalLabel">Edit account</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>You are going to edit account data. Do you really want to apply changes?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Dismiss</button>
                    <button type="submit" class="btn btn-primary">Apply</button>
                </div>
            </div>
        </div>
    </div>
</form>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script src="${rootUrl}/static/js/edit-account.js"></script>
</body>
</html>
