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
     * Private auxiliary method for creating new product.
     * @param rs result set, from which we take data.
     * @return new product.
     * @throws SQLException
     */
    private Product creatingNewProduct (ResultSet rs) throws SQLException {
        Product product = ProductServiceImpl.getProduct(rs.getString(DBConstant.F_PRODUCT_NAME),
                rs.getDouble(DBConstant.F_PRODUCT_PRICE), rs.getInt(DBConstant.F_PRODUCT_CATEGORY_ID));
        product.setId(rs.getInt(DBConstant.F_PRODUCT_ID));
        product.setDescription(rs.getString(DBConstant.F_PRODUCT_DESCRIPTION));
        product.setLogo(rs.getString(DBConstant.F_PRODUCT_LOGO));
        product.setCreate_date(rs.getDate(DBConstant.F_PRODUCT_CREATE_DATE));
        product.setLast_update(rs.getDate(DBConstant.F_PRODUCT_LAST_UPDATE));
        return product;
    }

    /**
     * Insert product to db.
     * @param con connection with database.
     * @param product product, which we needed to insert.
     * @return boolean value(true, if exist).
     * @throws DBException
     */
    public boolean insertProduct(Connection con, Product product) throws DBException {
        try{
            helperInsertProduct(con, product);
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DBException("Cannot add this account", e);
        }
    }
    /**
     * Supporting method, which help us to insert the product.
     * @param con connection with database.
     * @param con connection with db.
     * @param product product, which we needed to insert.
     * @throws SQLException
     */
    private void helperInsertProduct(Connection con, Product product) throws SQLException {
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
     * Getting product by full name.
     * @param con connection with database.
     * @param name name, by which we search product.
     * @return product with those name.
     * @throws DBException
     */
    public Product getProductByName(Connection con, String name) throws DBException{
        try(PreparedStatement stmt = con.prepareStatement((DBConstant.GET_PRODUCT_BY_NAME), Statement.RETURN_GENERATED_KEYS)){
            con.setAutoCommit(false);
            int i = 0;
            stmt.setString(++i, name);
            try (ResultSet rs = stmt.executeQuery()) {
                Product product = null;
                while (rs.next()) {
                    product = creatingNewProduct(rs);
                }
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("This product not found", e);
        }
    }

    /**
     * Delete product by name.
     * @param con connection with database.
     * @param name name, by which we find product.
     * @return boolean value(true, if exist).
     * @throws DBException
     */
    public boolean deleteProduct(Connection con, String name) throws DBException {
        try (PreparedStatement stmt = con.prepareStatement(DBConstant.DELETE_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
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
     * @param con connection with database.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> findAllProducts(Connection con) throws DBException{
        try(Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(DBConstant.FIND_ALL_PRODUCTS)){
            con.setAutoCommit(false);
            List<Product> result = new ArrayList<>();
            while(rs.next()){
                result.add(creatingNewProduct(rs));
            }
            return result;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DBException("There aren't products in the database", e);
        }
    }

    /**
     * Find all not subscribe product.
     * @param con connection with database.
     * @param login account's login, by which we found not subscribe products.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> findAllNotSubscribeProduct(Connection con, String login) throws DBException{
        try (PreparedStatement stmt = con.prepareStatement(DBConstant.FIND_ALL_NOT_SUBSCRIBE_PRODUCTS)){
            con.setAutoCommit(false);
            int i = 0;
            stmt.setString(++i, login);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Product> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(creatingNewProduct(rs));
                }
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("There aren't products in the database", e);
        }
    }

    /**
     *  Find all subscribe product.
     *  @param con connection with database.
     *  @param login account's login, by which we found subscribe products.
     *  @return product's list.
     *  @throws DBException
     */
    public List<Product> findAllSubscribeProduct(Connection con, String login) throws DBException{
        try (PreparedStatement stmt = con.prepareStatement(DBConstant.FIND_ALL_SUBSCRIBE_PRODUCTS)){
            con.setAutoCommit(false);
            int i = 0;
            stmt.setString(++i, login);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Product> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(creatingNewProduct(rs));
                }
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("There aren't products in the database", e);
        }
    }

    /**
     * Sort from low to high price.
     * @param con connection with database.
     * @param login account's login, by which we found subscribe products.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> sortFromLowToHigh(Connection con, String login) throws DBException{
        try(PreparedStatement stmt = con.prepareStatement(DBConstant.GET_PRODUCT_FROM_LOW_TO_HIGH, Statement.RETURN_GENERATED_KEYS)){
            con.setAutoCommit(false);
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            List<Product> result = new ArrayList<>();
            while(rs.next()){
                result.add(creatingNewProduct(rs));
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
     * @param con connection with database.
     * @param login login, by which we check not subscription product.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> sortFromHighToLow(Connection con, String login) throws DBException{
        try(PreparedStatement stmt = con.prepareStatement(DBConstant.GET_PRODUCT_FROM_HIGH_TO_LOW, Statement.RETURN_GENERATED_KEYS)){
            con.setAutoCommit(false);
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            List<Product> result = new ArrayList<>();
            while(rs.next()){
                result.add(creatingNewProduct(rs));
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
     * @param con connection with database.
     * @param login account's login, by which we found subscribe products.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> sortFromAToZ(Connection con, String login) throws DBException{
        try(PreparedStatement stmt = con.prepareStatement(DBConstant.GET_PRODUCT_FROM_A_TO_Z, Statement.RETURN_GENERATED_KEYS)){
            con.setAutoCommit(false);
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            List<Product> result = new ArrayList<>();
            while(rs.next()){
                result.add(creatingNewProduct(rs));
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
     * @param con connection with database.
     * @param login account's login, by which we found subscribe products.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> sortFromZToA(Connection con, String login) throws DBException{
        try(PreparedStatement stmt = con.prepareStatement(DBConstant.GET_PRODUCT_FROM_Z_TO_A, Statement.RETURN_GENERATED_KEYS)){
            con.setAutoCommit(false);
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            List<Product> result = new ArrayList<>();
            while(rs.next()){
                result.add(creatingNewProduct(rs));
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
     * @param con connection with database.
     * @param login account's login, by which we found subscribe products.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> sortFromNewToOld(Connection con, String login) throws DBException{
        try(PreparedStatement stmt = con.prepareStatement(DBConstant.GET_PRODUCT_FROM_NEW_TO_OLD, Statement.RETURN_GENERATED_KEYS)){
            con.setAutoCommit(false);
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            List<Product> result = new ArrayList<>();
            while(rs.next()){
                result.add(creatingNewProduct(rs));
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
     * @param con connection with database.
     * @param login account's login, by which we found subscribe products.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> sortFromOldToNew(Connection con, String login) throws DBException{
        try(PreparedStatement stmt = con.prepareStatement(DBConstant.GET_PRODUCT_FROM_OLD_TO_NEW, Statement.RETURN_GENERATED_KEYS)){
            con.setAutoCommit(false);
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            List<Product> result = new ArrayList<>();
            while(rs.next()){
                result.add(creatingNewProduct(rs));
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
     * @param con connection with database.
     * @param login account's login, by which we found subscribe products.
     * @param name name, by which we search product.
     * @return product with those name.
     * @throws DBException
     */
    public Product findProductByName(Connection con, String login, String name) throws DBException{
        try (PreparedStatement stmt = con.prepareStatement((DBConstant.FIND_PRODUCT_BY_NAME), Statement.RETURN_GENERATED_KEYS)) {
            con.setAutoCommit(false);
            name = "%" + name + "%";
            int i = 0;
            stmt.setString(++i, login);
            stmt.setString(++i, name);
            try (ResultSet rs = stmt.executeQuery()) {
                Product product = null;
                while (rs.next()) {
                    product = creatingNewProduct(rs);
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
     * @param con connection with database.
     * @param login account's login, by which we found subscribe products.
     * @param startPrice startPrice, price from which started find product.
     * @param endPrice endPrice, price to which continued find product.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> findProductByPrice(Connection con, String login, double startPrice, double endPrice) throws DBException{
        try (PreparedStatement stmt = con.prepareStatement(DBConstant.GET_PRODUCT_BY_PRICE)) {
            con.setAutoCommit(false);
            int i = 0;
            stmt.setString(++i, login);
            stmt.setDouble(++i, startPrice);
            stmt.setDouble(++i, endPrice);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Product> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(creatingNewProduct(rs));
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
     * @param con connection with database.
     * @param login account's login, by which we found subscribe products.
     * @param name category's name, by which product user found.
     * @return product's list.
     * @throws DBException
     */
    public List<Product> findProductsByCategory(Connection con, String login, String name) throws DBException{
        try (PreparedStatement stmt = con.prepareStatement(DBConstant.FIND_PRODUCT_BY_CATEGORY)) {
            con.setAutoCommit(false);
            int i = 0;
            stmt.setString(++i, login);
            stmt.setString(++i, name);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Product> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(creatingNewProduct(rs));
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
     * @param con connection with database.
     * @param price new price for product.
     * @param name product's name, which price we need to change.
     * @throws DBException
     */
    public void updateProductPrice(Connection con, double price, String name) throws DBException {
        try (PreparedStatement stmt = con.prepareStatement(DBConstant.UPDATE_PRODUCT_PRICE)) {
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
     * @param con connection with database.
     * @param logo new logo for product.
     * @param name product's name, which price we need to change.
     * @throws DBException
     */
    public void updateProductLogo(Connection con, String logo, String name) throws DBException {
        try (PreparedStatement stmt = con.prepareStatement(DBConstant.UPDATE_PRODUCT_LOGO)) {
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
     * @param con connection with database.
     * @param description new description for product.
     * @param name product's name, which price we need to change.
     * @throws DBException
     */
    public void updateProductDescription(Connection con, String description, String name) throws DBException {
        try (PreparedStatement stmt = con.prepareStatement(DBConstant.UPDATE_PRODUCT_DESCRIPTION)) {
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
