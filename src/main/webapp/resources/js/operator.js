var currentPageId = 1, currentTotal = 5;
var destination;
$(document).ready(function () {
    update(currentPageId, currentTotal,destination);

    setInterval(function () {
        constantUpdate();
    }, 2500);
});

function setDestinatuion(dest){
    destination=dest;
}
function constantUpdate() {
    update(currentPageId, currentTotal,destination);
}


function paginationLinks(count, onPage, curPage) {
    $('#divPagination').pagination({
        items: count,
        itemsOnPage: onPage,
        cssStyle: 'light-theme',
        currentPage: curPage,

        onPageClick: function (pageNumber) {
            update(pageNumber, onPage,destination)
        }
    });
}

function update(pageId, onPage, destination) {
    $.ajax({
        url: "/avgustBel/refresh-" + pageId + "-" + onPage + "-"+destination,
        success: function (map) {
            clientCount = map.clientCount;
            $('#clientsCount').html(clientCount);
            currentPageId = map.currentPagaId;
            currentTotal = map.currentTotal;
            filClientsList(map.clientList, currentPageId, onPage);
            paginationLinks(clientCount, onPage, currentPageId);
        }
    });
}

function filClientsList(clientList, currentPageId, onPage) {
    var clientTable;
    var n = 1 + onPage * (currentPageId - 1);
    int: i = 1;
    $('#table').empty();
    clientTable = '<thead>';
    clientTable += '<tr>';
    clientTable += '<th>' + '№' + '</th>';
    clientTable += '<th>' + locale['operator.trControlTable.carRegN'] + '</th>';
    clientTable += '<th>' + locale['operator.trControlTable.phoneN'] + '</th>';
    clientTable += '<th>' + locale['operator.trControlTable.destination'] + '</th>';
    clientTable += '<th>' + locale['client.company'] + '</th>';
    clientTable += '<th>' + locale['operator.trControlTable.regTime'] + '</th>';
    clientTable += '<th>' + locale['operator.trControlTable.callTime'] + '</th>';
    clientTable += '<th>' + locale['operator.trControl.stock'] + '</th>';
    clientTable += '<th>' + locale['operator.trControl.ramp'] + '</th>';
    clientTable += '<th>' + locale['operator.trControlTable.enteredTime'] + '</th>';
    clientTable += '<th>' + locale['operator.trControlTable.arriveTime'] + '</th>';

    clientTable += '<th>' + locale['operator.trControlTable.servedTime'] + '</th>';
    clientTable += '<th>' + locale['operator.trControlTable.returnTime'] + '</th>';

    clientTable += '<th>' + locale['operator.trControlTable.state'] + '</th>';
    clientTable += '</tr>';
    clientTable += '</thead>';

    clientTable += '<tbody>';
    clientList.forEach(function (client) {
        clientTable += '<tr>';
        clientTable += '<td>' + n++ + '</td>';

        var carN = " '" + client.carN + "'";

        clientTable += '<td><a onclick="linkSetCarNumber(' + carN + ')" href="#">' + client.carN + '</a></td>';
        clientTable += '<td>' + client.phoneN + '</td>';
        clientTable += '<td>' + client.destination + '</td>';
        clientTable += '<td>' + client.company + '</td>';
        clientTable += '<td>' + client.regTime + '</td>';
        clientTable += '<td>' + client.callTime + '</td>';
        clientTable += '<td>' + client.stock + '</td>';
        clientTable += '<td>' + client.ramp + '</td>';
        clientTable += '<td>' + client.enterTime + '</td>';
        clientTable += '<td>' + client.arrivedTime + '</td>';

        clientTable += '<td>' + client.servedTime + '</td>';
        clientTable += '<td>' + client.returnTime + '</td>';

        clientTable += '<td>' + client.state.state + '</td>';
        clientTable += '</tr>';
    });
    clientTable += '</tbody>';

    $('#table').append(clientTable);
}

function changeFunc() {
    var selectBox = document.getElementById("selectBox");
    var selectedValue = selectBox.options[selectBox.selectedIndex].value;
    update(1, selectedValue,destination);
}

function linkSetCarNumber(carN) {
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=UTF-8",
        url: "/avgustBel/checkCarN/" + carN,
        success: function (client) {
            document.getElementById("carNumberInputId").value = carN;

            try {
                document.getElementById("selectStockId").value = client.stock;
                selectStock();
                document.getElementById("selectRampId").value = client.ramp;
            } catch (err) {
            }
            checkCarNajax();
        }
    });
}

var imgOk = " <img src='/avgustBel/resources/images/ok.png'> ";
var imgInfo = " <img src='/avgustBel/resources/images/info.png'> ";
var imgAlert = " <img src='/avgustBel/resources/images/alert.png'> ";

var dangerDiv = '<div class="alert alert-danger">';
var infoDiv = '<div class="alert alert-info">';
var successDiv = '<div class="alert alert-success">';

function checkCarNajax() {

    var inputCarN = document.getElementById("carNumberInputId").value;
    var stock;
    var ramp;
    try {
        stock = document.getElementById("selectStockId").value;
        ramp = document.getElementById("selectRampId").value;
    } catch (err) {
    }

    if (inputCarN.length >= 1) {
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=UTF-8",
            url: "/avgustBel/checkCarN/" + inputCarN,
            success: function (client) {

                $('#trCntrlMes').empty();

                if (client.state.id === 7) {
                    bodyMes = infoDiv + imgInfo;
                }
                else if (client.state.id === 6) {
                    bodyMes = dangerDiv + imgAlert;
                } else {
                    bodyMes = successDiv + imgOk;
                }

                bodyMes += client.msg + '</div>';
                $('#trCntrlMes').append(bodyMes);

                if (client.state.id === 1) {
                    $('#callBtn').removeAttr("disabled");
                    $('#enterBtn').removeAttr("disabled");
                    $('#arrivedBtn').attr("disabled", true);
                    $('#servedBtn').attr("disabled", true);
                    $('#returnBtn').attr("disabled", true);
                    $('#leaveBtn').attr("disabled", true);
                }
                if (client.state.id === 2) {
                    $('#callBtn').attr("disabled", true);
                    $('#enterBtn').removeAttr("disabled");
                    $('#arrivedBtn').attr("disabled", true);
                    $('#servedBtn').attr("disabled", true);
                    $('#returnBtn').attr("disabled", true);
                    $('#leaveBtn').attr("disabled", true);
                }
                if (client.state.id === 8) {
                    $('#callBtn').attr("disabled", true);
                    $('#enterBtn').attr("disabled", true);
                    $('#arrivedBtn').removeAttr("disabled");
                    $('#servedBtn').attr("disabled", true);
                    $('#returnBtn').attr("disabled", true);
                    $('#leaveBtn').attr("disabled", true);
                }

                if (client.state.id === 3) {
                    $('#callBtn').attr("disabled", true);
                    $('#enterBtn').attr("disabled", true);
                    $('#arrivedBtn').attr("disabled", true);
                    $('#servedBtn').removeAttr("disabled");
                    $('#returnBtn').removeAttr("disabled");
                    $('#leaveBtn').attr("disabled", true);
                }
                if (client.state.id === 4 || client.state.id === 5 ) {
                    $('#callBtn').attr("disabled", true);
                    $('#enterBtn').attr("disabled", true);
                    $('#arrivedBtn').attr("disabled", true);
                    $('#servedBtn').attr("disabled",true);
                    $('#returnBtn').attr("disabled",true);
                    $('#leaveBtn').removeAttr("disabled");
                }

                if (client.state.id === 6 || client.state.id === 7) {
                    $('#callBtn').attr("disabled", true);
                    $('#enterBtn').attr("disabled", true);
                    $('#arrivedBtn').attr("disabled", true);
                    $('#servedBtn').attr("disabled", true);
                    $('#returnBtn').attr("disabled", true);
                    $('#leaveBtn').attr("disabled", true);
                }

                if (stock !== undefined && ramp !== undefined) {
                    if (stock.localeCompare("") === 0 || ramp.localeCompare("") === 0) {
                        $('#callBtn').attr("disabled", true);
                        $('#enterBtn').attr("disabled", true);
                        $('#arrivedBtn').attr("disabled", true);
                        $('#servedBtn').attr("disabled", true);
                        $('#returnBtn').attr("disabled", true);
                        $('#leaveBtn').attr("disabled", true);
                    }
                }
                if(stock.localeCompare("0")===0 || ramp.localeCompare("0")===0){
                    $('#callBtn').attr("disabled", true);
                }
            }
        });
    }

}
function serviceBtnJs(value) {
    var carN = document.getElementById("carNumberInputId").value;

    var stock="";
    var ramp="";
    try {
        stock = document.getElementById("selectStockId").value;
        ramp = document.getElementById("selectRampId").value;
    }catch (err){}

    var servedValue = locale['operator.trControl.servedBtn'];
    var returnValue = locale['operator.trControl.returnBtn'];

    var btn = value;

    $.ajax({
        type: "GET",
        contentType: "application/json; charset=UTF-8",
        url: "/avgustBel/trControlOperationBtns-" + carN + "-" + stock + "-" + ramp + "-" + btn,
        success: function (client) {
            update(currentPageId, currentTotal,destination);
            $('#trCntrlMes').empty();
            var bodyMes;
            if (client.state.id === 6) {

                bodyMes = dangerDiv + imgAlert + client.msg + '</div>';
                $('#trCntrlMes').append(bodyMes);

                return;
            } else if (client.state.id === 7) {

                bodyMes = infoDiv + imgInfo + client.msg + '</div>';
                $('#trCntrlMes').append(bodyMes);

                return;
            }else if (client.state.id === 9) {

                bodyMes = successDiv + imgOk + client.msg + '</div>';
                $('#trCntrlMes').append(bodyMes);

                return;
            }

            if (btn.localeCompare(servedValue) === 0 || btn.localeCompare(returnValue) === 0) {
                bodyMes = successDiv + imgOk + client.msg + '</div>';
                $('#trCntrlMes').append(bodyMes);

                $('#servedBtn').attr("disabled", true);
                $('#returnBtn').attr("disabled", true);
            } else {
                checkCarNajax();
            }
        }
    });
}
function selectStock() {
    var selStock = document.getElementById("selectStockId");
    var selectedStock = selStock.options[selStock.selectedIndex].value;

    $('#selectRampId').empty();

    var selectRampBody = '<option value="">' + locale['operator.selectRamp'] + '</option>';

    if (selectedStock.localeCompare("") === 0) {
        $('#selectRampId').attr("disabled", true);

    }
    else {
        var ramp;
        $('#selectRampId').removeAttr("disabled");
        var stock = parseInt(selectedStock);

        switch (stock) {
            case 0:
                ramp = 0;
                break;
            case 1:
                ramp = 8;
                break;
            case 2:
                ramp = 8;
                break;
            case 3:
                ramp = 7;
                break;
            case 4:
                ramp = 2;
                break;
            case 5:
                ramp = 5;
                break;
        }

        var rampN = locale['operator.rampN'];
        for (i = 1; i <= ramp; i++) {
            selectRampBody += '<option value="' + i + '">' + rampN + i + '</option>';
        }
        if(ramp === 0)
            selectRampBody = '<option value="0">Вне очереди</option>';

    }
    $('#selectRampId').append(selectRampBody);

    checkCarNajax();

}

function selectRamp() {
    checkCarNajax();
}



