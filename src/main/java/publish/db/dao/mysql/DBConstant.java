package publish.db.dao.mysql;

/**
 * Class, where stored all fields and queries.
 * @author Burykin
 */
public abstract class DBConstant {
    private DBConstant(){}

    //Queries of ACCOUNT
    public static final String INSERT_ACCOUNT = "INSERT INTO account (login, password, email, first_name, last_name, role_id) VALUES ((?), (?), (?), (?), (?), (?));";
    public static final String FIND_ACCOUNT_BY_LOGIN = "SELECT * FROM account WHERE login = (?);";
    public static final String FIND_ACCOUNT_BY_LOGIN_AND_PASSWORD = "SELECT * FROM account WHERE login = (?) AND password = (?);";
    public static final String BLOCKING_ACCOUNT = "UPDATE account SET isBlocked = (?) WHERE login = (?);";
    public static final String IS_ACCOUNT_BLOCKED = "SELECT isBlocked FROM account WHERE login = (?);";


    //Queries of ROLE
    public static final String GET_ROLE_BY_ID = "SELECT * FROM role WHERE id = (?);";
    public static final String GET_ROLE_BY_NAME = "SELECT * FROM role WHERE name = (?);";
    public static final String INSERT_ROLE = "INSERT INTO role (name, description) VALUES ((?), (?));";
    public static final String DELETE_ROLE = "DELETE FROM role WHERE name = (?);";

    //Queries of PRODUCT
    public static final String FIND_ALL_PRODUCTS = "SELECT * FROM product;";
    public static final String GET_PRODUCT_FROM_LOW_TO_HIGH = "SELECT * FROM product ORDER BY price;";
    public static final String GET_PRODUCT_FROM_HIGH_TO_LOW = "SELECT * FROM product ORDER BY price DESC;";
    public static final String GET_PRODUCT_FROM_Z_TO_A = "SELECT * FROM product ORDER BY name DESC;";
    public static final String GET_PRODUCT_FROM_A_TO_Z = "SELECT * FROM product ORDER BY name;";
    public static final String FIND_PRODUCT_BY_NAME = "SELECT * FROM product WHERE name like '%(?)%';";
    public static final String GET_PRODUCT_BY_PRICE = "SELECT * FROM product WHERE price BETWEEN (?) and (?);";
    public static final String GET_PRODUCT_FROM_OLD_TO_NEW = "SELECT * FROM product ORDER BY create_date;";
    public static final String GET_PRODUCT_FROM_NEW_TO_OLD = "SELECT * FROM product ORDER BY create_date DESC;";
    public static final String INSERT_PRODUCT = "INSERT INTO product (name, description, price, category_id, logo) VALUES ((?), (?), (?), (?), (?));";
    public static final String DELETE_PRODUCT = "DELETE FROM product WHERE name = (?);";
    public static final String FIND_PRODUCT_BY_CATEGORY = "SELECT * FROM product join category on product.category_id = category.id WHERE category.name = (?);";
    public static final String UPDATE_PRODUCT_PRICE = "UPDATE product SET price = (?) where name = (?);";
    public static final String UPDATE_PRODUCT_LOGO = "UPDATE product SET logo = (?) WHERE name = (?);";
    public static final String UPDATE_PRODUCT_DESCRIPTION = "UPDATE product SET description = (?) WHERE name = (?);";


    //Queries of CATEGORY
    public static final String GET_CATEGORY_BY_NAME = "SELECT * FROM category WHERE name = (?);";

    //Fields of ACCOUNT
    public static final String F_ACCOUNT_ID = "id";
    public static final String F_ACCOUNT_LOGIN = "login";
    public static final String F_ACCOUNT_PASSWORD = "password";
    public static final String F_ACCOUNT_EMAIL = "email";
    public static final String F_ACCOUNT_FIRST_NAME = "first_name";
    public static final String F_ACCOUNT_LAST_NAME = "last_name";
    public static final String F_ACCOUNT_ROLE_ID = "role_id";
    public static final String F_ACCOUNT_CREATE_DATE = "create_date";
    public static final String F_ACCOUNT_LAST_UPDATE = "last_update";

    //Fields of ROLE
    public static final String F_ROLE_ID = "id";
    public static final String F_ROLE_NAME = "name";
    public static final String F_ROLE_DESCRIPTION = "description";

    //Fields of PRODUCT
    public static final String F_PRODUCT_ID = "id";
    public static final String F_PRODUCT_NAME = "name";
    public static final String F_PRODUCT_DESCRIPTION = "description";
    public static final String F_PRODUCT_PRICE = "price";
    public static final String F_PRODUCT_CATEGORY_ID = "category_id";
    public static final String F_PRODUCT_LOGO = "logo";
    public static final String F_PRODUCT_CREATE_DATE = "create_date";
    public static final String F_PRODUCT_LAST_UPDATE = "last_update";

    //Fields of CATEGORY
    public static final String F_CATEGORY_ID = "id";
    public static final String F_CATEGORY_NAME = "name";
    public static final String F_CATEGORY_PARENT_ID = "parent_id";
    public static final String F_CATEGORY_DESCRIPTION = "description";
}
