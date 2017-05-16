package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.model.Client;
import spring.model.Order;
import spring.service.ClientService;
import java.util.Locale;

@RestController
public class ClientController {

    @Autowired
    ClientService clientService;


    @RequestMapping(value = "/client-upload", method = RequestMethod.GET)
    public ModelAndView goToClientUploadPage() {
        return new ModelAndView("client/upload", "client", new Client());
    }

    @RequestMapping(value = "/client-download", method = RequestMethod.GET)
    public ModelAndView goToClientDownloadPage() {
        return new ModelAndView("client/download", "client", new Client());
    }



    @RequestMapping(value = "clientReg/{carN}/{phoneN}/{orderN}", method = RequestMethod.GET)
    @ResponseBody
    public Client clientReg(@PathVariable String carN, @PathVariable String phoneN,@PathVariable String orderN, Locale locale){
        Client client = new Client();
        client.setCarNumber(carN);
        client.setPhoneNumber(phoneN);
        client.setOrderNumber(orderN);
        String destination = (orderN.equals("noOrder")) ?("разгрузка") : ("загрузка");
        return clientService.clientRegistration(client, destination, locale);
    }

    @RequestMapping(value = "checkOrder-{orderNumber}", method = RequestMethod.GET)
    @ResponseBody
    public Order checkOrder(@PathVariable String orderNumber, Locale locale) {
        return clientService.checkOrder(orderNumber, locale);
    }
}
