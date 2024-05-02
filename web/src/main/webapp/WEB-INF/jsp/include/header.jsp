<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<c:set var="account" value="${sessionScope.account}"/>
<c:if test="${account ne null}">
    <div style="background-color: bisque">
        <h4 style="display:inline;">Account: ${account.firstName} ${account.lastName}</h4>
        <a href="${rootUrl}/account?id=${account.id}">My page</a>&nbsp;&nbsp;
        <a href="${rootUrl}/account/all">All accounts</a>&nbsp;&nbsp;
        <a href="${rootUrl}/group/all">All groups</a>&nbsp;&nbsp;
        <a href="${rootUrl}/logout">Log out</a>
        <hr>
    </div>
    <div style="background-color: #efde59">
        <form action="${rootUrl}/register" method="POST">
            <label for="search">
                Search:&nbsp;&nbsp;&nbsp;<input type="text" id="search" name="search">
            </label>
        </form>
        <hr>
    </div>
</c:if>
