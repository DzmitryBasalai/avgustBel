package spring.service;

import org.springframework.stereotype.Service;
import spring.entity.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@Service
public class ClientService extends BaseService {

    public Client clientRegistration(Client client, Locale locale) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        if (client.getDestination().equals("разгрузка")) {
            client.setCompany("");
        }
        client.setId(0);
        client.setRegTime(dateFormat.format(date));
        client.setCallTime("");
        client.setStock("");
        client.setRamp("");
        client.setArrivedTime("");
        client.setServedTime("");
        client.setReturnTime("");
        client.setEnterTime("");
        client.setLeaveTime("");

        try {
            Client clientFromBD = clientDao.getClientByCarN(client.getCarN(), locale);
            if (clientFromBD != null) {
                client.setState(stateDao.getStateById(6));
                client.setMsg(messageSource.getMessage("client.registration.sameCarNumber", new Object[]{client.getCarN()}, locale));
            }
            else {
                client.setState(stateDao.getStateById(1));
                client.setMsg(messageSource.getMessage("client.registration.success", new Object[]{client.getCarN()}, locale));
                clientDao.insertClient(client, locale);
            }

            return client;

        } catch (Exception ex) {
            client.setState(stateDao.getStateById(6));
            client.setMsg(ex.getMessage());
            return client;
        }
    }
}
