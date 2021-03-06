package publish.service;

import publish.db.dao.DBException;
import publish.db.entity.Account;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Service for manage account.
 * @author Burykin
 */
public interface AccountService {
    Account findByLogin(String login) throws DBException;
    boolean insertAccount (Account account) throws DBException;
    List<Account> findAllAccounts() throws DBException;
    boolean findByLoginAndPassword(String login, String password) throws DBException;
    void updateScore(double score, String login) throws DBException;
    boolean isAdmin (String login) throws DBException;
    void changingUserBlock(int isBlocked, String login) throws DBException;
    int checkingUserBlock(String login) throws DBException;
}
