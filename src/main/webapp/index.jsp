<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="messages" prefix="index_jsp.">
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title"/></title>
    <meta charset="UTF-8">
    <link rel="icon" href="img/icon.png" type="image/png">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/index.css">
</head>
    <body>
        <jsp:include page="fragments/header.jsp"></jsp:include>
        <p class="h4" style="color: ${sessionScope.color}" align="center">
            <fmt:message key="user_previous_action">
                <c:choose>
                    <c:when test="${sessionScope.locale eq 'uk'}">
                        <fmt:param value="${sessionScope.status_uk}"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:param value="${sessionScope.status}"/>
                    </c:otherwise>
                </c:choose>
            </fmt:message>
        </p>
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
                                                    <input type="submit" class="btn btn-primary" value="<fmt:message key="buy_for"/> ${product.price} ₴">
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <form action="login.jsp">
                                                    <input type="submit" class="btn btn-primary" id="inf" href="login.jsp" value="<fmt:message key="buy_for"/> ${product.price} ₴">
                                                    <label for="inf"><fmt:message key="you_must_be"/> <a href="login.jsp"><fmt:message key="logged_in"/></a> <fmt:message key="to_buy_this_product"/></label>
                                                </form>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p class="h4"><fmt:message key="no_product"/></p>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col-2">
                    <div class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <fmt:message key="sorting"/>
                        </button>
                        <fmt:bundle basename="messages" prefix="index_jsp.sorting.">
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <a class="dropdown-item" href="sortProductFromAToZ"><fmt:message key="from_A_to_Z"/></a>
                            <a class="dropdown-item" href="sortProductFromZToA"><fmt:message key="from_Z_to_A"/></a>
                            <a class="dropdown-item" href="sortProductFromLowToHigh"><fmt:message key="from_Low_to_High"/></a>
                            <a class="dropdown-item" href="sortProductFromHighToLow"><fmt:message key="from_High_to_Low"/></a>
                            <a class="dropdown-item" href="sortProductFromNewToOld"><fmt:message key="from_New_to_Old"/></a>
                            <a class="dropdown-item" href="sortProductFromOldToNew"><fmt:message key="from_Old_to_New"/></a>
                        </div>
                        </fmt:bundle>
                    </div>
                </div>
                <div class="col-3">
                    <form action="filterProductByPrice" method="get">
                        <div class="container">
                            <div class="row">
                                <div class="col-12">
                                    <fmt:message key="filters"/>
                                </div>
                                <fmt:bundle basename="messages" prefix="index_jsp.filters.price.">
                                <div class="col-12">
                                    <fmt:message key="name"/>
                                </div>
                                <div class="col-12">
                                    <fmt:message key="minimum_price"/>
                                </div>
                                <div class="col-12">
                                    <input type="number" name="startPrice" min="0" max="4999" maxlength="5" >
                                </div>
                                <div class="col-12">
                                    <fmt:message key="maximum_price"/>
                                </div>
                                <div class="col-12">
                                    <input type="number" name="endPrice" min="1" max="5000">
                                </div>
                                <br>
                                <div class="col-12 margin">
                                    <input class="btn btn-primary" type="submit" value="<fmt:message key="enter"/>">
                                </div>
                                </fmt:bundle>
                            </div>
                        </div>
                    </form>
                    <form action="filterProductByCategory" method="get">
                        <div class="container">
                            <div class="row">
                                <fmt:bundle basename="messages" prefix="index_jsp.filters.category.">
                                <div class="col-12">
                                    <fmt:message key="name"/>
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
                                    <input class="btn btn-primary" type="submit" value="<fmt:message key="enter"/>">
                                </div>
                                </fmt:bundle>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-3">
                    <form action="findProductByName" method="get">
                        <div class="container">
                            <div class="row">
                                <div class="col-8 mt-1">
                                    <input type="text" placeholder="<fmt:message key="find_placeholder"/>" name="name" required>
                                </div>
                                <div class="col-4">
                                    <input type="submit" style="margin-left: 2%" class="btn btn-primary" value="<fmt:message key="find"/>">
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
</fmt:bundle>
</html>
