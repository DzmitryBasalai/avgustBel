package spring.dao.daoImpl;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.service.BaseService;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Component
public abstract class BaseDao extends BaseService{

    protected SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

   protected EntityManagerFactory emf = Persistence.createEntityManagerFactory("storage_unit");

}
