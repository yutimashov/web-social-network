<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Dialog</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div>
    <div>
        <p>Messages with: ${requestScope.account.firstName} ${requestScope.account.lastName}</p>
        <img src="${pageContext.request.contextPath}/avatar?id=${requestScope.account.id}" alt="Profile avatar"
             width="100px" height="100px">
        <hr>
    </div>
    <div>
        <form action="${pageContext.request.contextPath}/account/messages/create?id=${requestScope.account.id}"
              method="POST"
              enctype="multipart/form-data">
            <input type="hidden" name="accountReceiverId" value="${requestScope.account.id}">
            <label for="text">New message:</label><br>
            <textarea id="text" name="text" rows="5" cols="30"
                      placeholder="Enter message"></textarea>
            <br><br>
            <label for="photo">Add post photo (optional):<br>
                <input type="file" id="photo" name="photo">
            </label>
            <br><br>
            <button type="submit">Send message</button>
        </form>
        <hr>
    </div>
    <div>

    </div>
</div>
</body>
</html>
