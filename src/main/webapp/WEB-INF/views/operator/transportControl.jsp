<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><spring:message code="operatorIndex.transportControl"/></title>
    <link rel="shortcut icon" type="image/png" href="/avgustBel/resources/images/logo.png">

    <%--scripts--%>
    <script type="text/javascript" src="js/jquery-1.11.3.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script src="<c:url value='/resources/js/operator.js' />"></script>
    <script type="text/javascript" src="js/jquery.simplePagination.js"></script>
    <script type="text/javascript">
        var locale = new Array();
        locale['client.isFoundInDb'] = "<spring:message code='client.isFoundInDb' javaScriptEscape='true' />";
        locale['operator.trControlTable.carRegN'] = "<spring:message code='operator.trControlTable.carRegN' javaScriptEscape='true' />";
        locale['operator.trControlTable.phoneN'] = "<spring:message code='operator.trControlTable.phoneN' javaScriptEscape='true' />";
        locale['operator.trControlTable.destination'] = "<spring:message code='operator.trControlTable.destination' javaScriptEscape='true' />";
        locale['operator.trControl.orderN'] = "<spring:message code='operator.trControl.orderN' javaScriptEscape='true' />";
        locale['operator.trControlTable.regTime'] = "<spring:message code='operator.trControlTable.regTime' javaScriptEscape='true' />";
        locale['operator.trControlTable.callTime'] = "<spring:message code='operator.trControlTable.callTime' javaScriptEscape='true' />";
        locale['operator.trControl.stock'] = "<spring:message code='operator.trControl.stock' javaScriptEscape='true' />";
        locale['operator.trControl.ramp'] = "<spring:message code='operator.trControl.ramp' javaScriptEscape='true' />";
        locale['operator.trControlTable.arriveTime'] = "<spring:message code='operator.trControlTable.arriveTime' javaScriptEscape='true' />";
        locale['operator.trControlTable.servedTime'] = "<spring:message code='operator.trControlTable.servedTime' javaScriptEscape='true' />";
        locale['operator.trControlTable.returnTime'] = "<spring:message code='operator.trControlTable.returnTime' javaScriptEscape='true' />";
        locale['operator.trControlTable.state'] = "<spring:message code='operator.trControlTable.state' javaScriptEscape='true' />";

        locale['operator.trControl.operationByBtns'] = "<spring:message code='operator.trControl.callBtn' javaScriptEscape='true' />";
        locale['operator.trControl.arrivedBtn'] = "<spring:message code='operator.trControl.arrivedBtn' javaScriptEscape='true' />";
        locale['operator.trControl.servedBtn'] = "<spring:message code='operator.trControl.servedBtn' javaScriptEscape='true' />";
        locale['operator.trControl.returnBtn'] = "<spring:message code='operator.trControl.returnBtn' javaScriptEscape='true' />";

        locale['operator.rampN'] = "<spring:message code='operator.rampN' javaScriptEscape='true' />";
        locale['operator.selectRamp'] = "<spring:message code='operator.selectRamp' javaScriptEscape='true' />";
    </script>

    <%--style--%>
    <link href="<c:url value='/resources/css/client_table.css' />" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/operator.css' />" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/simplePagination.css' />" rel="stylesheet"/>
</head>
<body>

<div class="mainDiv">

    <jsp:include page="/WEB-INF/views/operator/header.jsp"/>

    <div class="container-fluid">

        <div class="col-sm-3 " style="width: 20%; min-height: 86%;">
            <h4><spring:message code="operatorIndex.transportControl"/></h4>

            <form method="POST" id="trControlFormId">
                <fieldset>
                    <div>
                        <spring:message code="operator.trControlTable.carRegN" var="carNum"/>
                        <label class="control-label">
                            <img border="0" src="/avgustBel/resources/images/car.png"> ${carNum}</label>
                        <input
                                title="${carNum}"
                                placeholder="${carNum}"
                                onkeyup="checkCarNajax()"
                                class="form-control"
                                id="carNumberInputId"
                                data-placement="bottom"/>
                    </div>
                    <br>
                    <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')||pageContext.request.isUserInRole('ROLE_OPERATOR_LOAD')||pageContext.request.isUserInRole('ROLE_OPERATOR_UNLOAD')}">
                        <div>
                            <spring:message code="operator.trControl.stock" var="stock"/>
                            <label class="control-label">
                                <img border="0" src="/avgustBel/resources/images/stock.png"> ${stock}</label>

                            <spring:message code="operator.stockN" var="stockN"/>
                            <select class="form-control" id="selectStockId" onchange="selectStock()">
                                <option value=""><spring:message code="operator.selectStock"/></option>
                                <option value="1">${stockN}1</option>
                                <option value="2">${stockN}2</option>
                                <option value="3">${stockN}3</option>
                                <option value="4">${stockN}4</option>
                                <option value="5">${stockN}5</option>
                            </select>
                        </div>
                        <br>

                        <div>
                            <spring:message code="operator.trControl.ramp" var="ramp"/>
                            <label class="control-label">
                                <img border="0" src="/avgustBel/resources/images/ramp.png"> ${ramp}</label>

                            <select class="form-control" id="selectRampId" disabled onchange="selectRamp()">
                                <option value=""><spring:message code="operator.selectRamp"/></option>
                            </select>
                        </div>
                    </c:if>
                </fieldset>
            </form>

            <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')||pageContext.request.isUserInRole('ROLE_OPERATOR_LOAD')||pageContext.request.isUserInRole('ROLE_OPERATOR_UNLOAD')}">
                <div>
                    <input type="submit" name="callBtn" value="<spring:message code="operator.trControl.callBtn"/>"
                           id="callBtn" disabled onclick="serviceBtnJs(this.value)" class="btn btn-info btn-block">
                </div>
                <br>
                <div>
                    <input type="submit" name="arrivedBtn"
                           value="<spring:message code="operator.trControl.arrivedBtn"/>"
                           id="arrivedBtn" disabled onclick="serviceBtnJs(this.value)" class="btn btn-info btn-block">
                </div>
                <br>
                <div>
                    <input type="submit" name="servedBtn" value="<spring:message code="operator.trControl.servedBtn"/>"
                           id="servedBtn" disabled onclick="serviceBtnJs(this.value)" class="btn btn-info btn-block">
                </div>
                <br/>
                <div>
                    <input type="submit" name="returnBtn" value="<spring:message code="operator.trControl.returnBtn"/>"
                           id="returnBtn" disabled onclick="serviceBtnJs(this.value)" class="btn btn-info btn-block">
                </div>
                <br/>
            </c:if>

            <c:if test="${pageContext.request.isUserInRole('ROLE_SECURITY') || pageContext.request.isUserInRole('ROLE_ADMIN')}">
                <div>
                    <input type="submit" name="enterBtn" value="<spring:message code="operator.trControl.enterBtn"/>"
                           id="enterBtn" disabled onclick="serviceBtnJs(this.value)" class="btn btn-info btn-block">
                </div>
            </c:if>
        </div>


        <div class="col-sm-9" style="min-width: 80%; height: auto;">
            <h4>
                <small><spring:message code="client.queue"/>
                    <span id="clientsCount"></span>
                </small>
            </h4>
            <div id="trCntrlMes" style="margin-top: 15px;"></div>
            <hr style="border-top: 1px solid rgba(84,84,84,0.95);">

            <div id="mainselection">
                <select id="selectBox" onchange="changeFunc();">
                    <option value="5">5</option>
                    <option value="10">10</option>
                    <option value="15">15</option>
                    <option value="20">20</option>
                </select>
            </div>


            <div id="divPagination" class="pagination" style="padding: 0px 5px 5px 10px;"></div>
            <br/>

            <table id="table" class="responstable">
            </table>

        </div>
    </div>

    <jsp:include page="/WEB-INF/views/operator/footer.jsp"/>

</div>

</body>
</html>
