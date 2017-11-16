package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import spring.dao.ClientArchDao;
import spring.entity.Client;
import spring.entity.ClientArchive;
import spring.entity.State;
import spring.service.infoTable.ConnectionSingleton;
import spring.service.infoTable.InfoTable;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service
public class OperatorService extends BaseService {

    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    private ClientArchDao clientArchDao;

    @Autowired
    public void setClientArchDao(ClientArchDao clientArchDao) {
        this.clientArchDao = clientArchDao;
    }

    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private Client dbError(String mes, String carN, Exception ex, Locale locale) {
        Client client = new Client();
        State state = new State();
        state.setId(6);
   /* state.setState("error");*/

        client.setState(state);
        client.setMsg(messageSource.getMessage(mes, new String[]{carN}, locale) + ex.getMessage());
        return client;
    }

    public Map<String, Object> getClientInfoForTableRefresh(String pageId, String total, String destination, Locale locale) {
        int pageIdInt = Integer.parseInt(pageId);
        int totalInt = Integer.parseInt(total);
        int offset = (pageIdInt - 1) * totalInt;

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("clientList", clientDao.getClientList(offset, totalInt, destination, locale));
            map.put("clientCount", clientDao.getTotalClientCount(locale, destination));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        map.put("currentPagaId", Integer.parseInt(pageId));
        map.put("currentTotal", totalInt);

        return map;
    }


    public Client checkCarN(String carN, Locale locale) {

        Client client;
        try {
            client = clientDao.getClientByCarN(carN, locale);

            if (client != null) {
                if (client.getState().getId() == 1)
                    client.setMsg(messageSource.getMessage("client.isFoundInDb", new String[]{client.getCarN()}, locale));
                else if (client.getState().getId() == 2) {
                    client.setMsg(messageSource.getMessage("client.isCalled", new String[]{client.getCarN()}, locale));
                }
                else if (client.getState().getId() == 3) {
                    client.setMsg(messageSource.getMessage("client.isArrived", new String[]{client.getCarN()}, locale));
                }
                else if (client.getState().getId() == 4) {
                    client.setMsg(messageSource.getMessage("client.isServed", new String[]{client.getCarN()}, locale));
                }
                else if (client.getState().getId() == 5) {
                    client.setMsg(messageSource.getMessage("client.isReturned", new String[]{client.getCarN()}, locale));
                }
                else if (client.getState().getId() == 8) {
                    client.setMsg(messageSource.getMessage("client.isEntered", new String[]{client.getCarN()}, locale));
                }
                else if (client.getState().getId() == 9) {
                    client.setMsg(messageSource.getMessage("client.isLeaved", new String[]{client.getCarN()}, locale));
                }
            }
            else {
                client = new Client();
                client.setState(stateDao.getStateById(7));
                client.setMsg(messageSource.getMessage("client.isNOTFoundInDb", new String[]{carN}, locale));
            }

        } catch (Exception ex) {
            return dbError("exception.dbError", carN, ex, locale);
        }

        return client;
    }


    public Client operationByBtns(String carN, String stock, String ramp, String btnValue, Locale locale) {

        Client client;
        try {
            client = clientDao.getClientByCarN(carN, locale);

            if (client == null) {
                client = new Client();
                client.setMsg(messageSource.getMessage("client.isNOTFoundInDb", new String[]{carN}, locale));
                client.setState(stateDao.getStateById(7));
                return client;
            }

        } catch (Exception ex) {
            return dbError("exception.dbError", carN, ex, locale);
        }

        Map<String, Client> mapClient = MapClientSingleton.getInstance().getMapClient();

        switch (btnValue) {
            case "callBtn":
                if (mapClient.size() < 4) {
                    client.setStock(stock);
                    client.setRamp(ramp);
                    client.setCallTime(dateFormat.format(new Date()));

/*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ОТОБРАЖЕНИЕ ИНФОРМАЦИИ НА ТАБЛО****************************************/
                    mapClient.put(client.getCarN(), client);

                    client = updateTable(mapClient, client);
                    if (client.getState().getId() == 6)
                        return client;
//********************************************************************************************
                    try {
                        client.setState(stateDao.getStateById(2));
                        clientDao.updateClient(client);
                        client.setMsg(messageSource.getMessage("client.isCalled", new String[]{client.getCarN()}, locale));

                    } catch (Exception ex) {
                        mapClient.remove(client.getCarN());
                        MapClientSingleton.getInstance().setMapClient(mapClient);

                        return dbError("client.isNOTCalled", client.getCarN(), ex, locale);
                    }
                }
                else {
                    client.setMsg(messageSource.getMessage("client.maxCalledClientCountInQueque", null, locale));
                    client.setState(stateDao.getStateById(7));
                    return client;
                }
                break;
            case "arrivedBtn":
                client.setArrivedTime(dateFormat.format(new Date()));

                if (null!=mapClient.get(client.getCarN())) {
                   /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ИНФОРМАЦИЯ С ТАБЛО ИСЧЕЗАЕТ*/
                    mapClient.remove(client.getCarN());
            /*очистка всего экрана*/
                    client = clearTable(client);

                    if (client.getState().getId() == 6) {
                        mapClient.put(client.getCarN(), client);
                        return client;
                    }
                    //`~~~~~~~~~~~~~~~~~~~~
                    client = updateTable(mapClient, client);
                    if (client.getState().getId() == 6) {
                        mapClient.put(client.getCarN(), client);
                        return client;
                    }
                /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
                }

                try {
                    client.setState(stateDao.getStateById(3));
                    clientDao.updateClient(client);
                    client.setMsg(messageSource.getMessage("client.isArrived", new String[]{client.getCarN()}, locale));
                } catch (Exception ex) {
                    return dbError("client.isNOTArrived", client.getCarN(), ex, locale);
                }
                break;
            case "servedBtn":
                client.setServedTime(dateFormat.format(new Date()));

                try {
                    client.setState(stateDao.getStateById(4));
                    clientDao.updateClient(client);
                    client.setMsg(messageSource.getMessage("client.isServed", new String[]{client.getCarN()}, locale));

                } catch (Exception ex) {
                    return dbError("client.isNOTServed", client.getCarN(), ex, locale);
                }
                break;
            case "returnBtn":
                client.setReturnTime(dateFormat.format(new Date()));

                try {

                    //clientDao.updateClient(client);
                    clientDao.deleteClient(client, locale);
                    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ДОБАВЛЕНИЕ КЛИЕНТА В АРХИВ!
                    client.setState(stateDao.getStateById(5));
                    client.setMsg(messageSource.getMessage("client.isReturned", new String[]{client.getCarN()}, locale));

                    clientArchDao.addClientToArch(clientCopyToArchive(client), locale);

                  /*Помещение клиента в конец списка ожидания*/
                    client = clientService.clientRegistration(client, locale);

                } catch (Exception ex) {
                    return dbError("client.isNOTReturned", client.getCarN(), ex, locale);
                }
                break;
            case "enterBtn":
                client.setEnterTime(dateFormat.format(new Date()));
                try {
                    client.setState(stateDao.getStateById(8));
                    if (client.getRamp().equals(""))
                        client.setRamp("0");
                    if (client.getStock().equals(""))
                        client.setStock("0");
                    clientDao.updateClient(client);
                    client.setMsg(messageSource.getMessage("client.isEntered", new String[]{client.getCarN()}, locale));
                } catch (Exception ex) {
                    return dbError("client.isNOTEntered", client.getCarN(), ex, locale);
                }
                break;
            case "leaveBtn":
                client.setLeaveTime(dateFormat.format(new Date()));
                try {
                    client.setState(stateDao.getStateById(9));
                    clientDao.updateClient(client);
                    client.setMsg(messageSource.getMessage("client.isLeaved", new String[]{client.getCarN()}, locale));

                    clientDao.deleteClient(client, locale);
                    clientArchDao.addClientToArch(clientCopyToArchive(client), locale);

                } catch (Exception ex) {
                    return dbError("client.isNOTLeaved", client.getCarN(), ex, locale);
                }
                break;
        }

        return client;
    }

    private ClientArchive clientCopyToArchive(Client client) {

        ClientArchive clientArchive = new ClientArchive();
        clientArchive.setArrivedTime(client.getArrivedTime());
        clientArchive.setCallTime(client.getCallTime());
        clientArchive.setCarN(client.getCarN());
        clientArchive.setCompany(client.getCompany());
        clientArchive.setDestination(client.getDestination());
        clientArchive.setPhoneN(client.getPhoneN());
        clientArchive.setRamp(client.getRamp());
        clientArchive.setRegTime(client.getRegTime());
        clientArchive.setReturnTime(client.getReturnTime());
        clientArchive.setServedTime(client.getServedTime());
        clientArchive.setState(client.getState());
        clientArchive.setStock(client.getStock());
        clientArchive.setEnterTime(client.getEnterTime());
        clientArchive.setLeaveTime(client.getLeaveTime());

        return clientArchive;
    }

    private Client updateTable(Map<String, Client> mapClient, Client client) {

        List<String> clientList = new ArrayList<String>();

        for (Map.Entry<String, Client> entry : mapClient.entrySet()) {
            Client clientEntry = entry.getValue();

            if (clientEntry == null) {
                clientList.add(" ");
                clientList.add(" ");
                clientList.add(" ");
            }
            else {
                clientList.add(clientEntry.getCarN().toUpperCase());
                clientList.add(clientEntry.getStock());
                clientList.add(clientEntry.getRamp());
            }
        }

        InfoTable infoTable = ConnectionSingleton.getInstance().getInfoTable();
        try {
            infoTable.SendData(clientList);

        } catch (Exception ex) {
            client.setMsg(ex.getMessage());
            client.setState(stateDao.getStateById(6));
            mapClient.remove(client.getCarN());
        }

        MapClientSingleton.getInstance().setMapClient(mapClient);
        return client;
    }

    private Client clearTable(Client client) {
        List<String> clientList = new ArrayList<String>();
        for (int i = 0; i < 12; i++) {
            clientList.add(" ");
        }

        InfoTable infoTable = ConnectionSingleton.getInstance().getInfoTable();
        try {
            infoTable.SendData(clientList);
        } catch (Exception ex) {
            client.setMsg(ex.getMessage());
            client.setState(stateDao.getStateById(6));
        }

        return client;
    }

    public ModelAndView getClientListFromArchive(HttpServletRequest request, Locale locale) {
        String from = request.getParameter("fromInput");
        String to = request.getParameter("toInput");
        ModelAndView model = new ModelAndView("operator/dataOperations");
        try {
            model.addObject("clientListFromArchive", clientArchDao.getClientListfromArch(from, to, locale));
        } catch (Exception ex) {

        }
        model.addObject("from", from);
        model.addObject("to", to);
        return model;
    }
}

class MapClientSingleton {

    private static MapClientSingleton getInstance = new MapClientSingleton();

    private Map<String, Client> map;

    static MapClientSingleton getInstance() {
        return getInstance;
    }

    private MapClientSingleton() {
        map = new LinkedHashMap<String, Client>();
    }

    Map<String, Client> getMapClient() {
        return map;
    }

    void setMapClient(Map<String, Client> map) {
        this.map = map;
    }
}