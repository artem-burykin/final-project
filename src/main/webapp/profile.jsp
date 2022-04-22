<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <title>Profile</title>
    <meta charset="UTF-8">
    <link rel="icon" href="img/icon.png" type="image/png">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
</head>
<body>
    <jsp:include page="fragments/header.jsp"></jsp:include>
    <div class="container">
        <div class="row">
            <div class="col-3">
                <h3>Hello, ${sessionScope.login}</h3>
            </div>
            <div class="col-6">
                <h2>List of your subscription</h2>
                <c:forEach var="product" items="${products}">
                    <div class="card" style="width: 18rem;">
                        <img src="img/${product.logo}" class="card-img-top" alt="logo">
                        <div class="card-body">
                            <h5 class="card-title">${product.name}</h5>
                            <p class="card-text">${product.description}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="col-3">
                <h4>Your score: ${sessionScope.score} ₴</h4>
                <a href="topupscore.jsp" class="btn btn-primary">Top up</a>
            </div>
        </div>
    </div>
</body>
</html>
