package publish.service;

import publish.db.dao.DBException;
import publish.db.entity.Order;

import java.util.List;

/**
 * Service for manage order.
 * @author Burykin
 */
public interface OrderService {
    boolean insertOrder (Order order) throws DBException;
    Order findById(int id) throws DBException;
    List<Order> findByAccountId (int account_id) throws DBException;
}
