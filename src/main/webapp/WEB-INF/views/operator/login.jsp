<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><spring:message code="login.title"/></title>
    <link rel="shortcut icon" type="image/png" href="/avgustBel/resources/images/logo.png">

    <link href="<c:url value='/resources/css/login.css' />" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet"/>

    <script type="text/javascript" src="js/jquery-1.11.3.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>

</head>

<body>

<div class="container, mainDivLogin">
    <c:url value="/j_spring_security_check" var="loginUrl"/>
    <form method="POST" action="${loginUrl}" id="login" class="form-horizontal">

        <jsp:include page="/WEB-INF/views/operator/locale.jsp"/>
        <spring:message code="backBtn" var="backBtn"/>
        <h1><spring:message code="login.title"/></h1>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <c:if test="${not empty msg}">
            <div class="msg">${msg}</div>
        </c:if>

        <br/>
        <div>

            <div class=" input-group" style="padding:10px 20px  20px 30px;">
                <spring:message code="login.username" var="username"/>

                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                <input type="text" title="${username}" placeholder="${username}"
                       class="form-control" name="username"/>
            </div>

            <div class=" input-group" style="padding:0 20px  20px 30px;">
                <spring:message code="login.password" var="pass"/>

                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input type="password" title="${pass}" placeholder="${pass}"
                       class="form-control" name="password"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-8">
                    <input title="<spring:message code="enterBtn"/>"
                           type="submit"
                           value="<spring:message code="enterBtn"/>" class="btn btn-info btn-block">
                </div>
            </div>
        </div>



        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
    </form>
</div>

</body>
</html>
