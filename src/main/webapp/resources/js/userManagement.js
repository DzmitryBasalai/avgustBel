$(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
    getUsersList();
});

var imgOk = " <img src='/avgustBel/resources/images/ok.png'> ";
var imgInfo = " <img src='/avgustBel/resources/images/info.png'> ";
var imgAlert = " <img src='/avgustBel/resources/images/alert.png'> ";

var infoDiv = '<div class="alert alert-info">';
var successDiv = '<div class="alert alert-success">';
var dangerDiv = '<div class="alert alert-danger">';

function add_modif_User(action) {

    var bodyMes;
    $('#userMes_Id').empty();

    var userNameRoot = document.getElementById("selectUser").value;
    var userName = document.getElementById("userName").value;
    var userPass = document.getElementById("userPass").value;
    var userPass_repeat = document.getElementById("userPass_repeat").value;
    var role_admin = document.getElementById("role_admin").checked;
    var role_operator_load = document.getElementById("role_operator_load").checked;
    var role_operator_unload = document.getElementById("role_operator_unload").checked;
    var role_security = document.getElementById("role_security").checked;

    if(role_admin===false && role_operator_load===false && role_operator_unload===false && role_security===false){
        $('#userMes_Id').append(infoDiv + imgInfo + locale['userManag.atLeastOneRoleMustBe'] + '</div>');
        return;
    }

    if (!verification(userName, userPass, userPass_repeat, $('#userMes_Id'))) {
        return;
    }
    var data;
    var url = '/avgustBel/';
    if (action.localeCompare('add') === 0) {
        url += 'addNewUser';
        data = {
            "userName": userName, "userPass": userPass,
            "role_admin": role_admin, "role_operator_load": role_operator_load,
            "role_operator_unload": role_operator_unload, "role_security": role_security
        };
    } else if (action.localeCompare('modify') === 0) {
        if (userNameRoot.localeCompare("admin") === 0 && userName.localeCompare("admin") !== 0) {
            $('#userMes_Id').append(infoDiv + imgInfo + locale['userManag.adminCanNotBeRenamed'] + '</div>');
            return;
        }
        if(userNameRoot.localeCompare("admin") === 0 && role_admin===false){
            $('#userMes_Id').append(infoDiv + imgInfo + locale['userManag.adminMustHaveRoleAdmin'] + '</div>');
            return;
        }
        url += 'mofifyUser';
        data = {
            "userNameRoot": userNameRoot,
            "userName": userName, "userPass": userPass,
            "role_admin": role_admin, "role_operator_load": role_operator_load,
            "role_operator_unload": role_operator_unload, "role_security": role_security
        };
    }
    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function (status) {

            if (action.localeCompare('add') === 0) {
                switch (status) {
                    case 0:
                        bodyMes = successDiv + imgOk + "'" + userName + "'" + " " + locale['userManag.AddUser.ok'] + '</div>';
                        clearForm();
                        getUsersList();
                        break;
                    case 1:
                        bodyMes = infoDiv + imgInfo + "'" + userName + "'" + " " + locale['userManag.AddUser.alreadyExists'] + '</div>';
                        break;
                    case 3:
                        bodyMes = dangerDiv + imgAlert + "'" + userName + "'" + " " + locale['userManag.AddUser.error'] + '</div>';
                        break;
                }
            } else if (action.localeCompare('modify') === 0) {
                switch (status) {
                    case 0:
                        bodyMes = successDiv + imgOk + "'" + userNameRoot + "'" + " " + locale['userManag.ModifyUser.ok'] + '</div>';
                        clearForm();
                        getUsersList();
                        break;
                    case 1:
                        bodyMes = dangerDiv + imgAlert + "'" + userNameRoot + "'" + " " + locale['userManag.ModifyUser.error'] + '</div>';
                        break;
                }
            }


            $('#userMes_Id').append(bodyMes);

        }
    });

}

function deleteUser() {
    var bodyMes;
    $('#userMes_Id').empty();
    user = document.getElementById("selectUser").value;
    if (user.localeCompare("admin") === 0) {
        $('#userMes_Id').append(infoDiv + imgInfo + locale['userManag.adminCanNotBeDeleted'] + '</div>');
        return;
    }
    $.ajax({
        type: "GET",
        url: "/avgustBel/deleteUser-" + user,
        contentType: "application/json; charset=utf-8",
        success: function (status) {
            switch (status) {
                case 0:
                    bodyMes = successDiv + imgOk + "'" + user + "'" + " " + locale['userManag.DeleteUser.ok'] + '</div>';
                    clearForm();
                    getUsersList();
                    break;
                case 2:
                    bodyMes = infoDiv + imgInfo + "'" + user + "'" + " " + locale['userManag.DeleteUser.noSuchUser'] + '</div>';
                    break;
                case 3:
                    bodyMes = dangerDiv + imgAlert + "'" + user + "'" + " " + locale['userManag.DeleteUser.error'] + '</div>';
                    break;
            }

            $('#userMes_Id').append(bodyMes);
        }
    });
}

function getUsersList() {
    $('#selectUser').empty();
    $('#selectUser').append('<option value="">' + locale['userManag.newUser'] + '</option>');

    $.ajax({
            type: "GET",
            url: "/avgustBel/getUsersList",
            contentType: "application/json; charset=utf-8",
            success: function (usersNames) {
                usersNames.forEach(function (userName, i, usersNames) {
                    $('#selectUser').append('<option value="' + userName + '">' + userName + '</option>');
                });

            }
        }
    )
}
function getUserByName() {
    var userName = document.getElementById("selectUser").value;
    $('#userMes_Id').empty();
    clearForm();

    if (userName === "") {
        return;
    }

    $.ajax({
        type: "GET",
        url: "/avgustBel/getUserByName-" + userName,
        contentType: "application/json; charset=utf-8",
        success: function (user) {
            $('#userName').val(user.username);
            $('#userPass').val();
            var userRoles = user.userRoles;
            userRoles.forEach(function (userRole, i, userRoles) {
                if (userRole.role.localeCompare('ROLE_ADMIN') === 0) {
                    $('#role_admin').prop("checked", true);
                }
                if (userRole.role.localeCompare('ROLE_OPERATOR_LOAD') === 0) {
                    $('#role_operator_load').prop("checked", true);
                }
                if (userRole.role.localeCompare('ROLE_OPERATOR_UNLOAD') === 0) {
                    $('#role_operator_unload').prop("checked", true);
                }
                if (userRole.role.localeCompare('ROLE_SECURITY') === 0) {
                    $('#role_security').prop("checked", true);
                }
            });
        }
    })

}
function verification(username, password, password_repeat, divId) {
    var bodyMes;
    if (username === null || username.length < 3) {
        bodyMes = dangerDiv + imgAlert + locale['userManag.verific.userName'] + '</div>';
        divId.append(bodyMes);
        return false;
    } else if (password === null || password.length < 5) {
        bodyMes = dangerDiv + imgAlert + locale['userManag.verefic.pass'] + '</div>';
        divId.append(bodyMes);
        return false;
    } else if (password.localeCompare(password_repeat) !== 0) {
        bodyMes = dangerDiv + imgAlert + locale['userManag.verefic.pass_passRepeat'] + '</div>';
        divId.append(bodyMes);
        return false;
    }
    else {
        return true;
    }
}
function clearForm() {
    $('#userName').val('');
    $('#userPass').val('');
    $('#userPass_repeat').val('');
    $('#role_admin').prop("checked", false);
    $('#role_operator_load').prop("checked", false);
    $('#role_operator_unload').prop("checked", false);
    $('#role_security').prop("checked", false);
}





