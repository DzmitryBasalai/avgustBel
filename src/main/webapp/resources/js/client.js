var imgOk = " <img src='/avgustBel/resources/images/ok.png'> ";
var imgInfo = " <img src='/avgustBel/resources/images/info.png'> ";
var imgAlert = " <img src='/avgustBel/resources/images/alert.png'> ";
var alertDiv = '<div class="alert alert-danger">';
var infoDiv = '<div class="alert alert-info">';
var successDiv = '<div class="alert alert-success">';
var bodyMes;

function validate(btn) {
    $('#footerMes').empty();

    var company = (btn.localeCompare("downloadBtn") === 0) ? (document.getElementById("companyInput").value) : ("noCompany");


    var carN = document.getElementById("carNInput").value;
    /* carN = replaceAll(carN, [" "], "");*/
    carN = carN.replace("/ +", "");

    var phoneN = document.getElementById("phoneNInput").value;

    var RegCompany = new RegExp("[^A-Za-z0-9А-Яа-я-_№] ");
    var RegCar = new RegExp("[^A-Za-z0-9А-Яа-я-]");
    var RegPhone = new RegExp("[^0-9-\+]");

    var errorOrderInputMes = "Поле \"Компания\" может содержать буквы,цыфры,-,№,_ пробел\n";
    var errorCarInputMes = "Поле \"Номер автомобиля\" может содержать буквы,цыфры,-\n";
    var errorPhoneInputMes = "Поле \"Номер телефона\" может содержать +,цыфры,-";
    var errorInputsMes = "";

    bodyMes = alertDiv + imgAlert;
    if (RegCompany.test(company)) {
        errorInputsMes += errorOrderInputMes;
    }
    if (RegCar.test(carN)) {
        errorInputsMes += errorCarInputMes;
    }
    if (RegPhone.test(phoneN)) {
        errorInputsMes += errorPhoneInputMes;
    }
    if (RegCompany.test(company) || RegCar.test(carN) || RegPhone.test(phoneN)) {
        bodyMes = alertDiv + imgAlert + "проверьте введенные данные" + '</div>';
        $('#footerMes').append(bodyMes);
        alert(errorInputsMes);
        return;
    }

    var errorMes;
    if (carN.localeCompare("") === 0 || phoneN.localeCompare("") === 0) {
        errorMes = 'ВНИМАНИЕ!!! Клиент не зарегестрирован.\n Номер автомобиля и номер телефона обязательные поля для заполнения';
        if (company.localeCompare("noCompany") !== 0) {
            errorMes = 'ВНИМАНИЕ!!! Клиент не зарегестрирован.\n Компания, Номер автомобиля и номер телефона обязательные поля для заполнения';
        }

        bodyMes = alertDiv + imgAlert + errorMes + '</div>';

        $('#footerMes').append(bodyMes);

        alert(errorMes);
        return;
    }
    var confirmationWithoutOrderN = "Вы действительно хотите зарегестрироваться со следующими данными:\n НОМЕР АВТОМОБИЛЯ : " + carN + "\n НОМЕР ТЕЛЕФОНА :" + phoneN;
    var confirmationOrderN = "\n НОМЕР ЗАКАЗА : " + company;
    var b;
    if (company.localeCompare("noCompany") === 0)
        b = confirm(confirmationWithoutOrderN);
    else
        b = confirm(confirmationWithoutOrderN + confirmationOrderN);
    if (b === false) {
        return;
    }

    $.ajax({
        type: "GET",
        contentType: "application/json; charset=UTF-8",
        url: "/avgustBel/clientReg/" + carN + "/" + phoneN + "/" + company,
        success: function (client) {
            var messageImg;
            var bodyMes;
            if (client.state.id == 6) {
                bodyMes = alertDiv + imgAlert;
            } else {
                bodyMes = successDiv + imgOk;
            }

            bodyMes += client.msg + '</div>';

            $('#footerMes').append(bodyMes);

            alert(client.msg);
            if (client.state.id != 6) {
                setTimeout(goToInitPage, 2000);
            }
        }
    });
}

function goToInitPage() {
    window.location = 'user-client'
}

/*function replaceAll(str, search, replacement) {
 /!* return str.split(search).join(replacement);*!/

 search.forEach(function (entry) {
 str = str.split(entry).join(replacement)
 });
 return str;
 };*/
