<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="messages" prefix="header_jsp.">
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
        <a href="/publish/" class="d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none">
            <img src="img/logo.jpg" width="207" height="73" alt="logo">
        </a>

        <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
            <li><a href="/publish/" class="nav-link px-2 link-secondary"><fmt:message key="li.home_page"/></a></li>
            <li><a href="/publish/setLocale?locale=uk" class="nav-link px-2 link-dark"><fmt:message key="li.ukranian"/></a></li>
            <li><a href="/publish/setLocale?locale=en" class="nav-link px-2 link-dark"><fmt:message key="li.english"/></a></li>
        </ul>
        <c:choose>
            <c:when test="${sessionScope.login eq null}">
                <div class="col-md-3 text-end">
                    <a href="login.jsp" class="btn btn-outline-primary me-2"><fmt:message key="sign_in"/></a>
                    <a href="registration.jsp" class="btn btn-outline-primary me-2"><fmt:message key="sign_up"/></a>
                </div>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${sessionScope.role eq 'admin'}">
                        <div class="col-md-3 text-end">
                            <a href="administration/showProductsAndCategories" class="btn btn-outline-primary me-2"><fmt:message key="admin_page"/></a>
                            <a href="user/logout" class="btn btn-outline-primary me-2"><fmt:message key="log_out"/></a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="col-md-3 text-end">
                            <a href="user/showProductWithSubscription" class="btn btn-outline-primary me-2"><fmt:message key="my_profile"/></a>
                            <a href="user/logout" class="btn btn-outline-primary me-2"><fmt:message key="log_out"/></a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </header>
</div>
</fmt:bundle>
</body>
</html>
