<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>header</title>

    <style>
        .mainHeader {
            max-width: none;
            font-size: 24px;
        }
    </style>
</head>
<body>

<form action="/avgustBel/user-client" method="get" id="exitForm">
</form>

<script>
    function formSubmit() {
        document.getElementById("exitForm").submit();
    }
</script>

<nav class="navbar navbar-inverse" style="border-radius: 0px;">
    <div class="container-fluid">
        <div class="navbar-header ">

            <div style="display: inline-block">
                <form name="submitFormDataOperation" method="GET" action="/avgustBel/client-download">
                    <label class="navbar-brand mainHeader"><spring:message code="client.uploading.title"/></label>
                </form>
            </div>
        </div>

        <div class="collapse navbar-collapse mainHeader" id="myNavbar">
            <jsp:include page="/WEB-INF/views/locale.jsp"/>

            <ul class="nav navbar-nav navbar-right">

                <li><a href="javascript:formSubmit()" title=" <spring:message code="client.backToOperationSelBtn"/>"
                       data-placement="bottom">
                    <span class="glyphicon glyphicon-circle-arrow-left mainHeader"
                          title=" <spring:message code="client.backToOperationSelBtn"/>"></span>
                    <spring:message code="client.backToOperationSelBtn"/>
                </a></li>
            </ul>


        </div>
    </div>
</nav>
</body>
</html>
