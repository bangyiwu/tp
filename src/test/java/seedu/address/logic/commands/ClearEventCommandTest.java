package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendar;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.events.ClearEventCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Calendar;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.TagTreeImpl;

public class ClearEventCommandTest {

    @Test
    public void execute_emptyCalendar_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearEventCommand(), model, ClearEventCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyCalendar_success() {
        Model model = new ModelManager(new AddressBook(), getTypicalCalendar(), new TagTreeImpl(), new UserPrefs());
        Model expectedModel = new ModelManager(
                new AddressBook(), getTypicalCalendar(), new TagTreeImpl(), new UserPrefs());
        expectedModel.setCalendar(new Calendar());

        assertCommandSuccess(new ClearEventCommand(), model, ClearEventCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
