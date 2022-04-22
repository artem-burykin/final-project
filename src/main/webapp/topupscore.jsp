<%--
  Created by IntelliJ IDEA.
  User: arta2
  Date: 11.04.2022
  Time: 13:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Top up</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="span12">
                <form class="form-horizontal span6" action="topUpScore" method="post">
                    <fieldset>
                        <legend>Payment</legend>

                        <div class="control-group">
                            <label class="control-label">Amount, which you want donate</label>
                            <div class="controls">
                                <input type="number" class="input-block-level" name="score" min="0" required>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label">Card Number</label>
                            <div class="controls">
                                <div class="row-fluid">
                                    <div class="span3">
                                        <input type="text" class="input-block-level" autocomplete="off" maxlength="4" pattern="\d{4}" title="First four digits" required>
                                    </div>
                                    <div class="span3">
                                        <input type="text" class="input-block-level" autocomplete="off" maxlength="4" pattern="\d{4}" title="Second four digits" required>
                                    </div>
                                    <div class="span3">
                                        <input type="text" class="input-block-level" autocomplete="off" maxlength="4" pattern="\d{4}" title="Third four digits" required>
                                    </div>
                                    <div class="span3">
                                        <input type="text" class="input-block-level" autocomplete="off" maxlength="4" pattern="\d{4}" title="Fourth four digits" required>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label">Card Expiry Date</label>
                            <div class="controls">
                                <div class="row-fluid">
                                    <div class="span9">
                                        <input type="number" min="1" max="12" required>
                                    </div>
                                    <div class="span3">
                                        <input type="number" min="2022" required>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label">Card CVV</label>
                            <div class="controls">
                                <div class="row-fluid">
                                    <div class="span3">
                                        <input type="text" class="input-block-level" autocomplete="off" maxlength="3" pattern="\d{3}" title="Three digits at back of your card" required>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="form-actions">
                            <input type="submit" class="btn btn-primary" value="Submit">
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
