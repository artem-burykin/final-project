package publish.service;

import publish.db.dao.DBException;
import publish.db.dao.DaoFactory;
import publish.db.dao.ProductDao;
import publish.db.dao.mysql.ConnectionPool;
import publish.db.entity.Product;

import java.sql.Connection;
import java.util.List;

/**
 * Implementation service for manage product.
 * @author Burykin
 */
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao = DaoFactory.getInstance().getProductDao();



    public static Product getProduct(String name, double price, int category_id){
        return Product.createProduct(name, price, category_id);
    }


    @Override
    public Product getProductByName(String name) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return productDao.getProductByName(con, name);
    }

    @Override
    public List<Product> sortFromLowToHigh(String login) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return productDao.sortFromLowToHigh(con, login);
    }

    @Override
    public List<Product> sortFromHighToLow(String login) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return productDao.sortFromHighToLow(con, login);
    }

    @Override
    public List<Product> sortFromAToZ(String login) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return productDao.sortFromAToZ(con, login);
    }

    @Override
    public List<Product> sortFromZToA(String login) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return productDao.sortFromZToA(con, login);
    }

    @Override
    public Product findProductByName(String login, String name) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return productDao.findProductByName(con, login, name);
    }

    @Override
    public List<Product> findProductByPrice(String login, double startPrice, double endPrice) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return productDao.findProductByPrice(con, login, startPrice, endPrice);
    }


    @Override
    public List<Product> sortFromOldToNew(String login) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return productDao.sortFromOldToNew(con, login);
    }


    @Override
    public List<Product> sortFromNewToOld(String login) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return productDao.sortFromNewToOld(con, login);
    }


    @Override
    public boolean insertProduct(Product product) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return productDao.insertProduct(con, product);
    }

    @Override
    public List<Product> findAllProducts() throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return productDao.findAllProducts(con);
    }

    @Override
    public List<Product> findAllNotSubscribeProduct(String login) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return productDao.findAllNotSubscribeProduct(con, login);
    }

    @Override
    public List<Product> findAllSubscribeProduct(String login) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return productDao.findAllSubscribeProduct(con, login);
    }

    @Override
    public boolean deleteProduct(String name) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return productDao.deleteProduct(con, name);
    }

    @Override
    public List<Product> findProductsByCategory(String login, String name) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return productDao.findProductsByCategory(con, login, name);
    }


    @Override
    public void updateProductPrice(double price, String name) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        productDao.updateProductPrice(con, price, name);
    }

    @Override
    public void updateProductLogo(String logo, String name) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        productDao.updateProductLogo(con, logo, name);
    }

    @Override
    public void updateProductDescription(String description, String name) throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        productDao.updateProductDescription(con, description, name);
    }
}
