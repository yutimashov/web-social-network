<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<c:set var="account" value="${sessionScope.account}"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
<c:if test="${not empty account}">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-xl container-fluid">
            <a class="navbar-brand" href="${rootUrl}/account?id=${account.id}">My page</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="${rootUrl}/account/all">All accounts</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${rootUrl}/group/all">All groups</a>
                    </li>
                </ul>
                <ul class="navbar-nav mb-2 mb-lg-0 profile-menu">
                    <li class="nav-item align-middle">
                        <div style="display:inline;">
                            <form action="${rootUrl}/search" style="margin:0;">
                                <input type="hidden" name="currentPage" value="1">
                                <label for="searchType" class="text-light">Search for:</label>
                                <select class="form-select-sm" name="searchType" id="searchType">
                                    <option value="account" selected>Account</option>
                                    <option value="group">Group</option>
                                </select>
                                <label for="searchQuery" class="text-light">
                                    <input type="text" id="searchQuery" name="searchQuery">
                                </label>
                                <button type="submit" class="btn-sm btn-outline-secondary">Find</button>
                            </form>
                        </div>
                    </li>
                </ul>
                <ul class="navbar-nav ms-auto mb-2 mb-lg-0 profile-menu">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle bsb-dropdown-toggle-caret-disable" href="#" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            <c:choose>
                                <c:when test="${not empty requestScope.account.avatar}">
                                    <img src="${rootUrl}/avatar?id=${account.id}" width="50" height="50"
                                         class="rounded-circle"
                                         alt="${account.firstName} ${account.lastName}">
                                </c:when>
                                <c:otherwise>
                                    <img src="${rootUrl}/static/img/img-coming-soon-placeholder.png" width="50"
                                         height="50" class="rounded-circle"
                                         alt="${account.firstName} ${account.lastName}">
                                </c:otherwise>
                            </c:choose>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="${rootUrl}/account?id=${account.id}">
                                <i class="fas fa-sliders-h fa-fw"></i>My page</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="${rootUrl}/logout"><i
                                    class="fas fa-sign-out-alt fa-fw"></i> Log Out</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</c:if>
