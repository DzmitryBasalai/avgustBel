package spring.dao.daoImpl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import spring.dao.ClientArchDao;

import spring.entity.ClientArchive;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Repository
public class ClientArchDaoImpl extends BaseDao implements ClientArchDao {

    @Override
    public void addClientToArch(ClientArchive clientArchive, Locale locale) throws Exception {
        try {
            Session session = sessionFactory.openSession();

            Transaction tx = session.beginTransaction();
            session.save(clientArchive);
            tx.commit();
            session.close();

        } catch (Exception ex) {
            throw new Exception(messageSource.getMessage("exception.dbError", new Object[]{}, locale) + ex.getMessage());
        }
    }

    @Override
    public List<ClientArchive> getClientListfromArch(String from, String to, Locale locale) throws Exception {
        try {
            //Open Session
            Session session = sessionFactory.openSession();

            //Get Criteria Builder
            CriteriaBuilder builder = session.getCriteriaBuilder();

            //Create Criteria
            CriteriaQuery<ClientArchive> criteriaQuery = builder.createQuery(ClientArchive.class);
            Root<ClientArchive> root = criteriaQuery.from(ClientArchive.class);

            criteriaQuery.select(root).where(builder.between(root.<String>get("regTime"), from, to));


            //Use criteriaQuery to query with session to fetch all contacts
            List<ClientArchive> clientList = session.createQuery(criteriaQuery)
                    .getResultList();


            //Close session
            session.close();

            return clientList;
        } catch (Exception ex) {
            throw new Exception(messageSource.getMessage("exception.dbError", new Object[]{}, locale) + ex.getMessage());
        }
    }


}
