package publish.db.dao.mysql;

import publish.db.dao.AccountDao;
import publish.db.dao.DBException;
import publish.db.entity.Account;
import publish.service.AccountServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlAccountDao implements AccountDao {

    private static MysqlAccountDao instance;
    private static final Object synh = new Object();

    public static MysqlAccountDao getInstance() {
        System.out.println("MysqlAccountDao@getInstance() start");
        if (instance == null) {
            synchronized (synh) {
                instance = new MysqlAccountDao();
                System.out.println("MysqlAccountDao@getInstance() instance created: " + instance);
            }
        }
        System.out.println("ConnectionPool@getInstance() exit. Instance: " + instance);
        return instance;
    }

    private MysqlAccountDao() {}

    public void close (AutoCloseable con){
        System.out.println("MysqlAccountDao@close(AutoCloseable con) start");
        if (con != null){
            try {
                con.close();
                System.out.println("MysqlAccountDao@close(AutoCloseable con) was closed");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("MysqlAccountDao@close(AutoCloseable con) exception");
            }
        }
    }

    static void rollback(Connection con) throws SQLException {
        System.out.println("MysqlAccountDao@rollback(Connection con) start");
        if (con != null) {
            con.rollback();
        }
        System.out.println("MysqlAccountDao@rollback(Connection con) was rollback");
    }

    public Account getAccountByLogin (String login) throws DBException {
        System.out.println("MysqlAccountDao@getAccountByLogin(String login) start");
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.GET_ACCOUNT)) {
            con.setAutoCommit(false);
            int i = 0;
            stmt.setString(++i, login);
            try (ResultSet rs = stmt.executeQuery()) {
                Account account = null;
                while (rs.next()) {
                    account = AccountServiceImpl.getAccount(rs.getString(DBConstant.F_ACCOUNT_LOGIN),
                            rs.getString(DBConstant.F_ACCOUNT_PASSWORD), rs.getInt(DBConstant.F_ACCOUNT_ROLE_ID));
                    account.setId(rs.getInt(DBConstant.F_ACCOUNT_ID));
                    account.setCreate_date(rs.getDate(DBConstant.F_ACCOUNT_CREATE_DATE));
                    account.setLast_update(rs.getDate(DBConstant.F_ACCOUNT_LAST_UPDATE));
                }
                System.out.println("Account with this login: " + login + " is " + account);
                return account;
            }
        } catch (SQLException e) {
            System.out.println("MysqlAccountDao@getAccount(String login) exception");
            e.printStackTrace();
            throw new DBException("This users not found", e);
        }
    }

    public boolean insertAccount (Account account) throws DBException {
        System.out.println("MysqlAccountDao@insertAccount(Account account) start");
        Connection con = null;
        try{
            con = ConnectionPool.getInstance().getConnection();
            insertAccount(con, account);
            System.out.println("MysqlAccountDao@insertAccount(Account account) was inserted");
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("MysqlAccountDao@insertAccount(Account account) exception");
            throw new DBException("Cannot add this account", e);
        }
        finally {
            close(con);
        }
    }
    private void insertAccount(Connection con, Account account) throws SQLException {
        System.out.println("MysqlAccountDao@insertAccount(Connection con, Account account) start");
        try (PreparedStatement st = con.prepareStatement(DBConstant.INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            st.setString(++i, account.getLogin());
            st.setString(++i, account.getPassword());
            st.setInt(++i, account.getRole_id());
            int c = st.executeUpdate();
            if (c > 0) {
                try (ResultSet keys = st.getGeneratedKeys()) {
                    if (keys.next()) {
                        account.setId(keys.getInt(1));
                    }
                }
            }
        }
        System.out.println("MysqlAccountDao@insertAccount(Connection con, Account account) exit");
    }

    public List<Account> findAllUsers() throws DBException{
        System.out.println("MysqlAccountDao@findAllUsers() start");
        try(Connection con = ConnectionPool.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(DBConstant.SELECT_USERS)){
            con.setAutoCommit(false);
            List<Account> result = new ArrayList<>();
            while(rs.next()){
                Account account = Account.createAccount(rs.getString(DBConstant.F_ACCOUNT_LOGIN),
                        rs.getString(DBConstant.F_ACCOUNT_PASSWORD), rs.getInt(DBConstant.F_ACCOUNT_ROLE_ID));
                account.setId(rs.getInt(DBConstant.F_ACCOUNT_ID));
                account.setCreate_date(rs.getDate(DBConstant.F_ACCOUNT_CREATE_DATE));
                account.setLast_update(rs.getDate(DBConstant.F_ACCOUNT_LAST_UPDATE));
                result.add(account);
                System.out.println("User: " + account);
            }
            System.out.println("MysqlAccountDao@findAllUsers() return list of users: " + result);
            return result;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("MysqlAccountDao@findAllUsers() exception");
            throw new DBException("There aren't users in the database", e);
        }
    }

    public List<Account> findAllAdmins() throws DBException{
        System.out.println("MysqlAccountDao@findAllAdmins() start");
        try(Connection con = ConnectionPool.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(DBConstant.SELECT_ADMINS)){
            con.setAutoCommit(false);
            List<Account> result = new ArrayList<>();
            while(rs.next()){
                Account account = Account.createAccount(rs.getString(DBConstant.F_ACCOUNT_LOGIN),
                        rs.getString(DBConstant.F_ACCOUNT_PASSWORD), rs.getInt(DBConstant.F_ACCOUNT_ROLE_ID));
                account.setId(rs.getInt(DBConstant.F_ACCOUNT_ID));
                account.setCreate_date(rs.getDate(DBConstant.F_ACCOUNT_CREATE_DATE));
                account.setLast_update(rs.getDate(DBConstant.F_ACCOUNT_LAST_UPDATE));
                result.add(account);
                System.out.println("Admin: " + account);
            }
            System.out.println("MysqlAccountDao@findAllUsers() return list of admins: " + result);
            return result;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("MysqlAccountDao@findAllUsers() exception");
            throw new DBException("There aren't admins in the database", e);
        }
    }

    public boolean deleteUsers(Account... accounts) throws DBException {
        System.out.println("MysqlAccountDao@deleteUsers(Account... accounts) start");
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.DELETE_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            for (Account user : accounts) {
                if ("User".equals(MysqlRoleDao.getInstance().getRoleById(MysqlAccountDao.
                        getInstance().getAccountByLogin(user.getLogin()).getRole_id()).getName())) {
                    stmt.setString(1, user.getLogin());
                    stmt.executeUpdate();
                }
            }
            System.out.println("MysqlAccountDao@deleteUsers(Account... accounts) accounts were deleted");
            return true;
        } catch (SQLException e) {
            System.out.println("MysqlAccountDao@deleteUsers(Account... accounts) exception");
            e.printStackTrace();
            throw new DBException("Cannot delete these accounts", e);
        }
    }

    public boolean deleteAdmins(Account... accounts) throws DBException {
        System.out.println("MysqlAccountDao@deleteAdmins(Account... accounts) start");
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.DELETE_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            for (Account user : accounts) {
                if ("Administrator".equals(MysqlRoleDao.getInstance().getRoleById(MysqlAccountDao.
                        getInstance().getAccountByLogin(user.getLogin()).getRole_id()).getName())) {
                    stmt.setString(1, user.getLogin());
                    stmt.executeUpdate();
                }
            }
            System.out.println("MysqlAccountDao@deleteAdmins(Account... accounts) accounts were deleted");
            return true;
        } catch (SQLException e) {
            System.out.println("MysqlAccountDao@deleteAdmin(Account... accounts) exception");
            e.printStackTrace();
            throw new DBException("Cannot delete these accounts", e);
        }
    }
}
