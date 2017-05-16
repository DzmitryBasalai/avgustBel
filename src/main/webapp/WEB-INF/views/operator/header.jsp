<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Header</title>
</head>
<body>

<c:url value="/j_spring_security_logout" var="logoutUrl"/>
<!-- csrt for log out-->
<form action="${logoutUrl}" method="post" id="logoutForm">
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</form>

<script>
    function formSubmit() {
        document.getElementById("logoutForm").submit();
    }
</script>


<nav class="navbar navbar-inverse" style="border-radius: 0px;">
    <div class="container-fluid">
        <div class="navbar-header">

            <div style="display: inline-block">
                <form name="submitFormTrControl" method="GET" action="/avgustBel/operator-transportControl">
                    <a class="navbar-brand" href="javascript:document.submitFormTrControl.submit()"
                       style="font-size: 22px"><spring:message code="operatorIndex.transportControl"/></a>
                </form>
            </div>

            <div style="display: inline-block">
                <form name="submitFormDataOperation" method="GET" action="/avgustBel/operator-dataOperations">
                    <a class="navbar-brand" href="javascript:document.submitFormDataOperation.submit()"
                       style="font-size: 22px"><spring:message code="operatorIndex.dataOperations"/></a>
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}"/>
                </form>
            </div>
        </div>

        <div class="collapse navbar-collapse" id="myNavbar">
            <jsp:include page="/WEB-INF/views/locale.jsp"/>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="javascript:formSubmit()" title=" <spring:message code="exitBtn"/>"
                       data-placement="bottom">
                    <span class="glyphicon glyphicon-log-out" title=" <spring:message code="exitBtn"/>"></span>
                    <spring:message code="exitBtn"/>
                </a></li>
            </ul>


        </div>
    </div>
</nav>
</body>
</html>
