package app.domain.command;

import app.dao.BookMarkDAO;
import app.ui.TextUI;
/**
 * Represents a non-existing command
 * 
 * <p>To avoid dealing with null pointer handling, an instance of this class
 * can be returned instead of null. If that instance is returned too many times, 
 * the command indicates that the program should close.</p>
 * 
 */
public class NullCommand extends Command {

    /**
     * How many repeated null commands are executed before the program quits
     */
    private int attemptLimit;
    /**
     * The number of repeated command executions where all commands have been 
     * null commands
     */
    private int repeatedAttempts;


    public NullCommand(TextUI ui, BookMarkDAO dao, int attemptLimit) {
        super(ui, dao);
        this.attemptLimit = attemptLimit;
        repeatedAttempts = 0;
    }
    
    /**
     * The count is begun anew.
     * 
     * <p>Should be called when another type of command is executed</p>
     */
    public void clear(){
        this.repeatedAttempts = 0; 
    }

    /**
     * Displays a warning message and increments the counter
     * 
     * @return True if execution should continue, false otherwise
     */
    @Override
    public boolean execute() {
        ui.printUnrecognizedOption();
        repeatedAttempts++; 
        return repeatedAttempts > attemptLimit; 
    }

}
