package publish.service;

import publish.db.dao.DBException;
import publish.db.dao.DaoFactory;
import publish.db.dao.OrderDao;
import publish.db.dao.mysql.ConnectionPool;
import publish.db.entity.Order;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementation service for manage order.
 * @author Burykin
 */
public class OrderServiceImpl implements OrderService{
    private final OrderDao orderDao = DaoFactory.getInstance().getOrderDao();

    public static Order getOrder(double total, int account_id, int product_id){
        return Order.createOrder(total, account_id, product_id);
    }

    @Override
    public boolean insertOrder(Order order) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            return orderDao.insertOrder(con, order);
        }
        catch (SQLException e){
            throw new DBException("Cannot add this account", e);
        }
    }

    @Override
    public Order findById(int id) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            return orderDao.findById(con, id);
        }
        catch (SQLException e){
            throw new DBException("This order not found", e);
        }
    }

    @Override
    public List<Order> findByAccountId(int account_id) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            return orderDao.findByAccountId(con, account_id);
        }
        catch (SQLException e){
            throw new DBException("Orders by this user not found", e);
        }
    }
}
