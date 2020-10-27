package seedu.address.logic.parser.contacts;


import seedu.address.logic.commands.contacts.DeleteContactByTagCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteContactCommand object
 */
public class DeleteContactByTagCommandParser implements Parser<DeleteContactByTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteContactCommand
     * and returns a DeleteContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteContactByTagCommand parse(String args) {
        assert Tag.isValidTagName(args.trim()) : "User input is not a valid tag name";
        Tag tagForDeletion = new Tag(args.trim());
        return new DeleteContactByTagCommand(tagForDeletion);
    }

}
