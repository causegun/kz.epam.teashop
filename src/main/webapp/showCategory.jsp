<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type = "text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    <c:choose>
        <c:when test="${language.id == 2}">
            <title>Магазин чая</title>
        </c:when>
        <c:otherwise>
            <title>Teashop</title>
        </c:otherwise>
    </c:choose>
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
                        <li><p></p></li>
                        <li><a href="/teashop/login">Login</a></li>
                        <li><a href="/teashop/register">Register</a></li>
                        <li><p></p></li>
                        <li><a href="/teashop/language?id=1">English</a></li>
                        <li><a href="/teashop/language?id=2">Русский</a></li>
                        <li><p></p></li>
                        <li><a href="/teashop/admin/login">Admin Page</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
        <div id="main">
            <c:choose>
                <c:when test="${language.id == 2}">
                <h2>Категории</h2>
                <div align="center">
                    <h2>
                        <a href="/teashop/admin/categories/new">Добавить новую категорию</a>
                        &nbsp;&nbsp;
                        <a href="/teashop/admin/categories">Показать категории (en)</a>
                        &nbsp;&nbsp;
                        <a href="/teashop/admin/categories/ru">Показать категории (ru)</a>
                    </h2>
                </div>
                <div align="center">
                    <table border = "1" cellpadding="3">
                        <tr>
                            <th>ID</th>
                            <th>Имя категории</th>
                            <th>Действия</th>
                        </tr>
                        <c:forEach var="category" items="${categories}">
                            <tr>
                            <c:if test="${category.languageId == 1}">
                                <td><c:out value="${category.id}" /></td>
                                <td><c:out value="${category.name}" /></td>
                                <td>
                                    <a href="/teashop/admin/categories/edit?id=<c:out value='${category.id}' />">Ред-ть</a>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <a href="/teashop/admin/categories/delete?id=<c:out value='${category.id}' />" onclick="return confirm('Delete category?')">Удалить</a>
                                </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </table>
                </div>
                </c:when>
                <c:otherwise>
                    <h2>Category List</h2>
                <div align="center">
                    <h2>
                        <a href="/teashop/admin/categories/new">Add new Category</a>
                        &nbsp;&nbsp;
                        <a href="/teashop/admin/categories">Show Categories (en)</a>
                        &nbsp;&nbsp;
                        <a href="/teashop/admin/categories/ru">Show Categories (ru)</a>
                    </h2>
                </div>
                <div align="center">
                    <table border = "1" cellpadding="3">
                        <tr>
                            <th>ID</th>
                            <th>Category Name</th>
                            <th>Actions</th>
                        </tr>
                        <c:forEach var="category" items="${categories}">
                            <tr>
                            <c:if test="${category.languageId == 1}">
                                <td><c:out value="${category.id}" /></td>
                                <td><c:out value="${category.name}" /></td>
                                <td>
                                    <a href="/teashop/admin/categories/edit?id=<c:out value='${category.id}' />">Edit</a>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <a href="/teashop/admin/categories/delete?id=<c:out value='${category.id}' />" onclick="return confirm('Delete category?')">Delete</a>
                                </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </table>
                </div>
                    </c:otherwise>
                    </c:choose>
        </div>
    </div>
</div>
</body>
</html>
