package publish.db.dao.mysql;

import publish.db.dao.DBException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class, which create connection to db
 * @author Burykin
 */
public class ConnectionPool {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ConnectionPool.class);
    private static ConnectionPool instance = null;
    private static Object synh = new Object();

    private ConnectionPool(){
    }

    /**
     * Created or return instance ConnectionPool.
     * @return instance ConnectionPool.
     */
    public static ConnectionPool getInstance(){
        if (instance==null)
            synchronized (synh) {
                instance = new ConnectionPool();
                LOG.info("Instance of ConnectionPool was received.");
            }
        return instance;
    }

    /**
     * Created connection to db.
     * @return connection to db.
     * @throws DBException
     */
    public Connection getConnection() throws DBException {
        Connection con = null;
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/mysql");
            con = ds.getConnection();
            LOG.info("Connection has been gotten. Con: " + con);
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
            LOG.error("Can't get a connection");
            throw new DBException("Can't get a connection", e);
        }
        return con;
    }
}
