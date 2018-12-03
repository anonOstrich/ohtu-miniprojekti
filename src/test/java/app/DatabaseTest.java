package app;

import app.dao.BookMarkDAO;
import app.domain.Tag;
import app.utilities.Utilities;
import bookmarks.BlogBookmark;
import bookmarks.BookBookmark;
import bookmarks.Bookmark;
import bookmarks.OtherBookmark;
import static org.junit.Assert.*;
import org.junit.*;

import java.util.*;
import javax.transaction.Transactional;


@Transactional
public class DatabaseTest {

    private BookMarkDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new BookMarkDAO(Utilities.TEST_DATABASE);
    }

    @After
    public void tearDown() throws Exception {
        dao.close();
    }

    @Transactional
    @Test
    public void blogBookmarksDontCauseCrashing() {
        // Just tests that this doesn't crash
        dao.saveBookmarkToDatabase(new BlogBookmark());
        assertNotNull(dao.getBookMarksOnDatabase());
    }

    @Transactional
    @Test
    public void bookBookmarksDontCauseCrashing() {
        dao.saveBookmarkToDatabase(new BookBookmark());
        assertNotNull(dao.getBookMarksOnDatabase());
    }
    
    @Transactional 
    @Test
    public void otherBookmarksDontCauseCrashing(){
        dao.saveBookmarkToDatabase(new OtherBookmark());
        assertNotNull(dao.getBookMarksOnDatabase());
    }

    @Transactional
    @Test
    public void searchMethodWorks() {
        dao.saveBookmarkToDatabase(new BookBookmark("simeon", "book", "", new ArrayList<Tag>(),  "", ""));
        assertNotNull(dao.searchField("author", "simeon"));
    }

    @Transactional
    @Test
    public void userCanEditAnEntry() {
        dao.saveBookmarkToDatabase(new BookBookmark());
        long bookmarkID = dao.getBookMarksOnDatabase().get(dao.getBookMarksOnDatabase().size() - 1).getId();
        dao.editEntry(bookmarkID, "author", "testAuthor");
        dao.editEntry(bookmarkID, "title", "testTitle");
        assertTrue(dao.getSingleBookmarkInfo(bookmarkID).contains("testAuthor"));
        assertTrue(dao.getSingleBookmarkInfo(bookmarkID).contains("testTitle"));
    }

    @Transactional
    @Test
    public void deleteMethodRemovesCorrectBookmark() {
        Bookmark bm = new BookBookmark();
        dao.saveBookmarkToDatabase(bm);
        long id = bm.getId();
        dao.deleteBookmarkFromDatabase(id);
        assertTrue(dao.getBookMarksOnDatabase().isEmpty());
    }

    @Transactional
    @Test
    public void deleteMethodRemovesOnlySpecifiedBookmark() {
        Bookmark bm1 = new BookBookmark();
        Bookmark bm2 = new BookBookmark();
        dao.saveBookmarkToDatabase(bm1);
        dao.saveBookmarkToDatabase(bm2);
        long id1 = bm1.getId();
        long id2 = bm2.getId();
        dao.deleteBookmarkFromDatabase(id1);
        List<Bookmark> bookmarks = dao.getBookMarksOnDatabase();
        assertFalse(bookmarks.stream().anyMatch(bm -> id1 == bm.getId()));
        assertTrue(bookmarks.stream().anyMatch(bm -> id2 == bm.getId()));
    }

    @Transactional
    @Test
    public void deleteRemovesNothingWithWrongId() {
        Bookmark bm = new BookBookmark();
        dao.saveBookmarkToDatabase(bm);
        long false_id = 666;  
        dao.deleteBookmarkFromDatabase(false_id);
        assertEquals(1, dao.getBookMarksOnDatabase().size());
    }

}
