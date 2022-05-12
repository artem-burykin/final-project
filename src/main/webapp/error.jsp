<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="messages" prefix="error_jsp.">
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title"/></title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/icon.png" type="image/png">
    <meta charset="utf-8">
    <link href="https://fonts.googleapis.com/css?family=Maven+Pro:400,900" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css"/>
</head>
<body>
    <div id="notfound">
        <div class="notfound">
            <div class="notfound-404">
                <h1>404</h1>
            </div>
            <h2><fmt:message key="we_are_sorry"/></h2>
            <p><fmt:message key="the_page_you_are_looking_for"/></p>
            <c:choose>
                <c:when test="${sessionScope.role eq 'admin'}">
                    <a href="/publish/administration/showProductsAndCategories"><fmt:message key="back_to_home"/></a>
                </c:when>
                <c:otherwise>
                    <a href="/publish/"><fmt:message key="back_to_home"/></a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</fmt:bundle>
</body>
</html>
