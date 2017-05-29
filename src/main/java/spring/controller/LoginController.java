package spring.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import spring.entity.Client;
import java.util.Locale;

@Controller
@SessionAttributes("user")
public class LoginController extends BaseController{

    @GetMapping(value = "/operator")
    public ModelAndView adminPage() {
        return new ModelAndView("operator/transportControl", "client", new Client());
    }

    @GetMapping(value = "/403")
    public String to403Page(){
        return "operator/403";
    }

    @GetMapping(value = "/login")
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Locale locale) {
        return loginService.login(error,logout,locale);
    }

}
