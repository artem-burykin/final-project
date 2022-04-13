package publish.db.dao;

import publish.db.entity.Account;

import java.util.List;

/**
 * Interface with method, which realization in MysqlAccountDao.
 * @author Byrukin
 */
public interface AccountDao {
    Account findByLogin(String login) throws DBException;
    boolean insertAccount (Account account) throws DBException;
    boolean findByLoginAndPassword(String login, String password) throws DBException;
    void changingUserBlock(int isBlocked, String login) throws DBException;
    void updateScore(double score, String login) throws DBException;
    int checkingUserBlock(String login) throws DBException;
}
