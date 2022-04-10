<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
            <li><a href="/publish/" class="nav-link px-2 link-secondary">Home</a></li>
            <li><a href="" class="nav-link px-2 link-dark">FAQs</a></li>
            <li><a href="#" class="nav-link px-2 link-dark">About</a></li>
        </ul>
        <c:choose>
            <c:when test="${sessionScope.login ne null}">
                <a href="logoutServlet" class="btn btn-primary">Log out</a>
            </c:when>
            <c:otherwise>
                <div class="col-md-3 text-end">
                    <a href="login.jsp" class="btn btn-outline-primary me-2">Sign in</a>
                    <a href="registration.jsp" class="btn btn-outline-primary me-2">Sign up</a>
                </div>
            </c:otherwise>
        </c:choose>
    </header>
</div>
</body>
</html>
