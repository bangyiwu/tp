package seedu.address.logic.parser.tags;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.tags.AddTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

public class AddTagCommandParserTest {

    private static final String TAG_NAME_VALID = " n/testtag";
    private static final Tag TAG_VALID = new Tag("testtag");
    private static final String TAG_NAME_INVALID = "n/   ";

    private static final String INDEX_STRING_ONE = " i/1";
    private static final String INDEX_STRING_TWO = " i/2";
    private static final String INDEX_STRING_INVALID = " i/c";
    private static final Set<Index> INDEX_SET = Set.of(Index.fromOneBased(1), Index.fromOneBased(2));

    private static final String TAG_STRING_CS2030 = " t/cs2030";
    private static final String TAG_STRING_CS2040 = " t/cs2040";
    private static final String TAG_STRING_INVALID = " t/+-*/";
    private static final Set<Tag> TAG_SET = Set.of(new Tag("cs2030"), new Tag("cs2040"));

    private AddTagCommandParser parser = new AddTagCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        // Only indices specified
        AddTagCommand expectedCommand = new AddTagCommand(TAG_VALID, Set.of(), INDEX_SET);
        assertParseSuccess(parser, TAG_NAME_VALID + INDEX_STRING_ONE
                + INDEX_STRING_TWO, expectedCommand);

        // Only tags specified
        expectedCommand = new AddTagCommand(TAG_VALID, TAG_SET, Set.of());
        assertParseSuccess(parser, TAG_NAME_VALID + TAG_STRING_CS2030
                + TAG_STRING_CS2040, expectedCommand);

        // Both tags and indices specified
        expectedCommand = new AddTagCommand(TAG_VALID, TAG_SET, INDEX_SET);
        assertParseSuccess(parser, TAG_NAME_VALID + INDEX_STRING_TWO + " "
                + TAG_STRING_CS2040 + INDEX_STRING_ONE + TAG_STRING_CS2030, expectedCommand);

    }

    @Test
    public void parse_invalidTagNameInput_parseExceptionThrown() {
        assertThrows(ParseException.class, () ->
                parser.parse(TAG_NAME_INVALID));
        assertThrows(ParseException.class, () ->
                parser.parse(TAG_NAME_INVALID + INDEX_STRING_ONE + TAG_STRING_CS2030));
    }

    @Test
    public void parse_invalidOptionalArguments_parseExceptionThrown() {
        assertThrows(ParseException.class, () ->
                parser.parse(TAG_NAME_VALID + INDEX_STRING_INVALID + INDEX_STRING_ONE));
        assertThrows(ParseException.class, () ->
                parser.parse(TAG_NAME_VALID + INDEX_STRING_ONE + TAG_STRING_INVALID));
    }

    @Test
    public void parse_noOptionalArguments_parseExceptionThrown() {
        assertThrows(ParseException.class, () ->
                parser.parse(TAG_NAME_VALID));
    }


}
