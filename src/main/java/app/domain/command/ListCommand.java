package app.domain.command;

import app.dao.BookMarkDAO;
import app.ui.TextUI;

public class ListCommand extends Command {

    public ListCommand(TextUI ui, BookMarkDAO dao) {
        super(ui, dao);
    }

    @Override
    public boolean execute() {
        String method = ui.askForListingMethod();
        if (method.equals("LT")) {
            ui.presentTags(dao.getTagDAO().getTagsOnDatabase());
        } else {
            ui.printBookmarkList(dao.getBookmarksInOrder(method));
        }
        return true; 
    }

}
