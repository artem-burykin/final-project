package publish.db.dao.mysql;

import publish.db.dao.CategoryDao;
import publish.db.dao.DBException;
import publish.db.entity.Category;
import publish.db.entity.Product;
import publish.service.CategoryServiceImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for Category entity.
 * @author Burykin
 */
public class MysqlCategoryDao implements CategoryDao {
    MysqlCategoryDao() {}

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

    private Category creatingNewCategory(ResultSet rs) throws SQLException{
        Category category = CategoryServiceImp.getCategory(rs.getString(DBConstant.F_CATEGORY_NAME));
        category.setDescription(rs.getString(DBConstant.F_CATEGORY_DESCRIPTION));
        category.setId(rs.getInt(DBConstant.F_CATEGORY_ID));
        category.setParent_id(rs.getInt(DBConstant.F_CATEGORY_PARENT_ID));
        return category;
    }
    /**
     * Find all category.
     * @return category's list.
     * @throws DBException
     */
    public List<Category> findAllCategories() throws DBException{
        try(Connection con = ConnectionPool.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(DBConstant.FIND_ALL_CATEGORIES)){
            con.setAutoCommit(false);
            List<Category> result = new ArrayList<>();
            while(rs.next()){
                result.add(creatingNewCategory(rs));
            }
            return result;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DBException("There aren't categories in the database", e);
        }
    }
}
