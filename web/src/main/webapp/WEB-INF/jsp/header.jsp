<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.account != null}">
    <div style="background-color: bisque">
        <form action="${pageContext.request.contextPath}/logout" method="POST">
            <button type="submit">Logout</button>
        </form>
        <h2>Account: ${sessionScope.account.firstName} ${sessionScope.account.lastName}</h2>
        <hr>
    </div>
</c:if>
