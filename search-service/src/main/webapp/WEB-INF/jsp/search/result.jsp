<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <link rel="stylesheet" href="${rootUrl}/static/css/style.css">
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
<script src="${rootUrl}/static/js/search/accounts-ajax.js"></script>
<script src="${rootUrl}/static/js/search/groups-ajax.js"></script>
</body>
</html>
