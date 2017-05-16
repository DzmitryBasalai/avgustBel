package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.model.Client;
import spring.service.ExcelService;
import spring.service.OperatorService;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
public class OperatorController {

    @RequestMapping(value = "/operator-dataOperations", method = RequestMethod.GET)
    public ModelAndView goToDataManagementPage() {
        return new ModelAndView("/operator/dataOperations");
    }

    @RequestMapping(value = "/operator-transportControl", method = RequestMethod.GET)
    public ModelAndView goToTransportManagementPage() {
        return new ModelAndView("operator/transportControl", "client", new Client());
    }


    /*********************DATA OPERATIONS*********************/
    @Autowired
    private OperatorService operatorService;

    @RequestMapping(value = "/operator-downloadFile", method = RequestMethod.POST)
    public ModelAndView processExcelFile(@RequestParam("file") MultipartFile file, Locale locale, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mapOrder", operatorService.processFile(file, locale));
        return new ModelAndView("redirect:operator-dataOperations-downloadFile");
    }

    @Autowired
    ExcelService excelService;

    @RequestMapping(value = "operator-dataOperations-downloadFile", method = RequestMethod.GET)
    public ModelAndView excelFile(HttpServletRequest request) {
        return excelService.downloadExcelFile(request);
    }


    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public ModelAndView getExcel(HttpServletRequest request) {
        return excelService.uploadFile(request);
    }
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

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
    Map<String, Object> getJsonObjectClientList(@PathVariable String pageId, @PathVariable String total) {
        return operatorService.getClientInfoForTableRefresh(pageId, total);
    }
}
