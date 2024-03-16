<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.account != null}">
    <h2>Пользователь: ${sessionScope.account.firstName} ${sessionScope.account.lastName}</h2>
</c:if>
