package app.domain.command;

import app.dao.BookMarkDAO;
import app.dao.TagDAO;
import app.io.StubIO;
import app.ui.TextUI;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*; 

public class CommandTest {
    private TextUI ui; 
    private BookMarkDAO dao; 
    private TagDAO tagDao; 

    public CommandTest() {

    }
    
    @Before
    public void initMocks(){
        ui = mock(TextUI.class);
        dao = mock(BookMarkDAO.class);
    }
    
    @Test
    public void askingListCommandToListTagsCausesDAOToRetrieveTags(){
        tagDao = mock(TagDAO.class);
        when(tagDao.getTagsOnDatabase()).thenReturn(null);
        when(ui.askForListingMethod()).thenReturn("LT");
        when(dao.getTagDAO()).thenReturn(tagDao);
        new ListCommand(ui, dao).execute();
        verify(dao).getTagDAO(); 
    }
    
    
    @Test
    public void executingExitCommandReturnsFalse(){
        assertFalse(new ExitCommand(ui, dao).execute());
    }
    
    
    

}
