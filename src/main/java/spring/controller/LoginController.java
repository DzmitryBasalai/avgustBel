package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.model.Client;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import spring.service.LoginService;

import java.util.Locale;

@Controller
@SessionAttributes("user")
public class LoginController {

    @RequestMapping(value = "/operator", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        return new ModelAndView("operator/transportControl", "client", new Client());
    }

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Locale locale) {
        return loginService.login(error,logout,locale);
    }

}
