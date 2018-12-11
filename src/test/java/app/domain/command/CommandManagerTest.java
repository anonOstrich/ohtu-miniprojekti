package app.domain.command;

import app.dao.BookMarkDAO;
import app.ui.TextUI;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CommandManagerTest {

    private TextUI ui;
    private BookMarkDAO dao;
    private CommandManager manager;

    public CommandManagerTest() {
        ui = mock(TextUI.class);
        dao = mock(BookMarkDAO.class);
    }

    @Before
    public void setUpManager() {
        manager = new CommandManager(ui, dao);

    }

    @Test
    public void properListSearchReturnsListCommand() {
        Command command = manager.getCommandByInput("list");
        assertEquals(ListCommand.class, command.getClass());
        command = manager.getCommandByInput("2");
        assertEquals(ListCommand.class, command.getClass());
    }

    @Test
    public void properNewSearchReturnsNewCommand() {
        Command command = manager.getCommandByInput("new");
        assertEquals(NewCommand.class, command.getClass());
        command = manager.getCommandByInput("1");
        assertEquals(NewCommand.class, command.getClass());
    }

    @Test
    public void properSearchSearchReturnsSearchCommand() {
        Command command = manager.getCommandByInput("search");
        assertEquals(SearchCommand.class, command.getClass());
        command = manager.getCommandByInput("3");
        assertEquals(SearchCommand.class, command.getClass());
    }

    @Test
    public void properEditSearchReturnsEditCommand() {
        Command command = manager.getCommandByInput("edit");
        assertEquals(EditCommand.class, command.getClass());
        command = manager.getCommandByInput("4");
        assertEquals(EditCommand.class, command.getClass());
    }

    @Test
    public void properDeleteSearchReturnsDeleteCommand() {
        Command command = manager.getCommandByInput("delete");
        assertEquals(DeleteCommand.class, command.getClass());
        command = manager.getCommandByInput("5");
        assertEquals(DeleteCommand.class, command.getClass());
    }

    @Test
    public void unsupportedSearchReturnsNullCommand() {
        Command command = manager.getCommandByInput("recommend");
        assertEquals(NullCommand.class, command.getClass());
    }

}
