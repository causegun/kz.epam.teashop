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
                <c:if test="${product != null}">
                <form action="/teashop/admin/products/update" method="post">
                    </c:if>
                    <c:if test="${product == null}">
                    <form action="/teashop/admin/products/insert" method="post">
                        </c:if>
                        <table border="1" cellpadding="3">
                            <caption>
                                <h2>
                                    <c:if test="${product != null}"><fmt:message key="product.edit"/></c:if>
                                    <c:if test="${product == null}"><fmt:message key="product.add"/></c:if>
                                </h2>
                            </caption>
                            <c:if test="${product != null}">
                                <input type="hidden" name="id" value="<c:out value='${product.id}' />"/>
                            </c:if>
                            <tr>
                                <th><fmt:message key="language"/></th>
                                <td>
                                    <select size="3" name="languageId" required>
                                        <c:forEach var="language" items="${languages}">
                                            <c:if test="${language.id == product.languageId}">
                                                <option selected
                                                        value='${language.id}'>${language.name}</option>
                                            </c:if>
                                            <c:if test="${language.id != product.languageId}">
                                                <option value='${language.id}'>${language.name}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th><fmt:message key="product.category"/>:</th>
                                <td>
                                    <select size="3" name="categoryId" required>
                                        <c:forEach var="category" items="${categories}">
                                            <c:if test="${category.id == product.categoryId}">
                                                <option selected
                                                        value='${category.id}'>${category.name}</option>
                                            </c:if>
                                            <c:if test="${category.id != product.categoryId}">
                                                <option value='${category.id}'>${category.name}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th><fmt:message key="product.name"/></th>
                                <td>
                                    <input type="text" name="name" size="45"
                                           value="<c:out value='${product.name}' />" required/>
                                </td>
                            </tr>
                            <tr>
                                <th><fmt:message key="product.description"/>:</th>
                                <td> <textarea name="description" rows="5" cols="45">
                    <c:out value='${product.description}'/>
                     </textarea>
                                </td>
                            </tr>
                            <tr>
                                <th><fmt:message key="price"/>:</th>
                                <td>
                                    <input type="number" name="price" value="<c:out value="${product.price}"/>"/>
                                </td>
                            </tr>
                            <tr>
                                <th><fmt:message key="product.path"/></th>
                                <td>
                                    <input type="text" name="pathToPicture"
                                           value="<c:out value="${product.pathToPicture}"/>"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="center">
                                    <input type="submit" value="<fmt:message key="save"/>"/>
                                </td>
                            </tr>
                        </table>
                        <br>${errorMessage}
                    </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>