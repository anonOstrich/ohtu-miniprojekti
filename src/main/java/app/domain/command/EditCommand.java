package app.domain.command;

import app.dao.BookMarkDAO;
import app.domain.Tag;
import app.ui.TextUI;
import bookmarks.Bookmark;
import java.util.List;

public class EditCommand extends Command {

    public EditCommand(TextUI ui, BookMarkDAO dao) {
        super(ui, dao);
    }

    @Override
    public boolean execute() {
        String newEntry = "";
        List<Bookmark> bookmarks = dao.getBookMarksOnDatabase();
        Long editID = ui.askForBookmarkToEdit(bookmarks);
        String editfield = ui.askForEditField(dao.getSingleBookmarkInfo(editID));
        if (editfield.isEmpty()) {
            ui.displayErrorMessage("Not a valid ID");
            return true;
        }
        List<Tag> tagList = null;
        if (editfield.equals("tags")) {
            tagList = ui.askForTags();
        } else {
            newEntry = ui.askForNewField(editfield);
        }

        if (dao.editEntry(editID, editfield, newEntry, tagList)) {
            ui.viewBookmarkEditedMessage();
        }
        return true;
    }

}
