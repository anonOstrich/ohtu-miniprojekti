package app.dao;

import app.domain.Tag;
import app.utilities.Utilities;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class TagDAO {

    private SessionFactory sessionFactory;

    /**
     * Initializes the class with the default database
     */
    public TagDAO() {
        this(Utilities.DEPLOYMENT_DATABASE);
    }

    /**
     * Initializes an instance with a SessionFactory
     *
     * @param configurationFileName Name of the database to be used in
     * connections
     */
    public TagDAO(String configurationFileName) {
        sessionFactory = new Configuration().configure(configurationFileName).buildSessionFactory();
    }

    /**
     * Closes the connection.
     */
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    /**
     *
     * @return List of all the tags
     */
    public List<Tag> getTagsOnDatabase() {
        List result;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            result = session.createQuery("from Tag").list();
            session.getTransaction().commit();
        }
        return (List<Tag>) result;
    }

    /**
     * Creates a session for database connection, then queries for the suitable
     * bookmark
     *
     * @param name
     * @return Tag
     */
    public Tag getTagWithName(String name) {
        Tag result;
        try (Session session = sessionFactory.openSession()) {
            result = getTagWithName(session, name);
        }
        return result;
    }

    /**
     * Using the parameter session, seeks ot return the bookmark with the
     * matching name
     *
     * @param session An open session that will be used to query
     * @param name Search term
     * @return The tag with the corresponding name, or null if such does not
     * exist.
     */
    public Tag getTagWithName(Session session, String name) {
        Query query = session.createQuery("from Tag where name = :name");
        query.setParameter("name", name.trim().toLowerCase());
        return (Tag) query.uniqueResult();
    }

    /**
     * Saves the given tag in the database.
     *
     * <p>
     * The tag will not be saved if there is already a tag with the same name in
     * the database</p>
     *
     * @param session Session for connections
     * @param tag To be saved
     * @return Tag that is saved in the database
     */
    public Tag saveTagToDatabase(Session session, Tag tag) {
        Tag existing = getTagWithName(session, tag.getName());
        if (existing != null) {
            return existing;
        }
        session.save(tag);
        return tag;
    }

    /**
     * Otherwise the same, but will create the session it needs for db
     * connections
     *
     * @param tag Tag to be saved
     */
    public void saveTagToDatabase(Tag tag) {
        try (Session session = sessionFactory.openSession()) {
            saveTagToDatabase(session, tag);
        }
    }

    /** 
     * Saves the tags to the database
     * 
     * <p>Uses the provided connection for database connections</p>
     * 
     * @param session Connectivity component
     * @param tags All the tags to be saved
     * @return All the tags with the same names as the provided tags, all of these
     * stored in the database
     */
    public List<Tag> saveTagsToDatabase(Session session, Collection<Tag> tags) {
        List<Tag> result = new ArrayList();
        for (Tag t : tags) {
            result.add(saveTagToDatabase(session, t));
        }
        return result;
    }

    /** 
     * Otherwise same as above, but the connection to the database is created and not provided 
     * as a parameter
     * 
     * @param tags 
     */
    public void saveTagsToDatabase(Collection<Tag> tags) {
        for (Tag t : tags) {
            saveTagToDatabase(t);
        }
    }

}
