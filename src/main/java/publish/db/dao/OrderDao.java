package publish.db.dao;

import publish.db.entity.Order;

import java.util.List;

/**
 * Interface with method, which realization in MysqlOrderDao.
 * @author Byrukin
 */
public interface OrderDao {
    boolean insertOrder (Order order) throws DBException;
    Order findById(int id) throws DBException;
    List<Order> findByAccountId (int account_id) throws DBException;
}
