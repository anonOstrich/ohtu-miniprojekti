package app.domain.command;

import app.io.IO;
import app.ui.TextUI;

public abstract class Command {

    protected TextUI ui;

    public Command(TextUI ui) {
        this.ui = ui;
    }

    public abstract void execute();

    public static Command newCommand(TextUI ui) {
        return new NewCommand(ui);
    }
    
    public static Command listCommand(TextUI ui){
        return new ListCommand(ui);
    }
    
    public static Command searchCommand(TextUI ui){
        return new SearchCommand(ui);
    }
    
    public static Command deleteCommand(TextUI ui){
        return new DeleteCommand(ui);
    }
    
    public static Command editCommand(TextUI ui){
        return new EditCommand(ui);
    }

}
