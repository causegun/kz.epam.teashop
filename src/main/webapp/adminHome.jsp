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
                        <li><p> </p> </li>
                        <li><a href="/teashop/login">Login</a></li>
                        <li><a href = "/teashop/register">Register</a></li>
                        <li><p> </p> </li>
                        <li><a href="/teashop/language?id=1">English</a></li>
                        <li><a href="/teashop/language?id=2">Русский</a></li>
                        <li><p> </p> </li>
                        <li><a href="/teashop/admin/login">Admin Page</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
        <div id="main">
            <c:choose>
                <c:when test="${language.id == 2}">
                    <h2>Страница Админа</h2>
                    <div style="text-align: center">
                        <h2>Привет</h2>
                        <b>${adminUser.name} (${adminUser.email})</b>
                        <br><br>
                        <a href = "users">Пользователи</a>
                        <a href = "products">Товары</a>
                        <a href = "categories">Категории</a>
                        <a href="logout">Выйти</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <h2>Admin Home</h2>
                    <div style="text-align: center">
                        <h2>Welcome</h2>
                        <b>${adminUser.name} (${adminUser.email})</b>
                        <br><br>
                        <a href = "users">Users</a>
                        <a href = "products">Products</a>
                        <a href = "categories">Categories</a>
                        <a href="logout">Logout</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>
