package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import spring.dao.ClientDao;
import spring.dao.StateDao;
import spring.dao.UserDao;

@Component
public abstract class BaseService {

    protected MessageSource messageSource;
    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    protected StateDao stateDao;
    @Autowired
    public void setStateDao(StateDao stateDao) {
        this.stateDao = stateDao;
    }

    protected ClientDao clientDao;
    @Autowired
    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    protected UserDao userDao;
    @Autowired
    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }
}
