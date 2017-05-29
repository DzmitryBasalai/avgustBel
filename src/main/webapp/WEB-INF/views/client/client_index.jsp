<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><spring:message code="clientIndex.title"/></title>
    <link rel="shortcut icon" type="image/png" href="/avgustBel/resources/images/logo.png">

    <link href="<c:url value='/resources/css/login.css' />" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/client.css' />" rel="stylesheet"/>


    <script type="text/javascript" src="js/jquery-1.11.3.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/client.js"></script>

</head>
<body>

<img border="0" src="/avgustBel/resources/images/logo.svg" style=" height: 130px; width: 220px; margin-left: 30px">

<div class="container,form-horizontal, mainDivLogin" style="background-color: #e4e4e4;">


    <jsp:include page="/WEB-INF/views/client/locale.jsp"/>

    <span class="mainFontSize"><spring:message code="clientIndex.title"/></span>

    <div class="form-group col-sm-offset-1 col-sm-10" style="margin-top: 15px;">
        <form action="/avgustBel/client-upload" method="get">
            <input type="submit" value="<spring:message code="clientIndex.uploading"/>"
                   class="btn btn-info btn-block btn-lg mainFontSize">
        </form>
    </div>

    <div class="form-group col-sm-offset-1 col-sm-10">
        <form action="/avgustBel/client-download" method="get">
            <input type="submit" value="<spring:message code="clientIndex.downloading"/>"
                   class="btn btn-info btn-block btn-lg mainFontSize">
        </form>
    </div>
</div>

</body>
</html>
