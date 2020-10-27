package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.tag.ReadOnlyTagTree;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagTree;
import seedu.address.model.tag.TagTreeImpl;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final Calendar calendar;
    private final UserPrefs userPrefs;
    private final TagTree tagTree;
    private final ContactTagIntegrationManager contactTagIntegrationManager;

    private final FilteredList<Person> filteredPersons;
    private final SortedList<Person> sortedPersons;
    private final FilteredList<Event> filteredEvents;
    private final SortedList<Event> sortedEvents;

    /**
     * Initializes a ModelManager with the given addressBook, calendar, tagTree and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook,
                        ReadOnlyCalendar calendar,
                        ReadOnlyTagTree tagTree,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, tagTree, userPrefs);

        logger.fine("Initializing with AddressBook: " + addressBook + "\n"
                + ", Calendar: " + calendar + "\n"
                + ", TagTree: " + tagTree + "\n"
                + " and UserPrefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.calendar = new Calendar(calendar);
        this.tagTree = new TagTreeImpl(tagTree);
        this.userPrefs = new UserPrefs(userPrefs);

        contactTagIntegrationManager = new ContactTagIntegrationManager(this.addressBook, this.tagTree);

        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        sortedPersons = new SortedList<>(filteredPersons);

        filteredEvents = new FilteredList<>(this.calendar.getEventList());
        sortedEvents = new SortedList<>(filteredEvents);
    }

    /**
     * Initializes a ModelManager with no data
     */
    public ModelManager() {
        this(new AddressBook(), new Calendar(), new TagTreeImpl(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getCalendarFilePath() {
        return userPrefs.getCalendarFilePath();
    }

    @Override
    public void setCalendarFilePath(Path calendarFilePath) {
        requireNonNull(calendarFilePath);
        userPrefs.setCalendarFilePath(calendarFilePath);
    }

    @Override
    public Path getTagTreeFilePath() {
        return userPrefs.getTagTreeFilePath();
    }

    @Override
    public void setTagTreeFilePath(Path tagTreeFilePath) {
        requireNonNull(tagTreeFilePath);
        userPrefs.setTagTreeFilePath(tagTreeFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void setCalendar(ReadOnlyCalendar calendar) {
        this.calendar.resetData(calendar);
    }

    @Override
    public ReadOnlyCalendar getCalendar() {
        return calendar;
    }

    @Override
    public void setTagTree(ReadOnlyTagTree tagTree) {
        this.tagTree.copy(tagTree);
    }

    @Override
    public ReadOnlyTagTree getTagTree() {
        return tagTree;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    /*
    TODO: hasEvent temporary place holder to be implemented in future
     */
    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return calendar.hasEvent(event);
    }

    @Override
    public boolean hasTag(Tag tag) {
        return contactTagIntegrationManager.hasTag(tag);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void deleteEvent(Event target) {
        calendar.removeEvent(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addEvent(Event event) {
        calendar.addEvent(event);
    }

    @Override
    public void addSubTagTo(Tag superTag, Tag subTag) {
        tagTree.addSubTagTo(superTag, subTag);
    }

    @Override
    public void addPersonToTag(Tag tag, Person person) {
        addressBook.addPersonToTag(tag, person);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        calendar.setEvent(target, editedEvent);
    }

    @Override
    public void sortPerson(Comparator<Person> comparator) {
        sortedPersons.comparatorProperty().setValue(comparator);
    }

    @Override
    public void permaSortContacts(Comparator<Person> comparator) {
        addressBook.sortPerson(comparator);
    }

    public void sortEvent(Comparator<Event> comparator) {
        sortedEvents.comparatorProperty().setValue(comparator);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getSortedFilteredPersonList() {
        return sortedPersons;
    }

    @Override
    public ObservableList<Event> getSortedFilteredEventList() {
        return sortedEvents;
    }

    // Person-tag related methods

    @Override
    public Set<Person> getPersonsWithTag(Tag tag) {
        return addressBook.getPersonsWithTag(tag);
    }

    @Override
    public Set<Tag> getPersonTags() {
        return addressBook.getTags();
    }

    @Override
    public Set<Tag> getSuperTags() {
        return tagTree.getSuperTags();
    }

    @Override
    public Set<Tag> getSubTagsRecursive(Tag tag) {
        return tagTree.getSubTagsRecursive(tag);
    }

    @Override
    public Set<Person> getPersonsRecursive(Tag tag) {
        return contactTagIntegrationManager.getAllPersonsUnderTag(tag);
    }

    @Override
    public void deletePersonsByTagRecursive(Tag tag) {
        contactTagIntegrationManager.deleteTagAndDirectContactsRecursive(tag);
    }

    // Filter/sort related methods

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

}
