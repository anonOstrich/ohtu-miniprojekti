package app.ui;

import app.domain.Tag;
import app.io.IO;
import org.fusesource.jansi.Ansi;
import app.utilities.Validator;
import bookmarks.Bookmark;
import bookmarks.BookBookmark;
import bookmarks.BlogBookmark;
import bookmarks.OtherBookmark;
import java.util.*;

/**
 *
 * @author jussiste
 */
public class TextUI {

    private final IO io;
    private final String defaultSuccessMessage = "Your bookmark has been read!";
    private final HashMap<String, String> selectedEditFieldMap = new HashMap<>();
    private final HashMap<String, String> bookmarkTypeToAskEditFieldPrintMap = new HashMap<>();

    /**
     * Constructor for class.
     *
     * @param io input/output device that gets prompts from the terminal
     */
    public TextUI(IO io) {
        this.io = io;
        initSelectedEditFieldMap();
        initBookmarkTypeToAskEditFieldPrintMap();
    }

    public IO getIO(){
        return io;
    }

    public void displayErrorMessage(String msg){
        io.boldColorPrint(msg, Ansi.Color.WHITE, Ansi.Color.RED);
        io.println("");
    }

    public void displaySuccessMessage(String msg){
        io.boldColorPrint(msg, Ansi.Color.GREEN);
        io.println("");
    }

    public void displayMessage(String msg){
        io.println(msg);
    }

    public void displayInstructionMessage(String msg){
        io.boldColorPrint(msg, Ansi.Color.YELLOW);
        io.println("");
    }

    public void printFieldName(String msg){
        io.boldColorPrint(msg, Ansi.Color.WHITE);
        io.println("");
    }

    public void printHR1(){
        //io.println("==================================================");
        io.println("\n");
    }

    public void printHR2(){
        //io.println("--------------------------------------------------");
        io.println("");
    }

    /**
     * Print the standard welcome message.
     */
    public void printWelcomeMessage() {
        io.boldColorPrint("Welcome!", Ansi.Color.BLUE);
        io.println("");
    }

    /**
     * Print the standard goodbye message.
     */
    public void printGoodbyeMessage() {
        io.boldColorPrint("Goodbye!", Ansi.Color.BLUE);
        io.println("");
    }

    /**
     * Prints an error if the userinput is not recognizable.
     */
    public void printUnrecognizedOption() {
        displayErrorMessage("Unrecognized option");
    }


    /**
     * Prints the available menu options and prompts the user to select one.
     *
     * @return user's choise of command.
     */
    public String getMenuCommand() {
        displayInstructionMessage("\nSelect one of the following options");
        io.print("1. ");
        io.boldRedPrint("Add");
        io.print(" a new bookmark \n2. ");
        io.boldRedPrint("List");
        io.print(" all existing bookmarks, or tags\n3. ");
        io.boldRedPrint("Search");
        io.print(" for bookmarks\n4. ");
        io.boldRedPrint("Edit");
        io.print(" a bookmark\n5. ");
        io.boldRedPrint("Delete");
        io.print(" a bookmark\n0. ");
        io.boldRedPrint("Exit");
        io.println(" the application");
        return io.nextLine();
    }

    /**
     * Asks for the field that user wants to edit.
     *
     * @return a valid choise if user selection is valid, empty string
     * otherwise.
     */
    public String askForField() {
        displayInstructionMessage("Which field would you like to search?");
        printFieldName("Give field (T = Title) (D = Description) (TA = Tag)");
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

    /**
     * Asks for the search term that user wants to use.
     *
     * @return
     */
    public String askForSearch() {
        displayInstructionMessage("Give a search term: ");
        return io.nextLine();
    }

    /**
     * Ask the user what kind of bookmark they want to make.
     *
     * @return bookmark of wanted type
     */
    public Bookmark askForBookmark() {
        displayInstructionMessage("Add a new bookmark.");
        printFieldName("Give type (B = Book) (BG = Blog), (O = Other): ");
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
            displaySuccessMessage(defaultSuccessMessage);
            return bookmark;
        }
        displayErrorMessage("Invalid choice");
        return null;

    }

    /**
     * Asks for the criteria that is used for sorting the list.
     *
     * @return
     */
    public String askForListingMethod() {
        displayInstructionMessage("Choose listing method:");
        printFieldName("(T = Title)(CD = Creation time Descending)(CA = Creation time Ascending)");
        printFieldName("(LT = List Tags)");
        return io.nextLine();
    }

    /**
     * Prints the given list of bookmarks. If the list is empty prints an error
     * message.
     *
     * @param bookmarks list of bookmarks
     */
    public void printBookmarkList(List<Bookmark> bookmarks) {
        io.println("");
        if (bookmarks.isEmpty()) {
            io.println("No bookmarks found.");
        }
        for (Bookmark bmark : bookmarks) {
            bmark.printInfo(io);
            printHR1();
        }
    }

    public void presentTags(List<Tag> tags) {
        io.println("");
        io.boldPrint("Tags:");
        printHR2();
        if (tags.isEmpty()) {
            io.println("No tags found.");
        }
        for (int i = 0; i < tags.size(); i++) {
            Tag tag = tags.get(i);
            Set<Bookmark> bmarks = tag.getBookmarks();
            io.boldPrint("tag: ");
            tag.print(io);
            io.boldPrint("\n" + "bookmarks tagged:\n");
            for (Bookmark bmark : bmarks) {
                bmark.printShortInfo(io);
            }
            io.boldPrint("Total amount of bookmarks tagged: " + bmarks.size() + "\n");
            printHR1();
        }
    }

    public void listTags(List<Tag> tags) {
        io.println("");
        io.println("Tags:");
        io.println("");
        for (Tag tag : tags) {
            tag.print(io);
            io.println("");
        }
    }

    public String askForTag() {
        io.println("");
        displayInstructionMessage("Enter name of the tag to list bookmarks associated with it:");
        return io.nextLine();
    }

    public void printTagsNotFound() {
        io.println("No tags with that title were found.");
    }

    private Bookmark askForOtherBookmarkInfo() {
        OtherBookmark bm = new OtherBookmark();
        printFieldName("Url: ");
        String url = validUrl();
        bm.setUrl(url);
        askForGeneralBookmarkInfo(bm);
        return bm;
    }

    private Bookmark askForBookBookmarkInfo() {
        BookBookmark bm = new BookBookmark();
        printFieldName("ISBN: ");
        String isbn = validISBN();
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
        printFieldName("Url: ");
        String url = validUrl();
        bm.setUrl(url);
        askForGeneralBookmarkInfo(bm);
        return bm;
    }

    private Bookmark askForGeneralBookmarkInfo(Bookmark bookmark) {
        printFieldName("Title; ");
        String title = validField();
        bookmark.setTitle(title);

        List<Tag> tagsList = askForTags();
        bookmark.setTags(tagsList);
        printFieldName("Description");
        String description = validField();
        bookmark.setDescription(description);

        return bookmark;
    }

    /**
     * Ask for the list of tags.
     *
     * @return
     */
    public List<Tag> askForTags() {
        printFieldName("Tags (separated by commas): ");
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

    /**
     * Ask for the ID of the bookmark that the user wants to edit. Also prints
     * the list of bookmarks in short form.
     *
     * @param bookmarks
     * @return
     */
    public Long askForBookmarkToEdit(List<Bookmark> bookmarks) {
        displayInstructionMessage("Select an entry to edit by typing its ID: ");
        shortListBookmarks(bookmarks);
        return getInt();
    }

    /**
     * Ask for the ID of the bookmark that the user wants to delete. Also prints
     * the list of bookmarks in short form.
     *
     * @param bookmarks
     * @return
     */
    public Long askForBookmarkToDelete(List<Bookmark> bookmarks) {
        displayInstructionMessage("Give ID of the bookmark you want to delete: ");
        shortListBookmarks(bookmarks);
        return getInt();
    }

    /**
     * Asks the user for the field they want to edit. Different bookmarktypes
     * have different fields that can be edited.
     *
     * @param bookmark that is beging edited
     * @return
     */
    public String askForEditField(Bookmark bookmark) {
        if (bookmark == null){
            return "";
        }
        bookmark.printInfo(io);
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
        bookmarkTypeToAskEditFieldPrintMap.put("Book", "A = Author\nT = title\nD = description\nX = tags");
        bookmarkTypeToAskEditFieldPrintMap.put("Blogpost", "T = title\nU = url\nD = description\nX = tags");
        bookmarkTypeToAskEditFieldPrintMap.put("Other", "T = title\nU = url\nD = description\nX = tags");
    }

    private void askUserForEditFieldMessage(Bookmark bookmark) {
        String bookmarkType = bookmark.type();
        printFieldName("Select field to edit:");
        io.println(bookmarkTypeToAskEditFieldPrintMap.get(bookmarkType));
    }

    
    /**
     * Asks the user for a new entry when the user is in edit state.
     *
     * @param field
     * @return
     */
    public String askForNewField(String field) {
        printFieldName("Give new entry for " + field);
        return io.nextLine();
    }

    private String askForInput(String prompt) {
        printFieldName(prompt);
        return io.nextLine();
    }

    /**
     * Prints a message when the bookmark is succesfully edited.
     */
    public void viewBookmarkEditedMessage() {
        displaySuccessMessage("Bookmark successfully edited.");
    }

    /**
     * Prints a message when the bookmark is succesfully deleted.
     */
    public void viewBookmarkDeletedMessage() {
        displaySuccessMessage("Bookmark successfully deleted.");
    }

    /**
     * Check if the url is valid. If the url is invalid it asks for a new url
     * until the requirements are met.
     *
     * @return
     */
    public String validUrl() {
        String url = io.nextLine();
        while (!Validator.validUrl(url)) {
            displayErrorMessage("The url is not valid. Try again. Remember to start the url with \"www\" or \"https://\"");
            url = io.nextLine();
        }
        return url;
    }

    /**
     * Check if the field is valid. If the input is invalid it asks for a new
     * input until the requirements are met.
     *
     * @return
     */
    public String validField() {
        String title = io.nextLine();
        while (!Validator.validName(title)) {
            displayErrorMessage("This field can't be empty, Please try again.");
            title = io.nextLine();
        }
        return title;
    }

    /**
     * Check if the ISBN is valid. If the ISBN is invalid it asks for a new ISBN
     * until the requirements are met.
     *
     * @return
     */
    public String validISBN() {
        String ISBN = io.nextLine();
        while (!Validator.validISBN(ISBN)) {
            displayErrorMessage("The ISBN is not valid. Try again. The ISBN can contain only numbers and lines.");
            ISBN = io.nextLine();
        }
        return ISBN;
    }
}
