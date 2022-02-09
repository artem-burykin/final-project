package publish.db;


import publish.db.entity.Dao;
import publish.db.entity.User;

import java.sql.*;

public class DBManager implements Dao {

    private static final String URL = "jdbc:mysql://localhost:3306/test2db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static DBManager instance;
    private static Object synh = new Object();

    public static DBManager getInstance() {
        if (instance == null) {
            synchronized (synh) {
                instance = new DBManager();
            }
        }
        return instance;
    }

    private DBManager() {
    }

    public void close (AutoCloseable con){
        if (con != null){
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection(boolean autocommit) throws SQLException {
        Connection con;
        con = DriverManager.getConnection(URL, USER, PASSWORD);
        con.setAutoCommit(autocommit);
        return con;
    }

    static void rollback(Connection con) throws SQLException {
        if (con != null) {
            con.rollback();
        }
    }

    public boolean insertUser (User user) throws DBException {
        Connection con = null;
        try{
            con = getConnection(true);
            insertUser(con, user);
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DBException("Cannot add user", e);
        }
        finally {
            close(con);
        }
    }
    private void insertUser(Connection con, User user) throws SQLException {
        try (PreparedStatement st = con.prepareStatement(DBConstant.INSERT_USER, Statement.RETURN_GENERATED_KEYS);) {
            int i = 0;
            st.setString(++i, user.getLogin());
            int c = st.executeUpdate();
            if (c > 0) {
                try (ResultSet keys = st.getGeneratedKeys();) {
                    if (keys.next()) {
                        user.setId(keys.getInt(1));
                    }
                }
            }
        }
    }
}
