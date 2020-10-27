package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BREAKFAST;
import static seedu.address.logic.commands.CommandTestUtil.DESC_LUNCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BREAKFAST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_BREAKFAST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.events.EditEventCommand;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditEventDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditEventCommand.EditEventDescriptor descriptorWithSameValues =
                new EditEventCommand.EditEventDescriptor(DESC_LUNCH);
        assertTrue(DESC_LUNCH.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_LUNCH.equals(DESC_LUNCH));

        // null -> returns false
        assertFalse(DESC_LUNCH.equals(null));

        // different types -> returns false
        assertFalse(DESC_LUNCH.equals(5));

        // different values -> returns false
        assertFalse(DESC_LUNCH.equals(DESC_BREAKFAST));

        // different name -> returns false
        EditEventCommand.EditEventDescriptor editedLunch =
                new EditEventDescriptorBuilder(DESC_LUNCH).withDescription(VALID_DESCRIPTION_BREAKFAST).build();
        assertFalse(DESC_LUNCH.equals(editedLunch));

        // different phone -> returns false
        editedLunch = new EditEventDescriptorBuilder(DESC_LUNCH).withTime(VALID_TIME_BREAKFAST).build();
        assertFalse(DESC_LUNCH.equals(editedLunch));
    }
}
