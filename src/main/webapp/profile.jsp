<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="messages" prefix="profile_jsp.">
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title"/></title>
    <meta charset="UTF-8">
    <link rel="icon" href="${pageContext.request.contextPath}/img/icon.png" type="image/png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
</head>
<body>
    <jsp:include page="fragments/header.jsp"></jsp:include>
    <div class="container">
        <div class="row">
            <div class="col-3">
                <p class="h3">
                    <fmt:message key="hello_user">
                        <fmt:param value="${sessionScope.login}"/>
                    </fmt:message>
                </p>
            </div>
            <div class="col-6">
                <p class="h2"><fmt:message key="list_subscription"/></p>
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
                <p class="h4"><fmt:message key="score_value"><fmt:param value="${sessionScope.score}"/></fmt:message></p>
                <a href="topupscore.jsp" class="btn btn-primary"><fmt:message key="top_up"/></a>
            </div>
        </div>
    </div>
</body>
</fmt:bundle>
</html>