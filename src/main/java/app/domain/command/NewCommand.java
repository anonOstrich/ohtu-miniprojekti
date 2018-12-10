package app.domain.command;

import app.io.IO;
import app.ui.TextUI;
import bookmarks.Bookmark;

public class NewCommand extends Command {

    public NewCommand(TextUI ui) {
        super(ui);
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}
