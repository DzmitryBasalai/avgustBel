<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title> <spring:message code="avgustBel"/></title>
    <link rel="shortcut icon" type="image/png" href="/avgustBel/resources/images/logo.png">

    <link href="<c:url value='/resources/css/login.css' />" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet"/>

    <script type="text/javascript" src="js/jquery-1.11.3.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
</head>

<body>

<div class="container,form-horizontal, mainDivLogin">

    <jsp:include page="/WEB-INF/views/locale.jsp"/>

    <div class="form-group col-sm-offset-1 col-sm-10">

            <form action="/avgustBel/user-client" method="get">
                <input type="submit" value="<spring:message code="client"/>" class="btn btn-info btn-block btn-lg">
            </form>
    </div>


    <div class="form-group col-sm-offset-1 col-sm-10">

            <form action="/avgustBel/user-operator" method="get">
                <input type="submit" value="<spring:message code="operator"/>" class="btn btn-info btn-block btn-lg">
            </form>

    </div>

</div>

</body>
</html>
