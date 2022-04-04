package publish.service;

import publish.db.dao.DBException;
import publish.db.dao.DaoFactory;
import publish.db.dao.PublicationDao;
import publish.db.entity.Publication;

import java.util.List;

public class PublicationServiceImpl implements PublicationService{
    private final PublicationDao publicationDao = DaoFactory.getInstance().getPublicationDao();

    public static Publication getPublication(int product_id, String name, String content){
        return Publication.createdPublication(product_id, name, content);
    }


    @Override
    public boolean insertPublication(Publication publication) throws DBException {
        return publicationDao.insertPublication(publication);
    }

    @Override
    public List<Publication> findPublicationsByProductId(int product_id) throws DBException {
        return publicationDao.findPublicationsByProductId(product_id);
    }

    @Override
    public void updatePublicationName(String name, int id) throws DBException {
        publicationDao.updatePublicationName(name, id);
    }

    @Override
    public void updatePublicationContent(String content, String name) throws DBException {
        publicationDao.updatePublicationContent(content, name);
    }
}
