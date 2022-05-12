package publish.db.dao;


import publish.db.entity.Product;

import java.sql.Connection;
import java.util.List;

/**
 * Interface with method, which realization in MysqlProductDao.
 * @author Byrukin
 */
public interface ProductDao {
    Product getProductByName(Connection con, String name) throws DBException;
    List<Product> sortFromLowToHigh(Connection con, String login) throws DBException;
    List<Product> sortFromHighToLow(Connection con, String login) throws DBException;
    List<Product> sortFromAToZ(Connection con, String login) throws DBException;
    List<Product> sortFromZToA(Connection con, String login) throws DBException;
    Product findProductByName(Connection con, String login, String name) throws DBException;
    List<Product> findProductByPrice(Connection con, String login, double startPrice, double endPrice) throws DBException;
    List<Product> sortFromOldToNew(Connection con, String login) throws DBException;
    List<Product> sortFromNewToOld(Connection con, String login) throws DBException;
    boolean insertProduct(Connection con, Product product) throws DBException;
    List<Product> findAllProducts(Connection con) throws DBException;
    List<Product> findAllNotSubscribeProduct(Connection con, String login) throws DBException;
    List<Product> findAllSubscribeProduct(Connection con, String login) throws DBException;
    boolean deleteProduct(Connection con, String name) throws DBException;
    List<Product> findProductsByCategory(Connection con, String login, String name) throws DBException;
    void updateProductPrice(Connection con, double price, String name) throws DBException;
    void updateProductLogo(Connection con, String logo, String name) throws DBException;
    void updateProductDescription(Connection con, String description, String name) throws DBException;
}