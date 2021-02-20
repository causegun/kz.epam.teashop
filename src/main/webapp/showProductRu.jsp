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
            <h2><fmt:message key="product.list"/></h2>
            <div align="center">
                <h2>
                    <a href="/teashop/admin/products/new"><fmt:message key="product.add"/></a>
                    &nbsp;&nbsp;&nbsp;
                    <a href="/teashop/admin/products"><fmt:message key="product.showEn"/></a>
                    &nbsp;&nbsp;
                    <a href="/teashop/admin/products/ru"><fmt:message key="product.showRu"/></a>
                </h2>
            </div>
            <div align="center">
                <table border="1" cellpadding="3">
                    <tr>
                        <th><fmt:message key="picture"/></th>
                        <th>ID</th>
                        <th><fmt:message key="product.category"/></th>
                        <th><fmt:message key="name"/></th>
                        <th><fmt:message key="product.description"/></th>
                        <th><fmt:message key="product.price"/></th>
                        <th><fmt:message key="actions"/></th>
                    </tr>
                    <c:forEach var="product" items="${products}">
                        <tr>
                        <c:if test="${product.languageId == 2}">
                            <td><img src="${pageContext.request.contextPath}/${product.pathToPicture}"
                                     width="100px" height="100px" alt="${product.pathToPicture}"/></td>
                            <td><c:out value="${product.id}"/></td>
                            <c:forEach var="category" items="${categories}">
                                <c:if test="${category.id == product.categoryId}">
                                    <td><c:out value="${category.name}"/></td>
                                </c:if>
                            </c:forEach>
                            <td><c:out value="${product.name}"/></td>
                            <td><c:out value="${product.description}"/></td>
                            <td><c:out value="${product.price}"/></td>
                            <td>
                                <a href="/teashop/admin/products/edit?id=<c:out value='${product.id}' />"><fmt:message
                                        key="edit"/></a>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="/teashop/admin/products/delete?id=<c:out value='${product.id}' />"
                                   onclick="return confirm('<fmt:message key=""/>Delete product?')"><fmt:message
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