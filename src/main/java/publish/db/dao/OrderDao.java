package publish.db.dao;

import publish.db.entity.Order;

import java.sql.Connection;
import java.util.List;

/**
 * Interface with method, which realization in MysqlOrderDao.
 * @author Byrukin
 */
public interface OrderDao {
    boolean insertOrder (Connection con, Order order) throws DBException;
    Order findById(Connection con, int id) throws DBException;
    List<Order> findByAccountId (Connection con, int account_id) throws DBException;
}
