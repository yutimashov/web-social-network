<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${!empty(sessionScope) && sessionScope.account != null}">
    <div style="background-color: bisque">
        <form style="display:inline;" action="${pageContext.request.contextPath}/logout" method="POST">
            <button type="submit">Logout</button>
        </form>&nbsp;&nbsp;
        <a href="${pageContext.request.contextPath}/account?id=${sessionScope.account.id}">
            <button>My page</button>
        </a>&nbsp;&nbsp;
        <a href="${pageContext.request.contextPath}/account/all">
            <button>All accounts</button>
        </a>
        <h4>Account: ${sessionScope.account.firstName} ${sessionScope.account.lastName}</h4>
        <hr>
    </div>
</c:if>
