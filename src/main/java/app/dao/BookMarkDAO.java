package app.dao;

import bookmarks.Bookmark;

import app.domain.Tag;
import app.utilities.Utilities;
import app.utilities.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

/**
 * Class that handles all database operations.
 *
 * @author jussiste
 */
public class BookMarkDAO {

    private SessionFactory sessionFactory;
    private TagDAO tagDAO;

    public final String titleOrderQuery = "FROM Bookmark b ORDER BY b.title ASC";
    public final String creationOrderQueryDESC = "FROM Bookmark b ORDER BY b.created DESC";
    public final String creationOrderQueryASC = "FROM Bookmark b ORDER BY b.created ASC";

    /**
     * Initializes the class with the default databse
     */
    public BookMarkDAO() {
        this(Utilities.DEPLOYMENT_DATABASE);
    }

    /**
     *
     * Initializes the class with a SessionFactory
     *
     * <p>
     * The factory will be able to provide connections to the database specified
     * by the given name. TagDAO is also initialized, and it will connect to the
     * same database.</p>
     *
     * @param configurationFileName Name of the database to be used. Different
     * one is used for tests
     */
    public BookMarkDAO(String configurationFileName) {
        sessionFactory = new Configuration().configure(configurationFileName).buildSessionFactory();
        // test database probably needs no legitimate-looking initialization data
        if (Utilities.DEPLOYMENT_DATABASE.equals(configurationFileName) && databaseIsEmpty()) {
            initializeDatabase();
        }

        tagDAO = new TagDAO(configurationFileName);
    }

    /**
     * Closes the connection and asks tagDAO to do the same
     */
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
        tagDAO.close();
    }

    /**
     * Returns all the bookmarks in the database.
     *
     * @return list of Bookmarks
     */
    public List<Bookmark> getBookMarksOnDatabase() {
        return getBookmarksWithQuery("from Bookmark");
    }

    /**
     * Returns bookmarks defined by given hql query.
     *
     * @param query hql query for fetching bookmarks
     * @return list of Bookmarks
     */
    public List<Bookmark> getBookmarksWithQuery(String query) {
        List result;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            // concern: can an user influence the query? Injection danger?
            result = session.createQuery(query).list();
            session.getTransaction().commit();
        }
        return (List<Bookmark>) result;
    }

    /**
     * Returns bookmarks listed in an order defined by variable method.
     *
     * @param method Bookmark listing method key.
     * @return list of Bookmarks
     */
    public List<Bookmark> getBookmarksInOrder(String method) {
        switch (method) {
            case ("T"):
                return getBookmarksWithQuery(titleOrderQuery);
            case ("CD"):
                return getBookmarksWithQuery(creationOrderQueryDESC);
            case ("CA"):
                return getBookmarksWithQuery(creationOrderQueryASC);
            default:
                return getBookMarksOnDatabase();
        }
    }

    /**
     * Saves a bookmark to the database.
     *
     * @param bookmark bookmark to be saved
     */
    public void saveBookmarkToDatabase(Bookmark bookmark) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(bookmark);
            bookmark.setTags(tagDAO.saveTagsToDatabase(session, bookmark.getTags()));
            session.getTransaction().commit();
        }
    }

    /**
     * Used to search a specific field in the database using a search term. The
     * term doesn't need to be an exact match, since it uses the like-operator.
     *
     * @param field field to be searched
     * @param search term used to search
     * @return list of bookmarks. If either of the parameters are invalid i.e
     * empty, this method returns an empty list.
     */
    public List<Bookmark> searchField(String field, String search) {
        if (field.equals("") || search.equals("")) {
            return new ArrayList<Bookmark>();
        }
        List result;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Criteria cr = session.createCriteria(Bookmark.class);
            cr.add(Restrictions.like(field, "%" + search + "%").ignoreCase());
            result = cr.list();
            session.getTransaction().commit();
        }
        return (List<Bookmark>) result;
    }

    /**
     * Used to search all fields in the database using a search term. The term
     * doesn't need to be an exact match, since it uses the like-operator.
     *
     * @param search term used to search
     * @return list of bookmarks. If either of the parameters are invalid i.e
     * empty, this method returns an empty list.
     */
    public List<Bookmark> searchAll(String search) {
        if (search.equals("")) {
            return new ArrayList<Bookmark>();
        }
        List result;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Criteria cr = session.createCriteria(Bookmark.class);
            Disjunction dis = Restrictions.disjunction();
            dis.add(Restrictions.like("author", "%" + search + "%").ignoreCase());
            dis.add(Restrictions.like("title", "%" + search + "%").ignoreCase());
            dis.add(Restrictions.like("description", "%" + search + "%").ignoreCase());
            dis.add(Restrictions.like("url", "%" + search + "%").ignoreCase());
            dis.add(Restrictions.like("ISBN", "%" + search + "%").ignoreCase());
            cr.add(dis);
            result = cr.list();
            session.getTransaction().commit();
        }
        return (List<Bookmark>) result;
    }

    /**
     * Returns the info of a single database entry
     *
     * @param id id of the database entity
     * @return bookmark
     */
    public Bookmark getSingleBookmarkInfo(Long id) {
        Bookmark bookmark;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Bookmark where id = :id").setParameter("id", id);
            bookmark = (Bookmark) query.uniqueResult();
        }
        return bookmark;
    }

    /**
     * Method that allows to edit either the author or the title field of a
     * given entry.
     *
     * @param id id of entry
     * @param field field to be edited
     * @param newEntry new data
     * @return success, true if bookmark was edited, false if not
     */
    public boolean editEntry(Long id, String field, String newEntry, List<Tag> taglist) {
        Bookmark bookmark;
        try {
            bookmark = (Bookmark) getSingleBookmarkInfo(id);
        } catch (Exception e) {
            return false;
        }
        if (!Validator.validName(newEntry) && taglist.isEmpty()) {
            return false;
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        switch (field) {
            case ("author"):
                bookmark.updateAttribute("author", newEntry);
                break;
            case ("title"):
                bookmark.setTitle(newEntry);
                break;
            case ("description"):
                bookmark.setDescription(newEntry);
                break;
            case ("url"):
                if (!Validator.validUrl(newEntry)) {
                    session.close();
                    return false;
                }
                bookmark.setUrl(newEntry);
                break;
            case ("ISBN"):
                if (!Validator.validISBN(newEntry)) {
                    session.close();
                    return false;
                }
                bookmark.setISBN(newEntry);
                break;
            case ("tags"):
                bookmark.setTags(tagDAO.saveTagsToDatabase(session, taglist));
                break;
            default:
                session.close();
                return false;
        }
        updateInformation(session, bookmark);
        session.close();
        return true;
    }

    /**
     * Updates the information of the bookmark in the database
     *
     * @param session
     * @param bookmark
     */
    public void updateInformation(Session session, Bookmark bookmark) {
        session.evict(bookmark);
        session.update(bookmark);
        session.getTransaction().commit();
        System.out.println("The entry has been updated!");
    }

    /**
     * Deletes bookmark from database by bookmark id.
     *
     * @param bookmark_id
     * @return true if bookmark was deleted, false if not
     */
    public boolean deleteBookmarkFromDatabase(Long bookmark_id) {
        Bookmark bookmark;
        try {
            bookmark = getSingleBookmarkInfo(bookmark_id);
        } catch (Exception e) {
            System.out.println("Bookmark not found");
            return false;
        }
        Session session = sessionFactory.openSession();

        if (bookmark != null) {
            session.beginTransaction();
            session.delete(session.load(Bookmark.class, bookmark_id));

            session.getTransaction().commit();
            return true;
        }
        return false;
    }

    public TagDAO getTagDAO() {
        return tagDAO;
    }

    /**
     * 
     * @return True if no bookmarks in database
     */
    private boolean databaseIsEmpty() {
        return getBookMarksOnDatabase().isEmpty();
    }

    /**
     * Fills initials bookmarks, if the database is empty
     * 
     * <p>Reads the insert statements from the file initial.sql, which is
     * located either in the resoures folder or in the same .jar package
     * as the program.</p>
     * 
     */
    private void initializeDatabase() {
        String objects = "";
        ClassLoader cl = getClass().getClassLoader();
        try (Scanner s = new Scanner(cl.getResourceAsStream("initial.sql"))) {
            while (s.hasNext()) {
                objects += s.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query q = session.createNativeQuery(objects);
            q.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
