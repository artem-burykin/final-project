package publish.service;

import publish.db.dao.DBException;
import publish.db.entity.Order;

import java.util.List;

public interface OrderService {
    boolean insertOrder (Order order) throws DBException;
    void updateOrderStatus(int status_id, int id) throws DBException;
    List<Order> findOrdersByStatus(String name) throws DBException;
    Order findById(int id) throws DBException;
    List<Order> findOrdersByAccountId (int account_id) throws DBException;
}
