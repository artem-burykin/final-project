package publish.db.dao.mysql;

import publish.db.dao.DBException;
import publish.db.dao.PublicationDao;
import publish.db.entity.Product;
import publish.db.entity.Publication;
import publish.service.ProductServiceImpl;
import publish.service.PublicationService;
import publish.service.PublicationServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlPublicationDao implements PublicationDao {

    MysqlPublicationDao(){}

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
     * Insert publication to db.
     * @param publication publication, which we needed to insert.
     * @return boolean value(true, if exist).
     * @throws DBException
     */
    public boolean insertPublication(Publication publication) throws DBException {
        Connection con = null;
        try{
            con = ConnectionPool.getInstance().getConnection();
            insertPublication(con, publication);
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
     * Supporting method, which help us to insert the publication.
     * @param con connection with db.
     * @param publication publication, which we needed to insert.
     * @throws SQLException
     */
    private void insertPublication(Connection con, Publication publication) throws SQLException {
        try (PreparedStatement st = con.prepareStatement(DBConstant.INSERT_PUBLICATION, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            st.setInt(++i, publication.getProduct_id());
            st.setString(++i, publication.getName());
            st.setString(++i, publication.getContent());
            st.setDate(++i, publication.getCreate_date());
            int c = st.executeUpdate();
            if (c > 0) {
                try (ResultSet keys = st.getGeneratedKeys()) {
                    if (keys.next()) {
                        publication.setId(keys.getInt(1));
                    }
                }
            }
        }
    }

    /**
     * Find publications by product id.
     * @param product_id id, by which we search publications.
     * @return list of publication with those product_id.
     * @throws DBException
     */
    public List<Publication> findPublicationsByProductId(int product_id) throws DBException{
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.FIND_PUBLICATION_BY_PRODUCT_ID)) {
            con.setAutoCommit(false);
            int i = 0;
            stmt.setInt(++i, product_id);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Publication> result = new ArrayList<>();
                while (rs.next()) {
                    Publication publication = PublicationServiceImpl.getPublication(rs.getInt(DBConstant.F_PUBLICATION_PRODUCT_ID), rs.getString(DBConstant.F_PUBLICATION_NAME), rs.getString(DBConstant.F_PUBLICATION_CONTENT));
                    publication.setId(rs.getInt(DBConstant.F_PUBLICATION_ID));
                    publication.setCreate_date(rs.getDate(DBConstant.F_PUBLICATION_CREATE_DATE));
                    result.add(publication);
                }
                return result;
                }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("No publication was found", e);
        }
    }

    /**
     * Changes certain publication's name.
     * @param name new name for publication.
     * @param id publication's id, which name we need to change.
     * @throws DBException
     */
    public void updatePublicationName(String name, int id) throws DBException {
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.UPDATE_PUBLICATION_NAME)) {
            int i = 0;
            stmt.setString(++i, name);
            stmt.setInt(++i, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("This publication not found", e);
        }
    }

    /**
     * Changes certain publication's content.
     * @param content new content for publication.
     * @param name publication's name, which content we need to change.
     * @throws DBException
     */
    public void updatePublicationContent(String content, String name) throws DBException{
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.UPDATE_PUBLICATION_CONTENT)) {
            int i = 0;
            stmt.setString(++i, content);
            stmt.setString(++i, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("This publication not found", e);
        }
    }
}
