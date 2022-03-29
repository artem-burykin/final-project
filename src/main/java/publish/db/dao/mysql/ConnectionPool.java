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
    private static ConnectionPool instance = null;
    private static Object synh = new Object();

    private ConnectionPool(){
    }

    /**
     * Created or return instance ConnectionPool.
     * @return instance ConnectionPool.
     */
    public static ConnectionPool getInstance(){
        System.out.println("ConnectionPool@getInstance() start");
        if (instance==null)
            synchronized (synh) {
                instance = new ConnectionPool();
                System.out.println("ConnetctionPool@getInstance() instance created: " + instance);
            }
        System.out.println("ConnectionPool@getInstance() exit");
        return instance;
    }

    /**
     * Created connection to db.
     * @return connection to db.
     * @throws DBException
     */
    public Connection getConnection() throws DBException {
        System.out.println("ConnectionPool@getConnection() start");
        Connection con = null;
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/mysql");
            con = ds.getConnection();
            System.out.println("Connection: " + con);
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
            System.out.println("ConnectionPool@getConnection() exception");
            throw new DBException("Can't get a connection", e);
        }
        return con;
    }
}
