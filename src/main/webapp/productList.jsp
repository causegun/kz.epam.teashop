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
            <h2><fmt:message key="product.tea"/></h2>
            <div class="product-container">
                <c:forEach var="product" items="${products}">
                    <div class="product-item">
                        <form action="/teashop/productList/addToCart/product?id=${product.id}" method="post">
                            <img src="${pageContext.request.contextPath}/${product.pathToPicture}">
                            <div class="product-list">
                                <h3>${product.name}</h3>
                                <details>
                                    <summary><fmt:message key="product.more"/></summary>
                                        ${product.description}
                                </details>
                                <span class="price">₸ ${product.price}</span>
                                <a href="/teashop/cart" class="button"><fmt:message key="cart"/></a>&nbsp;
                                <button type="submit" class="button"><fmt:message key="product.addToCart"/></button>
                                <label for="quantity${product.id}"><fmt:message key="product.amount"/></label>
                                <input id="quantity${product.id}" type="number" min="1" max="10"
                                       name="quantity${product.id}" size="2" required/>
                            </div>
                        </form>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <div id="footer">
        <h2><c:out value="${sessionScope.addMessage}"/></h2>
    </div>
</div>
</body>
</html>
