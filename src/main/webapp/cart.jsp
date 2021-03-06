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
            <h2><fmt:message key="cart"/></h2>
            <table border="1" cellpadding="3">
                <tr>
                    <th><fmt:message key="cart.product"/></th>
                    <th><fmt:message key="quantity"/></th>
                    <th><fmt:message key="price"/></th>
                    <th><fmt:message key="actions"/></th>
                </tr>
                <c:forEach var="cartItem" items="${cartItems}">
                    <tr>
                        <td><c:out value="${cartItem.productName}"/></td>
                        <td><c:out value="${cartItem.quantity}"/></td>
                        <td><c:out value="${cartItem.price}"/></td>
                        <td>
                            <a><fmt:message key="edit"/></a>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="/teashop/cart/delete?id=<c:out value='${cartItem.id}' />"
                               onclick="return confirm('<fmt:message key="delete"/>?')"><fmt:message key="delete"/></a>
                        </td>
                    </tr>
                </c:forEach>
                <tr align="right">
                    <td><fmt:message key="cart.totalPrice"/> <c:out value="${cart.totalPrice}"/></td>
                </tr>
            </table>
            <br><a href="/teashop/cart/order" class="button"><fmt:message key="cart.makeOrder"/></a>
            &nbsp;&nbsp;&nbsp;
            <a href="/teashop/cart/deleteCart" onclick="return confirm('<fmt:message key="cart.delete"/>?')" class="button"><fmt:message key="cart.delete"/></a>
        </div>
    </div>
</div>
</body>
</html>
