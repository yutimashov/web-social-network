<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<section class="py-5">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <nav>
                    <ul class="pagination pagination-lg justify-content-center">
                        <c:if test="${requestScope.currentPage != 1}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${rootUrl}/search_ajax_pages?searchType=${requestScope.searchType}&searchQuery=${requestScope.searchQuery}&currentPage=${requestScope.currentPage - 1}">
                                    Previous</a>
                            </li>
                        </c:if>
                        <c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
                            <c:choose>
                                <c:when test="${requestScope.currentPage eq i}">
                                    <li class="page-item"><a class="page-link disabled current-search-link" href="#" data-current-page="${i}">${i}</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="${rootUrl}/search_ajax_pages?searchType=${requestScope.searchType}&searchQuery=${requestScope.searchQuery}&currentPage=${i}"
                                           data-current-page="${i}">
                                                ${i}
                                        </a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${requestScope.currentPage lt requestScope.numberOfPages}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${rootUrl}/search_ajax_pages?searchType=${requestScope.searchType}&searchQuery=${requestScope.searchQuery}&currentPage=${requestScope.currentPage + 1}">
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