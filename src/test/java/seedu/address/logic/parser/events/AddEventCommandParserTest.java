package seedu.address.logic.parser.events;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.contacts.AddContactCommand;
import seedu.address.logic.commands.events.AddEventCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Description;
import seedu.address.model.event.Time;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;

public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        ArrayList<Index> personsToAdd;
        try {
            personsToAdd = ParserUtil.parseIndexes(VALID_CONTACT_INDEX_LIST);
        } catch (ParseException pe) {
            throw new AssertionError("Parsing index list should not fail.", pe);
        }
        AddEventCommand.AddEventDescriptor expectedEventDescriptor1 = new AddEventCommand.AddEventDescriptor();
        expectedEventDescriptor1.setDescription(new Description(VALID_DESCRIPTION_BREAKFAST));
        expectedEventDescriptor1.setTime(new Time(VALID_TIME_BREAKFAST));
        AddEventCommand.AddEventDescriptor expectedEventDescriptor2 =
                new AddEventCommand.AddEventDescriptor(expectedEventDescriptor1);

        expectedEventDescriptor1.setPersonsToAdd(personsToAdd);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DESCRIPTION_DESC_BREAKFAST
                        + TIME_DESC_BREAKFAST + ADD_PERSON_DESC_1, new AddEventCommand(expectedEventDescriptor1));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, DESCRIPTION_DESC_LUNCH + DESCRIPTION_DESC_BREAKFAST
                        + TIME_DESC_BREAKFAST + ADD_PERSON_DESC_1, new AddEventCommand(expectedEventDescriptor1));

        // multiple time inputs - last time input accepted
        assertParseSuccess(parser, DESCRIPTION_DESC_BREAKFAST + TIME_DESC_LUNCH +
                        TIME_DESC_BREAKFAST + ADD_PERSON_DESC_1, new AddEventCommand(expectedEventDescriptor1));

        // multiple add person inputs - last add person input accepted
        assertParseSuccess(parser, DESCRIPTION_DESC_BREAKFAST + TIME_DESC_LUNCH + TIME_DESC_BREAKFAST
                + ADD_PERSON_DESC_2 + ADD_PERSON_DESC_1, new AddEventCommand(expectedEventDescriptor1));

        expectedEventDescriptor2.setWildCardAdd();

        // wildcard add persons
        assertParseSuccess(parser, DESCRIPTION_DESC_BREAKFAST + TIME_DESC_BREAKFAST
                + ADD_PERSON_WILD_DESC, new AddEventCommand(expectedEventDescriptor2));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description
        assertParseFailure(parser, INVALID_DESCRIPTION_DESC + TIME_DESC_LUNCH, Description.MESSAGE_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser, DESCRIPTION_DESC_LUNCH + INVALID_TIME_DESC, Time.MESSAGE_CONSTRAINTS);

        // invalid contact index list
        assertParseFailure(parser, DESCRIPTION_DESC_LUNCH + TIME_DESC_LUNCH + INVALID_ADD_PERSON_DESC,
                ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid wildcard add persons
        assertParseFailure(parser, DESCRIPTION_DESC_LUNCH + TIME_DESC_LUNCH + INVALID_ADD_PERSON_WILD_DESC,
                ParserUtil.MESSAGE_INVALID_INDEX);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + DESCRIPTION_DESC_LUNCH + TIME_DESC_LUNCH
                        + ADD_PERSON_DESC_1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_FAILURE));
    }
}
