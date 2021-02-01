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
                    <h2>Пользователи</h2>
                    <div align="center">
                        <h2>
                            <a href="/teashop/admin/users/new">Добавить пользователя</a>
                            &nbsp;&nbsp;&nbsp;
                            <a href="/teashop/admin/users">Показать пользователей</a>
                        </h2>
                    </div>
                    <div align="center">
                        <c:if test="${user != null}">
                        <form action="/teashop/admin/users/update" method="post">
                            </c:if>
                            <c:if test="${user == null}">
                            <form action="/teashop/admin/users/insert" method="post">
                                </c:if>
                                <table border="1" cellpadding="3">
                                    <caption>
                                        <h2>
                                            <c:if test="${user != null}">
                                                Ред-ть пользователя
                                            </c:if>
                                            <c:if test="${user == null}">
                                                Добавить пользователя
                                            </c:if>
                                        </h2>
                                    </caption>
                                    <c:if test="${user != null}">
                                        <input type="hidden" name="id" value="<c:out value='${user.id}' />" />
                                    </c:if>
                                    <tr>
                                        <th>Имя: </th>
                                        <td>
                                            <input type="text" name="name" size="45"
                                                   value="<c:out value='${user.name}' />" required/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Роль Админа: </th>
                                        <td>
                                            <c:if test="${user.admin == true}">
                                                <input type="radio" id="yes" name="isAdmin" value="true" checked />
                                                <label for="yes">Yes</label><br>
                                                <input type="radio" id="no" name="isAdmin" value="false"/>
                                                <label for="no">No</label><br>
                                            </c:if>
                                            <c:if test="${user.admin == false}">
                                                <input type="radio" id="yes" name="isAdmin" value="true" />
                                                <label for="yes">Yes</label><br>
                                                <input type="radio" id="no" name="isAdmin" value="false" checked/>
                                                <label for="no">No</label><br>
                                            </c:if>
                                            <c:if test="${user == null}">
                                                <input type="radio" id="yes" name="isAdmin" value="true"/>
                                                <label for="yes">Yes</label><br>
                                                <input type="radio" id="no" name="isAdmin" value="false"/>
                                                <label for="no">No</label><br>
                                            </c:if>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Эл. почта: </th>
                                        <td>
                                            <input type="email" name="email" size="45"
                                                   value="<c:out value='${user.email}' />" required/>
                                        </td>
                                    </tr>
                                    <c:if test="${user == null}">
                                        <tr>
                                            <th>Пароль: </th>
                                            <td>
                                                <input type="text" name="password" size="45"
                                                       value="<c:out value='${user.password}' />" required/>
                                            </td>
                                        </tr>
                                    </c:if>
                                    <tr>
                                        <th>Тел. номер: </th>
                                        <td>
                                            <input type="tel" name="phoneNumber" size="45"
                                                   pattern="[0-9]{11,11}"
                                                   value="<c:out value='${user.phoneNumber}'/>" required/><br/>
                                            <small>Формат: 87771112233</small>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center">
                                            <input type="submit" value="Сохранить" />
                                        </td>
                                    </tr>
                                </table>
                            </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <h2>User List</h2>
                    <div align="center">
                    <h2>
                        <a href="/teashop/admin/users/new">Add new User</a>
                        &nbsp;&nbsp;&nbsp;
                        <a href="/teashop/admin/users">Show Users</a>
                    </h2>
                    </div>
                    <div align="center">
                        <c:if test="${user != null}">
                        <form action="/teashop/admin/users/update" method="post">
                            </c:if>
                            <c:if test="${user == null}">
                            <form action="/teashop/admin/users/insert" method="post">
                                </c:if>
                                <table border="1" cellpadding="3">
                                    <caption>
                                        <h2>
                                            <c:if test="${user != null}">
                                                Edit User
                                            </c:if>
                                            <c:if test="${user == null}">
                                                Add new User
                                            </c:if>
                                        </h2>
                                    </caption>
                                    <c:if test="${user != null}">
                                        <input type="hidden" name="id" value="<c:out value='${user.id}' />" />
                                    </c:if>
                                    <tr>
                                        <th>Name: </th>
                                        <td>
                                            <input type="text" name="name" size="45"
                                                   value="<c:out value='${user.name}' />" required/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Admin root: </th>
                                        <td>
                                            <c:if test="${user.admin == true}">
                                                <input type="radio" id="yes" name="isAdmin" value="true" checked />
                                                <label for="yes">Yes</label><br>
                                                <input type="radio" id="no" name="isAdmin" value="false"/>
                                                <label for="no">No</label><br>
                                            </c:if>
                                            <c:if test="${user.admin == false}">
                                                <input type="radio" id="yes" name="isAdmin" value="true" />
                                                <label for="yes">Yes</label><br>
                                                <input type="radio" id="no" name="isAdmin" value="false" checked/>
                                                <label for="no">No</label><br>
                                            </c:if>
                                            <c:if test="${user == null}">
                                                <input type="radio" id="yes" name="isAdmin" value="true"/>
                                                <label for="yes">Yes</label><br>
                                                <input type="radio" id="no" name="isAdmin" value="false"/>
                                                <label for="no">No</label><br>
                                            </c:if>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Email: </th>
                                        <td>
                                            <input type="email" name="email" size="45"
                                                   value="<c:out value='${user.email}' />" required/>
                                        </td>
                                    </tr>
                                    <c:if test="${user == null}">
                                        <tr>
                                            <th>Password: </th>
                                            <td>
                                                <input type="text" name="password" size="45"
                                                       value="<c:out value='${user.password}' />" required/>
                                            </td>
                                        </tr>
                                    </c:if>
                                    <tr>
                                        <th>Phone Number: </th>
                                        <td>
                                            <input type="tel" name="phoneNumber" size="45"
                                                   pattern="[0-9]{11,11}"
                                                   value="<c:out value='${user.phoneNumber}'/>" required/><br/>
                                            <small>Format: 87771112233</small>
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