package app.domain.command;

import app.dao.BookMarkDAO;
import app.ui.TextUI;

public class ExitCommand extends Command {

    public ExitCommand(TextUI ui, BookMarkDAO dao) {
        super(ui, dao);
    }

    @Override
    public boolean execute() {
        ui.printGoodbyeMessage();
        return false;
    }

}
