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
import java.util.ArrayList;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TextUITest {

    private TextUI ui;
    private InputStream in;
    private StubIO io;
    private List<String> inputLines = new ArrayList();

    @Before
    public void setUp() {
        this.inputLines = new ArrayList<>();
    }

    private void initializeBookmarkData() {
        this.inputLines.add("B");
        this.inputLines.add("testAuthor");
        this.inputLines.add("testTitle");
        
        this.inputLines.add("testTag1, testTag2");
        this.inputLines.add("testDescription");
        this.inputLines.add("1234");


    }

    private void initializeOtherBookmarkData() {
        this.inputLines.add("O");
        this.inputLines.add("www.otherurl.com");
        this.inputLines.add("otherTitle");
        this.inputLines.add("otherTag");
        this.inputLines.add("Description for a peculiar bookmark");
    }

    private void initializeBlogBookmarkData() {
        this.inputLines.add("BG");
        this.inputLines.add("www.testurl.com");
        this.inputLines.add("testTitle");
        this.inputLines.add("testTag");
        this.inputLines.add("Description for a peculiar bookmark");
    }

    private void initializeEdit() {
        this.inputLines.add("T");
        this.inputLines.add("U");
        this.inputLines.add("D");
        this.inputLines.add("X");
        this.inputLines.add("P");
        this.inputLines.add("A");
        this.inputLines.add("I");
    }

    private void initializeSearch() {
        inputLines.add("T");
        inputLines.add("U");
        inputLines.add("D");
        inputLines.add("A");
        inputLines.add("I");
        inputLines.add("TA");
        inputLines.add("Y");
        inputLines.add("P");
    }

    @Test
    public void textUIcreatesBookBookmarks() {
        initializeBookmarkData();
        this.io = new StubIO(inputLines);
        ui = new TextUI(io);
        Bookmark bookmark = ui.askForBookmark();
        assertEquals(bookmark.getDescription(), "testDescription");
        assertEquals(bookmark.getTitle(), "testTitle");
    }

    @Test
    public void textUIcreatesBlogBookmarks() {
        initializeBlogBookmarkData();
        io = new StubIO(inputLines);
        ui = new TextUI(io);
        Bookmark bookmark = ui.askForBookmark();
        BlogBookmark blog = (BlogBookmark) bookmark;
        assertEquals("www.testurl.com", blog.getUrl());
        assertEquals(bookmark.getTitle(), "testTitle");
    }

    @Test
    public void textUICreatesOtherBookmarks() {
        initializeOtherBookmarkData();
        io = new StubIO(inputLines);
        ui = new TextUI(io);
        Bookmark bookmark = ui.askForBookmark();
        assertTrue(bookmark.toString().contains("Type: Other"));
        OtherBookmark other = (OtherBookmark) bookmark;
        assertEquals("www.otherurl.com", other.getUrl());
        assertEquals("otherTitle", bookmark.getTitle());
        assertEquals("Description for a peculiar bookmark", bookmark.getDescription());
    }

    @Test
    public void invalidInputCreatesNoBookMark() {
        inputLines.add("X");
        io = new StubIO(inputLines);
        ui = new TextUI(io);
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
    public void editBookmarkWorksOnDifferentFields() {
        initializeEdit();
        io = new StubIO(inputLines);
        ui = new TextUI(io);

        Bookmark bookBookmark = new BookBookmark("", "", "", new ArrayList<Tag>(), "");
        Bookmark blogBookmark = new BlogBookmark("", "", new ArrayList<Tag>(), "");
        Bookmark otherBookmark = new OtherBookmark("", "", new ArrayList<Tag>(), "");
        assertEquals("title", ui.askForEditField(bookBookmark));
        assertEquals("url", ui.askForEditField(otherBookmark));
        assertEquals("description", ui.askForEditField(blogBookmark));
        assertEquals("tags", ui.askForEditField(bookBookmark));
        assertEquals("", ui.askForEditField(null));
        assertEquals("author", ui.askForEditField(bookBookmark));
        assertEquals("ISBN", ui.askForEditField(bookBookmark));
    }

    @Test
    public void searchFieldpromptGivesCorrectStrings() {
        initializeSearch();
        io = new StubIO(inputLines);
        ui = new TextUI(io);
        assertEquals(ui.askForField(), "title");
        assertEquals(ui.askForField(), "url");
        assertEquals(ui.askForField(), "description");
        assertEquals(ui.askForField(), "author");
        assertEquals(ui.askForField(), "ISBN");
        assertEquals(ui.askForField(), "tag");
        assertEquals(ui.askForField(), "all");
        assertEquals(ui.askForField(), "");
    }

}
