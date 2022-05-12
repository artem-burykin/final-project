<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="messages" prefix="login_jsp.">
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title"/></title>
    <meta charset="UTF-8">
    <link rel="icon" href="${pageContext.request.contextPath}/img/icon.png" type="image/png">
    <link href="https://fonts.googleapis.com/css?family=Karla:400,700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.materialdesignicons.com/4.8.95/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
    <main class="d-flex align-items-center min-vh-100 py-3 py-md-0">
        <div class="container">
            <div class="card login-card">
                <div class="row no-gutters">
                    <div class="col-md-5">
                        <img src="img/login.jpg" alt="login" class="login-card-img">
                    </div>
                    <div class="col-md-7">
                        <div class="card-body">
                            <div class="brand-wrapper">
                                <img src="img/logo.jpg" width="104" height="54" alt="logo" class="logo">
                            </div>
                            <p class="login-card-description"><fmt:message key="sign_into_your_account"/></p>
                            <form action="loginServlet" method="post">
                                <div class="form-group">
                                    <input type="text" name="login" required class="form-control" placeholder="Login">
                                </div>
                                <div class="form-group mb-4">
                                    <input type="password" name="password" id="password" required class="form-control" placeholder="***********">
                                </div>
                                <input name="enter" id="login" class="btn btn-block login-btn mb-4" type="submit" value="<fmt:message key="login"/>">
                            </form>
                            <p class="login-card-footer-text"><fmt:message key="dont_have_an_account"/> <a href="registration.jsp" class="text-reset"><fmt:message key="register_here" /></a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</body>
</fmt:bundle>
</html>
