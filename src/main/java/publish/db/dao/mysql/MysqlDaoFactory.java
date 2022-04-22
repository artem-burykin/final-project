package publish.db.dao.mysql;

import publish.db.dao.*;

/**
 * Class for return instance of classes, which manage db.
 * @author Burykin
 */
public class MysqlDaoFactory extends DaoFactory {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MysqlDaoFactory.class);
    private AccountDao instanceAccount;
    private ProductDao instanceProduct;
    private OrderDao instanceOrder;
    private PublicationDao instancePublication;
    private CategoryDao instanceCategory;
    private static final Object synh = new Object();

    /**
     * Method, which give us possibility manage table with accounts in db.
     * @return instance AccountDao.
     */
    public AccountDao getAccountDao() {
        if (instanceAccount == null) {
            synchronized (synh) {
                instanceAccount = new MysqlAccountDao();
                LOG.info("Instance of MysqlAccountDao was received.");
            }
        }
        return instanceAccount;
    }

    /**
     * Method, which give us possibility manage table with products in db.
     * @return instance ProductDao.
     */
    public ProductDao getProductDao(){
        if (instanceProduct == null) {
            synchronized (synh) {
                instanceProduct = new MysqlProductDao();
                LOG.info("Instance of MysqlProductDao was received.");
            }
        }
        return instanceProduct;
    }

    /**
     * Method, which give us possibility manage table with orders in db.
     * @return instance OrderDao.
     */
    public OrderDao getOrderDao(){
        if(instanceOrder == null){
            synchronized (synh){
                instanceOrder = new MysqlOrderDao();
                LOG.info("Instance of MysqlOrderDao was received.");
            }
        }
        return instanceOrder;
    }

    /**
     * Method, which give us possibility to manage table with orders in db
     * @return instance PublicationDao
     */
    public PublicationDao getPublicationDao(){
        if (instancePublication == null){
            synchronized (synh){
                instancePublication = new MysqlPublicationDao();
                LOG.info("Instance of MysqlPublicationDao was received.");
            }
        }
        return instancePublication;
    }

    /**
     * Method, which give us possibility to manage table with categories in db.
     * @return instance CategoryDao.
     */
    public CategoryDao getCategoryDao() {
        if (instanceCategory == null){
            synchronized (synh){
                instanceCategory = new MysqlCategoryDao();
                LOG.info("Instance of MysqlCategoryDao was received.");
            }
        }
        return instanceCategory;
    }
}
