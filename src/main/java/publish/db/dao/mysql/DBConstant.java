package publish.db.dao.mysql;

public abstract class DBConstant {
    private DBConstant(){}

    //Fields of ACCOUNT
    public static final String INSERT_ACCOUNT = "INSERT INTO account (login, password, role_id) VALUES ((?), (?), (?));";
    public static final String GET_ACCOUNT = "SELECT * FROM account WHERE login = (?);";
    public static final String SELECT_ADMINS = "SELECT account.* FROM account join role on account.role_id=role.id WHERE role.name = 'Administrator';";
    public static final String SELECT_USERS = "SELECT account.* FROM account join role on account.role_id=role.id WHERE role.name = 'User';";
    public static final String DELETE_ACCOUNT = "DELETE FROM account WHERE login = (?);";

    //Fields of ROLE
    public static final String GET_ROLE_BY_ID = "SELECT * FROM role WHERE id = (?);";
    public static final String GET_ROLE_BY_NAME = "SELECT * FROM role WHERE name = (?);";
    public static final String INSERT_ROLE = "INSERT INTO role (name, description) VALUES ((?), (?));";
    public static final String DELETE_ROLE = "DELETE FROM role WHERE name = (?);";

    //Queries of ACCOUNT
    public static final String F_ACCOUNT_ID = "id";
    public static final String F_ACCOUNT_LOGIN = "login";
    public static final String F_ACCOUNT_PASSWORD = "password";
    public static final String F_ACCOUNT_ROLE_ID = "role_id";
    public static final String F_ACCOUNT_CREATE_DATE = "create_date";
    public static final String F_ACCOUNT_LAST_UPDATE = "last_update";

    //Queries of ROLE
    public static final String F_ROLE_ID = "id";
    public static final String F_ROLE_NAME = "name";
    public static final String F_ROLE_DESCRIPTION = "description";
}
