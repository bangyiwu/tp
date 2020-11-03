package seedu.address.logic.commands.reminders;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.reminder.Reminder;

/**
 * Sets a custom reminder for an event identified using its displayed index from the Athena.
 */
public class RemindEventCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.ADD.toString();

    public static final String COMMAND_TYPE = CommandType.REMINDER.toString();

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reminds you of an event \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1" + " in/10";

    public static final String MESSAGE_REMIND_EVENT_SUCCESS = "Reminder set for event %d days prior: ";

    public static final String MESSAGE_FAILURE = "Please input a date with the prefix in/";

    private final Index targetIndex;
    private final int daysInAdvance;

    /**
     * @param targetIndex the index of the event that you want the reminder for
     * @param daysInAdvance how many days in advance do you want to start getting reminders for this event
     */
    public RemindEventCommand(Index targetIndex, int daysInAdvance) {
        this.targetIndex = targetIndex;
        this.daysInAdvance = daysInAdvance;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getSortedFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventForReminder = lastShownList.get(targetIndex.getZeroBased());
        Event modelEvent = new Event(eventForReminder.getDescription(), eventForReminder.getTime(), new HashSet<>());
        Reminder toAdd = new Reminder(modelEvent, daysInAdvance);
        LocalDate now = LocalDate.now();
        if (now.plusDays(daysInAdvance).isAfter(modelEvent.getTime().time.toLocalDate())) {
            throw new CommandException("You cannot set a reminder for after the event is over.");
        } else if (model.hasReminder(toAdd)) {
            throw new CommandException("You already have an existing reminder for this event.");
        }

        model.addReminder(toAdd);

        return new CommandResult(String.format(MESSAGE_REMIND_EVENT_SUCCESS, daysInAdvance)
                + "\n" + eventForReminder.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemindEventCommand // instanceof handles nulls
                && targetIndex.equals(((RemindEventCommand) other).targetIndex)); // state check
    }
}

