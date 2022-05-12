package publish.db.dao.mysql;

import publish.db.dao.AccountDao;
import publish.db.dao.DBException;
import publish.db.entity.Account;
import publish.service.AccountServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for Account entity.
 * @author Burykin
 */

public class MysqlAccountDao implements AccountDao {
    MysqlAccountDao() {}

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
     * Private auxiliary method for creating new account.
     * @param rs result set, from which we take data.
     * @return new account.
     * @throws SQLException
     */
    private Account creatingNewAccount(ResultSet rs) throws SQLException{
        Account account = AccountServiceImpl.getAccount(rs.getString(DBConstant.F_ACCOUNT_LOGIN),
                rs.getString(DBConstant.F_ACCOUNT_PASSWORD), rs.getString(DBConstant.F_ACCOUNT_EMAIL),
                rs.getString(DBConstant.F_ACCOUNT_FIRST_NAME), rs.getString(DBConstant.F_ACCOUNT_LAST_NAME),
                rs.getDouble(DBConstant.F_ACCOUNT_SCORE), rs.getInt(DBConstant.F_ACCOUNT_ROLE_ID));
        account.setId(rs.getInt(DBConstant.F_ACCOUNT_ID));
        account.setCreate_date(rs.getDate(DBConstant.F_ACCOUNT_CREATE_DATE));
        account.setLast_update(rs.getDate(DBConstant.F_ACCOUNT_LAST_UPDATE));
        account.setIsBlocked(rs.getInt(DBConstant.F_ACCOUNT_IS_BLOCKED));
        return account;
    }

    /**
     * Insert new account into db.
     * @param account account, which would be was inserted into db.
     * @return boolean value (true, if Account was inserted).
     * @throws DBException
     */
    public boolean insertAccount (Connection con, Account account) throws DBException {
        try{
            helperInsertAccount(con, account);
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
     * @param account account, which would be been inserted into db.
     * @throws SQLException
     */
    private void helperInsertAccount(Connection con, Account account) throws SQLException {
        try (PreparedStatement st = con.prepareStatement(DBConstant.INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            st.setString(++i, account.getLogin());
            st.setString(++i, account.getPassword());
            st.setString(++i, account.getEmail());
            st.setString(++i, account.getFirst_name());
            st.setString(++i, account.getLast_name());
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
    }

    /**
     * Find all accounts.
     * @return list with account.
     * @throws DBException
     */
    public List<Account> findAllAccounts(Connection con) throws DBException{
        try(Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(DBConstant.FIND_ALL_ACCOUNT)){
            con.setAutoCommit(false);
            List<Account> result = new ArrayList<>();
            while(rs.next()){
                result.add(creatingNewAccount(rs));
            }
            return result;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DBException("There aren't accounts in the database", e);
        }
    }
    /**
     * Find user by login.
     * @param login login, by which we search user.
     * @return user with stated login.
     * @throws DBException
     */
    public Account findByLogin(Connection con, String login) throws DBException{
        try (PreparedStatement stmt = con.prepareStatement(DBConstant.FIND_ACCOUNT_BY_LOGIN)) {
            con.setAutoCommit(false);
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                Account account = null;
                if (rs.next()) {
                    account = creatingNewAccount(rs);
                }
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("This user not found", e);
        }
    }

    /**
     * Search user by login and password.
     * @param login login, by which we search user.
     * @param password password, by which we search user.
     * @return boolean value(returns true, if method has found user).
     * @throws DBException
     */
    public boolean findByLoginAndPassword(Connection con, String login, String password) throws DBException {
        try (PreparedStatement stmt = con.prepareStatement(DBConstant.FIND_ACCOUNT_BY_LOGIN_AND_PASSWORD)) {
            int i = 0;
            stmt.setString(++i, login);
            stmt.setString(++i, password);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.isBeforeFirst()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("This user not found", e);
        }
    }

    /**
     * Update account score.
     * @param score state of user's score.
     * @param login user's login, which we need to change.
     * @throws DBException
     */
    public void updateScore(Connection con, double score, String login) throws DBException {
        try(PreparedStatement stmt = con.prepareStatement(DBConstant.UPDATE_ACCOUNT_SCORE)) {
            int i = 0;
            stmt.setDouble(++i, score);
            stmt.setString(++i, login);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("This user can't change score", e);
        }
    }

    /**
     * Block or unblock user.
     * @param isBlocked state of user's blocking.
     * @param login user's login, which we need to block/unblock.
     * @throws DBException
     */
    public void changingUserBlock(Connection con, int isBlocked, String login) throws DBException {
        try(PreparedStatement stmt = con.prepareStatement(DBConstant.BLOCKING_ACCOUNT)) {
            int i = 0;
            stmt.setInt(++i, isBlocked);
            stmt.setString(++i, login);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("This user can't to blocked or unblocked", e);
        }
    }

    /**
     * Checks what state of blocking user has.
     * @param login user's login, which state of blocking we need to check.
     * @return state of user's blocking.
     * @throws DBException
     */
    public int checkingUserBlock(Connection con, String login) throws DBException {
        try(PreparedStatement stmt = con.prepareStatement(DBConstant.IS_ACCOUNT_BLOCKED)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("This user not found", e);
        }
        return -1;
    }

    /**
     * Checks account on admin role.
     * @param login login, by which checking account on admin.
     * @return boolean value(returns true, if method has found admin).
     * @throws DBException
     */
    public boolean isAdmin (Connection con, String login) throws DBException{
        try (PreparedStatement stmt = con.prepareStatement(DBConstant.IS_ADMIN)){
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new DBException("Account with this login not found", e);
        }
        return false;
    }
}