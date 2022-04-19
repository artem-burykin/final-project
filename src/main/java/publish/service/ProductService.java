package publish.service;

import publish.db.dao.DBException;
import publish.db.entity.Product;

import java.util.List;

public interface ProductService {
    Product getProductByName(String name) throws DBException;
    List<Product> sortFromLowToHigh(String login) throws DBException;
    List<Product> sortFromHighToLow(String login) throws DBException;
    List<Product> sortFromAToZ(String login) throws DBException;
    List<Product> sortFromZToA(String login) throws DBException;
    Product findProductByName(String login, String name) throws DBException;
    List<Product> findProductByPrice(String login, double startPrice, double endPrice) throws DBException;
    List<Product> sortFromOldToNew(String login) throws DBException;
    List<Product> sortFromNewToOld(String login) throws DBException;
    boolean insertProduct(Product product) throws DBException;
    List<Product> findAllProducts() throws DBException;
    List<Product> findAllNotSubscribeProduct(String login) throws DBException;
    List<Product> findAllSubscribeProduct(String login) throws DBException;
    boolean deleteProduct(String name) throws DBException;
    List<Product> findProductsByCategory(String login, String name) throws DBException;
    void updateProductPrice(double price, String name) throws DBException;
    void updateProductLogo(String logo, String name) throws DBException;
    void updateProductDescription(String description, String name) throws DBException;
}
