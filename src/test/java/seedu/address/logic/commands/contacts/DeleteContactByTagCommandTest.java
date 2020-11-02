package seedu.address.logic.commands.contacts;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Calendar;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RemindersImpl;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagTreeImpl;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteContactCommand}.
 */
public class DeleteContactByTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new Calendar(),
            new TagTreeImpl(), new UserPrefs(), new RemindersImpl());

    @Test
    public void execute_deleteContactByTag_success() {
        Tag tagForDeletion = new Tag("friends");
        DeleteContactByTagCommand deleteContactByTagCommand = new DeleteContactByTagCommand(tagForDeletion);

        String expectedMessage = DeleteContactByTagCommand.MESSAGE_DELETE_PERSON_SUCCESS + tagForDeletion.tagName;


        ModelManager expectedModel = new ModelManager(model.getAddressBook(),
                new Calendar(), new TagTreeImpl(), new UserPrefs(), new RemindersImpl());
        Person personToDelete = model.getSortedFilteredPersonList().get(0);
        Person secondPersonToDelete = model.getSortedFilteredPersonList().get(1);
        Person thirdPersonToDelete = model.getSortedFilteredPersonList().get(3);
        expectedModel.deletePerson(personToDelete);
        expectedModel.deletePerson(secondPersonToDelete);
        expectedModel.deletePerson(thirdPersonToDelete);

        assertCommandSuccess(deleteContactByTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistantTag_throwsCommandException() {
        Tag nonExistantTag = new Tag("nonexistantTagthatshouldntbeinATypicalAddressbook");
        DeleteContactByTagCommand deleteContactByTagCommand = new DeleteContactByTagCommand(nonExistantTag);

        assertCommandFailure(deleteContactByTagCommand, model, deleteContactByTagCommand.MESSAGE_NON_EXISTENT_TAG);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getSortedFilteredPersonList().isEmpty());
    }
}