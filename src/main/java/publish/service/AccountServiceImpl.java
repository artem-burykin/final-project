package publish.service;

import publish.db.dao.AccountDao;
import publish.db.dao.DBException;
import publish.db.dao.DaoFactory;
import publish.db.dao.mysql.ConnectionPool;
import publish.db.entity.Account;

import java.sql.Connection;
import java.util.List;

/**
 * Implementation service for manage account.
 * @author Burykin
 */
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao = DaoFactory.getInstance().getAccountDao();

    public static Account getAccount(String login, String password, String email, String first_name, String last_name, double score, int role_id){
            return Account.createAccount(login, password, email, first_name, last_name, score, role_id);
    }

    @Override
    public Account findByLogin(String login) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return accountDao.findByLogin(con, login);
    }

    @Override
    public boolean insertAccount(Account account) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return accountDao.insertAccount(con, account);
    }

    @Override
    public List<Account> findAllAccounts() throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return accountDao.findAllAccounts(con);
    }

    @Override
    public boolean findByLoginAndPassword(String login, String password) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return accountDao.findByLoginAndPassword(con, login, password);
    }

    @Override
    public void updateScore(double score, String login) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        accountDao.updateScore(con, score, login);
    }

    @Override
    public boolean isAdmin(String login) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return accountDao.isAdmin(con, login);
    }

    @Override
    public void changingUserBlock(int isBlocked, String login) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        accountDao.changingUserBlock(con, isBlocked, login);
    }

    @Override
    public int checkingUserBlock(String login) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return accountDao.checkingUserBlock(con, login);
    }
}