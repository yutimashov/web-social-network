<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<section class="bg-light py-3 py-md-5 d-flex justify-content-center align-items-center min-vh-100">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-12 col-sm-10 col-md-8 col-lg-8 col-xl-8 col-xxl-8">
                <div class="card border border-light-subtle rounded-3 shadow-sm">
                    <div class="card-body p-3 p-md-4 p-xl-5">
                        <h2 class="fs-6 fw-normal text-center text-secondary mb-4">Sign up</h2>
                        <%-- Registration form --%>
                        <form action="${rootUrl}/register" method="POST" enctype="multipart/form-data"
                              id="registerForm">
                            <input type="hidden" id="personalPhones" name="personalPhones">
                            <input type="hidden" id="workingPhones" name="workingPhones">
                            <div class="row gy-2 overflow-hidden">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-floating mb-3">
                                            <input type="email" name="email" id="email" class="form-control" required>
                                            <label for="email" class="form-label">Email<span
                                                    style="color:red;">*</span></label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-floating mb-3">
                                            <input type="password" name="password" id="password" class="form-control"
                                                   required>
                                            <label for="password" class="form-label">Password<span
                                                    style="color:red;">*</span></label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4">
                                        <div class="form-floating mb-3">
                                            <input type="text" name="firstName" id="firstName" class="form-control"
                                                   required>
                                            <label for="firstName" class="form-label">First name<span
                                                    style="color:red;">*</span></label>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-floating mb-3">
                                            <input type="text" name="lastName" id="lastName" class="form-control"
                                                   required>
                                            <label for="lastName" class="form-label">Last name<span
                                                    style="color:red;">*</span></label>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-floating mb-3">
                                            <input type="text" name="middleName" id="middleName" class="form-control">
                                            <label for="middleName" class="form-label">Middle name</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-floating mb-3">
                                            <input type="text" name="skype" id="skype" class="form-control">
                                            <label for="skype" class="form-label">Skype</label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-floating mb-3">
                                            <input type="text" name="icq" id="icq" class="form-control">
                                            <label for="icq" class="form-label">ICQ</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="mb-3">
                                        <label for="avatar" class="form-label">Profile avatar</label>
                                        <input name="avatar" id="avatar" class="form-control" type="file"/>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-floating mb-3">
                                        <div class="col-auto">
                                            <label for="personalPhoneNumberInput" class="form-label">Personal phone
                                                number:</label>
                                        </div>
                                        <div class="col-auto">
                                            <div class="input-group mb-3" id="personalPhoneInputGroup">
                                                <input type="tel" class="form-control phone-input"
                                                       name="personalPhoneNumber"
                                                       id="personalPhoneNumberInput">
                                                <div class="input-group-append">
                                                    <button class="btn btn-success validate-phone-btn"
                                                            id="validatePersonalNumberBtn"
                                                            type="button">Add
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-text" id="personal-phone-numbers-generated-inputs">Add more
                                            personal phone numbers
                                            <button type="button" class="btn btn-primary btn-sm add-phone-btn"
                                                    id="addPersonalNumberBtn">&plus;
                                            </button>
                                        </div>
                                        <div class="personal-phone-container"></div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-floating mb-3">
                                        <div class="col-auto">
                                            <label for="workingPhoneNumberInput" class="form-label">Working phone
                                                number:</label>
                                        </div>
                                        <div class="col-auto">
                                            <div class="input-group mb-3" id="workPhoneInputGroup">
                                                <input type="tel" class="form-control phone-input"
                                                       name="workPhoneNumber"
                                                       id="workingPhoneNumberInput">
                                                <div class="input-group-append">
                                                    <button class="btn btn-success validate-phone-btn"
                                                            id="validateWorkingNumberBtn"
                                                            type="button">Add
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-text" id="working-phone-numbers-generated-inputs">Add more
                                            working phone numbers
                                            <button type="button" class="btn btn-primary btn-sm add-phone-btn"
                                                    id="addWorkingNumberBtn">&plus;
                                            </button>
                                        </div>
                                        <div class="personal-phone-container"></div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="d-grid my-3">
                                        <button class="btn btn-primary btn-lg" type="submit">Sign up</button>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <p class="m-0 text-secondary text-center">Already have account? <a
                                            href="${rootUrl}/login"
                                            class="link-primary text-decoration-none">Log in</a></p>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script src="${rootUrl}/static/js/register-account.js"></script>
</body>
</html>
