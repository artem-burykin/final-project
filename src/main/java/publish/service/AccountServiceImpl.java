package publish.service;

import publish.db.dao.AccountDao;
import publish.db.dao.DBException;
import publish.db.dao.DaoFactory;
import publish.db.entity.Account;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao = DaoFactory.getInstance().getAccountDao();

    public static Account getAccount(String login, String password, String email, String first_name, String last_name, double score, int role_id){
            return Account.createAccount(login, password, email, first_name, last_name, score, role_id);
    }

    @Override
    public Account findByLogin(String login) throws DBException {
        return accountDao.findByLogin(login);
    }

    @Override
    public boolean insertAccount(Account account) throws DBException {
        return accountDao.insertAccount(account);
    }

    @Override
    public List<Account> findAllAccounts() throws DBException {
        return accountDao.findAllAccounts();
    }

    @Override
    public boolean findByLoginAndPassword(String login, String password) throws DBException {
        return accountDao.findByLoginAndPassword(login, password);
    }

    @Override
    public void updateScore(double score, String login) throws DBException {
        accountDao.updateScore(score, login);
    }

    @Override
    public boolean isAdmin(String login) throws DBException {
        return accountDao.isAdmin(login);
    }

    @Override
    public void changingUserBlock(int isBlocked, String login) throws DBException {
        accountDao.changingUserBlock(isBlocked, login);
    }

    @Override
    public int checkingUserBlock(String login) throws DBException {
        return accountDao.checkingUserBlock(login);
    }
}