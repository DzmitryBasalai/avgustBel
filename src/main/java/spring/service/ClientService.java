package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import spring.DAO.DAOimpl.ClientDaoImpl;
import spring.DAO.OrderDao;
import spring.model.Client;
import spring.model.Order;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@Service
public class ClientService {
    @Autowired
    ClientDaoImpl clientDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    MessageSource messageSource;

    public Client clientRegistration(Client client, String destination, Locale locale) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        client.setDestination(destination);
        if (destination.equals("разгрузка")) {
            client.setOrderNumber("");
        }

        client.setRegTime(dateFormat.format(date));
        client.setCallTime("");
        client.setStock("");
        client.setRamp("");
        client.setArrivedTime("");
        client.setServedTime("");
        client.setReturnTime("");

        try {
            client = clientDao.insertClient(client, locale);
            return client;
        }
        catch (Exception ex){
            client.setMsg(ex.getMessage());
            return client;
        }
    }


    public Order checkOrder(String orderNumber,Locale locale) {

        Order order = orderDao.getOrderByOrderN(orderNumber);
        if (order.getState() == 0) {
            order.setOrder(orderNumber);
            order.setMsg(messageSource.getMessage("order.isNotFound",new Object[]{orderNumber},locale));
            return order;
        }
        else {
            order.setMsg(messageSource.getMessage("order.isFound",new Object[]{order.getOrder()},locale));
            return order;
        }
    }
}
