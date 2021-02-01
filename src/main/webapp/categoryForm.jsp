<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <c:choose>
        <c:when test="${language.id == 2}">
            <title>Магазин чая</title>
        </c:when>
        <c:otherwise>
            <title>Teashop</title>
        </c:otherwise>
    </c:choose>
    <link rel="stylesheet" type = "text/css" href="${pageContext.request.contextPath}/css/style.css"/>
</head>

<body>
<div id = "container">
    <div id = "header">
        <h1>Teashop</h1>
    </div>
    <div id="content">
        <div id="nav">
            <c:choose>
                <c:when test="${language.id == 2}">
                    <h3>Страницы</h3>
                </c:when>
                <c:otherwise>
                    <h3>Navigation</h3>
                </c:otherwise>
            </c:choose>
            <ul>
                <c:choose>
                    <c:when test="${language.id == 2}">
                        <li><a class="selected" href="/teashop">Главная</a></li>
                        <li><a href="/teashop/categoryList">Магазин</a></li>
                        <li><a href="/teashop/cart">Корзина</a></li>
                        <li><p> </p> </li>
                        <li><a href="/teashop/login">Войти</a></li>
                        <li><a href = "/teashop/register">Зарегистрироваться</a></li>
                        <li><p> </p> </li>
                        <li><a href="/teashop/language?id=1">English</a></li>
                        <li><a href="/teashop/language?id=2">Русский</a></li>
                        <li><p> </p> </li>
                        <li><a href="/teashop/admin/login">Страница Админа</a>
                    </c:when>
                    <c:otherwise>
                        <li><a href="/teashop">Main</a></li>
                        <li><a href="/teashop/categoryList">Shop</a>
                        <li><a href="/teashop/cart">Cart</a></li>
                        <li><p> </p> </li>
                        <li><a href="login">Login</a></li>
                        <li><a href = "register">Register</a></li>
                        <li><p> </p> </li>
                        <li><a href="language?id=1">English</a></li>
                        <li><a href="language?id=2">Русский</a></li>
                        <li><p> </p> </li>
                        <li><a href="admin/login">Admin Page</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
        <div id="main">
            <c:choose>
                <c:when test="${language.id == 2}">
                    <h2>Главная</h2>
                    <p>Добро пожаловать! Купите чай!</p>
                </c:when>
                <c:otherwise>
                    <h2>Category List</h2>
                    <center>
                        <h2>
                            <a href="/teashop/admin/categories/new">Add new Category</a>
                            &nbsp;&nbsp;&nbsp;
                            <a href="/teashop/admin/categories">Show Categories</a>
                        </h2>
                    </center>
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
                                            <c:if test="${category != null}">Edit category</c:if>
                                            <c:if test="${category == null}">Add new category</c:if>
                                        </h2>
                                    </caption>
                                    <c:if test="${category != null}">
                                        <input type="hidden" name="id" value="<c:out value='${category.id}' />" />
                                    </c:if>
                                    <tr>
                                        <th>Language: </th>
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
                                        <th>Category name: </th>
                                        <td>
                                            <input type="text" name="name" size="45"
                                                   value="<c:out value='${category.name}' />" required/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center">
                                            <input type="submit" value="Save" />
                                        </td>
                                    </tr>
                                </table>
                            </form>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>
