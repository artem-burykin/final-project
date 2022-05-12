package publish.db.dao.mysql;

import org.junit.jupiter.api.*;
import publish.db.entity.Account;
import publish.service.AccountServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class MysqlAccountDaoTest {
    private static final String CONNECTION_URL = "jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1";

    private static String CREATE_TABLE_ACCOUNT =
            "CREATE TABLE account \n " +
                    "(\n" +
                    "   id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "   login VARCHAR(32) NOT NULL UNIQUE,\n" +
                    "   password VARCHAR(32) NOT NULL,\n" +
                    "   email VARCHAR(32) NOT NULL,\n" +
                    "   first_name VARCHAR(32) NOT NULL,\n" +
                    "   last_name VARCHAR(32) NOT NULL,\n" +
                    "   score DOUBLE NOT NULL DEFAULT 0,\n" +
                    "   role_id INT,\n" +
                    "   create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                    "   last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
                    "   isBlocked boolean DEFAULT false,\n" +
                    "   CONSTRAINT fk_account_role_id FOREIGN KEY (role_id)\n" +
                    "       REFERENCES role (id)\n" +
                    "       ON UPDATE CASCADE ON DELETE RESTRICT\n" +
                ")";

    private static String CREATE_TABLE_ROLE =
            "CREATE TABLE role\n" +
                    "(\n" +
                    "   id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "   name VARCHAR(32) NOT NULL UNIQUE,\n" +
                    "   description VARCHAR(1024)\n" +
            ")";

    private static String DROP_TABLE_ROLE = "DROP TABLE role CASCADE";

    private static String DROP_TABLE_ACCOUNT = "DROP TABLE account CASCADE";

    private static String INSERT_ROLES =
            "INSERT INTO role (id, name, description)\n"+
                    "   VALUES (DEFAULT, 'Administrator', 'Has administrator rules'),\n" +
                    "          (DEFAULT, 'User', 'Can buy publication and has substricption');\n";

    private static MysqlAccountDao mysqlAccountDao;
    private static Connection connection;

    @BeforeAll
    static void globalSetUp() throws SQLException {
            mysqlAccountDao = new MysqlAccountDao();
            connection = DriverManager.getConnection(CONNECTION_URL);
    }

    @AfterAll
    static void globalTearDown() throws SQLException{
        connection.close();
        connection = DriverManager.getConnection(CONNECTION_URL);
    }

    @BeforeEach
    void setUp() throws SQLException {
        connection.createStatement().executeUpdate(CREATE_TABLE_ROLE);
        connection.createStatement().executeUpdate(INSERT_ROLES);
        connection.createStatement().executeUpdate(CREATE_TABLE_ACCOUNT);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.createStatement().executeUpdate(DROP_TABLE_ACCOUNT);
        connection.createStatement().executeUpdate(DROP_TABLE_ROLE);
    }

    @Test
    void testConnection() {
        assertNotNull(connection);
    }

    @Test
    void findByLogin() throws SQLException {
        String expectedAccountPassword = "example1";
        String expectedAccountLogin = "example";

        String insertStatement =
                "INSERT INTO account (login, password, email, first_name, last_name, role_id) " +
                        "VALUES ('" + expectedAccountLogin + "','" + expectedAccountPassword + "'," +
                        "'example@exaample.com' , 'Example', 'Example', 2);";
        connection.createStatement().executeUpdate(insertStatement);

        Account account = mysqlAccountDao.findByLogin(connection, expectedAccountLogin);

        assertNotNull(account);
        assertEquals(expectedAccountLogin, account.getLogin());
        assertEquals(expectedAccountPassword, account.getPassword());
    }

    @Test
    void insertAccount() throws SQLException {
        Account expected = AccountServiceImpl.getAccount("example", "example1",
                "example@example.com","Example", "Example", 0, 2);
        mysqlAccountDao.insertAccount(connection, expected);
        Account actual = mysqlAccountDao.findByLogin(connection, "example");
        assertEquals(expected.getLogin(), actual.getLogin());

        Account account2 = AccountServiceImpl.getAccount("example2", "example2", "example2@example.com",
                "Example2", "Example2", 0, 2);
        mysqlAccountDao.insertAccount(connection, account2);
        Account action2 = mysqlAccountDao.findByLogin(connection,"example2");
        assertNotEquals(expected, action2);
    }

    @Test
    void findAllAccounts() throws SQLException {
        int countAccount = 5;
        insertAccount(countAccount);

        List<Account> action = mysqlAccountDao.findAllAccounts(connection);
        assertEquals(countAccount, action.size());
    }

    @Test
    void findByLoginAndPassword() throws SQLException {
        String accountPassword = "example1";
        String accountLogin = "example";

        String insertStatement =
                "INSERT INTO account (login, password, email, first_name, last_name, role_id) " +
                        "VALUES ('" + accountLogin + "','" + accountPassword + "'," +
                        "'example@exaample.com' , 'Example', 'Example', 2);";
        connection.createStatement().executeUpdate(insertStatement);

        boolean isInsert = mysqlAccountDao.findByLoginAndPassword(connection, accountLogin, accountPassword);

        assertTrue(isInsert);
    }

    @Test
    void updateScore() throws SQLException {
        double expectedScore = 5.0;
        String login = "example1";

        insertAccount(1);
        mysqlAccountDao.updateScore(connection, expectedScore, login);
        Account account = mysqlAccountDao.findByLogin(connection, login);

        assertEquals(expectedScore, account.getScore());
    }

    @Test
    void changingUserBlock() throws SQLException{
        int expectedBlock = 1;
        String login = "example1";

        insertAccount(1);
        mysqlAccountDao.changingUserBlock(connection, expectedBlock, login);
        Account account = mysqlAccountDao.findByLogin(connection, login);

        assertEquals(expectedBlock, account.getIsBlocked());
    }

    @Test
    void checkingUserBlock() throws SQLException {
        int expectedBlock = 0;
        String login = "example1";

        insertAccount(1);
        int isUserBlock = mysqlAccountDao.checkingUserBlock(connection, login);

        assertEquals(expectedBlock, isUserBlock);
    }

    @Test
    void isAdmin() throws SQLException {
        String accountPassword = "admin1";
        String accountLogin = "admin";

        String insertStatement =
                "INSERT INTO account (login, password, email, first_name, last_name, role_id) " +
                        "VALUES ('" + accountLogin + "','" + accountPassword + "'," +
                        "'admin@admin.com' , 'Admin', 'Admin', 1);";
        connection.createStatement().executeUpdate(insertStatement);

        boolean isAdmin = mysqlAccountDao.isAdmin(connection, accountLogin);

        assertTrue(isAdmin);
    }

    private void insertAccount (int countAccount) throws SQLException{
        for (int i = 1; i <= countAccount; i++){
            Account account = AccountServiceImpl.getAccount("example" + i, "example" + i,
                    "example" + i + "@example.com", "Example" + i, "Example" + i, 0, 2);
            mysqlAccountDao.insertAccount(connection, account);
        }
    }
}