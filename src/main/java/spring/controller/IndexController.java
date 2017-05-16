package spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class IndexController {
    @RequestMapping(value = {"/","/avgustBel"}, method = RequestMethod.GET)
    public ModelAndView welcomePage() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/user-client", method = RequestMethod.GET)
    public ModelAndView goToClientIndexPage(){
        return new ModelAndView("client/client_index");
    }
    @RequestMapping(value = "/user-operator", method = RequestMethod.GET)
    public ModelAndView goToOperatorLoginPage(){
        return new ModelAndView("operator/login");
    }
}
