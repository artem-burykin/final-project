package publish.db.dao;

import publish.db.dao.mysql.MysqlDaoFactory;

/**
 * Factory to create entities of interface.
 * @author Burykin
 */
public abstract class DaoFactory {
    private static DaoFactory instance = new MysqlDaoFactory();
    public static DaoFactory getInstance() {
        return instance;
    }

    public abstract AccountDao getAccountDao();
    public abstract ProductDao getProductDao();
    public abstract OrderDao getOrderDao();
    public abstract PublicationDao getPublicationDao();
    public abstract CategoryDao getCategoryDao();
}
