<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="messages" prefix="registration_jsp.">
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title"/></title>
    <meta charset="UTF-8">
    <link rel="icon" href="img/icon.png" type="image/png">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/registration.css">
</head>
<body>
    <div class="d-lg-flex half">
        <div class="bg order-1 order-md-2" style="background-image: url('img/registration_bg.jpg');"></div>
        <div class="contents order-2 order-md-1">
            <div class="container">
                <div class="row align-items-center justify-content-center">
                    <div class="col-md-7 py-5">
                        <h3><fmt:message key="register"/></h3>
                        <form action="registration" method="post">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group first">
                                        <label for="fname"><fmt:message key="first_name"/></label>
                                        <input type="text" class="form-control" required
                                               placeholder="<fmt:message key="first_name_example"/>" name="firstName" id="fname">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group first">
                                        <label for="lname"><fmt:message key="last_name"/></label>
                                        <input type="text" class="form-control" required
                                               placeholder="<fmt:message key="last_name_example"/>" name="lastName" id="lname">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group first">
                                        <label for="login"><fmt:message key="login"/></label>
                                        <input type="login" class="form-control" required name="login"
                                               placeholder="<fmt:message key="login_example"/>" id="login">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group first">
                                        <label for="email">Email</label>
                                        <input type="email" class="form-control" name="email" required placeholder="john@domain.com"
                                               id="email">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group last mb-3">
                                        <label for="password"><fmt:message key="password"/></label>
                                        <input type="password" class="form-control" required placeholder="***********" name="password"
                                               id="password">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group last mb-3">
                                        <label for="re-password"><fmt:message key="confirm_password"/> </label>
                                        <input type="password" class="form-control" required placeholder="***********"
                                               name="re_password" id="re-password">
                                    </div>
                                </div>
                            </div>
                            <div class="d-flex mb-5 mt-4 align-items-center">
                                <div class="d-flex align-items-center">
                                    <label class="control control--checkbox mb-0"><span class="caption">
                                        <fmt:message key="creating_an_account"/>
                                        <a href="#"><fmt:message key="terms_and_conditions"/></a>
                                        <fmt:message key="and_our"/> <a href="#"><fmt:message key="privacy_policy"/></a>.</span>
                                        <input type="checkbox" required checked="checked"/>
                                        <div class="control__indicator"></div>
                                    </label>
                                </div>
                            </div>
                            <input type="submit" value="<fmt:message key="register_button"/>" class="btn px-5 btn-primary">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</fmt:bundle>
</html>
