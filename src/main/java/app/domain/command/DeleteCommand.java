package app.domain.command;

import app.io.IO;

public class DeleteCommand extends Command{

    public DeleteCommand(IO io) {
        super(io);
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
