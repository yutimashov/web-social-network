<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${userName != null}">
    <h1>Имя пользователя: ${userName}</h1>
</c:if>
