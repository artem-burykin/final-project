package publish.service;

import publish.db.dao.CategoryDao;
import publish.db.dao.DBException;
import publish.db.dao.DaoFactory;
import publish.db.dao.mysql.ConnectionPool;
import publish.db.entity.Category;

import java.sql.Connection;
import java.util.List;

/**
 * Implementation service for manage category.
 * @author Burykin
 */
public class CategoryServiceImp implements CategoryService {
    private final CategoryDao categoryDao = DaoFactory.getInstance().getCategoryDao();

    public static Category getCategory (String name){
        return Category.createCategory(name);
    }

    @Override
    public List<Category> findAllCategories() throws DBException {
        Connection con = ConnectionPool.getInstance().getConnection();
        return categoryDao.findAllCategories(con);
    }
}
