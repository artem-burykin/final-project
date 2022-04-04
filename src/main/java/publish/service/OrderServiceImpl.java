package publish.service;

import publish.db.dao.DBException;
import publish.db.dao.DaoFactory;
import publish.db.dao.OrderDao;
import publish.db.entity.Order;

import java.sql.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService{
    private final OrderDao orderDao = DaoFactory.getInstance().getOrderDao();

    public static Order getOrder(double total, int account_id, int status_id, Date date_start, Date date_end){
        return Order.createOrder(total, account_id, status_id, date_start, date_end);
    }


    @Override
    public boolean insertOrder(Order order) throws DBException {
        return orderDao.insertOrder(order);
    }

    @Override
    public void updateOrderStatus(int status_id, int id) throws DBException {
        orderDao.updateOrderStatus(status_id, id);
    }

    @Override
    public List<Order> findOrdersByStatus(String name) throws DBException {
        return orderDao.findOrdersByStatus(name);
    }

    @Override
    public Order findById(int id) throws DBException {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> findOrdersByAccountId(int account_id) throws DBException {
        return orderDao.findOrdersByAccountId(account_id);
    }
}
