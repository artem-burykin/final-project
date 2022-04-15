<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>Administration</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
</head>
<body>
    <jsp:include page="fragments/header.jsp"></jsp:include>
    <h3>Hello, ${sessionScope.login}</h3>
    <div class="container">
        <div class="row">
            <h2>Add new product</h2>
            <form method="post" action="addNewProduct">
                <div class="col-4">
                    <input type="text" class="form-control" name="name" placeholder="Name of product" required>
                </div>
                <div class="col-8"></div>
                <br>
                <div class="col-1">
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
                        Logo
                    </div>
                    <div class="col-5">
                        <h5>Description</h5>
                    </div>
                    <div class="col-1">
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
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#updateLogoModal">
                                Update
                            </button>

                            <div class="modal fade" id="updateLogoModal" tabindex="-1" aria-labelledby="updateLogoModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="updateLogoModalLabel">Updating logo</h5>
                                            <button type="button" class="btn-close" data-dismiss="modal" aria-label="close"></button>
                                        </div>
                                        <form action="changeProductLogo" method="post">
                                            <div class="modal-body">
                                                <input type="hidden" name="name" value="${product.name}">
                                                <input type="text" name="logo" placeholder="New product's logo" required>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                <input type="submit" class="btn btn-primary" value="Update">
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-5">
                            <p>${product.description}</p>
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#updateDescriptionModal">
                                Update
                            </button>

                            <div class="modal fade" id="updateDescriptionModal" tabindex="-1" aria-labelledby="updateDescriptionModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="updateDescriptionModalLabel">Updating description</h5>
                                            <button type="button" class="btn-close" data-dismiss="modal" aria-label="close"></button>
                                        </div>
                                        <form action="changeProductDescription" method="post">
                                            <div class="modal-body">
                                                <input type="hidden" name="name" value="${product.name}">
                                                <input type="text" name="description" placeholder="New product's description" required>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                <input type="submit" class="btn btn-primary" value="Update">
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-1">
                            <p>${product.price}</p>
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#updatePriceModal">
                                Update
                            </button>

                            <div class="modal fade" id="updatePriceModal" tabindex="-1" aria-labelledby="updatePriceModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="updatePriceModalLabel">Updating price</h5>
                                            <button type="button" class="btn-close" data-dismiss="modal" aria-label="close"></button>
                                        </div>
                                        <form action="changeProductPrice" method="post">
                                            <div class="modal-body">
                                                <input type="hidden" name="name" value="${product.name}">
                                                <input type="number" name="price" min="0" required>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                <input type="submit" class="btn btn-primary" value="Update">
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-1">
                            <form action="removeProduct" method="post">
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
                            <form action="blockAccount" method="post">
                                <input type="hidden" name="login" value="${account.login}">
                                <input type="submit" class="btn btn-primary" value="Block">
                            </form>
                        </div>
                        <div class="col-1">
                            <form action="unblockAccount" method="post">
                                <input type="hidden" name="login" value="${account.login}">
                                <input type="submit" class="btn btn-primary" value="Unblock">
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <form action="">

            </form>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>
