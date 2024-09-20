<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Group creation</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<h2>New group creation</h2>
<p>Enter necessary data:</p>
<form action="${pageContext.request.contextPath}/group/create" method="POST" enctype="multipart/form-data">
    <hr>
    <label for="groupName">Group name<span style="color:red;">*</span> :
        <input type="text" name="groupName" id="groupName" required>
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
    <button type="submit">Create</button>
</form>
</body>
</html>
