package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.service.ClientService;
import spring.service.LoginService;
import spring.service.OperatorService;
import spring.service.UserManagementService;

@Component
public abstract class BaseController {

    protected ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    protected LoginService loginService;

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    protected OperatorService operatorService;

    @Autowired
    public void setOperatorService(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    protected UserManagementService userManagementService;
    @Autowired
    public void setUserManagementService(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

}
