package publish.db.dao.mysql;

import org.junit.jupiter.api.*;
import publish.db.dao.DBException;
import publish.db.entity.Category;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MysqlCategoryDaoTest {
    private static final String CONNECTION_URL = "jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1";

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
    private static final String DROP_TABLE_CATEGORY = "DROP TABLE category CASCADE";

    private static MysqlCategoryDao mysqlCategoryDao;
    private static Connection connection;

    @BeforeAll
    static void globalSetUp() throws SQLException {
        mysqlCategoryDao = new MysqlCategoryDao();
        connection = DriverManager.getConnection(CONNECTION_URL);
    }

    @AfterAll
    static void globalTearDown() throws SQLException{
        connection.close();
        connection = DriverManager.getConnection(CONNECTION_URL);
    }

    @BeforeEach
    void setUp() throws SQLException {
        connection.createStatement().executeUpdate(CREATE_TABLE_CATEGORY);
        connection.createStatement().executeUpdate(INSERT_CATEGORIES);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.createStatement().executeUpdate(DROP_TABLE_CATEGORY);
    }
    @Test
    void findAllCategories() throws DBException {
        int countAccount = 3;

        List<Category> action = mysqlCategoryDao.findAllCategories(connection);
        assertEquals(countAccount, action.size());
    }
}