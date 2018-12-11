package app.domain.command;

import java.util.Map;
import java.util.HashMap;
import app.dao.BookMarkDAO;
import app.ui.TextUI;

/**
 * Provides access to all the different commands of the program
 *
 * <p>
 * Hides the concrete implementation classes from the app. Attempts to return a
 * reference to the correct command based on a string that represents either the
 * name of the command or its index in the list of commands displayed to the
 * user.</p>
 *
 */
public class CommandManager {
    
    /**
     * Constant that defines how many erroneous command inputs are allowed in row
     */
    private static final int  ALLOWED_REPEATED_ATTEMPTS = 100; 

    /**
     * Passed to the created commands.
     */
    private final TextUI ui;
    /**
     * Passed to the created commands.
     */
    private final BookMarkDAO dao;
    /**
     * All the commands indexed by their names
     */
    private final Map<String, Command> commandsByNames;
    /**
     * All the commands indexed by their identifying number displayed in the UI
     */
    private final Map<String, Command> commandsByNumbers;
    /**
     * Command that is returned if no other command fits
     */
    private final NullCommand nullCommand;
    /**
     * The last command that was returned
     */
    private Command prevCommand;

    public CommandManager(TextUI ui, BookMarkDAO dao) {
        this.ui = ui;
        this.dao = dao;
        commandsByNames = new HashMap();
        commandsByNumbers = new HashMap();
        nullCommand = new NullCommand(ui, dao, ALLOWED_REPEATED_ATTEMPTS);
        prevCommand = null;
        initializeCommands();
    }

    /**
     * Assigns all the commands so they can be found by name or id number
     * 
     * <p>
     * If a new command class is defined, it must also be added to the maps in 
     * this method. After these, it should work as part of the program.
     * </p>
     */
    private void initializeCommands() {
        Command exitC = new ExitCommand(ui, dao);
        commandsByNames.put("exit", exitC);
        commandsByNumbers.put("0", exitC);

        Command newC = new NewCommand(ui, dao);
        commandsByNames.put("new", newC);
        commandsByNumbers.put("1", newC);

        Command listC = new ListCommand(ui, dao);
        commandsByNames.put("list", listC);
        commandsByNumbers.put("2", listC);

        Command searchC = new SearchCommand(ui, dao);
        commandsByNames.put("search", searchC);
        commandsByNumbers.put("3", searchC);

        Command editC = new EditCommand(ui, dao);
        commandsByNames.put("edit", editC);
        commandsByNumbers.put("4", editC);

        Command deleteC = new DeleteCommand(ui, dao);
        commandsByNames.put("delete", deleteC);
        commandsByNumbers.put("5", deleteC);

    }

    /**
     * 
     * Returns the specified command or the null command
     * 
     * <p>Also updates the previous command to the returned value, and
     * clears the counter inside null command if the previous command has been
     * a command of another type.</p>
     * 
     * @param input Input string that the user has entered. Could be an id 
     * number or a short description
     * 
     * @return The command that is stored with a matching search phrase, 
     * or the null command if no such command exists.
     */
    public Command getCommandByInput(String input) {
        Command command = commandsByNames.get(input.toLowerCase().trim());
        if (command == null) {
            command = commandsByNumbers.get(input.trim());
        }

        if (command == null) {
            command = nullCommand;
            if (prevCommand != nullCommand) {
                nullCommand.clear();
            }
        }
        prevCommand = command; 
        return command;
    }

}
