package publish.db.dao;

import publish.db.entity.Order;

import java.util.List;

public interface OrderDao {
    boolean insertOrder (Order order) throws DBException;
    Order findById(int id) throws DBException;
    List<Order> findByAccountId (int account_id) throws DBException;
}
