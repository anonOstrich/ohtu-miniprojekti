package app.domain.command;

import app.dao.BookMarkDAO;
import app.ui.TextUI;

public class CommandGenerator {

    private TextUI ui;
    private BookMarkDAO dao;

    public CommandGenerator(TextUI ui, BookMarkDAO dao) {
        this.ui = ui;
        this.dao = dao;
    }

    public Command newCommand() {
        return new NewCommand(ui, dao);
    }

    public Command listCommand() {
        return new ListCommand(ui, dao);
    }

    public Command searchCommand() {
        return new SearchCommand(ui, dao);
    }

    public Command deleteCommand() {
        return new DeleteCommand(ui, dao);
    }

    public Command editCommand() {
        return new EditCommand(ui, dao);
    }

}
