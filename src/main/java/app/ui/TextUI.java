package app.ui;

import app.domain.Tag;
import app.io.IO;
import bookmarks.Bookmark;
import bookmarks.BookBookmark;
import bookmarks.BlogBookmark;
import bookmarks.OtherBookmark;
import java.util.*;

public class TextUI {

    private final IO io;
    private final String defaultSuccessMessage = "Your bookmark has been read!";
    private final HashMap<String,String> selectedEditFieldMap = new HashMap<>();
    private final HashMap<String,String> bookmarkTypeToAskEditFieldPrintMap = new HashMap<>();

    public TextUI(IO io) {
        this.io = io;
        initSelectedEditFieldMap();
        initBookmarkTypeToAskEditFieldPrintMap();
    }

    public void printWelcomeMessage() {
        io.println("Welcome!");
    }

    public void printGoodbyeMessage() {
        io.println("Goodbye");
    }

    public void printUnrecognizedOption() {
        io.println("Unrecognized option");
    }

    public String getMenuCommand() {
        io.print("\nSelect one of the following options\n1. ");
        io.redPrint("Add");
        io.print(" a new bookmark \n2. ");
        io.redPrint("List");
        io.print(" all existing bookmarks, or tags\n3. ");
        io.redPrint("Search");
        io.print(" for bookmarks\n4. ");
        io.redPrint("Edit");
        io.print(" a bookmark\n5. ");
        io.redPrint("Delete");
        io.print(" a bookmark\n0. ");
        io.redPrint("Exit");
        io.println(" the application");
        return io.nextLine();
    }

    public String askForField() {
        io.println("Which field would you like to search?");
        io.println("Give field (T = Title) (D = Description) (TA = Tag)");
        String command = io.nextLine();
        switch (command) {
            case ("T"):
                return "title";
            case ("D"):
                return "description";
            case ("TA"):
                return "tag";
            default:
                return "";
        }
    }

    public String askForSearch() {
        io.println("Give a search term: ");
        return io.nextLine();
    }

    public Bookmark askForBookmark() {
        io.println("Add a new bookmark.");
        io.println("Give type (B = Book) (BG = Blog), (O = Other): ");
        String type = io.nextLine();

        Bookmark bookmark = null;

        if (type.equals("B")) {
            bookmark = askForBookBookmarkInfo();
        } else if (type.equals("BG")) {
            bookmark = askForBlogBookmarkInfo();
        } else if (type.equals("O")) {
            bookmark = askForOtherBookmarkInfo();
        }

        if (bookmark != null) {
            io.println(defaultSuccessMessage);
            return bookmark;
        }

        io.println("Invalid choice");
        return null;

    }

    public String askForListingMethod() {
        io.println("Choose listing method:");
        io.println("(T = Title)(CD = Creation time Descending)(CA = Creation time Ascending)");
        io.println("(LT = List Tags)");
        return io.nextLine();
    }

    public void printBookmarkList(List<Bookmark> bookmarks) {
        io.println("");
        if (bookmarks.isEmpty()) {
            io.println("No bookmarks found.");
        }
        for (Bookmark bmark : bookmarks) {
            bmark.printInfo(io);
            io.println("==================================================");
        }
    }
    
    public void presentTags(List<Tag> tags) {
        io.println("");
        io.println("Tags:");
        io.println("----------------------------------------------");
        if (tags.isEmpty()) {
            io.println("No tags found.");
        }
        for (int i = 0; i < tags.size(); i++) {
            Tag tag = tags.get(i);
            Set<Bookmark> bmarks = tag.getBookmarks();
            io.println("name: " + tag + "\n" + "bookmarks tagged:");
            for (Bookmark bmark : bmarks) {
                io.println(bmark.shortPrint());
            }
            io.println("Total amount of bookmarks tagged: " + bmarks.size());
            io.println("==============================================");
        }
    }
    
    public void listTags(List<Tag> tags) {
        io.println("");
        io.println("Tags:");
        io.println("");
        for (Tag tag : tags) {
            io.println(tag.toString());
        }
    }
    
    public String askForTag() {
        io.println("");
        io.println("Enter name of the tag to list bookmarks associated with it:");
        return io.nextLine();
    }
    
    public void printTagsNotFound() {
        io.println("No tags with that title were found.");
    }

    private Bookmark askForOtherBookmarkInfo() {
        OtherBookmark bm = new OtherBookmark();
        String url = askForInput("Url: ");
        bm.setUrl(url);
        askForGeneralBookmarkInfo(bm);
        return bm;
    }

    private Bookmark askForBookBookmarkInfo() {
        BookBookmark bm = new BookBookmark();
        String isbn = askForInput("ISBN: ");
        bm.setISBN(isbn);

        String title = askForInput("Title: ");
        bm.setTitle(title);

        String author = askForInput("Author: ");
        bm.setAuthor(author);

        List<Tag> tagsList = askForTags();
        bm.setTags(tagsList);

        String description = askForInput("Description: ");
        bm.setDescription(description);

        return bm;
    }

    private Bookmark askForBlogBookmarkInfo() {
        BlogBookmark bm = new BlogBookmark();
        String url = askForInput("Url: ");
        bm.setUrl(url);
        askForGeneralBookmarkInfo(bm);
        return bm;
    }

    private Bookmark askForGeneralBookmarkInfo(Bookmark bookmark) {
        String title = askForInput("Title: ");
        bookmark.setTitle(title);

        List<Tag> tagsList = askForTags();
        bookmark.setTags(tagsList);

        String description = askForInput("Description: ");
        bookmark.setDescription(description);

        return bookmark;
    }

    public List<Tag> askForTags() {
        System.out.println("Tags (separated by commas): ");
        String input = io.nextLine();
        String[] tags = input.split(",");
        List<Tag> result = new ArrayList();
        for (int i = 0; i < tags.length; i++) {
            // pitäisikö tässä vaiheessa katsoa, että ei lisätä uutta tagia jos samanniminen on?
            result.add(new Tag(tags[i].trim()));
        }

        return result;
    }

    private void shortListBookmarks(List<Bookmark> bookmarks) {
        for (Bookmark bookmark : bookmarks) {
            bookmark.printShortInfo(io);
        }
    }

    private Long getInt() {
        Long value;
        try {
            value = Long.parseLong(io.nextLine());
        } catch (NumberFormatException e) {
            return 0L;
        }
        return value;
    }

    public Long askForBookmarkToEdit(List<Bookmark> bookmarks) {
        io.println("Select an entry to edit by typing its ID: ");
        shortListBookmarks(bookmarks);
        return getInt();
    }

    public Long askForBookmarkToDelete(List<Bookmark> bookmarks) {
        io.println("Give ID of the bookmark you want to delete: ");
        shortListBookmarks(bookmarks);
        return getInt();
    }

    public String askForEditField(String bookmark) {
        askUserForEditFieldMessage(bookmark);

        while (true) {
            String output = selectedEditFieldMap.get(io.nextLine());

            if (output != null) {
                return output;
            }
        }
    }

    private void initSelectedEditFieldMap() {
        selectedEditFieldMap.put("A", "author");
        selectedEditFieldMap.put("T", "title");
        selectedEditFieldMap.put("U", "url");
        selectedEditFieldMap.put("D", "description");
        selectedEditFieldMap.put("X", "tags");
    }

    private void initBookmarkTypeToAskEditFieldPrintMap() {
        bookmarkTypeToAskEditFieldPrintMap.put("Book", "\nA = Author\nT = title\nD = description\nX = tags");
        bookmarkTypeToAskEditFieldPrintMap.put("Blogpost", "\nT = title\nU = url\nD = description\nX = tags");
        bookmarkTypeToAskEditFieldPrintMap.put("Other", "\nT = title\nU = url\nD = description\nX = tags");
    }

    private void askUserForEditFieldMessage(String bookmark) {
        String bookmarkType = bookmark.split(" ")[3].trim();
        String ask = bookmarkType + "\n\nSelect field to edit:";

        ask += bookmarkTypeToAskEditFieldPrintMap.get(bookmarkType);

        io.println(ask);
    }

    public String askForNewField(String field) {
        io.println("Give new entry for " + field);
        return io.nextLine();
    }

    private String askForInput(String prompt) {
        io.println(prompt);
        return io.nextLine();
    }

    public void viewBookmarkEditedMessage() {
        io.println("Bookmark successfully edited.");
    }

    public void viewBookmarkDeletedMessage() {
        io.println("Bookmark successfully deleted.");
    }

}
