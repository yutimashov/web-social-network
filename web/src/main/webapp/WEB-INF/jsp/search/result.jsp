<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Search result</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl mt-4" id="search-result-container">
    <c:if test="${requestScope.accounts.size() != 0 or requestScope.groups.size() != 0}">
        <c:choose>
            <c:when test="${requestScope.searchType == 'account'}">
                <div id="search-results">
                    <jsp:include page="/WEB-INF/jsp/search/search-results-accounts.jsp"/>
                </div>
            </c:when>
            <c:otherwise>
                <div id="search-results">
                    <jsp:include page="/WEB-INF/jsp/search/search-results-groups.jsp"/>
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>
    <c:if test="${requestScope.searchResults.size() == 0}">
        <div class="alert alert-primary text-center" role="alert">
            No results found!
        </div>
    </c:if>
</div>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
<script src="${rootUrl}/static/js/ajax-search-accounts.js"></script>
<script src="${rootUrl}/static/js/ajax-search-groups.js"></script>
</body>
</html>
