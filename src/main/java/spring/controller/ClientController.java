package spring.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.entity.Client;
import java.util.Locale;

@RestController
public class ClientController extends BaseController{


    @RequestMapping(value = "/client-upload", method = RequestMethod.GET)
    public ModelAndView goToClientUploadPage() {
        return new ModelAndView("client/upload", "client", new Client());
    }

    @RequestMapping(value = "/client-download", method = RequestMethod.GET)
    public ModelAndView goToClientDownloadPage() {
        return new ModelAndView("client/download", "client", new Client());
    }



    @RequestMapping(value = "clientReg/{carN}/{phoneN}/{company}", method = RequestMethod.GET)

    public Client clientReg(@PathVariable String carN, @PathVariable String phoneN, @PathVariable String company, Locale locale){

        Client clientQueue = new Client();
        clientQueue.setCarN(carN);
        clientQueue.setPhoneN(phoneN);
        clientQueue.setCompany(company);
        clientQueue.setDestination((company.equals("noCompany")) ?("разгрузка") : ("загрузка"));

        return clientService.clientRegistration(clientQueue, locale);
    }

}
