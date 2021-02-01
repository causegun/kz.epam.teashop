<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Teashop</title>
    <link rel="stylesheet" type = "text/css" href="${pageContext.request.contextPath}/css/style.css"/>
</head>

<body>
<div id = "container">
    <div id = "header">
        <h1>Teashop</h1>
    </div>
    <div id="content">
        <div id="nav">
            <h3>Navigation</h3>
            <ul>
                <c:choose>
                    <c:when test="${language.id == 2}">
                        <li><a href="/teashop/">Главная</a></li>
                        <li> </li>
                        <li><a href="/teashop/categoryList">Магазин</a>
                        <li><a href="/teashop/cart">Корзина</a>
                        <li> </li>
                        <li><a href="/teashop/login">Войти</a></li>
                        <li><a href = "/teashop/register">Зарегистрироваться</a></li>
                        <li> </li>
                        <li><a href="/teashop/language?id=1">English</a></li>
                        <li><a href="/teashop/language?id=2">Русский</a></li>
                        <li> </li>
                        <li><a href="/teashop/admin/login">Вы админ?</a>
                    </c:when>
                    <c:otherwise>
                        <li><a href="/teashop">Main</a></li>
                        <li> </li>
                        <li><a href="/teashop/categoryList">Shop</a>
                        <li><a href="/teashop/cart">Cart</a>
                        <li> </li>
                        <li><a href="/teashop/login">Login</a></li>
                        <li><a href = "/teashop/register">Register</a></li>
                        <li> </li>
                        <li><a href="/teashop/language?id=1">English</a></li>
                        <li><a href="/teashop/language?id=2">Русский</a></li>
                        <li> </li>
                        <li><a href="/teashop/admin/login">Admin login</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
        <div id="main">
            <c:choose>
                <c:when test="${language.id == 2}">
                    <h2>Информация о заказе</h2>
                    <p>Спасибо!</p>
                    <p>(скоро будет больше функционала)</p>
                </c:when>
                <c:otherwise>
                    <h2>Order Info</h2>
                    <p>Thanks for purchasing!</p>
                    <p>(more will be added soon)</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>