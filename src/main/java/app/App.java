package app;

import app.dao.BookMarkDAO;
import app.domain.Tag;
import app.io.ConsoleIO;
import app.io.IO;
import app.ui.TextUI;
import bookmarks.Bookmark;
import java.util.ArrayList;

import java.util.List;

public class App {

    private final TextUI ui;
    private IO io;
    private BookMarkDAO dao;

    public App(IO io) {
        this(io, new BookMarkDAO());
    }

    public App(IO io, BookMarkDAO dao) {
        this.io = io;
        this.ui = new TextUI(io);
        this.dao = dao;
    }

    public void run() {
        ui.printWelcomeMessage();
        boolean run = true;
        String command = "";
        String newEntry = "";
        int safety = 0;
        //TODO: hide this to an other class
        while (run) {
            command = ui.getMenuCommand();

            if (command.equals("1") || command.equals("new")) {
                Bookmark bookmark = ui.askForBookmark();
                if (bookmark != null) {
                    dao.saveBookmarkToDatabase(bookmark);
                }
            } else if (command.equals("2") || command.equals("list")) {
                String method = ui.askForListingMethod();
                if (method.equals("LT")) {
                    ui.presentTags(dao.getTagDAO().getTagsOnDatabase());
                } else {
                    ui.printBookmarkList(dao.getBookmarksInOrder(method));
                }
            } else if (command.equals("3") || command.equals("search")) {
                String searchfield = ui.askForField();
                String search;
                if (searchfield.equals("tag")) {
                    List<Tag> tags = dao.getTagDAO().getTagsOnDatabase();
                    ui.listTags(tags);
                    search = ui.askForTag();
                    boolean found = false;
                    for (Tag tag : tags) {
                        if (tag.getName().equals(search)) {
                            ui.printBookmarkList(new ArrayList(tag.getBookmarks()));
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        ui.printTagsNotFound();
                    }
                } else {
                    search = ui.askForSearch();
                    ui.printBookmarkList(dao.searchField(searchfield, search));
                }
            } else if (command.equals("4") || command.equals("edit")) {
                List<Bookmark> bookmarks = dao.getBookMarksOnDatabase();
                Long editID = ui.askForBookmarkToEdit(bookmarks);
                String editfield = ui.askForEditField(dao.getSingleBookmarkInfo(editID));
                if(editfield.isEmpty()){
                    io.println("Not a valid ID");
                    continue;
                }
                List<Tag> tagList = null;
                io.println("\nOld values: ");
                io.println(dao.getSingleBookmarkInfo(editID));
                if (editfield.equals("tags")) {
                    tagList = ui.askForTags();
                } else {
                    newEntry = ui.askForNewField(editfield);
                }

                if (dao.editEntry(editID, editfield, newEntry, tagList)) {
                    ui.viewBookmarkEditedMessage();
                }

            } else if (command.equals("5") || command.equals("delete")) {
                List<Bookmark> bookmarks = dao.getBookMarksOnDatabase();
                Long bookmark_id = ui.askForBookmarkToDelete(bookmarks);
                if (bookmark_id != null) {
                    if (dao.deleteBookmarkFromDatabase(bookmark_id)) {
                        ui.viewBookmarkDeletedMessage();
                    }
                }
            } else if (command.equals("0") || command.equals("exit")) {
                ui.printGoodbyeMessage();
                run = false;
            } else {
                ui.printUnrecognizedOption();
                if (safety++ > 100) {
                    run = false;
                }
            }
        }
    }

    public void close() {
        this.dao.close();
    }

    public static void main(String[] args) {
        App app = new App(new ConsoleIO());
        app.run();
        app.close();
    }
}
