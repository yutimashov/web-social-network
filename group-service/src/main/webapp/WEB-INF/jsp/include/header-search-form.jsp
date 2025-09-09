<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<div style="display:inline;">
    <form action="${rootUrl}/search" style="margin:0;">
        <label for="searchType" class="text-light">Search for:</label>
        <select class="form-select-sm" name="searchType" id="searchType">
            <option value="account" selected>Account</option>
            <option value="group">Group</option>
        </select>
        <label for="searchQuery" class="text-light">
            <input type="text" id="searchQuery" name="searchQuery"
                   data-toggle="dropdown" placeholder="Search" aria-label="Search" minlength="4">
            <%-- dynamic search tips  --%>
            <div class="dropdown-menu dropdown-menu-right ml-4 scrollable-menu"
                 id="dropdown-container"></div>
        </label>
        <button type="submit" id="search-button" class="btn-sm btn-outline-secondary">Find</button>
    </form>
</div>
