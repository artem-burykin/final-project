package publish.service;

import publish.db.dao.DBException;
import publish.db.dao.DaoFactory;
import publish.db.dao.ProductDao;
import publish.db.entity.Product;

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
        return productDao.getProductByName(name);
    }

    @Override
    public List<Product> sortFromLowToHigh(String login) throws DBException {
        return productDao.sortFromLowToHigh(login);
    }

    @Override
    public List<Product> sortFromHighToLow(String login) throws DBException {
        return productDao.sortFromHighToLow(login);
    }

    @Override
    public List<Product> sortFromAToZ(String login) throws DBException {
        return productDao.sortFromAToZ(login);
    }

    @Override
    public List<Product> sortFromZToA(String login) throws DBException {
        return productDao.sortFromZToA(login);
    }

    @Override
    public Product findProductByName(String login, String name) throws DBException {
        return productDao.findProductByName(login, name);
    }

    @Override
    public List<Product> findProductByPrice(String login, double startPrice, double endPrice) throws DBException {
        return productDao.findProductByPrice(login, startPrice, endPrice);
    }


    @Override
    public List<Product> sortFromOldToNew(String login) throws DBException {
        return productDao.sortFromOldToNew(login);
    }


    @Override
    public List<Product> sortFromNewToOld(String login) throws DBException {
        return productDao.sortFromNewToOld(login);
    }


    @Override
    public boolean insertProduct(Product product) throws DBException {
        return productDao.insertProduct(product);
    }

    @Override
    public List<Product> findAllProducts() throws DBException {
        return productDao.findAllProducts();
    }

    @Override
    public List<Product> findAllNotSubscribeProduct(String login) throws DBException {
        return productDao.findAllNotSubscribeProduct(login);
    }

    @Override
    public List<Product> findAllSubscribeProduct(String login) throws DBException {
        return productDao.findAllSubscribeProduct(login);
    }

    @Override
    public boolean deleteProduct(String name) throws DBException {
        return productDao.deleteProduct(name);
    }

    @Override
    public List<Product> findProductsByCategory(String login, String name) throws DBException {
        return productDao.findProductsByCategory(login, name);
    }


    @Override
    public void updateProductPrice(double price, String name) throws DBException {
        productDao.updateProductPrice(price, name);
    }

    @Override
    public void updateProductLogo(String logo, String name) throws DBException {
        productDao.updateProductLogo(logo, name);
    }

    @Override
    public void updateProductDescription(String description, String name) throws DBException {
        productDao.updateProductDescription(description, name);
    }
}
