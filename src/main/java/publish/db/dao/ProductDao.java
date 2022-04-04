package publish.db.dao;


import publish.db.entity.Product;

import java.util.List;

/**
 * Interface with method, which realization in MysqlProductDao.
 * @author Byrukin
 */
public interface ProductDao {
    List<Product> sortFromLowToHigh() throws DBException;
    List<Product> sortFromHighToLow() throws DBException;
    List<Product> sortFromAToZ() throws DBException;
    List<Product> sortFromZToA() throws DBException;
    Product findProductByName(String name) throws DBException;
    List<Product> findProductByPrice(double startPrice, double endPrice) throws DBException;
    List<Product> sortFromOldToNew() throws DBException;
    List<Product> sortFromNewToOld() throws DBException;
    boolean insertProduct(Product product) throws DBException;
    List<Product> findAllProducts() throws DBException;
    boolean deleteProduct(String name) throws DBException;
    List<Product> findProductsByCategory(String name) throws DBException;
    void updateProductPrice(double price, String name) throws DBException;
    void updateProductLogo(String logo, String name) throws DBException;
    void updateProductDescription(String description, String name) throws DBException;
}