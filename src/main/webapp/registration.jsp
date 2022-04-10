<%--
  Created by IntelliJ IDEA.
  User: arta2
  Date: 08.04.2022
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
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
                        <h3>Register</h3>
                        <form action="registration" method="post">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group first">
                                        <label for="fname">First name</label>
                                        <input type="text" class="form-control" required placeholder="John" name="firstName" id="fname">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group first">
                                        <label for="lname">Last name</label>
                                        <input type="text" class="form-control" required placeholder="Smith" name="lastName" id="lname">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group first">
                                        <label for="login">Login</label>
                                        <input type="login" class="form-control" required name="login" placeholder="JohnSmith" id="login">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group first">
                                        <label for="email">Email</label>
                                        <input type="email" class="form-control" name="email" required placeholder="john@domain.com" id="email">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group last mb-3">
                                        <label for="password">Password</label>
                                        <input type="password" class="form-control" required placeholder="***********" name="password" id="password">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group last mb-3">
                                        <label for="re-password">Confirm Password</label>
                                        <input type="password" class="form-control" required placeholder="***********" name="re_password" id="re-password">
                                    </div>
                                </div>
                            </div>
                            <div class="d-flex mb-5 mt-4 align-items-center">
                                <div class="d-flex align-items-center">
                                    <label class="control control--checkbox mb-0"><span class="caption">Creating an account means you're okay with our <a href="#">Terms and Conditions</a> and our <a href="#">Privacy Policy</a>.</span>
                                        <input type="checkbox" required checked="checked"/>
                                        <div class="control__indicator"></div>
                                    </label>
                                </div>
                            </div>
                            <input type="submit" value="Register" class="btn px-5 btn-primary">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
