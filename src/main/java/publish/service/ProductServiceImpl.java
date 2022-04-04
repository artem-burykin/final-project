package publish.service;

import publish.db.dao.DBException;
import publish.db.dao.DaoFactory;
import publish.db.dao.ProductDao;
import publish.db.entity.Product;

import java.util.List;

public class ProductServiceImpl implements ProductDao {
    private final ProductDao productDao = DaoFactory.getInstance().getProductDao();

    public static Product getProduct(String name, double price, int category_id){
        return Product.createProduct(name, price, category_id);
    }


    @Override
    public List<Product> sortFromLowToHigh() throws DBException {
        return productDao.sortFromLowToHigh();
    }

    @Override
    public List<Product> sortFromHighToLow() throws DBException {
        return productDao.sortFromHighToLow();
    }

    @Override
    public List<Product> sortFromAToZ() throws DBException {
        return productDao.sortFromAToZ();
    }

    @Override
    public List<Product> sortFromZToA() throws DBException {
        return productDao.sortFromZToA();
    }

    @Override
    public Product findProductByName(String name) throws DBException {
        return productDao.findProductByName(name);
    }

    @Override
    public List<Product> findProductByPrice(double startPrice, double endPrice) throws DBException {
        return productDao.findProductByPrice(startPrice, endPrice);
    }

    @Override
    public List<Product> sortFromOldToNew() throws DBException {
        return productDao.sortFromOldToNew();
    }

    @Override
    public List<Product> sortFromNewToOld() throws DBException {
        return productDao.sortFromNewToOld();
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
    public boolean deleteProduct(String name) throws DBException {
        return productDao.deleteProduct(name);
    }

    @Override
    public List<Product> findProductsByCategory(String name) throws DBException {
        return productDao.findProductsByCategory(name);
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
