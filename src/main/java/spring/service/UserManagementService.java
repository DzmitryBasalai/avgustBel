package spring.service;

import org.json.simple.JSONObject;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import spring.entity.User;
import spring.entity.UserRole;
import spring.util.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static spring.util.Role.*;

@Service
public class UserManagementService extends BaseService {

    public int addNewUser(JSONObject jsonObject) {

        User user = new User();
        user.setUsername((String) jsonObject.get("userName"));
        user.setPassword(BCrypt.hashpw((String) jsonObject.get("userPass"), BCrypt.gensalt()));
        user.setEnabled(true);

        List<Role> roleList = new ArrayList<>();
        if ((boolean) jsonObject.get("role_admin"))
            roleList.add(ROLE_ADMIN);
        if ((boolean) jsonObject.get("role_operator_load"))
            roleList.add(ROLE_OPERATOR_LOAD);
        if ((boolean) jsonObject.get("role_operator_unload"))
            roleList.add(ROLE_OPERATOR_UNLOAD);
        if ((boolean) jsonObject.get("role_security"))
            roleList.add(ROLE_SECURITY);

        for (Role role : roleList) {
            UserRole userRole = new UserRole();
            userRole.setRole(role);
            userRole.setUser(user);
            user.getUserRoles().add(userRole);
        }

        return userDao.addUser(user);
    }

    public int modifyUser(JSONObject jsonObject) {
        int delStatus = deleteUser((String) jsonObject.get("userNameRoot"));
        int addStatus = addNewUser(jsonObject);
        if(delStatus==0&&addStatus==0)
            return 0;
        else
            return 1;
    }

    public List<String> getUsersList() {
        List<String> usersNames = new ArrayList<>();
        for (User user : userDao.getUsersList()) {
            usersNames.add(user.getUsername());
        }
        return usersNames;
    }

    public User getUserByName(String userName) {
        User user = userDao.getUserByName(userName);
        Collection<UserRole> userRoles = new ArrayList<>();
        for (UserRole userRole : user.getUserRoles()) {
            userRole.setUser(null);
            userRoles.add(userRole);
        }
        String password = "";
        BCrypt.checkpw(password, user.getPassword());
        user.setUserRoles(userRoles);
        return user;
    }

    public int deleteUser(String username) {
        return userDao.deleteUser(username);
    }

}
