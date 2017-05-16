package spring.service;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.DAO.ClientDao;
import spring.DAO.OrderDao;
import spring.model.Client;
import spring.model.Order;
import spring.service.infoTable.ConnectionSingleton;
import spring.service.infoTable.InfoTable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OperatorService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    ClientDao clientDao;

    @Autowired
    MessageSource messageSource;

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public Map<String, Object> processFile(MultipartFile file, Locale locale) {

        List<Order> orderList = new ArrayList<Order>();
        DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
        Map<String, Object> map = new HashMap<String, Object>();


        if (file.getOriginalFilename().endsWith("xlsx")) {
            try {
                int i = 0;
                // Creates a workbook model from the uploaded excelfile
                XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
                // Creates a worksheet model representing the first sheet
                XSSFSheet worksheet = workbook.getSheetAt(0);
                // Reads the data in excel file until last row is encountered
                while (i <= worksheet.getLastRowNum()) {
                    // Creates an model for the UserInfo Model
                    Order order = new Order();
                    Cell cell = worksheet.getRow(i).getCell(0);
                    order.setOrder(formatter.formatCellValue(cell));
                    // persist data into database in here
                    orderList.add(i, order);
                    i++;
                }
                workbook.close();
                map.put("orderList", orderList);

            } catch (Exception e) {
                e.printStackTrace();
                map.put("state", 0);
                map.put("message", messageSource.getMessage("order.error", null, locale));
                return map;
            }
        }
        else if (file.getOriginalFilename().endsWith("xls")) {
            try {
                int i = 0;
                // Creates a workbook model from the uploaded excelfile
                HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
                // Creates a worksheet model representing the first sheet
                HSSFSheet worksheet = workbook.getSheetAt(0);
                // Reads the data in excel file until last row is encountered
                while (i <= worksheet.getLastRowNum()) {
                    Order order = new Order();
                    // Creates an model representing a single row in excel
                    Cell cell = worksheet.getRow(i).getCell(0);
                    order.setOrder(formatter.formatCellValue(cell));

                    // Sets the Read data to the model class
                    // persist data into database in here
                    orderList.add(i, order);
                    i++;
                }

                workbook.close();
                map.put("orderList", orderList);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("state", 0);
                map.put("message", messageSource.getMessage("order.error", null, locale));
                return map;
            }
        }
        else {
            map.put("state", 0);
            map.put("message", messageSource.getMessage("order.unsuccess", null, locale));
            return map;
        }

        orderDao.clearOrderTable();
        orderDao.insertOrderList(orderList);
        map.put("state", 1);
        map.put("message", messageSource.getMessage("order.success", null, locale));
        return map;
    }

    public Client checkCarN(String carN, Locale locale) {
        //JSONObject jsonObject = new JSONObject();
        Client client = new Client();
        try {
            client = clientDao.getClientByCarN(carN, locale);
        } catch (Exception ex) {
            client.setMsg(ex.getMessage());
            // jsonObject.put("client", client);
            return client;
        }

        if (client != null) {
            if (client.getStateId() == 1)
                client.setMsg(messageSource.getMessage("client.isFoundInDb", new String[]{client.getCarNumber()}, locale));
            else if (client.getStateId() == 2) {
                client.setMsg(messageSource.getMessage("client.isCalled", new String[]{client.getCarNumber()}, locale));
            }
            else if (client.getStateId() == 3) {
                client.setMsg(messageSource.getMessage("client.isArrived", new String[]{client.getCarNumber()}, locale));
            }
        }
        else {
            client = new Client();
            client.setMsg(messageSource.getMessage("client.isNOTFoundInDb", new String[]{carN}, locale));
        }

        // jsonObject.put("client", client);
        return client;
    }


    @Autowired
    ClientService clientService;

    public Client operationByBtns(String carN, String stock, String ramp, String btnValue, Locale locale) {

        Client client = new Client();
        try {
            client = clientDao.getClientByCarN(carN, locale);
        } catch (Exception ex) {
            client.setMsg(ex.getMessage());
            return client;
        }

        Map<String, Client> mapClietn = MapClientSingleton.getInstance().getMapClient();

        if (btnValue.equals(messageSource.getMessage("operator.trControl.callBtn", null, locale))) {
            if (mapClietn.size() < 4) {
                mapClietn.put(client.getCarNumber(), client);

                if (client != null) {
                    client.setStock(stock);
                    client.setRamp(ramp);
                    client.setStateId(2);
                    client.setCallTime(dateFormat.format(new Date()));
                    client.setArrivedTime("");
                    client.setServedTime("");
                    client.setReturnTime("");

/*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ОТОБРАЖЕНИЕ ИНФОРМАЦИИ НА ТАБЛО****************************************/
                    client = updateTable(mapClietn, client);
                    if (client.getStateId() == 7)
                        return client;
//********************************************************************************************


                    if (clientDao.updateClient(client) != 0)
                        client.setMsg(messageSource.getMessage("client.isCalled", new String[]{client.getCarNumber()}, locale));
                    else
                        client.setMsg(messageSource.getMessage("client.isNOTCalled", new String[]{client.getCarNumber()}, locale));
                }
                else {
                    client = new Client();
                    client.setMsg(messageSource.getMessage("client.isNOTFoundInDb", new String[]{client.getCarNumber()}, locale));
                }

            }
            else {
                client.setMsg("Максимальное количесво одновременно вызываемых клиентов - 4");
                client.setStateId(7);
                return client;
            }


        }
        else if (btnValue.equals(messageSource.getMessage("operator.trControl.arrivedBtn", null, locale))) {
            if (client != null) {
                client.setStateId(3);
                client.setArrivedTime(dateFormat.format(new Date()));
                client.setServedTime("");
                client.setReturnTime("");

                if (clientDao.updateClient(client) != 0)
                    client.setMsg(messageSource.getMessage("client.isArrived", new String[]{client.getCarNumber()}, locale));
                else
                    client.setMsg(messageSource.getMessage("client.isNOTArrived", new String[]{client.getCarNumber()}, locale));
            }
            else {
                client = new Client();
                client.setMsg(messageSource.getMessage("client.isNOTFoundInDb", new String[]{client.getCarNumber()}, locale));
            }

/*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ИНФОРМАЦИЯ С ТАБЛО ИСЧЕЗАЕТ*/
            mapClietn.remove(client.getCarNumber());
            /*очистка всего экрана*/
            client = clearTable(client);
            if (client.getStateId() == 7) {
                mapClietn.put(client.getCarNumber(), client);
                return client;
            }
            //`~~~~~~~~~~~~~~~~~~~~
            client = updateTable(mapClietn, client);
            if (client.getStateId() == 7) {
                mapClietn.put(client.getCarNumber(), client);
                return client;
            }

/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        }
        else if (btnValue.equals(messageSource.getMessage("operator.trControl.servedBtn", null, locale))) {
            if (client != null) {
                client.setStateId(4);
                client.setServedTime(dateFormat.format(new Date()));
                client.setReturnTime("");

                if (clientDao.updateClient(client) != 0) {
                    client.setMsg(messageSource.getMessage("client.isServed", new String[]{client.getCarNumber()}, locale));
                    clientDao.deleteClient(client, locale);
                    //ДОБАВЛЕНИЕ КЛИЕНТА В АРХИВ!
                    clientDao.insertClientInArchive(client);
                }

                else
                    client.setMsg(messageSource.getMessage("client.isNOTServed", new String[]{client.getCarNumber()}, locale));
            }
            else {
                client = new Client();
                client.setMsg(messageSource.getMessage("client.isNOTFoundInDb", new String[]{client.getCarNumber()}, locale));
            }
        }
        else if (btnValue.equals(messageSource.getMessage("operator.trControl.returnBtn", null, locale))) {
            if (client != null) {
                client.setStateId(5);
                client.setReturnTime(dateFormat.format(new Date()));

                if (clientDao.updateClient(client) != 0) {

                    clientDao.deleteClient(client, locale);
                    /*ДОБАВЛЕНИЕ КЛИЕНТА В АРХИВ*/
                    clientDao.insertClientInArchive(client);


                    /*Помещение клиента в конец списка ожидания*/
                    client = clientService.clientRegistration(client, client.getDestination(), locale);
                    client.setMsg(messageSource.getMessage("client.isReturned", new String[]{client.getCarNumber()}, locale));
                }

                else
                    client.setMsg(messageSource.getMessage("client.isNOTReturned", new String[]{client.getCarNumber()}, locale));
            }
            else {
                client = new Client();
                client.setMsg(messageSource.getMessage("client.isNOTFoundInDb", new String[]{client.getCarNumber()}, locale));
            }
        }

        return client;
    }

    Client updateTable(Map<String, Client> mapClient, Client client) {

        List<String> clientList = new ArrayList<String>();

        for (Map.Entry<String, Client> entry : mapClient.entrySet()) {
            Client clientEntry = entry.getValue();

            if (clientEntry == null) {
                clientList.add(" ");
                clientList.add(" ");
                clientList.add(" ");
            }
            else {
                clientList.add(clientEntry.getCarNumber());
                clientList.add(clientEntry.getStock());
                clientList.add(clientEntry.getRamp());
            }
        }

        InfoTable infoTable = ConnectionSingleton.getInstance().getInfoTable();
        try {
            infoTable.SendData(clientList);
            //infoTable.SetBrightness(5);

            MapClientSingleton.getInstance().setMapClient(mapClient);
        } catch (Exception ex) {
            ex.printStackTrace();
            client.setMsg(ex.getMessage());
            client.setStateId(7);

            mapClient.remove(client.getCarNumber());
            MapClientSingleton.getInstance().setMapClient(mapClient);

            return client;
        }

        return client;
    }
    Client clearTable(Client client){
        List<String> clientList = new ArrayList<String>();
        for(int i=0;i<12;i++){
            clientList.add(" ");
        }

        InfoTable infoTable = ConnectionSingleton.getInstance().getInfoTable();
        try {
            infoTable.SendData(clientList);
            //infoTable.SetBrightness(5);
        } catch (Exception ex) {
            ex.printStackTrace();
            client.setMsg(ex.getMessage());
            client.setStateId(7);

            return client;
        }
        return client;
    }

    public Map<String, Object> getClientInfoForTableRefresh(String pageId, String total) {
        int pageIdInt = Integer.parseInt(pageId);
        int totalInt = Integer.parseInt(total);
        pageIdInt = (pageIdInt - 1) * totalInt + 1;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("clientList", clientDao.getClientListByPage(pageIdInt, totalInt));
        map.put("clientCount", clientDao.getClientCount());
        map.put("currentPagaId", Integer.parseInt(pageId));
        map.put("currentTotal", totalInt);
        return map;
    }
}

class MapClientSingleton {

    private static MapClientSingleton getInstance = new MapClientSingleton();

    Map<String, Client> map;

    public static MapClientSingleton getInstance() {
        return getInstance;
    }

    private MapClientSingleton() {
        map = new LinkedHashMap<String, Client>();
    }

    public Map<String, Client> getMapClient() {
        return map;
    }

    public void setMapClient(Map<String, Client> map) {
        this.map = map;
    }
}
