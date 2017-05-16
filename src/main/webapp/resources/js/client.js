var imgOk = " <img src='/avgustBel/resources/images/ok.png'> ";
var imgInfo = " <img src='/avgustBel/resources/images/info.png'> ";
var imgAlert = " <img src='/avgustBel/resources/images/alert.png'> ";
var alertDiv = '<div class="alert alert-danger">';
var infoDiv = '<div class="alert alert-info">';
var successDiv = '<div class="alert alert-success">';
var bodyMes;

function validate(btn) {
    $('#footerMes').empty();

    var orderN = (btn.localeCompare("downloadBtn") == 0) ? (document.getElementById("orderNInput").value) : ("noOrder");


    var carN = document.getElementById("carNInput").value;
   /* carN = replaceAll(carN, [" "], "");*/
    carN = carN.replace("/ +", "");

    var phoneN = document.getElementById("phoneNInput").value;

    var RegOrder = new RegExp("[^A-Za-z0-9А-Яа-я-_№]");
    var RegCar = new RegExp("[^A-Za-z0-9А-Яа-я-]");
    var RegPhone = new RegExp("[^0-9-\+]");

    var errorOrderInputMes="Номер заказа может содержать буквы,цыфры,-,№,_\n";
    var errorCarInputMes="Номер автомобиля может содержать буквы,цыфры,-\n";
    var errorPhoneInputMes="Номер телефона может содержать +,цыфры,-";
    var errorInputsMes="";

    bodyMes = alertDiv + imgAlert;
    if(RegOrder.test(orderN))
    {
        errorInputsMes+=errorOrderInputMes;
    }
    if(RegCar.test(carN))
    {
        errorInputsMes+=errorCarInputMes;
    }
    if(RegPhone.test(phoneN))
    {
        errorInputsMes+=errorPhoneInputMes;
    }
    if(RegOrder.test(orderN) || RegCar.test(carN) || RegPhone.test(phoneN)){
        bodyMes = alertDiv + imgAlert +"проверьте введенные данные"+ '</div>';
        $('#footerMes').append(bodyMes);
        alert(errorInputsMes);
        return;
    }


    if (carN.localeCompare("") == 0 || phoneN.localeCompare("") == 0) {
        var errorMes = 'ВНИМАНИЕ!!! Клиент не зарегестрирован.\nВведите рег. номер автомобиля и/или номер телефона';

        bodyMes = alertDiv + imgAlert + errorMes + '</div>';

        $('#footerMes').append(bodyMes);

        alert(errorMes);
        return;
    }
    var confirmationWithoutOrderN = "Вы действительно хотите зарегестрироваться со следующими данными:\n НОМЕР АВТОМОБИЛЯ : " + carN + "\n НОМЕР ТЕЛЕФОНА :" + phoneN;
    var confirmationOrderN = "\n НОМЕР ЗАКАЗА : " + orderN;
    var b;
    if (orderN.localeCompare("noOrder") == 0)
        b = confirm(confirmationWithoutOrderN);
    else
        b = confirm(confirmationWithoutOrderN + confirmationOrderN);
    if (b == false) {
        return;
    }

    $.ajax({
        type: "GET",
        contentType: "application/json; charset=UTF-8",
        url: "/avgustBel/clientReg/" + carN + "/" + phoneN + "/" + orderN,
        success: function (client) {
            var messageImg;
            var bodyMes;
            if (client.stateId == 6) {
                bodyMes = alertDiv + imgAlert;
            } else {
                bodyMes = successDiv + imgOk;
            }

            bodyMes += client.msg + '</div>';

            $('#footerMes').append(bodyMes);

            alert(client.msg);
            if (client.stateId != 6) {
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

function checkOrderN() {
    var orderN = document.getElementById("orderNInput").value;

    $.ajax({
        type: "GET",
        contentType: "application/json; charset=UTF-8",
        url: '/avgustBel/checkOrder-' + orderN,
        success: function (order) {

            if (order.state == 1) {
                bodyMes = infoDiv + imgOk;
                $('#regButton').removeAttr("disabled");
            } else {
                bodyMes = infoDiv + imgInfo;
                $('#regButton').attr("disabled", true);
            }
            bodyMes += order.msg + '</div>';

            $('#footerMes').empty();
            if (orderN.length >= 3) {
                $('#footerMes').append(bodyMes);
            }
        }

    });
}