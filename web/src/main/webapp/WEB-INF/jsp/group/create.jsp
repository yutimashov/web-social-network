<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Group creation</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container-xl mt-4">
    <h2>New group creation</h2>
    <p>Enter necessary data:</p>
    <form action="${pageContext.request.contextPath}/group/create" method="POST" enctype="multipart/form-data">
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
