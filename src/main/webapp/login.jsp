<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <meta charset="UTF-8">
    <link rel="icon" href="img/icon.png" type="image/png">
    <link href="https://fonts.googleapis.com/css?family=Karla:400,700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.materialdesignicons.com/4.8.95/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/login.css">
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
                            <p class="login-card-description">Sign into your account</p>
                            <form action="loginServlet" method="post">
                                <div class="form-group">
                                    <label for="login" class="sr-only">Email</label>
                                    <input type="text" name="login" required class="form-control" placeholder="Login">
                                </div>
                                <div class="form-group mb-4">
                                    <label for="password" class="sr-only">Password</label>
                                    <input type="password" name="password" id="password" required class="form-control" placeholder="***********">
                                </div>
                                <input name="enter" id="login" class="btn btn-block login-btn mb-4" type="submit" value="Login">
                            </form>
                            <p class="login-card-footer-text">Don't have an account? <a href="registration.jsp" class="text-reset">Register here</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</body>
</html>
