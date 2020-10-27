package seedu.address.logic.commands.tags;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.ContactTagIntegrationManagerTest.buildTestContactTagIntegrationManager;
import static seedu.address.testutil.TagTreeUtil.TAG_CS1231S;
import static seedu.address.testutil.TagTreeUtil.TAG_CS2040S_NOT_TREE;
import static seedu.address.testutil.TagTreeUtil.TAG_NOT_IN_TREE;
import static seedu.address.testutil.TagTreeUtil.TAG_SCIENCE;

import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ModelStub;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ContactTagIntegrationManager;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagTree;

public class AddTagCommandTest {

    @Test
    public void execute_tagAlreadyPresent_throwsCommandException() {
        ModelStubWithTagAndPersons model = new ModelStubWithTagAndPersons();
        AddTagCommand command = new AddTagCommand(TAG_SCIENCE, Set.of(TAG_CS1231S), Set.of());
        assertThrows(CommandException.class, () ->
                command.execute(model));
    }

    @Test
    public void execute_indicesOutOfRange_throwsCommandException() {
        ModelStubWithTagAndPersons model = new ModelStubWithTagAndPersons();
        AddTagCommand command = new AddTagCommand(TAG_CS2040S_NOT_TREE,
                Set.of(TAG_CS1231S), Set.of(Index.fromOneBased(999)));
        assertThrows(CommandException.class, () ->
                command.execute(model));
    }

    @Test
    public void execute_subTagNotFound_throwsCommandException() {
        ModelStubWithTagAndPersons model = new ModelStubWithTagAndPersons();
        AddTagCommand command = new AddTagCommand(TAG_CS2040S_NOT_TREE,
                Set.of(TAG_NOT_IN_TREE), Set.of());
        assertThrows(CommandException.class, () ->
                command.execute(model));
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        ModelStubWithTagAndPersons model = new ModelStubWithTagAndPersons();
        AddTagCommand command = new AddTagCommand(TAG_CS2040S_NOT_TREE,
                Set.of(TAG_CS1231S), Set.of(Index.fromOneBased(1)));
        command.execute(model);
        assertTrue(model.getTagTree().getTagSubTagMap().containsKey(TAG_CS2040S_NOT_TREE));
        assertTrue(model.getTagTree().getSubTagsOf(TAG_CS2040S_NOT_TREE).contains(TAG_CS1231S));
        assertFalse(model.getAddressBook().getPersonsWithTag(TAG_CS2040S_NOT_TREE).isEmpty());
    }

    private class ModelStubWithTagAndPersons extends ModelStub {
        private ContactTagIntegrationManager manager = buildTestContactTagIntegrationManager();
        private AddressBook addressBook = manager.getAddressBook();
        private TagTree tagTree = manager.getTagTree();

        @Override
        public void addSubTagTo(Tag superTag, Tag subTag) {
            tagTree.addSubTagTo(superTag, subTag);
        }

        @Override
        public void addPersonToTag(Tag tag, Person person) {
            addressBook.addPersonToTag(tag, person);
        }

        @Override
        public boolean hasTag(Tag tag) {
            return manager.hasTag(tag);
        }

        @Override
        public ObservableList<Person> getSortedFilteredPersonList() {
            return addressBook.getPersonList();
        }

        @Override
        public TagTree getTagTree() {
            return tagTree;
        }

        @Override
        public AddressBook getAddressBook() {
            return addressBook;
        }

    }


}
