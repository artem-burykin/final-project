<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="messages" prefix="admin_jsp.">
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title"/></title>
    <meta charset="UTF-8">
    <link rel="icon" href="img/icon.png" type="image/png">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
</head>
<body>
    <jsp:include page="fragments/header.jsp"></jsp:include>
    <p class="h3">
        <fmt:message key="hello_admin">
            <fmt:param value="${sessionScope.login}"/>
        </fmt:message>
    </p>
    <p class="h4" align="center" style="color: ${sessionScope.admin_color}">
        <fmt:message key="previous_action">
            <c:choose>
                <c:when test="${sessionScope.locale eq 'uk'}">
                    <fmt:param value="${sessionScope.admin_status_uk}"/>
                </c:when>
                <c:otherwise>
                    <fmt:param value="${sessionScope.admin_status}"/>
                </c:otherwise>
            </c:choose>
        </fmt:message>
    </p>
    <div class="container">
        <div class="row">
            <p class="h2"><fmt:message key="add_new_product"/></p>
            <fmt:bundle basename="messages" prefix="admin_jsp.add_new_product.">
                <form method="post" action="administration/addNewProduct">
                    <div class="col-4">
                        <input type="text" class="form-control" name="name"
                               placeholder="<fmt:message key="name_of_product"/>" required>
                    </div>
                    <div class="col-8"></div>
                    <br>
                    <div class="col-2">
                        <input type="number" class="form-control" name="price" min="0"
                               placeholder="<fmt:message key="price_of_product"/>" required>
                    </div>
                    <br>
                    <div class="col-12">
                        <c:forEach var="category" items="${categories}">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="category_id" value="${category.id}"
                                       id="flexRadioDefault1" required>
                                <label class="form-check-label" for="flexRadioDefault1">
                                        ${category.name}
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                    <br>
                    <div class="col-4">
                        <input type="text" class="form-control" name="logo"
                               placeholder="<fmt:message key="product_logo"/>">
                    </div>
                    <br>
                    <div class="col-12">
                        <input type="text" class="form-control" name="description"
                               placeholder="<fmt:message key="product_description"/>">
                    </div>
                    <br>
                    <input type="submit" class="btn btn-primary" value="<fmt:message key="button_add"/>">
                </form>
            </fmt:bundle>

            <hr class="mt-2">
            <fmt:bundle basename="messages" prefix="admin_jsp.list_with_product.">
            <div class="container">
                <div class="row">
                    <div class="col-1">
                        <p class="h5">Id</p>
                    </div>
                    <div class="col-2">
                        <p class="h5"><fmt:message key="name"/></p>
                    </div>
                    <div class="col-2">
                        <p class="h5"><fmt:message key="logo"/></p>
                    </div>
                    <div class="col-4">
                        <p class="h5"><fmt:message key="description"/></p>
                    </div>
                    <div class="col-2">
                        <p class="h5"><fmt:message key="price"/></p>
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
                            <p class="h5" align="center"><fmt:message key="or"/></p>
                            <hr>
                            <form action="administration/changeProductLogo" method="post">
                                <input type="hidden" name="name" value="${product.name}">
                                <input type="text" name="logo" placeholder="<fmt:message key="new_logo"/>">
                                <input type="submit" class="btn btn-primary mt-1" value="<fmt:message key="button_update"/>">
                            </form>
                        </div>
                        <div class="col-4">
                            <p>${product.description}</p>
                            <hr>
                            <p class="h5" align="center"><fmt:message key="or"/></p>
                            <hr>
                            <form action="administration/changeProductDescription" method="post">
                                <input type="hidden" name="name" value="${product.name}">
                                <input type="text" name="description" placeholder="<fmt:message key="new_description"/>">
                                <br>
                                <input type="submit" class="btn btn-primary mt-1" value="<fmt:message key="button_update"/>">
                            </form>
                        </div>
                        <div class="col-2">
                            <p>${product.price}</p>
                            <hr>
                            <p class="h5" align="center"><fmt:message key="or"/></p>
                            <hr>
                            <form action="administration/changeProductPrice" method="post">
                                <input type="hidden" name="name" value="${product.name}">
                                <input type="number" min="0" name="price" placeholder="<fmt:message key="new_price"/>">
                                <input type="submit" class="btn btn-primary mt-1" value="<fmt:message key="button_update"/>">
                            </form>
                        </div>
                        <div class="col-1">
                            <form action="administration/removeProduct" method="post">
                                <input type="hidden" name="name" value="${product.name}">
                                <input type="submit" class="btn btn-primary" value="<fmt:message key="button_remove"/>">
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </div>
            </fmt:bundle>

            <hr class="mt-2">
            <fmt:bundle basename="messages" prefix="admin_jsp.list_with_user.">
            <div class="container">
                <div class="row">
                    <div class="col-1">
                        <p class="h5">Id</p>
                    </div>
                    <div class="col-2">
                        <p class="h5"><fmt:message key="login"/></p>
                    </div>
                    <div class="col-1">
                        <p class="h5"><fmt:message key="first_name"/></p>
                    </div>
                    <div class="col-1">
                        <p class="h5"><fmt:message key="last_name"/></p>
                    </div>
                    <div class="col-2">
                        <p class="h5">Email</p>
                    </div>
                    <div class="col-1">
                        <p class="h5"><fmt:message key="score"/></p>
                    </div>
                    <div class="col-1">
                        <p class="h5">Role_id</p>
                    </div>
                    <div class="col-1">
                        <p class="h5">isBlocked</p>
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
                                <input type="submit" class="btn btn-primary" value="<fmt:message key="block"/>">
                            </form>
                        </div>
                        <div class="col-1">
                            <form action="administration/unblockAccount" method="post">
                                <input type="hidden" name="login" value="${account.login}">
                                <input type="submit" class="btn btn-primary" value="<fmt:message key="unblock"/>">
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </div>
            </fmt:bundle>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</fmt:bundle>
</html>
