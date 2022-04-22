<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <title>Administration</title>
    <meta charset="UTF-8">
    <link rel="icon" href="img/icon.png" type="image/png">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
</head>
<body>
    <jsp:include page="fragments/header.jsp"></jsp:include>
    <h3>Hello, ${sessionScope.login}</h3>
    <p class="h4" align="center" style="color: ${sessionScope.color}">Status previous action on admin page: ${sessionScope.status}</p>
    <div class="container">
        <div class="row">
            <h2>Add new product</h2>
            <form method="post" action="administration/addNewProduct">
                <div class="col-4">
                    <input type="text" class="form-control" name="name" placeholder="Name of product" required>
                </div>
                <div class="col-8"></div>
                <br>
                <div class="col-2">
                    <input type="number" class="form-control" name="price" min="0" placeholder="Price of product" required>
                </div>
                <br>
                <div class="col-12">
                    <c:forEach var="category" items="${categories}">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="category_id" value="${category.id}" id="flexRadioDefault1" required>
                            <label class="form-check-label" for="flexRadioDefault1">
                                    ${category.name}
                            </label>
                        </div>
                    </c:forEach>
                </div>
                <br>
                <div class="col-4">
                    <input type="text" class="form-control" name="logo" placeholder="Product's logo">
                </div>
                <br>
                <div class="col-12">
                    <input type="text" class="form-control" name="description" placeholder="Product's description">
                </div>
                <br>
                <input type="submit" class="btn btn-primary" value="Add">
            </form>
            <hr>
            <div class="container">
                <div class="row">
                    <div class="col-1">
                        <h5>Id</h5>
                    </div>
                    <div class="col-2">
                        <h5>Name</h5>
                    </div>
                    <div class="col-2">
                        <h5>Logo</h5>
                    </div>
                    <div class="col-4">
                        <h5>Description</h5>
                    </div>
                    <div class="col-2">
                        <h5>Price</h5>
                    </div>
                    <div class="col-1"></div>
                    <c:forEach var="product" items="${products}">
                        <div class="col-1">
                            <p>${product.id}</p>
                        </div>
                        <div class="col-2">
                            <p>${product.name}</p>
                        </div>
                        <div class="col-2">
                            <p>${product.logo}</p>
                            <hr>
                            <p class="h5" align="center">OR</p>
                            <hr>
                            <form action="administration/changeProductLogo" method="post">
                                <input type="hidden" name="name" value="${product.name}">
                                <input type="text" name="logo" placeholder="New logo">
                                <input type="submit" class="btn btn-primary mt-1" value="Update">
                            </form>
                        </div>
                        <div class="col-4">
                            <p>${product.description}</p>
                            <hr>
                            <p class="h5" align="center">OR</p>
                            <hr>
                            <form action="administration/changeProductDescription" method="post">
                                <input type="hidden" name="name" value="${product.name}">
                                <input type="text" name="description" placeholder="New description">
                                <br>
                                <input type="submit" class="btn btn-primary mt-1" value="Update">
                            </form>
                        </div>
                        <div class="col-2">
                            <p>${product.price}</p>
                            <hr>
                            <p class="h5" align="center">OR</p>
                            <hr>
                            <form action="administration/changeProductPrice" method="post">
                                <input type="hidden" name="name" value="${product.name}">
                                <input type="number" min="0" name="price" placeholder="New price">
                                <input type="submit" class="btn btn-primary mt-1" value="Update">
                            </form>
                        </div>
                        <div class="col-1">
                            <form action="administration/removeProduct" method="post">
                                <input type="hidden" name="name" value="${product.name}">
                                <input type="submit" class="btn btn-primary" value="Remove">
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <hr>
            <div class="container">
                <div class="row">
                    <div class="col-1">
                        <h5>Id</h5>
                    </div>
                    <div class="col-2">
                        <h5>Login</h5>
                    </div>
                    <div class="col-1">
                        <h5>First name</h5>
                    </div>
                    <div class="col-1">
                        <h5>Last name</h5>
                    </div>
                    <div class="col-2">
                        <h5>Email</h5>
                    </div>
                    <div class="col-1">
                        <h5>Score</h5>
                    </div>
                    <div class="col-1">
                        <h5>Role_id</h5>
                    </div>
                    <div class="col-1">
                        <h5>isBlocked</h5>
                    </div>
                    <div class="col-2"></div>
                    <c:forEach var="account" items="${accounts}">
                        <div class="col-1">
                            <p>${account.id}</p>
                        </div>
                        <div class="col-2">
                            <p>${account.login}</p>
                        </div>
                        <div class="col-1">
                            <p>${account.first_name}</p>
                        </div>
                        <div class="col-1">
                            <p>${account.last_name}</p>
                        </div>
                        <div class="col-2">
                            <p>${account.email}</p>
                        </div>
                        <div class="col-1">
                            <p>${account.score}</p>
                        </div>
                        <div class="col-1">
                            <p>${account.role_id}</p>
                        </div>
                        <div class="col-1">
                            <p>${account.isBlocked}</p>
                        </div>
                        <div class="col-1">
                            <form action="administration/blockAccount" method="post">
                                <input type="hidden" name="login" value="${account.login}">
                                <input type="submit" class="btn btn-primary" value="Block">
                            </form>
                        </div>
                        <div class="col-1">
                            <form action="administration/unblockAccount" method="post">
                                <input type="hidden" name="login" value="${account.login}">
                                <input type="submit" class="btn btn-primary" value="Unblock">
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>
