package app.domain.command;

import app.dao.BookMarkDAO;
import app.ui.TextUI;

public abstract class Command {
    protected TextUI ui;
    protected BookMarkDAO dao; 
    
    public Command(TextUI ui, BookMarkDAO dao) {
        this.ui = ui;
        this.dao = dao; 
    }

    public abstract boolean execute();



}
