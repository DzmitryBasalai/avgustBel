var imgOk = " <img src='/avgustBel/resources/images/ok.png'> ";
var imgInfo = " <img src='/avgustBel/resources/images/info.png'> ";
var imgAlert = " <img src='/avgustBel/resources/images/alert.png'> ";
var alertDiv = '<div class="alert alert-danger">';
var infoDiv = '<div class="alert alert-info">';
var successDiv = '<div class="alert alert-success">';
var bodyMes;

function validate(btn) {
    $('#footerMes').empty();

    var company = (btn.localeCompare("downloadBtn") === 0) ? ((document.getElementById("companyInput").value).trim()) : ("noCompany");
    company = company.replace(/  +/g, " ");
    var carN = document.getElementById("carNInput").value;
    carN = carN.replace(/ +/g, "");

    var phoneN = document.getElementById("phoneNInput").value;
    phoneN = phoneN.replace(/ +/g, "");

    var RegCompany = new RegExp("[^A-Za-z0-9А-Яа-я-_№# ]");
    var RegCar = new RegExp("[^A-Za-z0-9А-Яа-я-]");
    var RegPhone = new RegExp("[^0-9-\+]");

    var errorCompanyInputMes = locale['client.error.companyInput'];
    var errorCarInputMes = locale['client.error.carInput'];
    var errorPhoneInputMes =  locale['client.error.phoneNumber'];
    var errorInputsMes = "";

    bodyMes = alertDiv + imgAlert;
    if (RegCompany.test(company)) {
        errorInputsMes += errorCompanyInputMes + "\n";
    }
    if (RegCar.test(carN)) {
        errorInputsMes += errorCarInputMes + "\n";
    }
    if (RegPhone.test(phoneN)) {
        errorInputsMes += errorPhoneInputMes + "\n";
    }
    if (RegCompany.test(company) || RegCar.test(carN) || RegPhone.test(phoneN)) {
        bodyMes = alertDiv + imgAlert + locale['client.error.checkInputData'] + '</div>';
        $('#footerMes').append(bodyMes);
        alert(errorInputsMes);
        return;
    }

    var errorMes;
    if (carN.localeCompare("") === 0 || phoneN.localeCompare("") === 0 || company.localeCompare("") === 0) {
        errorMes =  locale['client.error.phoneAndCarRequired'];
        if (company.localeCompare("") === 0) {
            errorMes = locale['client.error.companyPhoneAndCarRequired'];
        }

        bodyMes = alertDiv + imgAlert + errorMes + '</div>';

        $('#footerMes').append(bodyMes);

        alert(errorMes);
        return;
    }
    var confirmationWithoutCompany = locale['client.confirmation']+"\n" + locale['client.carNumber']+" : " + carN + "\n"+locale['client.phoneNumber']+" : " + phoneN;
    var confirmationCompany = locale['client.company']+" : " + company;
    var confirmationWithCompany = locale['client.confirmation']+"\n" + locale['client.company']+" : " + company +"\n"
        + locale['client.carNumber']+" : " + carN + "\n"+locale['client.phoneNumber']+" : " + phoneN;
    var b;
    if (company.localeCompare("noCompany") === 0)
        b = confirm(confirmationWithoutCompany);
    else
        b = confirm(confirmationWithCompany);
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