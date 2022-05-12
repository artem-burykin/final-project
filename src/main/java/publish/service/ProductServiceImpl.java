package publish.service;

import publish.db.dao.DBException;
import publish.db.dao.DaoFactory;
import publish.db.dao.ProductDao;
import publish.db.dao.mysql.ConnectionPool;
import publish.db.entity.Product;

import java.sql.Connection;
import java.sql.SQLException;
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
        try(Connection con = ConnectionPool.getInstance().getConnection()){
            return productDao.getProductByName(con, name);
        }
        catch (SQLException e) {
            throw new DBException("This product not found", e);
        }
    }

    @Override
    public List<Product> sortFromLowToHigh(String login) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            return productDao.sortFromLowToHigh(con, login);
        }
        catch (SQLException e){
            throw new DBException("There aren't product in the database", e);
        }
    }

    @Override
    public List<Product> sortFromHighToLow(String login) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            return productDao.sortFromHighToLow(con, login);
        }
        catch (SQLException e){
            throw new DBException("There aren't product in the database", e);
        }
    }

    @Override
    public List<Product> sortFromAToZ(String login) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            return productDao.sortFromAToZ(con, login);
        }
        catch (SQLException e){
            throw new DBException("There aren't product in the database", e);
        }
    }

    @Override
    public List<Product> sortFromZToA(String login) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            return productDao.sortFromZToA(con, login);
        }
        catch (SQLException e){
            throw new DBException("There aren't product in the database", e);
        }
    }

    @Override
    public Product findProductByName(String login, String name) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            return productDao.findProductByName(con, login, name);
        }
        catch (SQLException e){
            throw new DBException("This product not found", e);
        }
    }

    @Override
    public List<Product> findProductByPrice(String login, double startPrice, double endPrice) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            return productDao.findProductByPrice(con, login, startPrice, endPrice);
        }
        catch (SQLException e){
            throw new DBException("These products not found", e);
        }
    }


    @Override
    public List<Product> sortFromOldToNew(String login) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            return productDao.sortFromOldToNew(con, login);
        }
        catch (SQLException e){
            throw new DBException("There aren't product in the database", e);
        }
    }


    @Override
    public List<Product> sortFromNewToOld(String login) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            return productDao.sortFromNewToOld(con, login);
        }
        catch (SQLException e){
            throw new DBException("There aren't product in the database", e);
        }
    }


    @Override
    public boolean insertProduct(Product product) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            return productDao.insertProduct(con, product);
        }
        catch (SQLException e){
            throw new DBException("Cannot add this account", e);
        }
    }

    @Override
    public List<Product> findAllProducts() throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            return productDao.findAllProducts(con);
        }
        catch (SQLException e){
            throw new DBException("There aren't products in the database", e);
        }
    }

    @Override
    public List<Product> findAllNotSubscribeProduct(String login) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            return productDao.findAllNotSubscribeProduct(con, login);
        }
        catch (SQLException e){
            throw new DBException("There aren't products in the database", e);
        }
    }

    @Override
    public List<Product> findAllSubscribeProduct(String login) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()){
            return productDao.findAllSubscribeProduct(con, login);
        }
        catch (SQLException e){
            throw new DBException("There aren't products in the database", e);
        }
    }

    @Override
    public boolean deleteProduct(String name) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection();) {
            return productDao.deleteProduct(con, name);
        } catch (SQLException e) {
            throw new DBException("Cannot delete these products", e);
        }
    }

    @Override
    public List<Product> findProductsByCategory(String login, String name) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()){
            return productDao.findProductsByCategory(con, login, name);
        }
        catch (SQLException e){
            throw new DBException("This product not found", e);
        }
    }


    @Override
    public void updateProductPrice(double price, String name) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            productDao.updateProductPrice(con, price, name);
        }
        catch (SQLException e){
            throw new DBException("This product not found", e);
        }
    }

    @Override
    public void updateProductLogo(String logo, String name) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            productDao.updateProductLogo(con, logo, name);
        }
        catch (SQLException e){
            throw new DBException("This product not found", e);
        }
    }

    @Override
    public void updateProductDescription(String description, String name) throws DBException {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            productDao.updateProductDescription(con, description, name);
        }
        catch (SQLException e){
            throw new DBException("This product not found", e);
        }
    }
}
