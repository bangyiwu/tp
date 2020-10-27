package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.logic.commands.contacts.ClearContactCommand;
import seedu.address.logic.commands.contacts.ListContactCommand;
import seedu.address.logic.commands.events.ClearEventCommand;
import seedu.address.logic.commands.events.ListEventCommand;
import seedu.address.logic.commands.events.ListReminderEventCommand;
import seedu.address.logic.commands.events.ShowReminderEventCommand;
import seedu.address.logic.commands.general.ExitCommand;
import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.commands.general.IntroCommand;
import seedu.address.logic.commands.tags.ListTagCommand;
import seedu.address.logic.parser.contacts.AddContactCommandParser;
import seedu.address.logic.parser.contacts.DeleteContactByTagCommandParser;
import seedu.address.logic.parser.contacts.DeleteContactCommandParser;
import seedu.address.logic.parser.contacts.EditContactCommandParser;
import seedu.address.logic.parser.contacts.FindContactCommandParser;
import seedu.address.logic.parser.contacts.PermaSortContactCommandParser;
import seedu.address.logic.parser.contacts.SortContactCommandParser;
import seedu.address.logic.parser.events.AddEventCommandParser;
import seedu.address.logic.parser.events.DeleteEventCommandParser;
import seedu.address.logic.parser.events.EditEventCommandParser;
import seedu.address.logic.parser.events.FindEventCommandParser;
import seedu.address.logic.parser.events.RemindEventCommandParser;
import seedu.address.logic.parser.events.SortEventCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.tags.AddTagCommandParser;
import seedu.address.logic.parser.tags.ViewTagCommandParser;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile(
            "(?<commandWord>\\S+)\\s*(?<commandType>[\\S]*)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {

        assert userInput != null;

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final CommandWord commandWord = CommandWord.get(matcher.group("commandWord"));
        final CommandType commandType = CommandType.get(matcher.group("commandType"));
        final String arguments = matcher.group("arguments");

        switch (commandType) {

        case CONTACT:

            switch (commandWord) {

            case ADD:
                return new AddContactCommandParser().parse(arguments);

            case EDIT:
                return new EditContactCommandParser().parse(arguments);

            case CLEAR:
                return new ClearContactCommand();

            case DELETE:
                return new DeleteContactCommandParser().parse(arguments);

            case FIND:
                return new FindContactCommandParser().parse(arguments);

            case LIST:
                return new ListContactCommand();

            case SORT:
                return new SortContactCommandParser().parse(arguments);

            case PERMASORT:
                return new PermaSortContactCommandParser().parse(arguments);

            case DELETE_BY_TAG:
                return new DeleteContactByTagCommandParser().parse(arguments);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }

        case EVENT:

            switch (commandWord) {

            case ADD:
                return new AddEventCommandParser().parse(arguments);

            case EDIT:
                return new EditEventCommandParser().parse(arguments);

            case CLEAR:
                return new ClearEventCommand();

            case DELETE:
                return new DeleteEventCommandParser().parse(arguments);

            case FIND:
                return new FindEventCommandParser().parse(arguments);

            case LIST:
                return new ListEventCommand();

            case SORT:
                return new SortEventCommandParser().parse(arguments);

            case REMIND:
                return new RemindEventCommandParser().parse(arguments);

            case REMINDER:
                return new ListReminderEventCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }

        case TAG:

            switch (commandWord) {

            case ADD:
                return new AddTagCommandParser().parse(arguments);

            case LIST:
                return new ListTagCommand();

            case VIEW:
                return new ViewTagCommandParser().parse(arguments);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);

            }

        default:

            switch (commandWord) {

            case EXIT:
                return new ExitCommand();

            case HELP:
                return new HelpCommand();

            case INTRO:
                return new IntroCommand();

            case SHOW_REMINDER:
                return new ShowReminderEventCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }

}
