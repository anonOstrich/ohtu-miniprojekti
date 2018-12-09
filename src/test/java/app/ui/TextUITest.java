package app.ui;

import app.io.ConsoleIO;
import app.io.StubIO;

import bookmarks.Bookmark;
import bookmarks.BlogBookmark;
import bookmarks.BookBookmark;
import bookmarks.OtherBookmark;
import app.domain.Tag;
import app.io.IO;

import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Scanner;

public class TextUITest {

    private Scanner scanner;
    private TextUI ui;
    private InputStream in;

    @Before
    public void setUp() {

    }

    @Test
    public void textUIcreatesBookBookmarks() throws FileNotFoundException {
        File file = null;
        try {
            file = new File("src/test/resources/app/testinput/bookmark.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner = new Scanner(file);

        ui = new TextUI(new ConsoleIO(scanner));
        Bookmark bookmark = ui.askForBookmark();

        assertEquals(bookmark.getDescription(), "testDescription");
        assertEquals(bookmark.getTitle(), "testTitle");
    }

    @Test
    public void textUIcreatesBlogBookmarks() throws FileNotFoundException {
        File file = null;
        try {
            file = new File("src/test/resources/app/testinput/blogbookmark.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner = new Scanner(file);
        ui = new TextUI(new ConsoleIO(scanner));
        Bookmark bookmark = ui.askForBookmark();
        try {
            BlogBookmark blog = (BlogBookmark) bookmark;
            assertEquals("testUrl", blog.getUrl());
        } catch (Exception e) {
            assertTrue(false);
        }
        assertEquals(bookmark.getTitle(), "testTitle");
    }

    @Test
    public void textUICreatesOtherBookmarks() throws FileNotFoundException {
        File file = null;
        try {
            file = new File("src/test/resources/app/testinput/otherbookmark.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        scanner = new Scanner(file);
        ui = new TextUI(new ConsoleIO(scanner));
        Bookmark bookmark = ui.askForBookmark();
        assertTrue(bookmark.toString().contains("Type: Other"));

        try {
            OtherBookmark other = (OtherBookmark) bookmark;
            assertEquals("otherUrl", other.getUrl());
        } catch (Exception e) {
            assertTrue(false);
        }
        assertEquals("otherTitle", bookmark.getTitle());
        assertEquals("Description for a peculiar bookmark", bookmark.getDescription());
    }

    @Test
    public void invalidInputCreatesNoBookMark() throws FileNotFoundException {
        File file = null;
        try {
            file = new File("src/test/resources/app/testinput/invalid.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner = new Scanner(file);
        ui = new TextUI(new ConsoleIO(scanner));
        Bookmark bookmark = ui.askForBookmark();
        assertNull(bookmark);
    }

    @Test
    public void askForTags() {
        IO io = new StubIO("a,b,c,d");
        TextUI ui = new TextUI(io);
        List<Tag> tags = ui.askForTags();
        assertEquals(tags.get(0).getName(), "a");
        assertEquals(tags.get(1).getName(), "b");
        assertEquals(tags.get(2).getName(), "c");
        assertEquals(tags.get(3).getName(), "d");
    }

    @Test
    public void askForTagsHandlesSpaces() {
        StubIO io = new StubIO("a, b, c, d ");
        TextUI ui = new TextUI(io);
        List<Tag> tags = ui.askForTags();
        assertEquals(tags.get(0).getName(), "a");
        assertEquals(tags.get(1).getName(), "b");
        assertEquals(tags.get(2).getName(), "c");
        assertEquals(tags.get(3).getName(), "d");
    }

    @Test
    public void editBookmarkWorksOnDifferentFields() throws FileNotFoundException {
        File file = null;
        try {
            file = new File("src/test/resources/app/testinput/editfield.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        scanner = new Scanner(file);
        ui = new TextUI(new ConsoleIO(scanner));
        String bookmark = "id title x Book";
        String other = "id title x Other";
        String blog = "id title x Blog";
        assertEquals("title", ui.askForEditField(bookmark));
        assertEquals("url", ui.askForEditField(other));
        assertEquals("description", ui.askForEditField(blog));
        assertEquals("tags", ui.askForEditField(bookmark));
        assertEquals("", ui.askForField());
        assertEquals("author", ui.askForEditField(bookmark));
    }
}
