package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import java.util.Locale;

@Service
public class LoginService {
    @Autowired
    MessageSource messageSource;

    public ModelAndView login(String error, String logout, Locale locale){
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", messageSource.getMessage("login.error", null, locale));
        }
        if (logout != null) {
            model.addObject("msg", messageSource.getMessage("login.logoutSuccess", null, locale));
        }
        model.setViewName("operator/login");
        return model;
    }
}
