package publish.db.dao.mysql;

import org.junit.jupiter.api.*;
import publish.db.dao.DBException;
import publish.db.entity.Order;
import publish.service.OrderServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MysqlOrderDaoTest {
    private static final String CONNECTION_URL = "jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1";

    private static final String CREATE_TABLE_ACCOUNT =
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
                    "   isBlocked boolean DEFAULT false\n" +
            ");";

    private static final String CREATE_TABLE_ORDER =
            "CREATE TABLE `order` \n" +
                    "(\n" +
                    "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "    total DOUBLE CONSTRAINT ch_total CHECK (total >= 0),\n" +
                    "    account_id INT,\n" +
                    "    product_id INT,\n" +
                    "    description VARCHAR(1024),\n" +
                    "    create_date DATETIME DEFAULT CURRENT_TIMESTAMP,\n" +
                    "    last_update DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
                    "    CONSTRAINT fk_order_account_id FOREIGN KEY (account_id)\n" +
                    "        REFERENCES account (id) \n" +
                    "        ON UPDATE CASCADE ON DELETE SET NULL,\n" +
                    "    CONSTRAINT fk_oder_has_product_product_id FOREIGN KEY (product_id)\n" +
                    "        REFERENCES product (id)\n" +
                    "        ON UPDATE CASCADE ON DELETE RESTRICT\n" +
            ");";

    private static final String CREATE_TABLE_PRODUCT =
            "CREATE TABLE product \n" +
                    "(\n" +
                    "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "    name VARCHAR(64) NOT NULL UNIQUE,\n" +
                    "    description VARCHAR(2048),\n" +
                    "    price DOUBLE NOT NULL CONSTRAINT ck_product_price CHECK (price >= 0),\n" +
                    "    category_id INT,\n" +
                    "    logo VARCHAR(64),\n" +
                    "    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                    "    last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP\n" +
            ");";

    private static final String INSERT_ACCOUNT =
            "INSERT INTO account (login, password, email, first_name, last_name, role_id) " +
                    "VALUES ('example','example1', 'example@exaample.com' , 'Example', 'Example', 2);";
    private static final String INSERT_PRODUCT =
            "INSERT INTO product(id, name, description, price, category_id, logo, create_date, last_update) " +
                    "VALUES (DEFAULT, 'example', '', 2.00, 1, '\"example.jpg\"', DEFAULT, DEFAULT);";

    private static final String DROP_TABLE_PRODUCT = "DROP TABLE product CASCADE";

    private static final String DROP_TABLE_ORDER = "DROP TABLE `order` CASCADE";

    private static final String DROP_TABLE_ACCOUNT = "DROP TABLE account CASCADE";

    private static MysqlOrderDao mysqlOrderDao;
    private static Connection connection;

    @BeforeAll
    static void globalSetUp() throws SQLException {
        mysqlOrderDao = new MysqlOrderDao();
        connection = DriverManager.getConnection(CONNECTION_URL);
    }

    @AfterAll
    static void globalTearDown() throws SQLException{
        connection.close();
        connection = DriverManager.getConnection(CONNECTION_URL);
    }

    @BeforeEach
    void setUp() throws SQLException {
        connection.createStatement().executeUpdate(CREATE_TABLE_ACCOUNT);
        connection.createStatement().executeUpdate(INSERT_ACCOUNT);
        connection.createStatement().executeUpdate(CREATE_TABLE_PRODUCT);
        connection.createStatement().executeUpdate(INSERT_PRODUCT);
        connection.createStatement().executeUpdate(CREATE_TABLE_ORDER);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.createStatement().executeUpdate(DROP_TABLE_ORDER);
        connection.createStatement().executeUpdate(DROP_TABLE_PRODUCT);
        connection.createStatement().executeUpdate(DROP_TABLE_ACCOUNT);
    }

    @Test
    void testConnection() {
        assertNotNull(connection);
    }

    @Test
    void findById() throws SQLException {
        int expectedOrderId = 1;

        String insertStatement =
                "INSERT INTO `order` (id, total, account_id, product_id, description) " +
                        "VALUES (" + expectedOrderId + ", 2.00, 1, 1, 'some description');";
        connection.createStatement().executeUpdate(insertStatement);

        Order order = mysqlOrderDao.findById(connection, expectedOrderId);

        assertNotNull(order);
        assertEquals(expectedOrderId, order.getAccount_id());
    }

    @Test
    void insertOrder() throws DBException {
        int orderId = 1;
        Order order = OrderServiceImpl.getOrder(2.00, 1, 1);
        order.setId(orderId);

        mysqlOrderDao.insertOrder(connection, order);
        Order actual = mysqlOrderDao.findById(connection, orderId);

        assertEquals(orderId, actual.getId());
    }

    @Test
    void findByAccountId() throws DBException {
        int accountId = 1;
        Order order = OrderServiceImpl.getOrder(2.00, accountId, 1);

        mysqlOrderDao.insertOrder(connection, order);
        List<Order> actual = mysqlOrderDao.findByAccountId(connection, accountId);

        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(accountId, actual.get(0).getAccount_id());
    }
}