package bookmarks;

import app.domain.Tag;
import bookmarks.BlogBookmark;
import bookmarks.BookBookmark;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkTest {
// Abstract bookmark variables.

    Long id;
    String author;
    String title;
    String description;
    List<Tag> tags;

    // Unique bookmark variables.
    String ISBN;

    // For every type of bookmark but books
    String url;

    // Bookmarks.
    BlogBookmark blogB;
    BookBookmark bookB;
    OtherBookmark otherB;

    public BookmarkTest() {
        setUp();
    }

    public void setUp() {
        initializeAbstractClassVariables();
        initializeUniqueClassVariables();
        initializeBookmarks();
    }

    private void initializeAbstractClassVariables() {
        author = "TestAuthor";
        title = "TestTitle";
        description = "TestDescription";

        id = 0L;

        tags = new ArrayList<>();

        for (int i = 1; i < 4; i++) {
            tags.add(new Tag("tag" + i));
        }
    }

    public void initializeUniqueClassVariables() {
        ISBN = "12345678";
        url = "www.testUrl.com";
    }

    public void initializeBookmarks() {
        blogB = new BlogBookmark(title, url, tags, description);
        bookB = new BookBookmark(ISBN, author, title, tags, description);
        otherB = new OtherBookmark(title, url, tags, description);
    }

    // Tests:
    @Test
    public void BlogBookmarkTest() {
        String tagsStr = tags.get(0) + ", " + tags.get(1) + ", " + tags.get(2);

        String testString = "ID: " + id + "\n"
                + " Type: Blogpost" + "\n"
                + " Url: " + url + "\n"
                + " Title: " + title + "\n"
                + " Tags: " + tagsStr + "\n"
                + " Description: " + description + "\n"
                + " Created: " + blogB.getCreated() + "\n"
                + " Last edited: " + blogB.getUpdated();
        assertEquals(testString, blogB.toString());
    }

    @Test
    public void BookBookmarkTest() {
        String tagsStr = tags.get(0) + ", " + tags.get(1) + ", " + tags.get(2);

        String testString = "ID: " + id + "\n"
                + " Type: Book" + "\n"
                + " ISBN: " + ISBN + "\n"
                + " Author: " + author + "\n"
                + " Title: " + title + "\n"
                + " Tags: " + tagsStr + "\n"
                + " Description: " + description + "\n"
                + " Created: " + bookB.getCreated() + "\n"
                + " Last edited: " + bookB.getUpdated();
        assertEquals(testString, bookB.toString());
    }

    @Test
    public void OtherBookmarkTest() {
        String tagsStr = tags.get(0) + ", " + tags.get(1) + ", " + tags.get(2);
        
        String testString = "ID: " + otherB.getId() 
                + "\n Type: Other"
                + "\n Url: " + url 
                + "\n Title: " + title 
                + "\n Tags: " + tagsStr
                + "\n Description: " + description + "\n"
                + " Created: " + otherB.getCreated() + "\n"
                + " Last edited: " + otherB.getUpdated();
        assertEquals(otherB.toString(), testString);
    }

}
