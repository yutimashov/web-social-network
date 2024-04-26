<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${!empty(sessionScope) && sessionScope.account != null}">
    <div style="background-color: bisque">
        <h4 style="display:inline;">Account: ${sessionScope.account.firstName} ${sessionScope.account.lastName}</h4>
        <a href="${pageContext.request.contextPath}/account?id=${sessionScope.account.id}">My page</a>&nbsp;&nbsp;
        <a href="${pageContext.request.contextPath}/account/all">All accounts</a>&nbsp;
        <a href="${pageContext.request.contextPath}/logout">Log out</a>
        <hr>
    </div>
</c:if>
