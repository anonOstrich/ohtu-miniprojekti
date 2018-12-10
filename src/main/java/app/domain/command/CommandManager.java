package app.domain.command;

import java.util.Map;
import java.util.HashMap;
import app.dao.BookMarkDAO;
import app.ui.TextUI;

public class CommandManager {

    private TextUI ui;
    private BookMarkDAO dao;
    private Map<String, Command> commandsByNames;
    private Map<String, Command> commandsByNumbers;

    public CommandManager(TextUI ui, BookMarkDAO dao) {
        this.ui = ui;
        this.dao = dao;
        commandsByNames = new HashMap(); 
        commandsByNumbers = new HashMap(); 
        initializeCommands();
    }

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
    
    public Command getCommandByInput(String input){
        Command command = commandsByNames.get(input.toLowerCase().trim());
        if(command == null){
            command = commandsByNumbers.get(input.trim());
        }
        return command; 
    }

}
