package publish.service;

import publish.db.dao.DBException;
import publish.db.dao.DaoFactory;
import publish.db.dao.OrderDao;
import publish.db.entity.Order;

import java.sql.Date;
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
        return orderDao.insertOrder(order);
    }

    @Override
    public Order findById(int id) throws DBException {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> findByAccountId(int account_id) throws DBException {
        return orderDao.findByAccountId(account_id);
    }
}
