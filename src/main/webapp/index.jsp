<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <meta charset="UTF-8">
    <link rel="icon" href="img/icon.png" type="image/png">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/index.css">
</head>
<body>
    <jsp:include page="fragments/header.jsp"></jsp:include>
    <p class="h4" style="color: ${sessionScope.color}" align="center">User previous action: ${sessionScope.status}</p>
    <div class="container">
        <div class="row">
            <div class="col-4">
                <c:choose>
                    <c:when test="${!products.isEmpty() && products.get(0) != null}">
                        <c:forEach var="product" items="${products}">
                            <div class="card" style="width: 18rem;">
                                <img src="img/${product.logo}" class="card-img-top" alt="logo">
                                <div class="card-body">
                                    <h5 class="card-title">${product.name}</h5>
                                    <p class="card-text">${product.description}</p>
                                    <c:choose>
                                        <c:when test="${sessionScope.login ne null}">
                                            <form action="user/buyProduct" method="post">
                                                <input type="hidden" name="product" value="${product.name}">
                                                <input type="submit" class="btn btn-primary" value="Buy for ${product.price} ₴">
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <form action="login.jsp">
                                                <input type="submit" class="btn btn-primary" id="inf" href="login.jsp" value="Buy for ${product.price} ₴">
                                                <label for="inf">You must be <a href="login.jsp">logged in</a> to buy this product.</label>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <h4>Sorry, but no product was found!</h4>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-2">
                <div class="dropdown">
                    <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Sorting
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <a class="dropdown-item" href="sortProductFromAToZ">From A to Z</a>
                        <a class="dropdown-item" href="sortProductFromZToA">From Z to A</a>
                        <a class="dropdown-item" href="sortProductFromLowToHigh">From low to high price</a>
                        <a class="dropdown-item" href="sortProductFromHighToLow">From high to low price</a>
                        <a class="dropdown-item" href="sortProductFromNewToOld">From new to old</a>
                        <a class="dropdown-item" href="sortProductFromOldToNew">From old to new</a>
                    </div>
                </div>
            </div>
            <div class="col-3">
                <form action="filterProductByPrice" method="get">
                    <div class="container">
                        <div class="row">
                            <div class="col-12">
                                Filters
                            </div>
                            <div class="col-12">
                                Filter by price:
                            </div>
                            <div class="col-12">
                                Minimum price:
                            </div>
                            <div class="col-12">
                                <input type="number" name="startPrice" min="0" max="4999" maxlength="5" >
                            </div>
                            <div class="col-12">
                                Maximum price:
                            </div>
                            <div class="col-12">
                                <input type="number" name="endPrice" min="1" max="5000">
                            </div>
                            <br>
                            <div class="col-12 margin">
                                <input class="btn btn-primary" type="submit" value="Enter">
                            </div>
                        </div>
                    </div>
                </form>
                <form action="filterProductByCategory" method="get">
                    <div class="container">
                        <div class="row">
                            <div class="col-12">
                                Filter by category:
                            </div>
                            <div class="col-12">
                                <c:forEach var="category" items="${categories}">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="categoryName" value="${category.name}" id="flexRadioDefault1">
                                        <label class="form-check-label" for="flexRadioDefault1">
                                            ${category.name}
                                        </label>
                                    </div>
                                </c:forEach>
                            </div>
                            <br>
                            <div class="col-12 margin">
                                <input class="btn btn-primary" type="submit" value="Enter">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-3">
                <form action="findProductByName" method="get">
                    <div class="container">
                        <div class="row">
                            <div class="col-8">
                                <input type="text" name="name" required>
                            </div>
                            <div class="col-4">
                                <input type="submit" class="btn btn-primary" value="Find">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>
