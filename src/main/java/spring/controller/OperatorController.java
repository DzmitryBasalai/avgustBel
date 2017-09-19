package spring.controller;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.entity.Client;
import spring.entity.User;
import spring.util.UserState;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
public class OperatorController extends BaseController {

    @GetMapping(value = "/operator-dataOperations")
    public ModelAndView goToDataManagementPage() {
        return new ModelAndView("/operator/dataOperations");
    }

    @GetMapping(value = "/operator-transportControl")
    public ModelAndView goToTransportManagementPage() {
        return new ModelAndView("operator/transportControl", "client", new Client());
    }

    @GetMapping(value = "/operator-userManagement")
    public ModelAndView goToUsertManagementPage() {
        return new ModelAndView("operator/userManagement", "null", null);
    }

    /*********************TRANSPORT CONTROL*********************/
    @GetMapping(value = "/checkCarN/{carN}")
    public Client getJsonObjectClientByCarN(@PathVariable String carN, Locale locale) {
        return operatorService.checkCarN(carN, locale);
    }


    @GetMapping(value = "/trControlOperationBtns-{carN}-{stock}-{ramp}-{btnValue}")
    public Client callBtn(@PathVariable String carN, @PathVariable String stock, @PathVariable String ramp,
                          @PathVariable String btnValue, Locale locale) {
        return operatorService.operationByBtns(carN, stock, ramp, btnValue, locale);
    }

    @GetMapping(value = "/refresh-{pageId}-{total}-{destination}")
    public Map<String, Object> getJsonObjectClientList(@PathVariable String pageId, @PathVariable String total, @PathVariable String destination, Locale locale) {
        return operatorService.getClientInfoForTableRefresh(pageId, total, destination, locale);
    }

    @GetMapping(value = "/report")
    public ModelAndView getClientListFromArchive(HttpServletRequest request, Locale locale) {
        return operatorService.getClientListFromArchive(request, locale);
    }

    @PostMapping(value = "/addNewUser", produces = "application/json")
    public int addNewUser(@RequestBody JSONObject jsonData) {
        return userManagementService.addNewUser(jsonData);
    }
    @PostMapping(value = "/mofifyUser", produces = "application/json")
    public int mofifyUser(@RequestBody JSONObject jsonData) {
        return userManagementService.modifyUser(jsonData);
    }

    @GetMapping(value = "/getUsersList")
    public List<String> getUsersList() {
        return userManagementService.getUsersList();
    }
    @GetMapping(value = "/getUserByName-{userName}")
    public User getUserByName(@PathVariable String userName) {
        return userManagementService.getUserByName(userName);
    }
    @GetMapping(value = "/deleteUser-{userName}")
    public int deleteUser(@PathVariable String userName) {
        return userManagementService.deleteUser(userName);
    }
}
