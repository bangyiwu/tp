package seedu.address.logic.commands.events;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.comparators.EventComparator;
import seedu.address.model.Model;

/**
 * Sorts the currently displayed events in a specific order.
 * Index entered determines the specific order.
 */
public class SortEventCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.SORT.toString();

    public static final String COMMAND_TYPE = CommandType.EVENT.toString();

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE
            + ": Sorts the currently displayed list of events "
            + "by the index command entered:\n"
            + "1 sorts by alphabetical order of the events' descriptions,\n"
            + "2 sorts by alphabetical order of the events' time.\n\n"
            + "Parameters:\nINDEX (must be between 1 and 2)\n\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_TYPE + " 1 ";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d";

    private final Index index;

    /**
     * @param index the order in which to sort the event entries in Athena.
     */
    public SortEventCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.sortEvent(EventComparator.chooseComparator(index));

        return new CommandResult(indexMessage(index));
    }

    /**
     * Returns the appropriate console message for the index.
     */
    public String indexMessage(Index index) {
        int input = index.getOneBased();
        switch (input) {
        case 1:
            return "Sorted by description in alphabetical order";
        case 2:
            return "Sorted by time in chronological order";
        default:
            return "Invalid index entered, refer to below for the command's proper usage: "
                    + MESSAGE_USAGE;
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortEventCommand)) {
            return false;
        }

        // state check
        SortEventCommand e = (SortEventCommand) other;
        return index.equals(e.index);
    }

}
