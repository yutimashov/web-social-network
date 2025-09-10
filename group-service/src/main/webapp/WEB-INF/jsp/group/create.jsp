<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="rootUrl" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Group creation</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl mt-4">
    <h2>New group creation</h2>
    <p>Enter necessary data:</p>
    <form action="${rootUrl}/group/create" method="POST" enctype="multipart/form-data">
        <hr>
        <label for="name">Group name<span style="color:red;">*</span> :
            <input type="text" name="name" id="name" required>
        </label>
        <br>
        <hr>
        <label for="avatar">Group avatar:
            <input type="file" name="avatar" id="avatar">
        </label>
        <br>
        <hr>
        <label for="description">Description:
            <input type="text" name="description" id="description">
        </label>
        <br>
        <hr>
        <button type="submit" class="btn btn-warning">Create</button>
    </form>
</div>
</body>
</html>
