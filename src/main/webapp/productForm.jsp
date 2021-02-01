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
                    <h2>Список товаров</h2>
                    <div align="center">
                        <h2>
                            <a href="/teashop/admin/products/new">Добавит товар</a>
                            &nbsp;&nbsp;&nbsp;
                            <a href="/teashop/admin/products">Показать товары (en)</a>
                            &nbsp;&nbsp;
                            <a href="/teashop/admin/products/ru">Показать товары (ru)</a>
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
                                            <c:if test="${product != null}">Edit product</c:if>
                                            <c:if test="${product == null}">Add new product</c:if>
                                        </h2>
                                    </caption>
                                    <c:if test="${product != null}">
                                        <input type="hidden" name="id" value="<c:out value='${product.id}' />"/>
                                    </c:if>
                                    <tr>
                                        <th>Язык:</th>
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
                                        <th>Категория:</th>
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
                                        <th>Наименование товара:</th>
                                        <td>
                                            <input type="text" name="name" size="45"
                                                   value="<c:out value='${product.name}' />" required/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Описание:</th>
                                        <td> <textarea name="description" rows="5" cols="45">
                    <c:out value='${product.description}'/>
                     </textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Цена:</th>
                                        <td>
                                            <input type="text" name="price" value="<c:out value="${product.price}"/>"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Путь к картинке:</th>
                                        <td>
                                            <input type="text" name="pathToPicture"
                                                   value="<c:out value="${product.pathToPicture}"/>"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center">
                                            <input type="submit" value="Save"/>
                                        </td>
                                    </tr>
                                </table>
                                <br>${errorMessage}
                            </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <h2>Product List</h2>
                    <div align="center">
                        <h2>
                            <a href="/teashop/admin/products/new">Add new product</a>
                            &nbsp;&nbsp;&nbsp;
                            <a href="/teashop/admin/products">Show Products (en)</a>
                            &nbsp;&nbsp;
                            <a href="/teashop/admin/products/ru">Show Products (ru)</a>
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
                                            <c:if test="${product != null}">Edit product</c:if>
                                            <c:if test="${product == null}">Add new product</c:if>
                                        </h2>
                                    </caption>
                                    <c:if test="${product != null}">
                                        <input type="hidden" name="id" value="<c:out value='${product.id}' />" />
                                    </c:if>
                                    <tr>
                                        <th>Language: </th>
                                        <td>
                                            <select size="3" name="languageId" required>
                                                <c:forEach var="language" items="${languages}">
                                                    <c:if test="${language.id == product.languageId}">
                                                        <option selected value='${language.id}'>${language.name}</option>
                                                    </c:if>
                                                    <c:if test="${language.id != product.languageId}">
                                                        <option value='${language.id}'>${language.name}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Category: </th>
                                        <td>
                                            <select size="3" name="categoryId" required>
                                                <c:forEach var="category" items="${categories}">
                                                    <c:if test="${category.id == product.categoryId}">
                                                        <option selected value='${category.id}'>${category.name}</option>
                                                    </c:if>
                                                    <c:if test="${category.id != product.categoryId}">
                                                        <option value='${category.id}'>${category.name}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Product name: </th>
                                        <td>
                                            <input type="text" name="name" size="45"
                                                   value="<c:out value='${product.name}' />" required/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Description: </th>
                                        <td> <textarea name="description" rows= "5" cols="45">
                    <c:out value='${product.description}'/>
                     </textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Price: </th>
                                        <td>
                                            <input type="text" name="price" value="<c:out value="${product.price}"/>"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Absolute path to picture: </th>
                                        <td>
                                            <input type="text" name="pathToPicture" value="<c:out value="${product.pathToPicture}"/>"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center">
                                            <input type="submit" value="Save" />
                                        </td>
                                    </tr>
                                </table>
                                <br>${errorMessage}
                            </form>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>