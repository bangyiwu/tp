package seedu.address.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.tag.TagManagerImplTest.BENSON_EDITED;
import static seedu.address.model.tag.TagManagerImplTest.TAG_FRIENDS;
import static seedu.address.model.tag.TagManagerImplTest.TAG_MODULE;
import static seedu.address.model.tag.TagManagerImplTest.TAG_OWES_MONEY;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

public class AddressBookTagManagerIntegrationTest {

    private static final AddressBook addressBook = new AddressBook();

    @Test
    public void getPersonsWithTag_newPerson_success() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.getPersonsWithTag(TAG_FRIENDS).contains(ALICE));
    }

    @Test
    public void getPersonWithTag_editPerson_success() {
        addressBook.addPerson(BENSON);
        addressBook.setPerson(BENSON, BENSON_EDITED);

        assertTrue(addressBook.getPersonsWithTag(TAG_OWES_MONEY).isEmpty());
        assertTrue(addressBook.getPersonsWithTag(TAG_MODULE).contains(BENSON_EDITED));

        // Checks that the Person object under the friends tag is the new edited Person object instead of the old.
        assertFalse(addressBook.getPersonsWithTag(TAG_FRIENDS).contains(BENSON));
        assertTrue(addressBook.getPersonsWithTag(TAG_FRIENDS).contains(BENSON_EDITED));
    }

    @Test
    public void getPersonWithTag_deletePerson_success() {
        addressBook.addPerson(ALICE);
        addressBook.removePerson(ALICE);
        assertFalse(addressBook.getPersonsWithTag(TAG_FRIENDS).contains(ALICE));
    }
}
