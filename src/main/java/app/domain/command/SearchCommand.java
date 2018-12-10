package app.domain.command;

import app.dao.BookMarkDAO;
import app.domain.Tag;
import app.ui.TextUI;
import java.util.ArrayList;
import java.util.List;

public class SearchCommand extends Command {

    public SearchCommand(TextUI ui, BookMarkDAO dao) {
        super(ui, dao);
    }

    @Override
    public void execute() {
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
    }

}
