<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="messages" prefix="topupscore_jsp.">
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
            <div class="span12">
                <form class="form-horizontal span6" action="user/topUpScore" method="post">
                    <fieldset>
                        <legend><fmt:message key="payment"/></legend>

                        <div class="control-group">
                            <label class="control-label"><fmt:message key="amount_donate"/></label>
                            <div class="controls">
                                <input type="number" class="input-block-level" name="score" min="0" required>
                            </div>
                        </div>

                         <div class="control-group">
                            <label class="control-label"><fmt:message key="card_number"/></label>
                                <div class="row-fluid">
                                    <div class="span3">
                                        <input type="tel" class="input-block-level" inputmode="numeric" maxlength="19" pattern="[0-9\s]{13,19}" required>
                                    </div>
                                </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label"><fmt:message key="card_expiry_date"/></label>
                            <div class="controls">
                                <div class="row-fluid">
                                    <div class="span9">
                                        <label for="month"><fmt:message key="month"/></label>
                                        <input id="month" type="number" min="1" max="12" required>
                                    </div>
                                    <div class="span9 mt-1">
                                        <label for="year"><fmt:message key="year"/></label>
                                        <input type="number" id="year" min="22" max="26" required>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label"><fmt:message key="card_cvv"/></label>
                            <div class="controls">
                                <div class="row-fluid">
                                    <div class="span3">
                                        <input type="text" class="input-block-level" autocomplete="off" maxlength="3" pattern="\d{3}" title="Three digits at back of your card" required>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="form-actions mt-2">
                            <input type="submit" class="btn btn-primary" value="<fmt:message key="top_up"/>">
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</body>
</fmt:bundle>
</html>