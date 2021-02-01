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
                    <h2>Зарегистрироваться</h2>
                    <form action="/teashop/user/home" method="post">
                        Имя: <input type="text" name="name" required/><br/><br/>
                        Эл.почта:<input type="email" name="email" required/><br/><br/>
                        Пароль:<input type="password" id="password" name="password" required/><br/><br/>
                        Подтверждение пароля:<input type="password" id="password2" required/><br/><br/>
                        <script type="text/javascript">
                            window.onload = function () {
                                document.getElementById("password").onchange = validatePassword;
                                document.getElementById("password2").onchange = validatePassword;
                            }
                            function validatePassword(){
                                var pass2=document.getElementById("password2").value;
                                var pass1=document.getElementById("password").value;
                                if(pass1!=pass2)
                                    document.getElementById("password2").setCustomValidity("Пароли не совпадают");
                                else
                                    document.getElementById("password2").setCustomValidity('');
                            }
                        </script>
                        Тел. номер: <input type="number" min = "7000000000" max="9999999999" name="phoneNumber" required/><br/><br/>
                        <input type="submit" value="register"/>
                    </form>
                </c:when>
                <c:otherwise>
                    <h2>Register</h2>
                    <form action="/teashop/user/home" method="post">
                        User Name: <input type="text" name="name" required/><br/><br/>
                        Email:<input type="email" name="email" required/><br/><br/>
                        Password:<input type="password" id="password1" name="password" required/><br/><br/>
                        Confirm password:<input type="password" id="password3" required/><br/><br/>
                        <script type="text/javascript">
                            window.onload = function () {
                                document.getElementById("password1").onchange = validatePassword;
                                document.getElementById("password3").onchange = validatePassword;
                            }
                            function validatePassword(){
                                var pass2=document.getElementById("password3").value;
                                var pass1=document.getElementById("password1").value;
                                if(pass1!=pass2)
                                    document.getElementById("password3").setCustomValidity("Passwords don't match");
                                else
                                    document.getElementById("password3").setCustomValidity('');
                            }
                        </script>
                        Phone Number: <input type="number" min ="7000000000" max="9999999999" name="phoneNumber" required/><br/><br/>
                        <input type="submit" value="register"/>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>