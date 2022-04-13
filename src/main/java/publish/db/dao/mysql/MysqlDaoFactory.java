package publish.db.dao.mysql;

import publish.db.dao.*;

/**
 * Class for return instance of classes, which manage db
 * @author Burykin
 */
public class MysqlDaoFactory extends DaoFactory {
    private AccountDao instanceAccount;
    private ProductDao instanceProduct;
    private OrderDao instanceOrder;
    private PublicationDao instancePublication;
    private CategoryDao instanceCategory;
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

    /**
     * Method, which give us possibility manage table with orders in db
     * @return instance OrderDao
     */
    public OrderDao getOrderDao(){
        if(instanceOrder == null){
            synchronized (synh){
                instanceOrder = new MysqlOrderDao();
            }
        }
        return instanceOrder;
    }

    /**
     * Method, which give us possibility to manage table with orders in db
     * @return instance PublicationDAo
     */
    public PublicationDao getPublicationDao(){
        if (instancePublication == null){
            synchronized (synh){
                instancePublication = new MysqlPublicationDao();
            }
        }
        return instancePublication;
    }

    @Override
    public CategoryDao getCategoryDao() {
        if (instanceCategory == null){
            synchronized (synh){
                instanceCategory = new MysqlCategoryDao();
            }
        }
        return instanceCategory;
    }
}
