package spring.DAO;

import spring.model.Order;

import java.util.List;

/**
 * Created by Basalai on 09.04.2017.
 */
public interface OrderDao {
    Order getOrderByOrderN(String orderN);

    void insertOrderList(List<Order> orderList);

    void insertOrder(Order order);

    void clearOrderTable();
}
