<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Teashop</title>
    <link rel="stylesheet" type = "text/css" href="${pageContext.request.contextPath}/css/style.css"/>
</head>

<body>
<div id ="container">
    <div id ="header">
        <h1>Teashop</h1>
    </div>

    <div id="content">
        <div id="nav">
            <h3>Navigation</h3>
            <ul>
                <c:choose>
                    <c:when test="${language.id == 2}">
                        <li><a  href="/teashop">Главная</a></li>
                        <li><a href="/teashop/login">Войти</a></li>
                        <li><a href = "/teashop/register">Зарегистрироваться</a></li>
                        <li><a class="selected" href="/teashop/categoryList">Магазин</a>
                        <li><a href="/teashop/admin/login">Вы админ?</a>
                        <li><a href="/teashop/language?id=1">English</a></li>
                        <li><a href="/teashop/language?id=2">Русский</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="/teashop">Main</a></li>
                        <li><a href="/teashop/login">Login</a></li>
                        <li><a href = "/teashop/register">Register</a></li>
                        <li><a class="selected" href="/teashop/categoryList">Shop</a>
                        <li><a href="/teashop/admin/login">Admin login</a>
                        <li><a href="/teashop/language?id=1">English</a></li>
                        <li><a href="/teashop/language?id=2">Русский</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
        <div id="main">
            <c:choose>
                <c:when test="${language.id == 2}">
                    <h2>Корзина</h2>
                <table border = "1" cellpadding="3">
                    <tr>
                        <th>Товар</th>
                        <th>Количество</th>
                        <th>Цена</th>
                        <th>Действия</th>
                    </tr>
                    <c:forEach var="cartItem" items="${cartItems}">
                        <tr>
                            <td><c:out value="${cartItem.productName}"/></td>
                            <td><c:out value="${cartItem.quantity}"/></td>
                            <td><c:out value="${cartItem.price}"/></td>
                            <td>
                                <a>Редактировать</a>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="/teashop/cart/delete?id=<c:out value='${cartItem.id}' />"
                                   onclick="return confirm('Удалить товар из корзины?')">Удалить</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr align="right">
                        <td>Итого: <c:out value="${cart.totalPrice}"/></td>
                    </tr>
                </table>
                    <br><a href="/teashop/cart/order" class="button">Оформить заказ</a>
                    &nbsp;&nbsp;&nbsp;
                    <a href="/teashop/cart/deleteCart" onclick="return confirm('Удалить корзину?')" class="button">Удалить
                        Корзину</a>
                </c:when>
                    <c:otherwise>
                    <h2>Cart</h2>
                    <table border = "1" cellpadding="3">
                        <tr>
                            <th>Product</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Actions</th>
                        </tr>
                        <c:forEach var="cartItem" items="${cartItems}">
                            <tr>
                                <td><c:out value="${cartItem.productName}"/></td>
                                <td><c:out value="${cartItem.quantity}"/></td>
                                <td><c:out value="${cartItem.price}"/></td>
                                <td>
                                    <a>Edit</a>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <a href="/teashop/cart/delete?id=<c:out value='${cartItem.id}' />"
                                       onclick="return confirm('Delete product?')">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr align="right">
                            <td>Total price: <c:out value="${cart.totalPrice}"/></td>
                        </tr>
                    </table>
                        <br><a href="/teashop/cart/order" class="button">Make Order</a>
                        &nbsp;&nbsp;&nbsp;
                        <a href="/teashop/cart/deleteCart" onclick="return confirm('Delete the cart?')" class="button">Delete
                            the Cart</a>
                    </c:otherwise>
                    </c:choose>

        </div>
    </div>
</div>
</body>
</html>
