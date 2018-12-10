package app;

import app.dao.BookMarkDAO;
import app.domain.command.Command;
import app.domain.command.CommandGenerator;
import app.io.ConsoleIO;
import app.io.IO;
import app.ui.TextUI;

import java.util.HashMap;
import java.util.Map;

public class App {

    private Map<String, Command> commandsByNames;
    private Map<String, Command> commandsByNumbers;

    private final TextUI ui;
    private IO io;
    private BookMarkDAO dao;

    public App(IO io) {
        this(io, new BookMarkDAO());
    }

    public App(IO io, BookMarkDAO dao) {
        this.io = io;
        this.ui = new TextUI(io);
        this.dao = dao;
        this.commandsByNames = new HashMap();
        this.commandsByNumbers = new HashMap();

        CommandGenerator commandGenerator = new CommandGenerator(ui, dao);

        Command exitC = commandGenerator.exitCommand();
        commandsByNames.put("exit", exitC);
        commandsByNumbers.put("0", exitC);

        Command newC = commandGenerator.newCommand();
        commandsByNames.put("new", newC);
        commandsByNumbers.put("1", newC);

        Command listC = commandGenerator.listCommand();
        commandsByNames.put("list", listC);
        commandsByNumbers.put("2", listC);

        Command searchC = commandGenerator.searchCommand();
        commandsByNames.put("search", searchC);
        commandsByNumbers.put("3", searchC);

        Command editC = commandGenerator.editCommand();
        commandsByNames.put("edit", editC);
        commandsByNumbers.put("4", editC);

        Command deleteC = commandGenerator.deleteCommand();
        commandsByNames.put("delete", deleteC);
        commandsByNumbers.put("5", deleteC);
    }

    public void run() {
        ui.printWelcomeMessage();
        boolean run = true;
        String input;
        int safety = 0;
        Command command;
        //TODO: hide this to an other class

        while (run) {
            input = ui.getMenuCommand();
            command = commandsByNames.get(input) != null ? commandsByNames.get(input) : commandsByNumbers.get(input);

            if (command == null) {
                ui.printUnrecognizedOption();
                if (safety++ > 100) {
                    run = false;
                }
                continue;
            }
            run = command.execute();
        }

    }

    public void close() {
        this.dao.close();
    }

    public static void main(String[] args) {
        App app = new App(new ConsoleIO());
        app.run();
        app.close();
    }
}
