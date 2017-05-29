<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title><spring:message code="clientIndex.uploading"/></title>
    <link rel="shortcut icon" type="image/png" href="/avgustBel/resources/images/logo.png">

    <%--scripts--%>
    <script type="text/javascript" src="js/jquery-1.11.3.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script src="<c:url value='/resources/js/client.js' />"></script>
    <script src="<c:url value='/resources/js/keyboard.js' />"></script>
    <link href="<c:url value='/resources/css/client.css' />" rel="stylesheet"/>

    <%--styles--%>
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/login.css' />" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/operator.css' />" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/keyboard.css' />" rel="stylesheet"/>

</head>
<body>
<div class="mainDiv">
    <jsp:include page="/WEB-INF/views/client/header.jsp"/>
    <div class="container">
        <div class="row" >
            <div class="col-sm-12" >
                <div class="panel panel-primary">
                    <div class="panel-heading"><spring:message code="client.uploading.title"/></div>


                    <div class="panel-body" style="height: 79%">
                        <div class="container, mainDivLogin">

                            <form:form commandName="client" class="form-horizontal">
                                <fieldset>
                                    <div class="form-group">
                                        <spring:message code="client.carNumber" var="carN"/>
                                        <label class="control-label col-sm-2">
                                            <img border="0" src="/avgustBel/resources/images/car.png"> ${carN}</label>

                                        <div class="col-sm-10">
                                            <form:input title="${carN}"
                                                        placeholder="${carN}"
                                                        path="carN"
                                                        class="form-control keyboardInput mainFontSize"
                                                        id="carNInput"/>
                                        </div>

                                    </div>

                                    <div class="form-group">
                                        <spring:message code="client.phoneNumber" var="phoneN"/>

                                        <label class="control-label col-sm-2">
                                            <span class=" glyphicon glyphicon-earphone"></span> ${phoneN}</label>
                                        <div class="col-sm-10">
                                            <form:input title="${phoneN}"
                                                        placeholder="${phoneN}"
                                                        path="phoneN"
                                                        class="form-control keyboardInput mainFontSize"
                                                        id="phoneNInput"/>
                                        </div>

                                    </div>
                                </fieldset>

                            </form:form>

                            <div class="col-sm-offset-2 col-sm-10">
                                <input title="<spring:message code="enterBtn"/>"
                                       type="submit" value="<spring:message code="client.registrationBtn"/>"
                                       class="btn btn-info btn-block mainFontSize" name="uploadBtn" id="clinetUpRegBtn" onclick="validate(this.name)">
                            </div>

                        </div>
                        <div id="footerMes"></div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
