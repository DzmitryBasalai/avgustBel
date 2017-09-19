<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>header</title>

    <style>
        .mainHeader {
            max-width: none;
            font-size: 30px;
        }
    </style>
    <script type="text/javascript">
        var locale = new Array();
        locale['client.error.companyInput'] = "<spring:message code='client.error.companyInput' javaScriptEscape='true' />";
        locale['client.error.carInput'] = "<spring:message code='client.error.carInput' javaScriptEscape='true' />";
        locale['client.error.phoneNumber'] = "<spring:message code='client.error.phoneNumber' javaScriptEscape='true' />";
        locale['client.error.checkInputData'] = "<spring:message code='client.error.checkInputData' javaScriptEscape='true' />";
        locale['client.error.phoneAndCarRequired'] = "<spring:message code='client.error.phoneAndCarRequired' javaScriptEscape='true' />";
        locale['client.error.companyPhoneAndCarRequired'] = "<spring:message code='client.error.companyPhoneAndCarRequired' javaScriptEscape='true' />";
        locale['client.confirmation'] = "<spring:message code='client.confirmation' javaScriptEscape='true' />";
        locale['client.carNumber'] = "<spring:message code='client.carNumber' javaScriptEscape='true' />";
        locale['client.phoneNumber'] = "<spring:message code='client.phoneNumber' javaScriptEscape='true' />";
        locale['client.company'] = "<spring:message code='client.company' javaScriptEscape='true' />";
    </script>
</head>
<body>

<form action="/avgustBel/user-client" method="get" id="exitForm">
</form>

<script>
    function formSubmit() {
        document.getElementById("exitForm").submit();
    }
</script>

<nav class="navbar navbar-inverse" style="border-radius:0;">
    <div class="container-fluid">
        <div class="navbar-header ">

            <div style="display: inline-block">
                <img border="0" src="/avgustBel/resources/images/logo.svg" style=" height: 80px; width: 220px;">
            </div>
        </div>

        <div class="collapse navbar-collapse mainHeader" id="myNavbar">
            <jsp:include page="/WEB-INF/views/client/locale.jsp"/>

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
