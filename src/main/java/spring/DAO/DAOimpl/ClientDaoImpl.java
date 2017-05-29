package spring.dao.daoImpl;

import org.hibernate.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.dao.ClientDao;
import spring.entity.Client;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Locale;

@Repository
public class ClientDaoImpl extends BaseDao implements ClientDao {


    @Override
    public Client getClientByCarN(String carN, Locale locale) throws Exception {
        //Open Session
        try {
            Session session = sessionFactory.openSession();

            //Get Criteria Builder
            CriteriaBuilder builder = session.getCriteriaBuilder();

            //Create Criteria
            CriteriaQuery<Client> criteria = builder.createQuery(Client.class);
            Root<Client> root = criteria.from(Client.class);
            criteria.select(root).where(builder.equal(root.get("carN"), carN));

            //Use criteria to query with session to fetch all contacts
            List<Client> clientList = session.createQuery(criteria).getResultList();

            //Close session
            session.close();
            if (clientList.size() == 0) {
                return null;
            }
            else
                return clientList.get(0);

        } catch (Exception ex) {
            throw new Exception(messageSource.getMessage("exception.dbError", new Object[]{}, locale) + ex.getMessage());

        }
    }

    @Override
    public void insertClient(Client client, Locale locale) throws Exception {
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(client);
            tx.commit();
            session.close();
        } catch (Exception ex) {

            throw new Exception(messageSource.getMessage("exception.dbError", new Object[]{}, locale) + ex.getMessage());
        }
    }

    @Override
    public List<Client> getClientList(int offset, int total, Locale locale) throws Exception {

        try {
            //Open Session
            Session session = sessionFactory.openSession();

            //Get Criteria Builder
            CriteriaBuilder builder = session.getCriteriaBuilder();

            //Create Criteria
            CriteriaQuery<Client> criteriaQuery = builder.createQuery(Client.class);
            Root<Client> root = criteriaQuery.from(Client.class);
            criteriaQuery.select(root);

            //Use criteriaQuery to query with session to fetch all contacts
            List<Client> clientList = session.createQuery(criteriaQuery)
                    .setFirstResult(offset).setMaxResults(total)
                    .getResultList();


            //Close session
            session.close();

            return clientList;
        } catch (Exception ex) {
            throw new Exception(messageSource.getMessage("exception.dbError", new Object[]{}, locale) + ex.getMessage());
        }
    }

    @Transactional
    @Override
    public int getTotalClientCount(Locale locale) throws Exception {
        try {
            //Open Session
            Session session = sessionFactory.openSession();

            //Get Criteria Builder
            CriteriaBuilder builder = session.getCriteriaBuilder();

            //Create Criteria
            CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

            Root<Client> root = criteria.from(Client.class);
            criteria.select(builder.count(root));

            //Use criteria to query with session to fetch all contacts
            int count = (session.createQuery(criteria).getSingleResult()).intValue();

            //Close session
            session.close();
            return count;
        } catch (Exception ex) {
            throw new Exception(messageSource.getMessage("exception.dbError", new Object[]{}, locale) + ex.getMessage());
        }
    }


    @Override
    public void updateClient(Client client) throws Exception {
        try {
            Session session = sessionFactory.openSession();

            Transaction tx = session.beginTransaction();
            session.merge(client);
            tx.commit();
            session.close();

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }


    @Override
    public void deleteClient(Client client, Locale locale) throws Exception {
        try {
            Session session = sessionFactory.openSession();

            Transaction tx = session.beginTransaction();
            session.delete(client);
            tx.commit();
            session.close();

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
