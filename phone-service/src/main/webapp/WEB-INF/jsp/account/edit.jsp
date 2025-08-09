<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<c:set var="sessionAccountId" value="${sessionScope.account.id}"/>
<c:set var="account" value="${requestScope.account}"/>
<html>
<head>
    <title>Edit account</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl px-4 mt-4">
    <div class="row">
        <div class="col-xl-4">
            <!-- Account picture -->
            <div class="card mb-4 mb-xl-0">
                <div class="card-header">${account.firstName} ${account.lastName}</div>
                <div class="card-body text-center">
                    <c:if test="${not empty requestScope.avatarInputStream}">
                        <img src="${rootUrl}/account/avatar?id=${account.id}" alt="Account avatar"
                             width="250px" height="250px">
                    </c:if>
                    <c:if test="${empty requestScope.avatarInputStream}">
                        <img src="${rootUrl}/static/img/img-coming-soon-placeholder.png" alt="Profile avatar">
                    </c:if>
                    <div class="small font-italic text-muted mb-4">JPG or PNG no larger than 5 MB</div>
                    <label for="avatar" class="form-label">Upload new avatar</label>
                    <input form="editAccountForm" class="form-control form-control-sm" name="avatar" id="avatar"
                           type="file"/>
                    <!-- Download account info to xml file -->
                    <c:if test="${sessionAccountId eq param.id or sessionScope.account.role eq 'ADMIN'}">
                        <div align="left">
                            <a href="${rootUrl}/account/xml-download?id=${requestScope.account.id}"
                               class="btn btn-dark btn-sm mt-2">Download info as xml-file</a>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="col-xl-8">
            <!-- Account details card-->
            <div class="card mb-4">
                <div class="card-header">Account Details</div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/account/edit?id=${requestScope.account.id}"
                          method="POST"
                          enctype="multipart/form-data" id="editAccountForm">
                        <!-- Form Group (username)-->
                        <!-- Form Row first name and last name -->
                        <div class="row gx-3 mb-3">
                            <!-- Form Group (first name)-->
                            <div class="col-md-6">
                                <label class="small mb-1" for="firstName">First name</label>
                                <input class="form-control" id="firstName" name="firstName" type="text"
                                       placeholder="Enter your first name" value="${account.firstName}">
                            </div>
                            <!-- Form Group (last name)-->
                            <div class="col-md-6">
                                <label class="small mb-1" for="lastName">Last name</label>
                                <input class="form-control" id="lastName" name="lastName" type="text"
                                       placeholder="Enter your last name" value="${account.lastName}">
                            </div>
                        </div>
                        <!-- Form Row -->
                        <div class="row gx-3 mb-3">
                            <!-- Form Group (middle name)-->
                            <div class="col-md-6">
                                <label class="small mb-1" for="middleName">Middle name</label>
                                <input class="form-control" id="middleName" name="middleName" type="text"
                                       placeholder="Enter your middle name" value="${requestScope.account.middleName}">
                            </div>
                            <!-- Form Group (birthdate)-->
                            <div class="col-md-6">
                                <label class="small mb-1" for="birthDate">Birthdate</label>
                                <input class="form-control" id="birthDate" name="birthDate" type="date"
                                       placeholder="Enter your birthdate" value="${requestScope.account.birthDate}">
                            </div>
                        </div>
                        <!-- Form Row skype and icq -->
                        <div class="row gx-3 mb-3">
                            <!-- Form Group (skype)-->
                            <div class="col-md-6">
                                <label class="small mb-1" for="skype">Skype</label>
                                <input class="form-control" id="skype" name="skype" type="text"
                                       placeholder="Enter your skype" value="${requestScope.account.skype}">
                            </div>
                            <!-- Form Group (ICQ)-->
                            <div class="col-md-6">
                                <label class="small mb-1" for="icq">ICQ</label>
                                <input class="form-control" id="icq" name="icq" type="text"
                                       placeholder="Enter your ICQ" value="${requestScope.account.icq}">
                            </div>
                        </div>
                        <!-- Form Row email and address -->
                        <div class="row gx-3 mb-3">
                            <!-- Form Group (email)-->
                            <div class="col-md-6">
                                <label class="small mb-1" for="email">Email</label>
                                <input class="form-control" id="email" name="email" type="email"
                                       placeholder="Enter your email" value="${requestScope.account.email}">
                            </div>
                            <!-- Personal address-->
                            <div class="col-md-6">
                                <label class="small mb-1" for="address">Address</label>
                                <input class="form-control" id="address" name="address" type="text"
                                       placeholder="Enter new address" value="${requestScope.account.personalAddress}">
                            </div>
                        </div>
                        <%--<!-- Form Row phones -->
                        <div class="row gx-3 mb-2">
                            <input type="hidden" id="phoneData" name="phoneData">
                            <!-- Form Group (phone number)-->
                            <div class="col-md-6" id="personalPhones">
                                <label class="small mb-1">Personal phone numbers</label>
                                <div class="form-text" id="personal-phone-numbers-generated-inputs">Add personal
                                    phone
                                    <button type="button" class="btn btn-warning btn-sm add-phone-btn"
                                            data-add-phone-type="personal">
                                        &plus;
                                    </button>
                                </div>
                                <!-- Personal phones -->
                                <c:if test="${not empty requestScope.personalPhones}">
                                    <c:forEach items="${requestScope.personalPhones}" var="phone">
                                        <div class="row gx-2 mb-3">
                                            <div class="col-md-8">
                                                <input class="form-control phone-input" type="tel"
                                                       name="personalPhoneValue"
                                                       data-phone-type="personal"
                                                       placeholder="Enter new phone number"
                                                       data-personal-phone-id="${phone.id}" value="${phone.number}">
                                            </div>
                                            <div class="col-md-2">
                                                <button class="btn btn-success btn-sm change-phone-btn" type="button">
                                                    Change
                                                </button>
                                            </div>
                                            <div class="col-md-2">
                                                <button type="button" class="btn-close delete-phone-btn btn-sm"
                                                        aria-label="Close"></button>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </div>
                            <!-- Form Group (working phones)-->
                            <div class="col-md-6" id="workingPhones">
                                <label class="small mb-1">Working phone numbers</label>
                                <div class="form-text" id="personal-phone-numbers-generated-inputs">Add working
                                    phone
                                    <button type="button" class="btn btn-warning btn-sm add-phone-btn"
                                            data-add-phone-type="working">
                                        &plus;
                                    </button>
                                </div>
                                <!-- Working phones -->
                                <c:if test="${not empty requestScope.workingPhones}">
                                    <c:forEach items="${requestScope.workingPhones}" var="phone">
                                        <div class="row gx-2 mb-3">
                                            <div class="col-md-8">
                                                <input class="form-control phone-input" type="tel"
                                                       name="workingPhoneValue"
                                                       data-phone-type="working"
                                                       placeholder="Enter new phone number"
                                                       data-working-phone-id="${phone.id}" value="${phone.number}">
                                            </div>
                                            <div class="col-md-2">
                                                <button class="btn btn-success btn-sm change-phone-btn"
                                                        type="button">Change
                                                </button>
                                            </div>
                                            <div class="col-md-2">
                                                <button type="button" class="btn-close delete-phone-btn btn-sm"
                                                        aria-label="Close"></button>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </div>
                        </div>--%>
                        <!-- Save changes button-->
                        <button type="button" class="btn btn-warning" data-bs-toggle="modal"
                                data-bs-target="#editAccountModal">Save changes
                        </button>
                        <!-- Modal -->
                        <div class="modal fade" id="editAccountModal" tabindex="-1"
                             aria-labelledby="editAccountModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5" id="editAccountModalLabel">Edit account</h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <p>You are going to edit account data. Do you really want to apply changes?</p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary"
                                                data-bs-dismiss="modal">Dismiss
                                        </button>
                                        <button type="submit" class="btn btn-dark">Apply</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <hr class="hr"/>
                    <!-- edit using xml file -->
                    <c:if test="${sessionAccountId eq param.id or sessionScope.account.role eq 'ADMIN'}">
                        <!-- Update account through uploading xml file -->
                        <div class="row gx-3 mb-3">
                            <form action="${rootUrl}/account/xml-update?id=${requestScope.account.id}"
                                  method="POST" enctype="multipart/form-data" id="xmlFile">
                                <label for="xmlFile" class="form-label text-primary">Update account using
                                    xml-file</label>
                                <input form="xmlFile" class="form-control form-control-sm" name="file"
                                       id="file" type="file"/>
                                <button type="submit" class="btn btn-dark btn-sm mt-2">Update</button>
                            </form>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
<script src="${rootUrl}/static/js/account/edit.js"></script>
</body>
</html>
