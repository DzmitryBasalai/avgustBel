
/* *********************DATA OPERATION********************** */
function enableGetClientArchBtn() {
    var dataFrom = document.getElementById("datetimepickerFrom").value;
    var dataTo = document.getElementById("datetimepickerTo").value;

    if (dataFrom.localeCompare("") === 0 || dataTo.localeCompare("") === 0) {

        $('#getClArchBtn').attr("disabled", true);
    }
    else if (dataFrom.localeCompare("") !== 0 && dataTo.localeCompare("") !== 0) {
        $('#getClArchBtn').removeAttr("disabled");
    }
}