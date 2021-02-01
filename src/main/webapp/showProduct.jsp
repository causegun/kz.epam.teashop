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
                    <h2>Товары</h2>
                    <div align="center">
                        <h2>
                            <a href="/teashop/admin/products/new">Добавить товар</a>
                            &nbsp;&nbsp;
                            <a href="/teashop/admin/products">Показать товары (en)</a>
                            &nbsp;&nbsp;
                            <a href="/teashop/admin/products/ru">Показать товары (ru)</a>
                        </h2>
                    </div>
                    <div align="center">
                        <table border = "1" cellpadding="3">
                            <tr>
                                <th>Картинка</th>
                                <th>ID</th>
                                <th>Категория</th>
                                <th>Наименование</th>
                                <th>Описание</th>
                                <th>Цена, тенге</th>
                                <th>Действия</th>
                            </tr>
                            <c:forEach var="product" items="${products}">
                                <tr>
                                <c:if test="${product.languageId == 1}">
                                    <td><img src="<c:url value = "${product.pathToPicture}"/>" alt="${product.pathToPicture}"/></td>
                                    <td><c:out value="${product.id}" /></td>
                                    <c:forEach var = "category" items="${categories}">
                                        <c:if test="${category.id == product.categoryId}">
                                            <td><c:out value="${category.name}" /></td>
                                        </c:if>
                                    </c:forEach>
                                    <td><c:out value="${product.name}" /></td>
                                    <td><c:out value="${product.description}" /></td>
                                    <td><c:out value="${product.price}" /></td>
                                    <td>
                                        <a href="/teashop/admin/products/edit?id=<c:out value='${product.id}' />">Ред-ть</a>
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                        <a href="/teashop/admin/products/delete?id=<c:out value='${product.id}' />" onclick="return confirm('Удалить товар?')">Удалить</a>
                                    </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <h2>Product List</h2>
                    <div align="center">
                        <h2>
                            <a href="/teashop/admin/products/new">Add new Product</a>
                            &nbsp;&nbsp;
                            <a href="/teashop/admin/products">Show Products (en)</a>
                            &nbsp;&nbsp;
                            <a href="/teashop/admin/products/ru">Show Products (ru)</a>
                        </h2>
                    </div>
                    <div align="center">
                        <table border = "1" cellpadding="3">
                            <tr>
                                <th>Picture</th>
                                <th>ID</th>
                                <th>Category</th>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Price, tenge</th>
                                <th>Actions</th>
                            </tr>
                            <c:forEach var="product" items="${products}">
                                <tr>
                                <c:if test="${product.languageId == 1}">
                                    <td><img src="<c:url value = "${product.pathToPicture}"/>" alt="${product.pathToPicture}"/></td>
                                    <td><c:out value="${product.id}" /></td>
                                    <%--                <td><c:out value="${product.languageId}" /></td>--%>
                                    <c:forEach var = "category" items="${categories}">
                                        <c:if test="${category.id == product.categoryId}">
                                            <td><c:out value="${category.name}" /></td>
                                        </c:if>
                                    </c:forEach>
                                    <td><c:out value="${product.name}" /></td>
                                    <td><c:out value="${product.description}" /></td>
                                    <td><c:out value="${product.price}" /></td>
                                    <td>
                                        <a href="/teashop/admin/products/edit?id=<c:out value='${product.id}' />">Edit</a>
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                        <a href="/teashop/admin/products/delete?id=<c:out value='${product.id}' />" onclick="return confirm('Delete product?')">Delete</a>
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