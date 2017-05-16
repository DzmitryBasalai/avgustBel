<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title><spring:message code="operatorIndex.dataOperations"/></title>
    <link rel="shortcut icon" type="image/png" href="/avgustBel/resources/images/logo.png">
    <%--scripts--%>
    <script src="<c:url value='/resources/js/operator.js' />"></script>
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
            <div class="col-sm-6">
                <div class="panel panel-primary">
                    <div class="panel-heading"><spring:message code="dataOperation.dataDownloading"/></div>
                    <div class="panel-body" style="height: 67%">

                        <form method="POST"
                              action="/avgustBel/operator-downloadFile?${_csrf.parameterName}=${_csrf.token}"
                              enctype="multipart/form-data">
                            <div class="form-group">
                                <label for="upFile"><spring:message code="operator.fileToDownload"/></label>
                                <span class="btn btn-default btn-file">
                                        <spring:message code="dataOperation.browse"/>&hellip;
                                        <input type="file" name="file" id="upFile">
                                    </span>
                            </div>
                            <input type="submit" value="<spring:message code="operator.downloadBtn"/>"
                                   class="btn btn-info btn-block btn-lg">


                        </form>

                        <div style="max-height:80%; overflow:auto;">
                            <table class="responstable" style="width: 100%">
                                <c:set var="orderList" scope="session" value="${orderList}"/>
                                <c:if test="${orderList.size() > 0}">
                                    <tr>
                                        <th>Номер Заказа</th>
                                    </tr>
                                </c:if>
                                <c:forEach items="${orderList}" var="order">
                                    <tr>
                                        <td>${order.order}</td>
                                    </tr>

                                </c:forEach>
                            </table>
                        </div>

                    </div>
                    <div class="panel-footer" style="height: 105px;">
                        <c:if test="${fileDownloadMes.length() > 0}">
                            <c:choose>
                                <c:when test="${state==0}">
                                    <div class="alert alert-danger"><img
                                            src='/avgustBel/resources/images/alert.png'> ${fileDownloadMes} </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="alert alert-success"><img
                                            src='/avgustBel/resources/images/ok.png'> ${fileDownloadMes} </div>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </div>
                    <%-- <div class="panel-footer"><spring:message code="dataOperation.message"/> ${fileDownloadMes}</div>--%>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="panel panel-primary">
                    <div class="panel-heading"><spring:message code="dataOperation.dataUploading"/></div>
                    <div class="panel-body" style="height: 67%">
                        <form action="/avgustBel/report" method="get">
                            <div class="form-group input-group" style="padding-top: 50px;">

                                <span class="input-group-addon"><i
                                        class="glyphicon glyphicon-calendar"></i> <spring:message
                                        code="dataOperation.from"/></span>
                                <input id="datetimepickerFrom" name="fromInput" type="text"
                                       placeholder="<spring:message code="dataOperation.from"/>"
                                       onchange="writeExcelFile()" class="form-control">
                            </div>

                            <div class="form-group" style="padding-top: 50px;">
                                <div class="input-group">
                                    <span class="input-group-addon"><i
                                            class="glyphicon glyphicon-calendar"></i> <spring:message
                                            code="dataOperation.to"/></span>
                                    <input id="datetimepickerTo" name="toInput" type="text"
                                           placeholder="<spring:message code="dataOperation.to"/>"
                                           onchange="writeExcelFile()" class="form-control">
                                </div>
                            </div>
                            <div style="padding-top: 50px;">
                                <input type="submit" value="<spring:message code="dataOperation.uploadBtn"/>"
                                       id="writeToExcelBtn" disabled
                                       class="btn btn-info btn-block btn-lg">
                            </div>
                        </form>
                    </div>
                    <div class="panel-footer" style="height: 105px;"></div>
                </div>
            </div>

        </div>
    </div>


    <jsp:include page="/WEB-INF/views/footer.jsp"/>
</div>
</body>


<link href="<c:url value='/resources/css/jquery.datetimepicker.css' />" rel="stylesheet"/>
<script src="<c:url value='/resources/js/jquery.datetimepicker.full.min.js' />"></script>

<script>
    $('#datetimepickerFrom').datetimepicker();
    $('#datetimepickerTo').datetimepicker();
</script>

</html>
