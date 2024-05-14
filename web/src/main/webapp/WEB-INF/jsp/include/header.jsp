<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<c:set var="account" value="${sessionScope.account}"/>
<c:if test="${account ne null}">
    <div style="background-color: bisque">
        <h4 style="display:inline;">Account: ${account.firstName} ${account.lastName}</h4>
        <div style="display:inline;">
            <form action="${rootUrl}/search" style="margin:0;">
                <input type="hidden" name="currentPage" value="1">
                <label for="searchQuery">
                    Search:&nbsp;&nbsp;&nbsp;<input type="text" id="searchQuery" name="searchQuery">
                </label>
                <label for="searchType">Type of search:</label>
                <select name="searchType" id="searchType">
                    <option value="account">Account</option>
                    <option value="group">Group</option>
                </select>
                <button type="submit">Find</button>
            </form>
        </div>
        <div style="display:inline;">
            <a href="${rootUrl}/account?id=${account.id}">My page</a>&nbsp;&nbsp;
            <a href="${rootUrl}/account/all">All accounts</a>&nbsp;&nbsp;
            <a href="${rootUrl}/group/all">All groups</a>&nbsp;&nbsp;
            <a href="${rootUrl}/logout">Log out</a>
        </div>
        <hr>
    </div>
</c:if>
