package publish.db.dao.mysql;

import org.junit.jupiter.api.*;
import publish.db.dao.DBException;
import publish.db.entity.Product;
import publish.service.ProductServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MysqlProductDaoTest {
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

    private static final String CREATE_TABLE_CATEGORY =
            "CREATE TABLE category ( \n" +
                    "   id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "   name VARCHAR(32) NOT NULL UNIQUE,\n" +
                    "   parent_id INT,\n" +
                    "   description VARCHAR(1024),\n" +
                    "   CONSTRAINT fk_category_parent_id FOREIGN KEY (parent_id)\n" +
                    "       REFERENCES category (id)\n" +
                    "       ON UPDATE CASCADE ON DELETE SET NULL\n" +
                    ")";

    private static final String INSERT_CATEGORIES =
            "INSERT INTO category(id, name, description) " +
                    "VALUES (DEFAULT, 'Family', 'If you find some information about family and chill time, come to us'),\n" +
                    "       (DEFAULT, 'Sport', 'This category is for everyone, who like sport and is interested in it'),\n" +
                    "       (DEFAULT, 'Travelling', 'You can find a place to come and take a lot of pleasure')";

    private static final String INSERT_ACCOUNT =
            "INSERT INTO account (login, password, email, first_name, last_name, role_id) " +
                    "VALUES ('example','example1', 'example@exaample.com' , 'Example', 'Example', 2);";

    private static final String DROP_TABLE_PRODUCT = "DROP TABLE product CASCADE";

    private static final String DROP_TABLE_ORDER = "DROP TABLE `order` CASCADE";

    private static final String DROP_TABLE_ACCOUNT = "DROP TABLE account CASCADE";

    private static final String DROP_TABLE_CATEGORY = "DROP TABLE category CASCADE";

    private static MysqlProductDao mysqlProductDao;
    private static Connection connection;

    @BeforeAll
    static void globalSetUp() throws SQLException {
        mysqlProductDao = new MysqlProductDao();
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
        connection.createStatement().executeUpdate(CREATE_TABLE_CATEGORY);
        connection.createStatement().executeUpdate(INSERT_CATEGORIES);
        connection.createStatement().executeUpdate(CREATE_TABLE_ORDER);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.createStatement().executeUpdate(DROP_TABLE_ORDER);
        connection.createStatement().executeUpdate(DROP_TABLE_CATEGORY);
        connection.createStatement().executeUpdate(DROP_TABLE_PRODUCT);
        connection.createStatement().executeUpdate(DROP_TABLE_ACCOUNT);
    }

    @Test
    void testConnection() {
        assertNotNull(connection);
    }

    @Test
    void getProductByName() throws SQLException {
        String expectedProductName = "example";

        String insertStatement =
                "INSERT INTO product (id, name, description, price, category_id, logo) " +
                        "VALUES (DEFAULT ,'" + expectedProductName + "', '' ,2.00, 1, '\"example.jpg\"');";
        connection.createStatement().executeUpdate(insertStatement);

        Product product = mysqlProductDao.getProductByName(connection, expectedProductName);

        assertNotNull(product);
        assertEquals(expectedProductName, product.getName());
    }

    @Test
    void insertProduct() throws DBException {
        String expectedProductName = "example";
        double expectedProductPrice = 799.00;

        mysqlProductDao.insertProduct(connection, ProductServiceImpl.getProduct(expectedProductName, expectedProductPrice, 1));

        Product actual = mysqlProductDao.getProductByName(connection, expectedProductName);
        assertNotNull(actual);
        assertEquals(expectedProductName, actual.getName());
        assertEquals(expectedProductPrice, actual.getPrice());
    }

    @Test
    void findAllProducts() throws SQLException {
        int countProduct = 5;
        insertProducts(countProduct);

        List<Product> action = mysqlProductDao.findAllProducts(connection);
        assertEquals(countProduct, action.size());
    }

    @Test
    void deleteProduct() throws SQLException {
        String productName = "example1";
        int countProduct = 5;
        insertProducts(countProduct);

        mysqlProductDao.deleteProduct(connection, productName);
        List<Product> actual = mysqlProductDao.findAllProducts(connection);
        assertNull(mysqlProductDao.getProductByName(connection, productName));
        assertNotEquals(countProduct, actual.size());
        assertEquals(countProduct - 1, actual.size());
    }



    @Test
    void findAllNotSubscribeProduct() throws SQLException {
        int countProduct = 5;
        insertProducts(countProduct);

        String insertStatement =
                "INSERT INTO `order` (id, total, account_id, product_id, description) " +
                        "VALUES (DEFAULT, 1.00, 1, 1, 'some description');";
        connection.createStatement().executeUpdate(insertStatement);
        List<Product> actual = mysqlProductDao.findAllNotSubscribeProduct(connection, "example");
        assertEquals(countProduct - 1, actual.size());
    }

    @Test
    void findAllSubscribeProduct() throws SQLException {
        int countProduct = 5;
        insertProducts(countProduct);

        String insertStatement =
                "INSERT INTO `order` (id, total, account_id, product_id, description) " +
                        "VALUES (DEFAULT, 1.00, 1, 1, 'some description');";
        connection.createStatement().executeUpdate(insertStatement);
        List<Product> actual = mysqlProductDao.findAllSubscribeProduct(connection, "example");
        assertEquals(1, actual.size());
    }

    @Test
    void sortFromLowToHigh() throws SQLException {
        int countProduct = 5;
        insertProducts(countProduct);

        List<Product> actual = mysqlProductDao.sortFromLowToHigh(connection, "example");
        assertTrue(actual.get(1).getPrice() < actual.get(actual.size()-1).getPrice());
    }

    @Test
    void sortFromHighToLow() throws SQLException {
        int countProduct = 5;
        insertProducts(countProduct);

        List<Product> actual = mysqlProductDao.sortFromHighToLow(connection, "example");
        assertTrue(actual.get(1).getPrice() > actual.get(actual.size()-1).getPrice());
    }

    @Test
    void sortFromAToZ() throws SQLException {
        int countProduct = 5;
        insertProducts(countProduct);

        List<Product> actual = mysqlProductDao.sortFromAToZ(connection, "example");
        assertTrue(actual.get(1).getName().compareTo(actual.get(actual.size() - 1).getName()) <= 0);
    }

    @Test
    void sortFromZToA() throws SQLException {
        int countProduct = 5;
        insertProducts(countProduct);

        List<Product> actual = mysqlProductDao.sortFromZToA(connection, "example");
        assertTrue(actual.get(1).getName().compareTo(actual.get(actual.size() - 1).getName()) >= 0);
    }

    @Test
    void sortFromNewToOld() throws SQLException {
        int countProduct = 5;
        insertProducts(countProduct);

        List<Product> actual = mysqlProductDao.sortFromNewToOld(connection, "example");
        assertTrue(actual.get(1).getCreate_date().compareTo(actual.get(actual.size() - 1).getCreate_date()) >= 0);
    }

    @Test
    void sortFromOldToNew() throws SQLException {
        int countProduct = 5;
        insertProducts(countProduct);

        List<Product> actual = mysqlProductDao.sortFromOldToNew(connection, "example");
        assertTrue(actual.get(1).getCreate_date().compareTo(actual.get(actual.size() - 1).getCreate_date()) <= 0);
    }

    @Test
    void findProductByName() throws SQLException {
        String expectedName = "example4";
        int countProduct = 5;
        insertProducts(countProduct);

        Product actual = mysqlProductDao.findProductByName(connection, "example", "4");
        assertNotNull(actual);
        assertEquals(expectedName, actual.getName());
    }

    @Test
    void findProductByPrice() throws SQLException {
        double startPrice = 2;
        double endPrice = 4;
        int countProduct = 5;
        insertProducts(countProduct);

        List<Product> actual = mysqlProductDao.findProductByPrice(connection, "example", startPrice, endPrice);
        assertNotNull(actual);
        assertEquals(endPrice - startPrice + 1, actual.size());
    }

    @Test
    void findProductsByCategory() throws SQLException{
        int countEveryCategory = 2;
        int k = 0;
        for (int j = 1; j <= countEveryCategory; j++) {
            for (int i = 1; i <= 3; i++) {
                k++;
                Product product = ProductServiceImpl.getProduct("example" + k, k, i);
                mysqlProductDao.insertProduct(connection, product);
            }
        }

        List<Product> actual = mysqlProductDao.findProductsByCategory(connection, "example", "Sport");
        assertEquals(countEveryCategory, actual.size());
    }

    @Test
    void updateProductPrice() throws SQLException {
        String productName = "example2";
        double expectedPrice = 4;
        int countProduct = 5;
        insertProducts(countProduct);

        mysqlProductDao.updateProductPrice(connection, expectedPrice, productName);
        Product actual = mysqlProductDao.getProductByName(connection, productName);
        assertEquals(expectedPrice, actual.getPrice());
    }

    @Test
    void updateProductLogo() throws SQLException {
        String productName = "example2";
        String expectedLogo = "example2.jpg";
        int countProduct = 5;
        insertProducts(countProduct);
        assertNull(mysqlProductDao.getProductByName(connection, productName).getLogo());

        mysqlProductDao.updateProductLogo(connection, expectedLogo, productName);
        Product actual = mysqlProductDao.getProductByName(connection, productName);
        assertEquals(expectedLogo, actual.getLogo());
    }

    @Test
    void updateProductDescription() throws SQLException {
        String productName = "example2";
        String expectedDescription = "some description about " + productName;
        int countProduct = 5;
        insertProducts(countProduct);
        assertNull(mysqlProductDao.getProductByName(connection, productName).getDescription());

        mysqlProductDao.updateProductDescription(connection, expectedDescription, productName);
        Product actual = mysqlProductDao.getProductByName(connection, productName);
        assertEquals(expectedDescription, actual.getDescription());
    }

    private void insertProducts (int countProduct) throws SQLException{
        for (int i = 1; i <= countProduct; i++){
            Product product = ProductServiceImpl.getProduct("example" + i,  i,
                    new Random().nextInt(3) + 1);
            mysqlProductDao.insertProduct(connection, product);
        }
    }
}