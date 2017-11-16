package spring.dao.daoImpl;

import org.springframework.stereotype.Repository;
import spring.dao.UserDao;
import spring.entity.User;
import spring.util.UserState;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;


import static spring.util.UserState.*;

@Repository
public class UserDaoImpl extends BaseDao implements UserDao {
    private UserState userState;

    @Override
    public int addUser(User user) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        userState = OK;

        try {
            tx.begin();
            if (getUserByName(user.getUsername()) == null) {
                em.persist(user);
                tx.commit();
            }
            else {
                userState = USER_WITH_SUCH_USERNAME_ALREADY_EXISTS;
            }
        } catch (Exception e) {
            userState = ERROR;
        }
        em.close();
        return userState.ordinal();

    }

    @Override
    public int deleteUser(String userName) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        userState = OK;

        try {
            tx.begin();
            User userFromDb = getUserByName(userName);
            if ( userFromDb!= null) {
                em.remove(em.contains(userFromDb) ? userFromDb : em.merge(userFromDb));
            }else{
                userState = NO_USER_WITH_SUCH_USER_NAME;
            }

            tx.commit();
        }catch (Exception ex){
            userState = ERROR;
        }
        em.close();

        return userState.ordinal();
    }

    @Override
    public User getUserByName(String username) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        User user = em.find(User.class, username);
        tx.commit();
        em.close();
        return user;
    }

    @Override
    public List<User> getUsersList() {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> u = criteriaQuery.from(User.class);
        criteriaQuery.select(u);

        List<User> usersList = em.createQuery(criteriaQuery).getResultList();

        em.close();
        return usersList;
    }
}
