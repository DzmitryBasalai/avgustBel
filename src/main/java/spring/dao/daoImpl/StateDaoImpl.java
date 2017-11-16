package spring.dao.daoImpl;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import spring.dao.StateDao;
import spring.entity.State;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

@Component
public class StateDaoImpl extends BaseDao implements StateDao {

    @Override
    public State getStateById(int id) {
            Session session = sessionFactory.openSession();

            //Get Criteria Builder
            CriteriaBuilder builder = session.getCriteriaBuilder();

            //Create Criteria
            CriteriaQuery<State> criteria = builder.createQuery(State.class);
            Root<State> stateRoot = criteria.from(State.class);
            criteria.select(stateRoot);

            ParameterExpression<Integer> parameterExpression = builder.parameter(Integer.class);
            Predicate carQueRestriction = builder.equal(stateRoot.get("id"), parameterExpression);

            criteria.where(carQueRestriction);

            TypedQuery<State> query = session.createQuery(criteria);
            query.setParameter(parameterExpression, id);
            //Use criteria to query with session to fetch all contacts
            State state = query.getResultList().get(0);

            //Close session
            session.close();
            return state;
    }
}
