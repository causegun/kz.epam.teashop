<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:choose>
    <c:when test="${language.name == null}">
        <c:set var="currentLocale" value="en"/>
    </c:when>
    <c:otherwise>
        <c:set var="currentLocale" value="${language.name}"/>
    </c:otherwise>
</c:choose>

<fmt:setLocale value="${currentLocale}"/>
<fmt:setBundle basename="messages"/>

<html lang="${currentLocale}">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    <title><fmt:message key="title"/></title>
</head>

<body>
<div id="container">
    <div id="header">
        <h1><fmt:message key="title"/></h1>
    </div>
    <div id="content">
        <div id="nav">
            <h3><fmt:message key="navigation"/></h3>
            <ul>
                <li><a href="/teashop"><fmt:message key="main"/></a></li>
                <li><a href="/teashop/categoryList"><fmt:message key="shop"/></a>
                <li><a href="/teashop/cart"><fmt:message key="cart"/></a></li>
                <li><p></p></li>
                <li><a href="/teashop/login"><fmt:message key="login"/></a></li>
                <li><a href="/teashop/register"><fmt:message key="register"/></a></li>
                <li><p></p></li>
                <li><a href="/teashop/language?id=1">English</a></li>
                <li><a href="/teashop/language?id=2">Русский</a></li>
                <li><p></p></li>
                <li><a href="/teashop/admin/login"><fmt:message key="adminPage"/></a></li>
            </ul>
        </div>
        <div id="main">
            <h2><fmt:message key="register"/></h2>
            <form action="/teashop/user/home" method="post">
                <fmt:message key="register.user"/><input type="text" name="name" required/><br/><br/>
                <fmt:message key="login.email"/><input type="email" name="email" required/>&nbsp; ${invalidEmailMessage} &nbsp; ${userExistMessage}
                <br/><br/>
                <fmt:message key="login.password"/><input type="password" id="password1" name="password" required/>&nbsp; ${invalidPasswordMessage}<br/><br/>
                <fmt:message key="register.confirm"/><input type="password" id="password3" required/><br/><br/>
                <script type="text/javascript">
                    window.onload = function () {
                        document.getElementById("password1").onchange = validatePassword;
                        document.getElementById("password3").onchange = validatePassword;
                    }

                    function validatePassword() {
                        const pass2 = document.getElementById("password3").value;
                        const pass1 = document.getElementById("password1").value;
                        if (pass1 != pass2)
                            document.getElementById("password3").setCustomValidity("<fmt:message key="register.message"/>");
                        else
                            document.getElementById("password3").setCustomValidity('');
                    }
                </script>
                <fmt:message key="register.phone"/><input type="number" name="phoneNumber" value="+77"
                                                          required/>&nbsp; ${invalidPhoneNumberMessage}<br/><br/>
                <button type="submit"><fmt:message key="register"/></button>
            </form>
        </div>
    </div>
</div>
</body>
</html>