package publish.db.dao.mysql;

import publish.db.dao.*;

/**
 * Class for return instance of classes, which manage db
 * @author Burykin
 */
public class MysqlDaoFactory extends DaoFactory {
    private AccountDao instanceAccount;
    private ProductDao instanceProduct;
    private static final Object synh = new Object();

    /**
     * Method, which give us possibility manage table with accounts in db
     * @return instance AccountDao
     */
    public AccountDao getAccountDao() {
        System.out.println("MysqlDaoFactory@getAccountDao() start");
        if (instanceAccount == null) {
            synchronized (synh) {
                instanceAccount = new MysqlAccountDao();
                System.out.println("MysqlDaoFactory@getAccountDao() instance created: " + instanceAccount);
            }
        }
        System.out.println("MysqlDaoFactory@getAccountDao() exit. Instance: " + instanceAccount);
        return instanceAccount;
    }

    /**
     * Method, which give us possibility manage table with products in db
     * @return instance ProductDao
     */
    public ProductDao getProductDao(){
        System.out.println("MysqlDaoFactory@getProductDao() start");
        if (instanceProduct == null) {
            synchronized (synh) {
                instanceProduct = new MysqlProductDao();
                System.out.println("MysqlDaoFactory@getProductDao() instance created: " + instanceProduct);
            }
        }
        System.out.println("MysqlDaoFactory@getProductDao() exit. Instance: " + instanceProduct);
        return instanceProduct;
    }
}
