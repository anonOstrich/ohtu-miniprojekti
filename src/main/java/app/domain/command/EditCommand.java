package app.domain.command;

import app.dao.BookMarkDAO;
import app.domain.Tag;
import app.io.IO;
import app.ui.TextUI;
import bookmarks.Bookmark;
import java.util.List;

public class EditCommand extends Command {

    public EditCommand(TextUI ui, BookMarkDAO dao) {
        super(ui, dao);
    }

    @Override
    public void execute() {
        String newEntry = ""; 
        Long editID = ui.askForBookmarkToEdit(dao.getBookMarksOnDatabase());
        String editfield = ui.askForEditField(dao.getSingleBookmarkInfo(editID));
        List<Tag> tagList = null;
        String bm_info = dao.getSingleBookmarkInfo(editID);
        
        
        ui.temporaryMethod(bm_info);
        if (editfield.equals("tags")) {
            tagList = ui.askForTags();
        } else {
            newEntry = ui.askForNewField(editfield);
        }

        if (dao.editEntry(editID, editfield, newEntry, tagList)) {
            ui.viewBookmarkEditedMessage();
        }
    }

}
