package app.domain.command;

import app.dao.BookMarkDAO;
import app.ui.TextUI;

public class DeleteCommand extends Command {

    public DeleteCommand(TextUI ui, BookMarkDAO dao) {
        super(ui, dao);
    }

    @Override
    public boolean execute() {
        Long bookmark_id = ui.askForBookmarkToDelete(dao.getBookMarksOnDatabase());
        if (bookmark_id != null) {
            if (dao.deleteBookmarkFromDatabase(bookmark_id)) {
                ui.viewBookmarkDeletedMessage();
            }
        }
        return true; 
    }

}
