<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<html>
<head>
    <title>Search result</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl mt-4">
    <p>Search results for query: <em>${requestScope.searchQuery}</em></p>
    <c:forEach var="account" items="${requestScope.accounts}">
        <div class="row">
            <div class="col-md-2 col-sm-2">
                <c:if test="${not empty account.avatar}">
                    <img src="${rootUrl}/account/avatar?id=${account.id}" alt="user" class="profile-photo-lg" width="100px"
                         height="100px">
                </c:if>
                <c:if test="${empty account.avatar}">
                    <img src="${rootUrl}/static/img/img-coming-soon-placeholder.png" alt="user"
                         class="profile-photo-lg" width="100px" height="100px">
                </c:if>
            </div>
            <div class="col-md-10 col-sm-10">
                <h5><a href="${rootUrl}/account?id=${account.id}">${account.firstName} ${account.lastName}</a></h5>
            </div>
        </div>
    </c:forEach>
    <c:forEach var="group" items="${requestScope.groups}">
        <div class="row">
            <div class="col-md-2 col-sm-2">
                <c:if test="${not empty group.avatar}">
                    <img src="${rootUrl}/avatar?id=${group.id}" alt="user" class="profile-photo-lg" width="100px"
                         height="100px">
                </c:if>
                <c:if test="${empty group.avatar}">
                    <img src="${rootUrl}/static/img/img-coming-soon-placeholder.png" alt="user"
                         class="profile-photo-lg" width="100px" height="100px">
                </c:if>
            </div>
            <div class="col-md-10 col-sm-10">
                <h5><a href="${rootUrl}/group?id=${group.id}">${group.groupName}</a></h5>
            </div>
        </div>
    </c:forEach>
    <section class="py-5">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <nav aria-label="BSB Pagination 1 Example">
                        <ul class="pagination bsb-pagination-1 pagination-lg justify-content-center">
                            <c:if test="${requestScope.currentPage != 1}">
                                <li class="page-item"><a class="page-link"
                                                         href="${rootUrl}/search?searchType=${requestScope.searchType}&searchQuery=${requestScope.searchQuery}&currentPage=${requestScope.currentPage - 1}">Previous</a>
                                </li>
                            </c:if>
                            <c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
                                <c:choose>
                                    <c:when test="${requestScope.currentPage eq i}">
                                        <li class="page-item"><a class="page-link" href="">${i}
                                            <span>(current)</span></a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="${rootUrl}/search?searchType=${requestScope.searchType}&searchQuery=${requestScope.searchQuery}&currentPage=${i}">
                                                    ${i}
                                            </a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:if test="${requestScope.currentPage lt requestScope.numberOfPages}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${rootUrl}/search?searchType=${requestScope.searchType}&searchQuery=${requestScope.searchQuery}&currentPage=${requestScope.currentPage + 1}">
                                        Next
                                    </a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </section>
</div>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
</body>
</html>
