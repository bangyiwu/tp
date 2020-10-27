package seedu.address.logic.commands;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.tag.ReadOnlyTagTree;
import seedu.address.model.tag.Tag;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getAddressBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getCalendarFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getTagTreeFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCalendarFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTagTreeFilePath(Path tagTreeFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addEvent(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPersonToTag(Tag tag, Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addSubTagTo(Tag superTag, Tag subTag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCalendar(ReadOnlyCalendar newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTagTree(ReadOnlyTagTree tagTree) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyCalendar getCalendar() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyTagTree getTagTree() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasEvent(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasTag(Tag tag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePerson(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteEvent(Event target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setEvent(Event target, Event editedEvent) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortPerson(Comparator<Person> comparator) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortEvent(Comparator<Event> comparator) {
        throw new AssertionError("This method should not be called.");
    }

    public ObservableList<Person> getSortedFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Event> getSortedFilteredEventList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Set<Person> getPersonsWithTag(Tag tag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Set<Tag> getPersonTags() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Set<Tag> getSuperTags() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Set<Tag> getSubTagsRecursive(Tag tag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Set<Person> getPersonsRecursive(Tag tag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePersonsByTag(Tag tag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void permaSortContacts(Comparator<Person> comparator) {
        throw new AssertionError("This method should not be called.");
    }
}
