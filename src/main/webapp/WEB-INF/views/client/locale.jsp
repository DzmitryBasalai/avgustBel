<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>locale</title>
    <link href="<c:url value='/resources/css/login.css' />" rel="stylesheet"/>
    <script src="<c:url value='/resources/js/keyboard.js' />"></script>
</head>
<body>
<ul class="hr">
    <li>
        <a href="?lang=ru" data-toggle="ruTooltip"  data-placement="bottom" title="Русский(Ру)">
            <img border="0" src="/avgustBel/resources/images/ru.svg" style="height: 30px; width: 40px" onclick="changeKeyboardLang(ru)">
        </a>
    </li>
    <li>
        <a href="?lang=en"   data-placement="bottom" title="English(UK)" style="padding-left:10px;" >
            <img border="0" src="/avgustBel/resources/images/gb.svg" style="height: 30px; width: 40px" onclick="changeKeyboardLang(en)">
        </a>
    </li>
</ul>
</select>
</body>
</html>
