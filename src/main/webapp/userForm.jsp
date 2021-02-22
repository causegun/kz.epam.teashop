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
            <h2><fmt:message key="user.list"/></h2>
            <div align="center">
                <h2>
                    <a href="/teashop/admin/users/new"><fmt:message key="user.add"/></a>
                    &nbsp;&nbsp;&nbsp;
                    <a href="/teashop/admin/users"><fmt:message key="user.show"/></a>
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
                                        <fmt:message key="user.edit"/>
                                    </c:if>
                                    <c:if test="${user == null}">
                                        <fmt:message key="adminPage"/>
                                    </c:if>
                                </h2>
                            </caption>
                            <c:if test="${user != null}">
                                <input type="hidden" name="id" value="<c:out value='${user.id}' />"/>
                            </c:if>
                            <tr>
                                <th><fmt:message key="name"/>:</th>
                                <td>
                                    <input type="text" name="name" size="45"
                                           value="<c:out value='${user.name}' />" required/>
                                </td>
                            </tr>
                            <tr>
                                <th><fmt:message key="user.isAdmin"/>:</th>
                                <td>
                                    <c:if test="${user.admin == true}">
                                        <input type="radio" id="yes" name="isAdmin" value="true" checked/>
                                        <label for="yes"><fmt:message key="yes"/></label><br>
                                        <input type="radio" id="no" name="isAdmin" value="false"/>
                                        <label for="no"><fmt:message key="no"/></label><br>
                                    </c:if>
                                    <c:if test="${user.admin == false}">
                                        <input type="radio" id="yes" name="isAdmin" value="true"/>
                                        <label for="yes"><fmt:message key="yes"/></label><br>
                                        <input type="radio" id="no" name="isAdmin" value="false" checked/>
                                        <label for="no"><fmt:message key="no"/></label><br>
                                    </c:if>
                                    <c:if test="${user == null}">
                                        <input type="radio" id="yes" name="isAdmin" value="true"/>
                                        <label for="yes"><fmt:message key="yes"/></label><br>
                                        <input type="radio" id="no" name="isAdmin" value="false"/>
                                        <label for="no"><fmt:message key="no"/></label><br>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <th><fmt:message key="login.email"/></th>
                                <td>
                                    <input type="email" name="email" size="45"
                                           value="<c:out value='${user.email}' />" required/><br/>
                                    <small>${invalidEmailMessage} &nbsp; ${userExistMessage}</small>
                                </td>
                            </tr>
                            <c:if test="${user == null}">
                                <tr>
                                    <th><fmt:message key="login.password"/></th>
                                    <td>
                                        <input type="text" name="password" size="45"
                                               value="<c:out value='${user.password}' />" required/><br/>
                                        <small>${invalidPasswordMessage}</small>
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <th><fmt:message key="register.phone"/></th>
                                <td>
                                    <input type="text" name="phoneNumber" size="45"
                                           pattern="[0-9]{11,11}"
                                           value="<c:out value='${user.phoneNumber}'/>" required/><br/>
                                    <small>${invalidPhoneNumberMessage}</small>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="center">
                                    <input type="submit" value="<fmt:message key="save"/>"/>
                                </td>
                            </tr>
                        </table>
                    </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>