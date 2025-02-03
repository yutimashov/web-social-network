<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<section class="py-5">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <nav>
                    <ul class="pagination pagination-lg justify-content-center">
                        <%-- Previous page link --%>
                        <li class="page-item" style="display:none">
                            <a class="page-link" href="#" id="prevPageLink">Previous</a>
                        </li>
                        <c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
                            <li class="page-item">
                                <a class="page-link" data-current-page="${i}"
                                   href="${rootUrl}/search_ajax_pages?searchType=${requestScope.searchType}&searchQuery=${requestScope.searchQuery}&currentPage=${i}">
                                        ${i}
                                </a>
                            </li>
                        </c:forEach>
                        <li class="page-item" style="display:none">
                            <a class="page-link" href="#" id="nextPageLink">Next</a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</section>