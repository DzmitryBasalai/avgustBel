package spring.view;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import spring.model.Client;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelReportView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment;filename=\"Clients_Archive.xls\"");
        List<Client> clientServedList = (List<Client>) model.get("clientServedList");
        List<Client> clientReturnedList = (List<Client>) model.get("clientReturnedList");
        Sheet sheet0 = workbook.createSheet("Архивные данные обсдуженных клиентов");
        Sheet sheet1 = workbook.createSheet("Архивные данные возвращенных клиентов");
        createBodyOfExcel(sheet0,clientServedList,"served");
        createBodyOfExcel(sheet1,clientReturnedList,"returned");
    }

    private void createBodyOfExcel(Sheet sheet, List<Client> clientList, String state){
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Гос номер автомобиля");
        header.createCell(1).setCellValue("Номер телефона");
        header.createCell(2).setCellValue("Назначение");
        header.createCell(3).setCellValue("Номер заказа");
        header.createCell(4).setCellValue("Время регистрации");
        header.createCell(5).setCellValue("Время вызова");
        header.createCell(6).setCellValue("Склад");
        header.createCell(7).setCellValue("Рампа");
        header.createCell(8).setCellValue("Время прибытия");

        if(state.equals("served")){
            header.createCell(9).setCellValue("Время обслужен");
        }else if(state.equals("returned")){
            header.createCell(9).setCellValue("Время возврат");
        }

        int rowNum = 1;
        for (Client client : clientList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(client.getCarNumber());
            row.createCell(1).setCellValue(client.getPhoneNumber());
            row.createCell(2).setCellValue(client.getDestination());
            row.createCell(3).setCellValue(client.getOrderNumber());
            row.createCell(4).setCellValue(client.getRegTime());
            row.createCell(5).setCellValue(client.getCallTime());
            row.createCell(6).setCellValue(client.getStock());
            row.createCell(7).setCellValue(client.getRamp());
            row.createCell(8).setCellValue(client.getArrivedTime());

            if(state.equals("served")){
                row.createCell(9).setCellValue(client.getServedTime());
            }else if(state.equals("returned")){
                row.createCell(9).setCellValue(client.getReturnTime());
            }

        }
    }
}