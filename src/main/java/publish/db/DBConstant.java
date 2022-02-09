package publish.db;

public abstract class DBConstant {
    private DBConstant(){}

    public static final String INSERT_USER = "INSERT INTO users (login, password, role_id) VALUES (?);";
}
