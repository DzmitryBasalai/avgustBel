package spring.DAO.DAOimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import spring.DAO.OrderDao;
import spring.model.Order;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Basalai on 09.04.2017.
 */
@Component
public class OrderDaoImpl implements OrderDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void getDataSourse(DataSource dataSourse) {
        this.jdbcTemplate = new JdbcTemplate(dataSourse);
    }

    @Override
    public Order getOrderByOrderN(String orderN) {

        Order order = new Order();

        String sql = "SELECT * FROM avgustbeldb.order where avgustbeldb.order.orderN = ?;";
        List<Order> orderList = this.jdbcTemplate.query(
                sql, new Object[]{orderN}, new RowMapper<Order>() {

                    @Override
                    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Order aOrder = new Order();
                        aOrder.setId(rs.getInt("id"));
                        aOrder.setOrder(rs.getString("orderN"));
                        return aOrder;
                    }
                });

        if (orderList.size() == 0) {
            order.setState(0);
            return order;
        }
        order = orderList.get(0);
        order.setState(1);
        return order;
    }

    @Override
    public void insertOrderList(List<Order> orderList) {
        for (Order order : orderList) {
            insertOrder(order);
        }
    }

    @Override
    public void insertOrder(Order order) {
        String sql = "insert into avgustbeldb.order (orderN) VALUES (?)";
        this.jdbcTemplate.update(sql, order.getOrder());
    }

    @Override
    public void clearOrderTable() {
        String sqlAutoIncr = "ALTER TABLE avgustbeldb.order AUTO_INCREMENT = 1";

        String sqlSafeUpdate = "SET SQL_SAFE_UPDATES=0";
        this.jdbcTemplate.update(sqlSafeUpdate);
        String sqlClearTable = "DELETE FROM avgustbeldb.order";
        this.jdbcTemplate.update(sqlClearTable);

        this.jdbcTemplate.update(sqlAutoIncr);
    }
}
