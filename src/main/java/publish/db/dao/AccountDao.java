package publish.db.dao;

import publish.db.entity.Account;

import java.util.List;

public interface AccountDao {
    Account getAccountByLogin(String login) throws DBException;
    boolean insertAccount (Account account) throws DBException;
    List<Account> findAllUsers() throws DBException;
    List<Account> findAllAdmins() throws DBException;
    boolean deleteUsers(Account... users) throws DBException;
    boolean deleteAdmins(Account... admins) throws DBException;
}
