package spring.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.entity.Client;
import java.util.*;


@RestController
public class OperatorController extends BaseController{

    @RequestMapping(value = "/operator-dataOperations", method = RequestMethod.GET)
    public ModelAndView goToDataManagementPage() {
        return new ModelAndView("/operator/dataOperations");
    }

    @RequestMapping(value = "/operator-transportControl", method = RequestMethod.GET)
    public ModelAndView goToTransportManagementPage() {
        return new ModelAndView("operator/transportControl", "client", new Client());
    }


    /*********************TRANSPORT CONTROL*********************/
    @RequestMapping(value = "/checkCarN/{carN}", method = RequestMethod.GET)
    public @ResponseBody
    Client getJsonObjectClientByCarN(@PathVariable String carN, Locale locale) {
        return operatorService.checkCarN(carN, locale);
    }


    @GetMapping(value = "/trControlOperationBtns-{carN}-{stock}-{ramp}-{btnValue}")
    public @ResponseBody
    Client callBtn(@PathVariable String carN, @PathVariable String stock, @PathVariable String ramp,
                   @PathVariable String btnValue, Locale locale) {
        return operatorService.operationByBtns(carN, stock, ramp, btnValue, locale);
    }


    @RequestMapping(value = "/refresh-{pageId}-{total}", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getJsonObjectClientList(@PathVariable String pageId, @PathVariable String total, Locale locale) {
        return operatorService.getClientInfoForTableRefresh(pageId, total, locale);
    }
}
