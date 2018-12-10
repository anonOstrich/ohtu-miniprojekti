package app.domain.command;

import app.io.IO;

public abstract class Command {

    protected IO io;

    public Command(IO io) {
        this.io = io;
    }

    public abstract void execute();

    public static Command newCommand(IO io) {
        return new NewCommand(io);
    }
    
    public static Command listCommand(IO io){
        return new ListCommand(io);
    }
    
    public static Command searchCommand(IO io){
        return new SearchCommand(io);
    }
    
    public static Command deleteCommand(IO io){
        return new DeleteCommand(io);
    }
    
    public static Command editCommand(IO io){
        return new EditCommand(io);
    }

}
