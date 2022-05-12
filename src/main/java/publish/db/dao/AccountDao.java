package publish.db.dao;

import publish.db.entity.Account;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface with method, which realization in MysqlAccountDao.
 * @author Byrukin
 */
public interface AccountDao {
    Account findByLogin(Connection con, String login) throws DBException;
    List<Account> findAllAccounts(Connection con) throws DBException;
    boolean insertAccount (Connection con, Account account) throws DBException;
    boolean findByLoginAndPassword(Connection con, String login, String password) throws DBException;
    void changingUserBlock(Connection con, int isBlocked, String login) throws DBException;
    void updateScore(Connection con, double score, String login) throws DBException;
    int checkingUserBlock(Connection con, String login) throws DBException;
    boolean isAdmin (Connection con, String login) throws DBException;
}
