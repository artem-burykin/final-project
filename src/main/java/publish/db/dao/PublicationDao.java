package publish.db.dao;

import publish.db.entity.Publication;

import java.util.List;

public interface PublicationDao {
    boolean insertPublication(Publication publication) throws DBException;
    List<Publication> findPublicationsByProductId(int product_id) throws DBException;
    void updatePublicationName(String name, int id) throws DBException;
    void updatePublicationContent(String content, String name) throws DBException;
}
