package publish.service;

import publish.db.dao.DBException;
import publish.db.entity.Account;

public interface AccountService {
    Account findByLogin(String login) throws DBException;
    boolean insertAccount (Account account) throws DBException;
    boolean findByLoginAndPassword(String login, String password) throws DBException;
    void changingUserBlock(int isBlocked, String login) throws DBException;
    int checkingUserBlock(String login) throws DBException;
}
