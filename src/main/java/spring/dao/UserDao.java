package spring.dao;


import spring.entity.User;
import spring.util.UserState;

import java.util.List;

public interface UserDao {
   int addUser(User user);
   User getUserByName(String username);
   List<User> getUsersList();
   int deleteUser(String userName);
}
