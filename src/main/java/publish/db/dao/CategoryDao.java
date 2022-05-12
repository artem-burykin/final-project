package publish.db.dao;

import publish.db.entity.Category;

import java.sql.Connection;
import java.util.List;

/**
 * Interface with method, which realization in MysqlCategoryDao.
 * @author Byrukin
 */
public interface CategoryDao {
    List<Category> findAllCategories(Connection con) throws DBException;
}
