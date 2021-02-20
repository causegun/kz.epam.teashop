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
            <h2><fmt:message key="user.list"/></h2>
            <div align="center">
                <h2>
                    <a href="/teashop/admin/users/new"><fmt:message key="user.add"/></a>
                    &nbsp;&nbsp;&nbsp;
                    <a href="/teashop/admin/users"><fmt:message key="user.show"/></a>
                </h2>
            </div>
            <div align="center">
                <table border="1" cellpadding="3">
                    <tr>
                        <th>ID</th>
                        <th><fmt:message key="name"/></th>
                        <th><fmt:message key="user.isAdmin"/></th>
                        <th><fmt:message key="user.email"/></th>
                        <th><fmt:message key="user.phone"/></th>
                        <th><fmt:message key="actions"/></th>
                    </tr>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td><c:out value="${user.id}"/></td>
                            <td><c:out value="${user.name}"/></td>
                            <td><c:out value="${user.admin}"/></td>
                            <td><c:out value="${user.email}"/></td>
                            <td><c:out value="${user.phoneNumber}"/></td>
                            <td>
                                <a href="/teashop/admin/users/edit?id=<c:out value='${user.id}' />"><fmt:message
                                        key="edit"/></a>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="/teashop/admin/users/delete?id=<c:out value='${user.id}' />"
                                   onclick="return confirm('<fmt:message key="user.message"/>')"><fmt:message
                                        key="delete"/></a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>