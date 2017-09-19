package spring.dao.daoImpl;

import org.apache.xmlbeans.impl.xb.xsdschema.RestrictionDocument;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.dao.ClientDao;
import spring.entity.Client;
import spring.entity.State;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Repository
public class ClientDaoImpl extends BaseDao implements ClientDao {

    @Override
    public void insertClient(Client client, Locale locale) throws Exception {

        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.persist(client);
            tx.commit();
            session.close();

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    @Override
    public int getTotalClientCount(Locale locale, String destination) throws Exception {
        try {

            //Open Session
            Session session = sessionFactory.openSession();

            //Get Criteria Builder
            CriteriaBuilder builder = session.getCriteriaBuilder();

            //Create Criteria
            CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

            Root<Client> root = criteria.from(Client.class);

            if(destination.equals("all")){
                criteria.select(builder.count(root));
            }
            if(destination.equals("load")){
                criteria.select(builder.count(root)).where(builder.equal(root.get("destination"), "загрузка"));;
            }
            if(destination.equals("unload")){
                criteria.select(builder.count(root)).where(builder.equal(root.get("destination"), "разгрузка"));;
            }
            if(destination.equals("security")){
               List<Client> clientList = session.createQuery("Select c From Client c where state.state='зарег.' or state.state='вызван' or state.state='обслужен'").getResultList();
                session.close();
               return clientList.size();
            }
            if(destination.equals("securityAndUnload")){
                List<Client> clientList = session.createQuery("Select c From Client c where state.state='зарег.' or state.state='вызван' or state.state='обслужен' or destination='разгрузка'").getResultList();
                session.close();
                return clientList.size();
            }
            if(destination.equals("securityAndLoad")){
                List<Client> clientList = session.createQuery("Select c From Client c where state.state='зарег.' or state.state='вызван' or state.state='обслужен' or destination='загрузка'").getResultList();
                session.close();
                return clientList.size();
            }

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
    public List<Client> getClientList(int offset, int total, String destination, Locale locale) throws Exception {

        try {
            //Open Session
            Session session = sessionFactory.openSession();

            //Get Criteria Builder
            CriteriaBuilder builder = session.getCriteriaBuilder();

            //Create Criteria
            CriteriaQuery<Client> criteriaQuery = builder.createQuery(Client.class);
            Root<Client> root = criteriaQuery.from(Client.class);
            criteriaQuery.select(root);
            if(destination.equals("all")){
                criteriaQuery.select(root);
            }
            if(destination.equals("load")){
                criteriaQuery.select(root).where(builder.equal(root.get("destination"), "загрузка"));
            }
            if(destination.equals("unload")){
                criteriaQuery.select(root).where(builder.equal(root.get("destination"), "разгрузка"));
            }
            if(destination.equals("security")){
                List<Client> clientList = session.createQuery("Select c From Client c where state.state='зарег.' or state.state='вызван' or state.state='обслужен'").setFirstResult(offset).setMaxResults(total).getResultList();
                session.close();
                return clientList;
            }
            if(destination.equals("securityAndUnload")){
                List<Client> clientList = session.createQuery("Select c From Client c where state.state='зарег.' or state.state='вызван' or state.state='обслужен' or destination='разгрузка'").setFirstResult(offset).setMaxResults(total).getResultList();
                session.close();
                return clientList;
            }
            if(destination.equals("securityAndLoad")){
                List<Client> clientList = session.createQuery("Select c From Client c where state.state='зарег.' or state.state='вызван' or state.state='обслужен' or destination='загрузка'").setFirstResult(offset).setMaxResults(total).getResultList();
                session.close();
                return clientList;
            }

            List<Client> clientList = session.createQuery(criteriaQuery)
                    .setFirstResult(offset).setMaxResults(total)
                    .getResultList();

            session.close();

            return clientList;
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
