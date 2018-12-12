package app.dao;

import bookmarks.Bookmark;

import app.domain.Tag;
import app.utilities.Utilities;
import bookmarks.OtherBookmark;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
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
     * Initializes the class with a SessionFactory.
     */
    public BookMarkDAO() {
        this(Utilities.DEPLOYMENT_DATABASE);
    }

    public BookMarkDAO(String configurationFileName) {
        sessionFactory = new Configuration().configure(configurationFileName).buildSessionFactory();
        // test database probably needs no legitimate-looking initialization data
        if (Utilities.DEPLOYMENT_DATABASE.equals(configurationFileName) && databaseIsEmpty()) {
            initializeDatabase();
        }

        tagDAO = new TagDAO(configurationFileName);
    }

    /**
     * Closes the connection.
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
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        // concern: can an user influence the query? Injection danger?
        List result = session.createQuery(query).list();
        session.getTransaction().commit();
        session.close();
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
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(bookmark);
        // save tags and stuff

        bookmark.setTags(tagDAO.saveTagsToDatabase(session, bookmark.getTags()));

        session.getTransaction().commit();
        session.close();
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
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(Bookmark.class);
        cr.add(Restrictions.like(field, "%" + search + "%"));
        List result = cr.list();
        session.getTransaction().commit();
        session.close();
        return (List<Bookmark>) result;
    }

    /**
     * Method that returns the info of a single database entry in String form.
     *
     * @param id id of the database entity
     * @return bookmark
     */
    public Bookmark getSingleBookmarkInfo(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Bookmark bookmark;
        bookmark = (Bookmark) session.createQuery("from Bookmark where id = " + id).uniqueResult();
        session.close();
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
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Bookmark bookmark;
        try {
            bookmark = (Bookmark) session.createQuery("from Bookmark where id = " + id).uniqueResult();
        } catch (Exception e) {
            System.out.println("Bookmark not found");
            return false;
        }
        if (bookmark == null) {
            return false;
        }
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
                bookmark.setUrl(newEntry);
                break;
            case ("tags"):
                bookmark.setTags(tagDAO.saveTagsToDatabase(session, taglist));
                break;
            default:
                return false;
        }
        updateInformation(session, bookmark);
        session.close();
        return true;
    }

    public void updateInformation(Session session, Bookmark bookmark) {
        session.evict(bookmark);
        session.update(bookmark);
        session.getTransaction().commit();
        System.out.println("The entry has been updated!");
    }

    /**
     * bookmark from database by bookmark-id.
     *
     * @param bookmark_id
     * @return success, true if bookmark was deleted, false if not
     */
    public boolean deleteBookmarkFromDatabase(Long bookmark_id) {
        Session session = sessionFactory.openSession();
        Bookmark bookmark;
        try {
            bookmark = (Bookmark) session.createQuery("from Bookmark where id = " + bookmark_id).uniqueResult();
        } catch (Exception e) {
            System.out.println("Bookmark not found");
            return false;
        }
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

    private boolean databaseIsEmpty() {
        return getBookMarksOnDatabase().isEmpty();
    }

    private void initializeDatabase() {
        String objects = "";

        // classloader and resource as stream are a cumbersome solution, 
        // but the only one I managed to make work in every situation (also running as a .jar)
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
