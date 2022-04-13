package publish.service;

import publish.db.dao.DBException;
import publish.db.entity.Product;

import java.util.List;

public interface ProductService {
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
    List<Product> findAllNotSubscribeProduct(String login) throws DBException;
    List<Product> findAllSubscribeProduct(String login) throws DBException;
    boolean deleteProduct(String name) throws DBException;
    List<Product> findProductsByCategory(String name) throws DBException;
    void updateProductPrice(double price, String name) throws DBException;
    void updateProductLogo(String logo, String name) throws DBException;
    void updateProductDescription(String description, String name) throws DBException;
}
