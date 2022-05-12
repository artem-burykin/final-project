package publish.db.dao.mysql;

import publish.db.dao.DBException;
import publish.db.dao.OrderDao;
import publish.db.entity.Order;
import publish.service.OrderServiceImpl;

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
     * Private auxiliary method for creating new order.
     * @param rs result set, from which we take data.
     * @return new order.
     * @throws SQLException
     */
    private Order creatingNewOrder(ResultSet rs) throws SQLException {
        Order order = OrderServiceImpl.getOrder(rs.getDouble(DBConstant.F_ORDER_TOTAL), rs.getInt(DBConstant.F_ORDER_ACCOUNT_ID),
                rs.getInt(DBConstant.F_ORDER_PRODUCT_ID));
        order.setDescription(rs.getString(DBConstant.F_ORDER_DESCRIPTION));
        order.setId(rs.getInt(DBConstant.F_ORDER_ID));
        order.setCreate_date(rs.getDate(DBConstant.F_ORDER_CREATE_DATE));
        order.setLast_update(rs.getDate(DBConstant.F_ORDER_LAST_UPDATE));
        return order;
    }

    /**
     * Insert new account into db.
     * @param order order, which would be was inserted into db.
     * @return boolean value (true, if Account was inserted).
     * @throws DBException
     */
    public boolean insertOrder (Connection con, Order order) throws DBException {
        try{
            helperInsertOrder(con, order);
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DBException("Cannot add this account", e);
        }
    }
    /**
     * Supporting method, which help us insert account into db.
     * @param con connection with database.
     * @param order order, which would be been inserted into db.
     * @throws SQLException
     */
    private void helperInsertOrder(Connection con, Order order) throws SQLException {
        try (PreparedStatement st = con.prepareStatement(DBConstant.INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            st.setDouble(++i, order.getTotal());
            st.setInt(++i, order.getAccount_id());
            st.setInt(++i, order.getProduct_id());
            st.setString(++i, order.getDescription());
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
     * Find order by id.
     * @param id id, by which we search order.
     * @return order with stated id.
     * @throws DBException
     */
    public Order findById(Connection con, int id) throws DBException{
        try (PreparedStatement stmt = con.prepareStatement(DBConstant.FIND_ORDER_BY_ID)) {
            con.setAutoCommit(false);
            int i = 0;
            stmt.setInt(++i, id);
            try (ResultSet rs = stmt.executeQuery()) {
                Order order = null;
                while (rs.next()) {
                    order = creatingNewOrder(rs);
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
    public List<Order> findByAccountId (Connection con, int account_id) throws DBException{
        try (PreparedStatement stmt = con.prepareStatement(DBConstant.FIND_ORDERS_BY_ACCOUNT_ID)) {
            con.setAutoCommit(false);
            int i = 0;
            stmt.setInt(++i, account_id);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Order> result = new ArrayList<>();
                while (rs.next()) {
                    Order order = creatingNewOrder(rs);
                    result.add(order);
                }
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Orders by this user not found", e);
        }
    }
}
