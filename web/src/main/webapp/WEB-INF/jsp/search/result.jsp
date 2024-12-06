<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <div id="search-results" data-page-result-amount="${requestScope.numberOfPages}">
        <jsp:include page="/WEB-INF/jsp/search/search-results.jsp"/>
    </div>
    <jsp:include page="/WEB-INF/jsp/search/pagination.jsp"/>
</div>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp"/>
<script src="${rootUrl}/static/js/ajax-search-pages.js"></script>
</body>
</html>
