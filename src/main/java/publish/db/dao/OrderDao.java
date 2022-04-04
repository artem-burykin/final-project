package publish.db.dao;

import publish.db.entity.Order;

import java.util.List;

public interface OrderDao {
    boolean insertOrder (Order order) throws DBException;
    void updateOrderStatus(int status_id, int id) throws DBException;
    List<Order> findOrdersByStatus(String name) throws DBException;
    Order findById(int id) throws DBException;
    List<Order> findOrdersByAccountId (int account_id) throws DBException;
}
