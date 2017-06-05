package spring.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.entity.Client;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
public class OperatorController extends BaseController{

    @GetMapping(value = "/operator-dataOperations")
    public ModelAndView goToDataManagementPage() {
        return new ModelAndView("/operator/dataOperations");
    }

    @GetMapping(value = "/operator-transportControl")
    public ModelAndView goToTransportManagementPage() {
        return new ModelAndView("operator/transportControl", "client", new Client());
    }


    /*********************TRANSPORT CONTROL*********************/
    @GetMapping(value = "/checkCarN/{carN}")
    public
    Client getJsonObjectClientByCarN(@PathVariable String carN, Locale locale) {
        return operatorService.checkCarN(carN, locale);
    }


    @GetMapping(value = "/trControlOperationBtns-{carN}-{stock}-{ramp}-{btnValue}")
    public
    Client callBtn(@PathVariable String carN, @PathVariable String stock, @PathVariable String ramp,
                   @PathVariable String btnValue, Locale locale) {
        return operatorService.operationByBtns(carN, stock, ramp, btnValue, locale);
    }

    @GetMapping(value = "/refresh-{pageId}-{total}-{destination}")
    public
    Map<String, Object> getJsonObjectClientList(@PathVariable String pageId, @PathVariable String total,@PathVariable String destination, Locale locale) {
        return operatorService.getClientInfoForTableRefresh(pageId, total,destination, locale);
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public ModelAndView getClientListFromArchive(HttpServletRequest request, Locale locale) {
        return operatorService.getClientListFromArchive(request, locale);
    }

/* @RequestMapping(value = "/report", produces = "application/json" )
   public Map<String, Object> getClientListFromArchive(@RequestBody String data, Locale locale) {
       String s = data;
       return operatorService.getClientListFromArchive("", "", locale);
   }*/
}
