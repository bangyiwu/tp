package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TagTreeUtil.TAG_COMPUTING;
import static seedu.address.testutil.TagTreeUtil.TAG_CS2040S_NOT_TREE;
import static seedu.address.testutil.TagTreeUtil.TAG_MA1101R;
import static seedu.address.testutil.TagTreeUtil.TAG_SCIENCE_COMP;
import static seedu.address.testutil.TagTreeUtil.buildTestTree;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


public class ContactTagIntegrationManagerTest {

    public static final Person PERSON_CS1231S_1 = new PersonBuilder()
            .withName("person1")
            .withTags("cs1231s").build();
    public static final Person PERSON_CS1231S_2 = new PersonBuilder()
            .withName("person2")
            .withTags("cs1231s").build();
    public static final Person PERSON_MA1101R = new PersonBuilder()
            .withName("person3")
            .withTags("ma1101r").build();
    public static final Person PERSON_SCIENCECOMP = new PersonBuilder()
            .withName("person4")
            .withTags("sciencecomp").build();
    public static final Person PERSON_COMPUTING = new PersonBuilder()
            .withName("person5")
            .withTags("computing").build();
    public static final Person PERSON_COMPUTING_SCIENCE = new PersonBuilder()
            .withName("person6")
            .withTags("computing", "science").build();
    public static final Person PERSON_CS2040S_NOT_IN = new PersonBuilder()
            .withName("person7")
            .withTags("CS2040S").build();
    public static final List<Person> TEST_PERSONS = List.of(PERSON_CS1231S_1, PERSON_CS1231S_2,
            PERSON_MA1101R, PERSON_SCIENCECOMP, PERSON_COMPUTING, PERSON_COMPUTING_SCIENCE);

    public static AddressBook buildTestIntegrationAddressBook() {
        AddressBook addressBook = new AddressBook();
        addressBook.setPersons(TEST_PERSONS);
        return addressBook;
    }

    public static ContactTagIntegrationManager buildTestContactTagIntegrationManager() {
        return new ContactTagIntegrationManager(buildTestIntegrationAddressBook(), buildTestTree());
    }

    @Test
    public void getAllPersonsUnderTag_validTag_success() {
        ContactTagIntegrationManager manager = buildTestContactTagIntegrationManager();
        Set<Person> expectedSet1 = Set.of(PERSON_COMPUTING_SCIENCE, PERSON_COMPUTING, PERSON_SCIENCECOMP,
                PERSON_CS1231S_1, PERSON_CS1231S_2, PERSON_MA1101R);
        assertEquals(expectedSet1, manager.getAllPersonsUnderTag(TAG_COMPUTING));
        Set<Person> expectedSet2 = Set.of(PERSON_SCIENCECOMP, PERSON_CS1231S_1, PERSON_CS1231S_2, PERSON_MA1101R);
        assertEquals(expectedSet2, manager.getAllPersonsUnderTag(TAG_SCIENCE_COMP));
        Set<Person> expectedSet3 = Set.of(PERSON_MA1101R);
        assertEquals(expectedSet3, manager.getAllPersonsUnderTag(TAG_MA1101R));
    }

    @Test
    public void getAllPersonsUnderTag_tagNotInTree_emptySetReturned() {
        assertTrue(buildTestContactTagIntegrationManager().getAllPersonsUnderTag(TAG_CS2040S_NOT_TREE).isEmpty());
    }


    @Test
    public void deleteTag_validTag_success() {
        ContactTagIntegrationManager manager = buildTestContactTagIntegrationManager();
        manager.deleteTag(TAG_SCIENCE_COMP);
        Person expectedScienceCompPerson = new PersonBuilder(PERSON_SCIENCECOMP)
                .withTags().build();
        assertTrue(manager.getAddressBook().hasPerson(expectedScienceCompPerson));
        assertTrue(manager.getAddressBook().getPersonsWithTag(TAG_SCIENCE_COMP).isEmpty());
    }

    @Test
    public void deleteTag_tagNotInTree_noChange() {
        ContactTagIntegrationManager manager = buildTestContactTagIntegrationManager();
        manager.deleteTag(TAG_CS2040S_NOT_TREE);
        assertEquals(manager, buildTestContactTagIntegrationManager());
    }

    @Test
    public void deleteTagRecursive_validTag_success() {
        ContactTagIntegrationManager manager = buildTestContactTagIntegrationManager();
        manager.deleteTagRecursive(TAG_SCIENCE_COMP);
        Person expectedPersonMA1101R = new PersonBuilder(PERSON_MA1101R).withTags().build();
        Person expectedPersonScienceComp = new PersonBuilder(PERSON_SCIENCECOMP).withTags().build();
        assertTrue(manager.getAddressBook().hasPerson(expectedPersonMA1101R));
        assertTrue(manager.getAddressBook().hasPerson(expectedPersonScienceComp));
    }

    @Test
    public void deleteTagRecursive_tagNotInTree_noChange() {
        ContactTagIntegrationManager manager = buildTestContactTagIntegrationManager();
        manager.deleteTagRecursive(TAG_CS2040S_NOT_TREE);
        assertEquals(manager, buildTestContactTagIntegrationManager());
    }

    @Test
    public void deleteTagAndDirectContacts_validTag_success() {
        ContactTagIntegrationManager manager = buildTestContactTagIntegrationManager();
        int expectedSize = manager.getAddressBook().getPersonList().size()
               - manager.getAddressBook().getPersonsWithTag(TAG_SCIENCE_COMP).size();
        manager.deleteTagAndDirectContacts(TAG_SCIENCE_COMP);
        assertEquals(expectedSize, manager.getAddressBook().getPersonList().size());
    }

    @Test
    public void deleteTagAndDirectContacts_tagNotInTree_noChange() {
        ContactTagIntegrationManager manager = buildTestContactTagIntegrationManager();
        manager.deleteTagAndDirectContacts(TAG_CS2040S_NOT_TREE);
        assertEquals(manager, buildTestContactTagIntegrationManager());
    }

    @Test
    public void deleteTagAndDirectContactsRecursive_validTag_success() {
        ContactTagIntegrationManager manager = buildTestContactTagIntegrationManager();
        int expectedSize = manager.getAddressBook().getPersonList().size()
                - manager.getAllPersonsUnderTag(TAG_COMPUTING).size();
        manager.deleteTagAndDirectContactsRecursive(TAG_COMPUTING);
        assertEquals(expectedSize, manager.getAddressBook().getPersonList().size());
    }

    @Test
    public void deleteTagAndDirectContactsRecursive_tagNotInTree_noChange() {
        ContactTagIntegrationManager manager = buildTestContactTagIntegrationManager();
        manager.deleteTagAndDirectContactsRecursive(TAG_CS2040S_NOT_TREE);
        assertEquals(manager, buildTestContactTagIntegrationManager());
    }


}
