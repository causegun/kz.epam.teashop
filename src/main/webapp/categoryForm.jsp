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
                    &nbsp;&nbsp;&nbsp;
                    <a href="/teashop/admin/categories"><fmt:message key="categoryForm.showCategories"/></a>
                </h2>
            </div>
            <div align="center">
                <c:if test="${category != null}">
                <form action="/teashop/admin/categories/update" method="post">
                    </c:if>
                    <c:if test="${category == null}">
                    <form action="/teashop/admin/categories/insert" method="post">
                        </c:if>
                        <table border="1" cellpadding="3">
                            <caption>
                                <h2>
                                    <c:if test="${category != null}"><fmt:message
                                            key="categoryForm.editCategory"/></c:if>
                                    <c:if test="${category == null}"><fmt:message
                                            key="categoryForm.addNewCategory"/></c:if>
                                </h2>
                            </caption>
                            <c:if test="${category != null}">
                                <input type="hidden" name="id" value="<c:out value='${category.id}' />"/>
                            </c:if>
                            <tr>
                                <th><fmt:message key="language"/></th>
                                <td>
                                    <select size="3" name="languageId" required>
                                        <c:forEach var="language" items="${languages}">
                                            <c:if test="${language.id == category.languageId}">
                                                <option selected value='${language.id}'>${language.name}</option>
                                            </c:if>
                                            <c:if test="${language.id != category.languageId}">
                                                <option value='${language.id}'>${language.name}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th><fmt:message key="categoryForm.categoryName"/>:</th>
                                <td>
                                    <input type="text" name="name" size="45"
                                           value="<c:out value='${category.name}' />" required/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="center">
                                    <input type="submit" value="<fmt:message key="save"/>"/>
                                </td>
                            </tr>
                        </table>
                    </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
