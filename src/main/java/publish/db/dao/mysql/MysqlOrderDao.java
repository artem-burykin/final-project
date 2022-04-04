package publish.db.dao.mysql;

import com.sun.org.apache.xpath.internal.operations.Or;
import publish.db.dao.DBException;
import publish.db.dao.OrderDao;
import publish.db.entity.Account;
import publish.db.entity.Order;
import publish.db.entity.Product;
import publish.service.AccountServiceImpl;
import publish.service.OrderServiceImpl;
import publish.service.ProductServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlOrderDao implements OrderDao {
    MysqlOrderDao(){}

    /**
     * Close connection.
     * @param con connection, which we try to close.
     */
    public void close (AutoCloseable con){
        if (con != null){
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Insert new account into db.
     * @param order order, which would be was inserted into db.
     * @return boolean value (true, if Account was inserted).
     * @throws DBException
     */
    public boolean insertOrder (Order order) throws DBException {
        Connection con = null;
        try{
            con = ConnectionPool.getInstance().getConnection();
            insertOrder(con, order);
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DBException("Cannot add this account", e);
        }
        finally {
            close(con);
        }
    }
    /**
     * Supporting method, which help us insert account into db.
     * @param con connection with database.
     * @param order order, which would be been inserted into db.
     * @throws SQLException
     */
    private void insertOrder(Connection con, Order order) throws SQLException {
        try (PreparedStatement st = con.prepareStatement(DBConstant.INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            st.setDouble(++i, order.getTotal());
            st.setInt(++i, order.getAccount_id());
            st.setInt(++i, order.getStatus_id());
            st.setString(++i, order.getDescription());
            st.setDate(++i, order.getDate_start());
            st.setDate(++i, order.getDate_end());
            int c = st.executeUpdate();
            if (c > 0) {
                try (ResultSet keys = st.getGeneratedKeys()) {
                    if (keys.next()) {
                        order.setId(keys.getInt(1));
                    }
                }
            }
        }
    }

    /**
     * Changes certain order's status.
     * @param status_id new status for order.
     * @param id order's id, by which status we need to change.
     * @throws DBException
     */
    public void updateOrderStatus(int status_id, int id) throws DBException {
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.UPDATE_ORDER_STATUS)) {
            int i = 0;
            stmt.setInt(++i, status_id);
            stmt.setInt(++i, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("This order not found", e);
        }
    }

    /**
     * Find list of orders by status name.
     * @param name status's name, by which orders found.
     * @return order's list.
     * @throws DBException
     */
    public List<Order> findOrdersByStatus(String name) throws DBException{
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.FIND_ORDER_BY_STATUS_NAME)) {
            con.setAutoCommit(false);
            int i = 0;
            stmt.setString(++i, name);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Order> result = new ArrayList<>();
                while (rs.next()) {
                    Order order = OrderServiceImpl.getOrder(rs.getDouble(DBConstant.F_ORDER_TOTAL),
                            rs.getInt(DBConstant.F_ORDER_ACCOUNT_ID), rs.getInt(DBConstant.F_ORDER_STATUS_ID),
                            rs.getDate(DBConstant.F_ORDER_DATE_START), rs.getDate(DBConstant.F_ORDER_DATE_END));
                    order.setId(rs.getInt(DBConstant.F_ORDER_ID));
                    order.setDescription(rs.getString(DBConstant.F_ORDER_DESCRIPTION));
                    order.setCreate_date(rs.getDate(DBConstant.F_ORDER_CREATE_DATE));
                    order.setLast_update(rs.getDate(DBConstant.F_ORDER_LAST_UPDATE));
                    result.add(order);
                }
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("This order not found", e);
        }
    }

    /**
     * Find order by id.
     * @param id id, by which we search order.
     * @return order with stated id.
     * @throws DBException
     */
    public Order findById(int id) throws DBException{
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.FIND_ORDER_BY_ID)) {
            con.setAutoCommit(false);
            int i = 0;
            stmt.setInt(++i, id);
            try (ResultSet rs = stmt.executeQuery()) {
                Order order = null;
                while (rs.next()) {
                    order = OrderServiceImpl.getOrder(rs.getDouble(DBConstant.F_ORDER_TOTAL),
                            rs.getInt(DBConstant.F_ORDER_ACCOUNT_ID), rs.getInt(DBConstant.F_ORDER_STATUS_ID),
                            rs.getDate(DBConstant.F_ORDER_DATE_START), rs.getDate(DBConstant.F_ORDER_DATE_END));
                    order.setId(rs.getInt(DBConstant.F_ORDER_ID));
                    order.setDescription(rs.getString(DBConstant.F_ORDER_DESCRIPTION));
                    order.setCreate_date(rs.getDate(DBConstant.F_ORDER_CREATE_DATE));
                    order.setLast_update(rs.getDate(DBConstant.F_ORDER_LAST_UPDATE));
                }
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("This order not found", e);
        }
    }

    /**
     * Find list of orders by account's id.
     * @param account_id account's id, by which orders found.
     * @return order's list.
     * @throws DBException
     */
    public List<Order> findOrdersByAccountId (int account_id) throws DBException{
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.FIND_ORDER_BY_ACCOUNT_ID)) {
            con.setAutoCommit(false);
            int i = 0;
            stmt.setInt(++i, account_id);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Order> result = new ArrayList<>();
                while (rs.next()) {
                    Order order = OrderServiceImpl.getOrder(rs.getDouble(DBConstant.F_ORDER_TOTAL),
                            rs.getInt(DBConstant.F_ORDER_ACCOUNT_ID), rs.getInt(DBConstant.F_ORDER_STATUS_ID),
                            rs.getDate(DBConstant.F_ORDER_DATE_START), rs.getDate(DBConstant.F_ORDER_DATE_END));
                    order.setId(rs.getInt(DBConstant.F_ORDER_ID));
                    order.setDescription(rs.getString(DBConstant.F_ORDER_DESCRIPTION));
                    order.setCreate_date(rs.getDate(DBConstant.F_ORDER_CREATE_DATE));
                    order.setLast_update(rs.getDate(DBConstant.F_ORDER_LAST_UPDATE));
                    result.add(order);
                }
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("This order not found", e);
        }
    }
}
