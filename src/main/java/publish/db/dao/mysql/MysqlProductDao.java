package publish.db.dao.mysql;

import publish.db.dao.DBException;
import publish.db.dao.ProductDao;
import publish.db.entity.Product;
import publish.service.ProductServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for Product entity.
 * @autor Burykin
 */
public class MysqlProductDao implements ProductDao {

    MysqlProductDao() {}

    /**
     * Close connection.
     * @param con connection, which we try to close.
     */
    public void close (AutoCloseable con){
        if (con != null){
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Insert product to db.
     * @param product product, which we needed to insert.
     * @return boolean value(true, if exist).
     * @throws DBException
     */
    public boolean insertProduct(Product product) throws DBException {
        Connection con = null;
        try{
            con = ConnectionPool.getInstance().getConnection();
            insertProduct(con, product);
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DBException("Cannot add this account", e);
        }
        finally {
            close(con);
        }
    }
    /**
     * Supporting method, which help us to insert the product.
     * @param con connection with db.
     * @param product product, which we needed to insert.
     * @throws SQLException
     */
    private void insertProduct(Connection con, Product product) throws SQLException {
        try (PreparedStatement st = con.prepareStatement(DBConstant.INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            st.setString(++i, product.getName());
            st.setString(++i, product.getDescription());
            st.setDouble(++i, product.getPrice());
            st.setInt(++i, product.getCategory_id());
            st.setString(++i, product.getLogo());
            int c = st.executeUpdate();
            if (c > 0) {
                try (ResultSet keys = st.getGeneratedKeys()) {
                    if (keys.next()) {
                        product.setId(keys.getInt(1));
                    }
                }
            }
        }
    }

    /**
     * Delete product by name.
     * @param name name, by which we find product.
     * @return boolean value(true, if exist).
     * @throws DBException
     */
    public boolean deleteProduct(String name) throws DBException {
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.DELETE_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Cannot delete these products", e);
        }
    }

    /**
     * Find all products.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> findAllProducts() throws DBException{
        try(Connection con = ConnectionPool.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(DBConstant.FIND_ALL_PRODUCTS)){
            con.setAutoCommit(false);
            List<Product> result = new ArrayList<>();
            while(rs.next()){
                Product product = ProductServiceImpl.getProduct(rs.getString(DBConstant.F_PRODUCT_NAME),
                        rs.getDouble(DBConstant.F_PRODUCT_PRICE), rs.getInt(DBConstant.F_PRODUCT_CATEGORY_ID));
                product.setId(rs.getInt(DBConstant.F_PRODUCT_ID));
                product.setDescription(DBConstant.F_PRODUCT_DESCRIPTION);
                product.setLogo(DBConstant.F_PRODUCT_LOGO);
                product.setCreate_date(rs.getDate(DBConstant.F_PRODUCT_CREATE_DATE));
                product.setLast_update(rs.getDate(DBConstant.F_PRODUCT_LAST_UPDATE));
                result.add(product);
            }
            return result;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DBException("There aren't products in the database", e);
        }
    }

    /**
     * Sort from low to high price.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> sortFromLowToHigh() throws DBException{
        try(Connection con = ConnectionPool.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(DBConstant.GET_PRODUCT_FROM_LOW_TO_HIGH)){
            con.setAutoCommit(false);
            List<Product> result = new ArrayList<>();
            while(rs.next()){
                Product product = ProductServiceImpl.getProduct(rs.getString(DBConstant.F_PRODUCT_NAME),
                        rs.getDouble(DBConstant.F_PRODUCT_PRICE), rs.getInt(DBConstant.F_PRODUCT_CATEGORY_ID));
                product.setId(rs.getInt(DBConstant.F_PRODUCT_ID));
                product.setDescription(DBConstant.F_PRODUCT_DESCRIPTION);
                product.setLogo(DBConstant.F_PRODUCT_LOGO);
                product.setCreate_date(rs.getDate(DBConstant.F_PRODUCT_CREATE_DATE));
                product.setLast_update(rs.getDate(DBConstant.F_PRODUCT_LAST_UPDATE));
                result.add(product);
            }
            return result;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DBException("There aren't product in the database", e);
        }
    }

    /**
     * Sort from high to low price.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> sortFromHighToLow() throws DBException{
        try(Connection con = ConnectionPool.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(DBConstant.GET_PRODUCT_FROM_HIGH_TO_LOW)){
            con.setAutoCommit(false);
            List<Product> result = new ArrayList<>();
            while(rs.next()){
                Product product = ProductServiceImpl.getProduct(rs.getString(DBConstant.F_PRODUCT_NAME),
                        rs.getDouble(DBConstant.F_PRODUCT_PRICE), rs.getInt(DBConstant.F_PRODUCT_CATEGORY_ID));
                product.setId(rs.getInt(DBConstant.F_PRODUCT_ID));
                product.setDescription(DBConstant.F_PRODUCT_DESCRIPTION);
                product.setLogo(DBConstant.F_PRODUCT_LOGO);
                product.setCreate_date(rs.getDate(DBConstant.F_PRODUCT_CREATE_DATE));
                product.setLast_update(rs.getDate(DBConstant.F_PRODUCT_LAST_UPDATE));
                result.add(product);
            }
            return result;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DBException("There aren't product in the database", e);
        }
    }

    /**
     * Sort product's name from A to Z.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> sortFromAToZ() throws DBException{
        try(Connection con = ConnectionPool.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(DBConstant.GET_PRODUCT_FROM_A_TO_Z)){
            con.setAutoCommit(false);
            List<Product> result = new ArrayList<>();
            while(rs.next()){
                Product product = ProductServiceImpl.getProduct(rs.getString(DBConstant.F_PRODUCT_NAME),
                        rs.getDouble(DBConstant.F_PRODUCT_PRICE), rs.getInt(DBConstant.F_PRODUCT_CATEGORY_ID));
                product.setId(rs.getInt(DBConstant.F_PRODUCT_ID));
                product.setDescription(DBConstant.F_PRODUCT_DESCRIPTION);
                product.setLogo(DBConstant.F_PRODUCT_LOGO);
                product.setCreate_date(rs.getDate(DBConstant.F_PRODUCT_CREATE_DATE));
                product.setLast_update(rs.getDate(DBConstant.F_PRODUCT_LAST_UPDATE));
                result.add(product);
            }
            return result;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DBException("There aren't product in the database", e);
        }
    }

    /**
     * Sort product's name from Z to A.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> sortFromZToA() throws DBException{
        try(Connection con = ConnectionPool.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(DBConstant.GET_PRODUCT_FROM_Z_TO_A)){
            con.setAutoCommit(false);
            List<Product> result = new ArrayList<>();
            while(rs.next()){
                Product product = ProductServiceImpl.getProduct(rs.getString(DBConstant.F_PRODUCT_NAME),
                        rs.getDouble(DBConstant.F_PRODUCT_PRICE), rs.getInt(DBConstant.F_PRODUCT_CATEGORY_ID));
                product.setId(rs.getInt(DBConstant.F_PRODUCT_ID));
                product.setDescription(DBConstant.F_PRODUCT_DESCRIPTION);
                product.setLogo(DBConstant.F_PRODUCT_LOGO);
                product.setCreate_date(rs.getDate(DBConstant.F_PRODUCT_CREATE_DATE));
                product.setLast_update(rs.getDate(DBConstant.F_PRODUCT_LAST_UPDATE));
                result.add(product);
            }
            return result;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DBException("There aren't product in the database", e);
        }
    }

    /**
     * Sort product from new to old.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> sortFromNewToOld() throws DBException{
        try(Connection con = ConnectionPool.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(DBConstant.GET_PRODUCT_FROM_NEW_TO_OLD)){
            con.setAutoCommit(false);
            List<Product> result = new ArrayList<>();
            while(rs.next()){
                Product product = ProductServiceImpl.getProduct(rs.getString(DBConstant.F_PRODUCT_NAME),
                        rs.getDouble(DBConstant.F_PRODUCT_PRICE), rs.getInt(DBConstant.F_PRODUCT_CATEGORY_ID));
                product.setId(rs.getInt(DBConstant.F_PRODUCT_ID));
                product.setDescription(DBConstant.F_PRODUCT_DESCRIPTION);
                product.setLogo(DBConstant.F_PRODUCT_LOGO);
                product.setCreate_date(rs.getDate(DBConstant.F_PRODUCT_CREATE_DATE));
                product.setLast_update(rs.getDate(DBConstant.F_PRODUCT_LAST_UPDATE));
                result.add(product);
            }
            return result;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DBException("There aren't product in the database", e);
        }
    }

    /**
     * Sort product from old to new.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> sortFromOldToNew() throws DBException{
        try(Connection con = ConnectionPool.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(DBConstant.GET_PRODUCT_FROM_OLD_TO_NEW)){
            con.setAutoCommit(false);
            List<Product> result = new ArrayList<>();
            while(rs.next()){
                Product product = ProductServiceImpl.getProduct(rs.getString(DBConstant.F_PRODUCT_NAME),
                        rs.getDouble(DBConstant.F_PRODUCT_PRICE), rs.getInt(DBConstant.F_PRODUCT_CATEGORY_ID));
                product.setId(rs.getInt(DBConstant.F_PRODUCT_ID));
                product.setDescription(DBConstant.F_PRODUCT_DESCRIPTION);
                product.setLogo(DBConstant.F_PRODUCT_LOGO);
                product.setCreate_date(rs.getDate(DBConstant.F_PRODUCT_CREATE_DATE));
                product.setLast_update(rs.getDate(DBConstant.F_PRODUCT_LAST_UPDATE));
                result.add(product);
            }
            return result;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DBException("There aren't product in the database", e);
        }
    }

    /**
     * Find product by name.
     * @param name name, by which we search product.
     * @return product with those name.
     * @throws DBException
     */
    public Product findProductByName(String name) throws DBException{
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.FIND_PRODUCT_BY_NAME)) {
            con.setAutoCommit(false);
            int i = 0;
            stmt.setString(++i, name);
            try (ResultSet rs = stmt.executeQuery()) {
                Product product = null;
                while (rs.next()) {
                    product = ProductServiceImpl.getProduct(rs.getString(DBConstant.F_PRODUCT_NAME),
                            rs.getDouble(DBConstant.F_PRODUCT_PRICE), rs.getInt(DBConstant.F_PRODUCT_CATEGORY_ID));
                    product.setId(rs.getInt(DBConstant.F_PRODUCT_ID));
                    product.setDescription(DBConstant.F_PRODUCT_DESCRIPTION);
                    product.setLogo(DBConstant.F_PRODUCT_LOGO);
                    product.setCreate_date(rs.getDate(DBConstant.F_PRODUCT_CREATE_DATE));
                    product.setLast_update(rs.getDate(DBConstant.F_PRODUCT_LAST_UPDATE));
                }
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("This product not found", e);
        }
    }

    /**
     * Filter product from startPrice to endPrice.
     * @param startPrice startPrice, price from which started find product.
     * @param endPrice endPrice, price to which continued find product.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> findProductByPrice(double startPrice, double endPrice) throws DBException{
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.GET_PRODUCT_BY_PRICE)) {
            con.setAutoCommit(false);
            int i = 0;
            stmt.setDouble(++i, startPrice);
            stmt.setDouble(++i, endPrice);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Product> result = new ArrayList<>();
                while (rs.next()) {
                    Product product = ProductServiceImpl.getProduct(rs.getString(DBConstant.F_PRODUCT_NAME),
                            rs.getDouble(DBConstant.F_PRODUCT_PRICE), rs.getInt(DBConstant.F_PRODUCT_CATEGORY_ID));
                    product.setId(rs.getInt(DBConstant.F_PRODUCT_ID));
                    product.setDescription(DBConstant.F_PRODUCT_DESCRIPTION);
                    product.setLogo(DBConstant.F_PRODUCT_LOGO);
                    product.setCreate_date(rs.getDate(DBConstant.F_PRODUCT_CREATE_DATE));
                    product.setLast_update(rs.getDate(DBConstant.F_PRODUCT_LAST_UPDATE));
                    result.add(product);
                }
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("These products not found", e);
        }
    }

    /**
     * Filter product by category.
     * @param name category's name, by which product user found.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> findProductsByCategory(String name) throws DBException{
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.FIND_PRODUCT_BY_CATEGORY)) {
            con.setAutoCommit(false);
            int i = 0;
            stmt.setString(++i, name);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Product> result = new ArrayList<>();
                while (rs.next()) {
                    Product product = ProductServiceImpl.getProduct(rs.getString(DBConstant.F_PRODUCT_NAME),
                            rs.getDouble(DBConstant.F_PRODUCT_PRICE), rs.getInt(DBConstant.F_PRODUCT_CATEGORY_ID));
                    product.setId(rs.getInt(DBConstant.F_PRODUCT_ID));
                    product.setDescription(DBConstant.F_PRODUCT_DESCRIPTION);
                    product.setLogo(DBConstant.F_PRODUCT_LOGO);
                    product.setCreate_date(rs.getDate(DBConstant.F_PRODUCT_CREATE_DATE));
                    product.setLast_update(rs.getDate(DBConstant.F_PRODUCT_LAST_UPDATE));
                    result.add(product);
                }
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("This product not found", e);
        }
    }

    /**
     * Changes certain product's price.
     * @param price new price for product.
     * @param name product's name, which price we need to change.
     * @throws DBException
     */
    public void updateProductPrice(double price, String name) throws DBException {
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.UPDATE_PRODUCT_PRICE)) {
            int i = 0;
            stmt.setDouble(++i, price);
            stmt.setString(++i, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("This product not found", e);
        }
    }

    /**
     * Changes certain product's logo.
     * @param logo new logo for product.
     * @param name product's name, which price we need to change.
     * @throws DBException
     */
    public void updateProductLogo(String logo, String name) throws DBException {
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.UPDATE_PRODUCT_LOGO)) {
            int i = 0;
            stmt.setString(++i, logo);
            stmt.setString(++i, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("This product not found", e);
        }
    }

    /**
     * Changes certain product's description.
     * @param description new description for product.
     * @param name product's name, which price we need to change.
     * @throws DBException
     */
    public void updateProductDescription(String description, String name) throws DBException {
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.UPDATE_PRODUCT_DESCRIPTION)) {
            int i = 0;
            stmt.setString(++i, description);
            stmt.setString(++i, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("This product not found", e);
        }
    }
}
