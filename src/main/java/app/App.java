package app;

import app.dao.BookMarkDAO;
import app.domain.command.Command;
import app.domain.command.CommandManager;
import app.io.ConsoleIO;
import app.ui.TextUI;

public class App {

    private final TextUI ui;
    private BookMarkDAO dao;
    private CommandManager commandManager; 

    public App(TextUI ui) {
        this(ui, new BookMarkDAO());
    }

    public App(TextUI ui, BookMarkDAO dao) {
        this.ui = ui;
        this.dao = dao;
        commandManager = new CommandManager(ui, dao);
    }


    public void run() {
        ui.printWelcomeMessage();
        boolean run = true;
        String input;
        Command command;
        //TODO: hide this to an other class


        while (run) {
            input = ui.getMenuCommand();
            command = commandManager.getCommandByInput(input);
            run = command.execute();

        }

    }

    public void close() {
        this.dao.close();
    }

    public static void main(String[] args) {

        App app = new App(new TextUI(new ConsoleIO()));

        app.run();
        app.close();
    }
}
