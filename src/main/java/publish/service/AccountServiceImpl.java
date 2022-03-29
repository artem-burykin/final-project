package publish.service;

import publish.db.dao.AccountDao;
import publish.db.entity.Account;

public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public static Account getAccount(String login, String password, String email, String first_name, String last_name, int role_id){
            return Account.createAccount(login, password, email, first_name, last_name, role_id);
    }
}
