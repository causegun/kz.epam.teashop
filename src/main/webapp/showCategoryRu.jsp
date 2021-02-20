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
            <h2><fmt:message key="categoryForm.categoryList"/></h2>
            <div align="center">
                <h2>
                    <a href="/teashop/admin/categories/new"><fmt:message key="categoryForm.addNewCategory"/></a>
                    &nbsp;&nbsp;
                    <a href="/teashop/admin/categories"><fmt:message key="categoryForm.showCategories"/> (en)</a>
                    &nbsp;&nbsp;
                    <a href="/teashop/admin/categories/ru"><fmt:message key="categoryForm.showCategories"/> (ru)</a>
                </h2>
            </div>
            <div align="center">
                <table border="1" cellpadding="3">
                    <tr>
                        <th>ID</th>
                        <th><fmt:message key="categoryForm.categoryName"/></th>
                        <th><fmt:message key="actions"/></th>
                    </tr>
                    <c:forEach var="category" items="${categories}">
                        <tr>
                        <c:if test="${category.languageId == 2}">
                            <td><c:out value="${category.id}"/></td>
                            <td><c:out value="${category.name}"/></td>
                            <td>
                                <a href="/teashop/admin/categories/edit?id=<c:out value='${category.id}' />"><fmt:message
                                        key="edit"/></a>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="/teashop/admin/categories/delete?id=<c:out value='${category.id}' />"
                                   onclick="return confirm('<fmt:message key="category.delete"/>')"><fmt:message
                                        key="delete"/></a>
                            </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>