<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title><spring:message code="operatorIndex.dataOperations"/></title>
    <link rel="shortcut icon" type="image/png" href="/avgustBel/resources/images/logo.png">
    <%--scripts--%>
    <script src="<c:url value='/resources/js/dataOperations.js' />"></script>
    <script type="text/javascript" src="js/jquery-1.11.3.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>

    <%--style--%>
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/operator.css' />" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/client_table.css' />" rel="stylesheet"/>
</head>
<body>

<div class="mainDiv">
    <jsp:include page="/WEB-INF/views/operator/header.jsp"/>
    <div class="container">
        <div class="row">

            <div class="col-md-12">
                <div class="panel panel-primary">
                    <div class="panel-heading"><spring:message code="dataOperation.dataUploading"/></div>
                    <div class="panel-body" style="height: 70%">

                        <form:form action="/avgustBel/report" method="get">

                            <div class="form-group input-group" style="padding-top: 20px;">

                                <span class="input-group-addon"><i
                                        class="glyphicon glyphicon-calendar"></i> <spring:message
                                        code="dataOperation.from"/></span>
                                <input id="datetimepickerFrom" name="fromInput" type="text" value="${from}"
                                       placeholder="<spring:message code="dataOperation.from"/>"
                                       onchange="enableGetClientArchBtn()" class="form-control">
                            </div>

                            <div class="form-group" style="padding-top: 20px;">
                                <div class="input-group">
                                    <span class="input-group-addon"><i
                                            class="glyphicon glyphicon-calendar"></i> <spring:message
                                            code="dataOperation.to"/></span>
                                    <input id="datetimepickerTo" name="toInput" type="text" value="${to}"
                                           placeholder="<spring:message code="dataOperation.to"/>"
                                           onchange="enableGetClientArchBtn()" class="form-control">
                                </div>
                            </div>
                            <div style="padding-top: 20px;">
                                <input type="submit" value="<spring:message code="dataOperation.uploadBtn"/>"
                                       id="getClArchBtn" disabled
                                       class="btn btn-info btn-block btn-lg">
                            </div>
                        </form:form>

                        <div style="max-height:50%; overflow:scroll;">
                            <table class="responstable" style="width: 95%" id="archTable">
                                <c:set var="clientListArch" scope="session" value="${clientListFromArchive}"/>
                                <c:if test="${clientListArch.size() > 0}">

                                    <tr>
                                        <th>№</th>
                                        <th><spring:message code='operator.trControlTable.carRegN'
                                                            javaScriptEscape='true'/></th>
                                        <th><spring:message code='operator.trControlTable.phoneN'
                                                            javaScriptEscape='true'/></th>
                                        <th><spring:message code='operator.trControlTable.destination'
                                                            javaScriptEscape='true'/></th>
                                        <th><spring:message code='client.company' javaScriptEscape='true'/></th>
                                        <th><spring:message code='operator.trControlTable.regTime'
                                                            javaScriptEscape='true'/></th>
                                        <th><spring:message code='operator.trControlTable.callTime'
                                                            javaScriptEscape='true'/></th>
                                        <th><spring:message code='operator.trControl.stock'
                                                            javaScriptEscape='true'/></th>
                                        <th><spring:message code='operator.trControl.ramp'
                                                            javaScriptEscape='true'/></th>
                                        <th><spring:message code='operator.trControlTable.enterTime'
                                                            javaScriptEscape='true'/></th>
                                        <th><spring:message code='operator.trControlTable.arriveTime'
                                                            javaScriptEscape='true'/></th>
                                        <th><spring:message code='operator.trControlTable.servedTime'
                                                            javaScriptEscape='true'/></th>
                                        <th><spring:message code='operator.trControlTable.returnTime'
                                                            javaScriptEscape='true'/></th>
                                        <th><spring:message code='operator.trControlTable.leaveTime'
                                                            javaScriptEscape='true'/></th>
                                        <th><spring:message code='operator.trControlTable.state'
                                                            javaScriptEscape='true'/></th>
                                    </tr>

                                    <c:forEach items="${clientListArch}" var="client" varStatus="clientLoop">
                                        <tr>
                                            <td>${clientLoop.index}</td>
                                            <td>${client.carN}</td>
                                            <td>${client.phoneN}</td>
                                            <td>${client.destination}</td>
                                            <td>${client.company}</td>
                                            <td>${client.regTime}</td>
                                            <td>${client.callTime}</td>
                                            <td>${client.stock}</td>
                                            <td>${client.ramp}</td>
                                            <td>${client.enterTime}</td>
                                            <td>${client.arrivedTime}</td>
                                            <td>${client.servedTime}</td>
                                            <td>${client.returnTime}</td>
                                            <td>${client.leaveTime}</td>
                                            <td>${client.state.state}</td>
                                        </tr>

                                    </c:forEach>
                                </c:if>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
    <div style="height: auto">
        <c:if test="${clientListArch.size() == 0}">
            <div class="alert alert-info"><img src='/avgustBel/resources/images/info.png'> Данные за указанный промежуток времени не найдены</div>
        </c:if>
        <c:if test="${clientListArch.size() > 0}">
            <div class="alert alert-success"><img src='/avgustBel/resources/images/ok.png'> Данные загружены успешно</div>
        </c:if>
    </div>

    <jsp:include page="/WEB-INF/views/operator/footer.jsp"/>
</div>
</body>


<link href="<c:url value='/resources/css/jquery.datetimepicker.css' />" rel="stylesheet"/>
<script src="<c:url value='/resources/js/jquery.datetimepicker.full.min.js' />"></script>

<script>
    $('#datetimepickerFrom').datetimepicker();
    $('#datetimepickerTo').datetimepicker();
</script>

</html>
