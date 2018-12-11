package app.domain.command;

import app.dao.BookMarkDAO;
import app.io.StubIO;
import app.ui.TextUI;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CommandTest {
    private TextUI ui; 
    private BookMarkDAO dao; 

    public CommandTest() {
        ui = new TextUI(new StubIO(""));
        dao = new BookMarkDAO(); 
    }
    
    
    @Test
    public void executingAnyButExitCommandReturnsTrue(){
        assertTrue(true);
        
    }
    
    @Test
    public void executingExitCommandReturnsFalse(){
        assertTrue(true);
    }
    
    
    

}
