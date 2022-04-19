package publish.db.dao.mysql;

/**
 * Class, where stored all fields and queries.
 * @author Burykin
 */
public abstract class DBConstant {
    private DBConstant(){}

    //Queries of ACCOUNT
    public static final String INSERT_ACCOUNT = "INSERT INTO account (login, password, email, first_name, last_name, role_id) VALUES ((?), (?), (?), (?), (?), (?));";
    public static final String FIND_ALL_ACCOUNT = "SELECT * FROM account;";
    public static final String FIND_ACCOUNT_BY_LOGIN = "SELECT * FROM account WHERE login = (?);";
    public static final String FIND_ACCOUNT_BY_LOGIN_AND_PASSWORD = "SELECT * FROM account WHERE login = (?) AND password = (?);";
    public static final String UPDATE_ACCOUNT_SCORE = "UPDATE account SET score = (?) WHERE login = (?);";
    public static final String BLOCKING_ACCOUNT = "UPDATE account SET isBlocked = (?) WHERE login = (?);";
    public static final String IS_ACCOUNT_BLOCKED = "SELECT isBlocked FROM account WHERE login = (?);";
    public static final String IS_ADMIN = "SELECT EXISTS (SELECT account.id FROM account join role on account.role_id = role.id WHERE role.name = 'Administrator' AND account.login = (?));";


//    //Queries of ROLE
//    public static final String GET_ROLE_BY_ID = "SELECT * FROM role WHERE id = (?);";
//    public static final String GET_ROLE_BY_NAME = "SELECT * FROM role WHERE name = (?);";
//    public static final String INSERT_ROLE = "INSERT INTO role (name, description) VALUES ((?), (?));";
//    public static final String DELETE_ROLE = "DELETE FROM role WHERE name = (?);";

    //Queries of PRODUCT
    public static final String FIND_ALL_PRODUCTS = "SELECT * FROM product;";
    public static final String GET_PRODUCT_BY_NAME = "SELECT * FROM product WHERE name like (?);";
    public static final String FIND_ALL_NOT_SUBSCRIBE_PRODUCTS = "SELECT product.* FROM product WHERE NOT EXISTS (SELECT * FROM publisherhouse.`order` WHERE publisherhouse.`order`.product_id = product.id AND publisherhouse.`order`.account_id = (SELECT id FROM account WHERE login = (?) ));";
    public static final String FIND_ALL_SUBSCRIBE_PRODUCTS = "SELECT product.* FROM product join publisherhouse.order on product.id = publisherhouse.order.product_id join account on account.id = publisherhouse.order.account_id WHERE publisherhouse.order.account_id = (SELECT account.id FROM account WHERE login = (?));";
    public static final String GET_PRODUCT_FROM_LOW_TO_HIGH = "SELECT product.* FROM product WHERE NOT EXISTS (SELECT * FROM publisherhouse.`order` WHERE publisherhouse.`order`.product_id = product.id AND publisherhouse.`order`.account_id = (SELECT id FROM account WHERE login = (?))) ORDER BY price;";
    public static final String GET_PRODUCT_FROM_HIGH_TO_LOW = "SELECT product.* FROM product WHERE NOT EXISTS (SELECT * FROM publisherhouse.`order` WHERE publisherhouse.`order`.product_id = product.id AND publisherhouse.`order`.account_id = (SELECT id FROM account WHERE login = (?))) ORDER BY price DESC;";
    public static final String GET_PRODUCT_FROM_Z_TO_A = "SELECT product.* FROM product WHERE NOT EXISTS (SELECT * FROM publisherhouse.`order` WHERE publisherhouse.`order`.product_id = product.id AND publisherhouse.`order`.account_id = (SELECT id FROM account WHERE login = (?))) ORDER BY name DESC;";
    public static final String GET_PRODUCT_FROM_A_TO_Z = "SELECT product.* FROM product WHERE NOT EXISTS (SELECT * FROM publisherhouse.`order` WHERE publisherhouse.`order`.product_id = product.id AND publisherhouse.`order`.account_id = (SELECT id FROM account WHERE login = (?))) ORDER BY name;";
    public static final String FIND_PRODUCT_BY_NAME = "SELECT product.* FROM product WHERE NOT EXISTS (SELECT * FROM publisherhouse.`order` WHERE publisherhouse.`order`.product_id = product.id AND publisherhouse.`order`.account_id = (SELECT id FROM account WHERE login = (?))) AND name like ?;";
    public static final String GET_PRODUCT_BY_PRICE = "SELECT product.* FROM product WHERE NOT EXISTS (SELECT * FROM publisherhouse.`order` WHERE publisherhouse.`order`.product_id = product.id AND publisherhouse.`order`.account_id = (SELECT id FROM account WHERE login = (?))) AND price BETWEEN (?) and (?);";
    public static final String GET_PRODUCT_FROM_OLD_TO_NEW = "SELECT product.* FROM product WHERE NOT EXISTS (SELECT * FROM publisherhouse.`order` WHERE publisherhouse.`order`.product_id = product.id AND publisherhouse.`order`.account_id = (SELECT id FROM account WHERE login = (?))) ORDER BY create_date;";
    public static final String GET_PRODUCT_FROM_NEW_TO_OLD = "SELECT product.* FROM product WHERE NOT EXISTS (SELECT * FROM publisherhouse.`order` WHERE publisherhouse.`order`.product_id = product.id AND publisherhouse.`order`.account_id = (SELECT id FROM account WHERE login = (?))) ORDER BY create_date DESC;";
    public static final String INSERT_PRODUCT = "INSERT INTO product (name, description, price, category_id, logo) VALUES ((?), (?), (?), (?), (?));";
    public static final String DELETE_PRODUCT = "DELETE FROM product WHERE name = (?);";
    public static final String FIND_PRODUCT_BY_CATEGORY = "SELECT product.* FROM product join category on product.category_id = category.id WHERE NOT EXISTS (SELECT * FROM publisherhouse.`order` WHERE publisherhouse.`order`.product_id = product.id AND publisherhouse.`order`.account_id = (SELECT id FROM account WHERE login = (?))) AND category.name = (?);";
    public static final String UPDATE_PRODUCT_PRICE = "UPDATE product SET price = (?) WHERE name = (?);";
    public static final String UPDATE_PRODUCT_LOGO = "UPDATE product SET logo = (?) WHERE name = (?);";
    public static final String UPDATE_PRODUCT_DESCRIPTION = "UPDATE product SET description = (?) WHERE name = (?);";


//    Queries of CATEGORY
//    public static final String GET_CATEGORY_BY_NAME = "SELECT * FROM category WHERE name = (?);";
    public static final String FIND_ALL_CATEGORIES = "SELECT * FROM category;";

    //Queries of ORDER
    public static final String INSERT_ORDER = "INSERT INTO publisherhouse.order (total, account_id, product_id, description) VALUES ((?), (?), (?), (?));";
    public static final String FIND_ORDER_BY_ID = "SELECT * FROM publisherhouse.order WHERE id = (?);";
    public static final String FIND_ORDERS_BY_ACCOUNT_ID = "SELECT * FROM publisherhouse.order WHERE account_id = (?);";

    //Queries of PUBLICATION
    public static final String INSERT_PUBLICATION = "INSERT INTO publication(product_id, name, content, create_date) VALUES ((?),(?), (?), (?));";
    public static final String FIND_PUBLICATION_BY_PRODUCT_ID = "SELECT * FROM publication WHERE product_id = (?);";
    public static final String UPDATE_PUBLICATION_NAME = "UPDATE publication SET name = (?) WHERE id = (?);";
    public static final String UPDATE_PUBLICATION_CONTENT = "UPDATE publication SET content = (?) WHERE name = (?);";

    //Fields of ACCOUNT
    public static final String F_ACCOUNT_ID = "id";
    public static final String F_ACCOUNT_LOGIN = "login";
    public static final String F_ACCOUNT_PASSWORD = "password";
    public static final String F_ACCOUNT_EMAIL = "email";
    public static final String F_ACCOUNT_FIRST_NAME = "first_name";
    public static final String F_ACCOUNT_LAST_NAME = "last_name";
    public static final String F_ACCOUNT_SCORE = "score";
    public static final String F_ACCOUNT_ROLE_ID = "role_id";
    public static final String F_ACCOUNT_IS_BLOCKED = "isBlocked";
    public static final String F_ACCOUNT_CREATE_DATE = "create_date";
    public static final String F_ACCOUNT_LAST_UPDATE = "last_update";

//    //Fields of ROLE
//    public static final String F_ROLE_ID = "id";
//    public static final String F_ROLE_NAME = "name";
//    public static final String F_ROLE_DESCRIPTION = "description";

    //Fields of PRODUCT
    public static final String F_PRODUCT_ID = "id";
    public static final String F_PRODUCT_NAME = "name";
    public static final String F_PRODUCT_DESCRIPTION = "description";
    public static final String F_PRODUCT_PRICE = "price";
    public static final String F_PRODUCT_CATEGORY_ID = "category_id";
    public static final String F_PRODUCT_LOGO = "logo";
    public static final String F_PRODUCT_CREATE_DATE = "create_date";
    public static final String F_PRODUCT_LAST_UPDATE = "last_update";

//    //Fields of CATEGORY
    public static final String F_CATEGORY_ID = "id";
    public static final String F_CATEGORY_NAME = "name";
    public static final String F_CATEGORY_PARENT_ID = "parent_id";
    public static final String F_CATEGORY_DESCRIPTION = "description";

    //Fields of ORDER
    public static final String F_ORDER_ID = "id";
    public static final String F_ORDER_TOTAL = "total";
    public static final String F_ORDER_ACCOUNT_ID = "account_id";
    public static final String F_ORDER_PRODUCT_ID = "product_id";
    public static final String F_ORDER_DESCRIPTION = "description";
    public static final String F_ORDER_CREATE_DATE = "create_date";
    public static final String F_ORDER_LAST_UPDATE = "last_update";

    //Fields of PUBLICATION
    public static final String F_PUBLICATION_ID = "id";
    public static final String F_PUBLICATION_NAME = "name";
    public static final String F_PUBLICATION_PRODUCT_ID = "product_id";
    public static final String F_PUBLICATION_CONTENT = "content";
    public static final String F_PUBLICATION_CREATE_DATE = "create_date";
}
