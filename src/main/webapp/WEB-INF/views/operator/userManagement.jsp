<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="userManagement.title"/></title>

    <link rel="shortcut icon" type="image/png" href="/avgustBel/resources/images/logo.png">

    <script type="text/javascript" src="js/jquery-1.11.3.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script src="<c:url value='/resources/js/userManagement.js' />"></script>
    <script type="text/javascript">
        var locale = new Array();
        locale['userManag.AddUser.ok'] = "<spring:message code='userManag.AddUser.ok' javaScriptEscape='true' />";
        locale['userManag.AddUser.error'] = "<spring:message code='userManag.AddUser.error' javaScriptEscape='true' />";
        locale['userManag.AddUser.alreadyExists'] = "<spring:message code='userManag.AddUser.alreadyExists' javaScriptEscape='true' />";

        locale['userManag.DeleteUser.ok'] = "<spring:message code='userManag.DeleteUser.ok' javaScriptEscape='true' />";
        locale['userManag.DeleteUser.error'] = "<spring:message code='userManag.DeleteUser.error' javaScriptEscape='true' />";
        locale['userManag.DeleteUser.noSuchUser'] = "<spring:message code='userManag.DeleteUser.noSuchUser' javaScriptEscape='true' />";

        locale['userManag.ModifyUser.ok'] = "<spring:message code='userManag.ModifyUser.ok' javaScriptEscape='true' />";
        locale['userManag.ModifyUser.error'] = "<spring:message code='userManag.ModifyUser.error' javaScriptEscape='true' />";

        locale['userManag.verific.userName'] = "<spring:message code='userManag.verific.userName' javaScriptEscape='true' />";
        locale['userManag.verefic.pass'] = "<spring:message code='userManag.verefic.pass' javaScriptEscape='true' />";
        locale['userManag.verefic.pass_passRepeat'] = "<spring:message code='userManag.verefic.pass_passRepeat' javaScriptEscape='true' />";

        locale['userManag.newUser'] = "<spring:message code='userManag.newUser' javaScriptEscape='true' />";
        locale['userManag.adminCanNotBeDeleted'] = "<spring:message code='userManag.adminCanNotBeDeleted' javaScriptEscape='true' />";
        locale['userManag.adminCanNotBeRenamed'] = "<spring:message code='userManag.adminCanNotBeRenamed' javaScriptEscape='true' />";

        locale['userManag.atLeastOneRoleMustBe'] = "<spring:message code='userManag.atLeastOneRoleMustBe' javaScriptEscape='true' />";
        locale['userManag.adminMustHaveRoleAdmin'] = "<spring:message code='userManag.adminMustHaveRoleAdmin' javaScriptEscape='true' />";
    </script>

    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/operator.css' />" rel="stylesheet"/>


    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <!-- ... -->
</head>
<body>
<jsp:include page="/WEB-INF/views/operator/header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading"><spring:message code="userManagement"/></div>
                <div class="panel-body" style="height: 70%">


                    <div class="form-group">
                        <spring:message code='userManag.selectUserTitle' var="selectUser_title"/>
                        <select title="${selectUser_title}" id="selectUser" class="form-control"
                                onchange="getUserByName();">
                        </select>
                    </div>

                    <div class=" input-group" style="padding:10px 20px  20px 30px;">
                        <spring:message code="login.username" var="username"/>

                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input type="text" title="${username}" placeholder="${username}" id="userName"
                               class="form-control" name="username"/>
                    </div>

                    <div class=" input-group" style="padding:0 20px  20px 30px;">
                        <spring:message code="login.password" var="pass"/>

                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                        <input type="password" title="${pass}" placeholder="${pass}" id="userPass"
                               class="form-control" name="password"/>
                    </div>
                    <div class=" input-group" style="padding:0 20px  20px 30px;">
                        <spring:message code="login.password_repeat" var="pass_repeat"/>

                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                        <input type="password" title="${pass_repeat}" placeholder="${pass_repeat}" id="userPass_repeat"
                               class="form-control" name="password_repeat"/>
                    </div>

                    <div class="checkbox" style="padding-left: 30px">
                        <h3><spring:message
                                code="userManagement.roles"/></h3>
                        <label for="role_admin"><input type="checkbox" id="role_admin"
                                                       value="role_admin"><spring:message
                                code="userManagement.role.admin"/></label><br/>
                        <label for="role_operator_load"><input type="checkbox"
                                                               id="role_operator_load"
                                                               value="role_operator_load"><spring:message
                                code="userManagement.role.operator_load"/></label><br/>
                        <label for="role_operator_unload"><input type="checkbox"
                                                                 id="role_operator_unload"
                                                                 value="role_operator_unload"><spring:message
                                code="userManagement.role.operator_unload"/></label><br/>
                        <label for="role_security"><input type="checkbox" id="role_security"
                                                          value="role_security"><spring:message
                                code="userManagement.role.security"/></label>
                    </div>
                    <br>
                    <div class="dropdown form-group" style="padding-bottom: 15px;">
                        <button class="btn btn-primary dropdown-toggle"
                                style="background:url(/avgustBel/resources/images/takeAction.png) no-repeat; background-color: #337ab7; text-align: right; width:125px;"
                                type="button" data-toggle="dropdown"><spring:message code='userManag.action'/>
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li><a onclick="add_modif_User('add');"><spring:message
                                    code="userManagement.addNewUserBtn"/></a></li>
                            <li><a onclick="add_modif_User('modify')"><spring:message
                                    code="userManagement.modifyUserBtn"/></a></li>
                            <li><a onclick="deleteUser()"><spring:message code="userManagement.deleteUserBtn"/></a></li>
                        </ul>
                    </div>

                </div>
                <div id="userMes_Id" class="panel-footer" style="height: 90px;">
                </div>
            </div>
        </div>
    </div>
</div>
    <jsp:include page="/WEB-INF/views/operator/footer.jsp"/>
</body>
</html>