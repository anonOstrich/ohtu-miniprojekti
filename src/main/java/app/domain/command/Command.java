package app.domain.command;

import app.dao.BookMarkDAO;
import app.ui.TextUI;

/**
 * Represents a selectable and executable command
 * 
 * <p>A command may modify the contents of the database through an instance of a
 * dao class, and also exchange information with the user via a user interface.</p>
 * 
 */
public abstract class Command {
    protected TextUI ui;
    protected BookMarkDAO dao; 
    
    public Command(TextUI ui, BookMarkDAO dao) {
        this.ui = ui;
        this.dao = dao; 
    }

    /**
     * All the processing that differentiates commands from one another
     * 
     * @return Whether the execution of the program should continue
     */
    public abstract boolean execute();



}
