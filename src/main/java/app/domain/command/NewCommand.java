package app.domain.command;

import app.dao.BookMarkDAO;
import app.ui.TextUI;
import bookmarks.Bookmark;

public class NewCommand extends Command {

    public NewCommand(TextUI ui, BookMarkDAO dao) {
        super(ui, dao);
    }



    @Override
    public boolean execute() {
        Bookmark bookmark = ui.askForBookmark();
        if (bookmark != null) {
            dao.saveBookmarkToDatabase(bookmark);
        }
        return true; 
    }

}
