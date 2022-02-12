package publish.service;

import publish.db.dao.AccountDao;
import publish.db.entity.Account;

public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public static Account getAccount(String login, String password, int role_id){
            return Account.createAccount(login, password, role_id);
    }
}
