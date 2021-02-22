<%
    String message = pageContext.getException().getMessage();
    String exception = pageContext.getException().getClass().toString();
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Exception</title>
</head>
<body>
<div align="center">
    <h2>Exception occurred while processing the request</h2>
    <p>Type: <%= exception%>
    </p>
    <p>Message: <%= message %>
    </p>
</div>
</body>
</html>
