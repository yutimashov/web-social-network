<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Login</title>
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/img/favicon.ico"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<section class="bg-light py-3 py-md-5 d-flex justify-content-center align-items-center min-vh-100">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-5 col-xxl-4">
                <div class="card border border-light-subtle rounded-3 shadow-sm">
                    <div class="card-body p-3 p-md-4 p-xl-5">
                        <h2 class="fs-6 fw-normal text-center text-secondary mb-4">Sign in to your account</h2>
                        <form action="${pageContext.request.contextPath}/login" method="POST">
                            <div class="mb-3">
                                <c:choose>
                                    <c:when test="${param.error eq 'auth_data'}">
                                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                            Incorrect email or password!
                                            <button type="button" class="btn-close" data-bs-dismiss="alert"
                                                    aria-label="Close"></button>
                                        </div>
                                    </c:when>
                                    <c:when test="${param.error eq 'authorization'}">
                                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                            You are not authorized!
                                            <button type="button" class="btn-close" data-bs-dismiss="alert"
                                                    aria-label="Close"></button>
                                        </div>
                                    </c:when>
                                    <c:when test="${param.error eq 'reg'}">
                                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                            Account has not been registered!
                                            <button type="button" class="btn-close" data-bs-dismiss="alert"
                                                    aria-label="Close"></button>
                                        </div>
                                    </c:when>
                                    <c:when test="${param.reg eq 'success'}">
                                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                                            Account has been registered!
                                            <button type="button" class="btn-close" data-bs-dismiss="alert"
                                                    aria-label="Close"></button>
                                        </div>
                                    </c:when>
                                    <c:when test="${param.delete_account eq 'success'}">
                                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                                            Account has been deleted!
                                            <button type="button" class="btn-close" data-bs-dismiss="alert"
                                                    aria-label="Close"></button>
                                        </div>
                                    </c:when>
                                </c:choose>
                            </div>
                            <div class="row gy-2 overflow-hidden">
                                <div class="col-12">
                                    <div class="form-floating mb-3">
                                        <input type="email" id="email" name="email" class="form-control"
                                               placeholder="name@example.com" required>
                                        <label for="email" class="form-label">Email</label>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-floating mb-3">
                                        <input type="password" id="password" name="password" class="form-control"
                                               required value="" placeholder="Password">
                                        <label for="password" class="form-label">Password</label>
                                    </div>
                                </div>
                                <div class="form-check" style="padding-left: 40px;">
                                    <input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe">
                                    <label class="form-check-label" for="rememberMe">Remember me</label>
                                </div>
                                <div class="col-12">
                                    <div class="d-grid my-3">
                                        <button class="btn btn-primary btn-lg" type="submit">Log in</button>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <p class="m-0 text-secondary text-center">Don't have an account? <a
                                            href="${rootUrl}/register"
                                            class="link-primary text-decoration-none">Sign
                                        up</a></p>
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
</body>
</html>
