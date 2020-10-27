package seedu.address.logic.commands.tags;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Adds a new tag to the system. The tag can be assigned sub-tags and/or contacts.
 */
public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.ADD.toString();

    public static final String COMMAND_TYPE = CommandType.TAG.toString();

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE
            + ": Adds a new tag to Athena.\n\n"
            + "Parameters:\n"
            + PREFIX_NAME + "TAG_NAME\n"
            + "[" + PREFIX_INDEX + "CONTACT_INDEX]...\n"
            + "[" + PREFIX_TAG + "SUB_TAG]...\n\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_TYPE + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_INDEX + "3 "
            + PREFIX_TAG + "cs2103";

    public static final String MESSAGE_SUCCESS = "New tag added: %s";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag has already been created!";
    public static final String MESSAGE_INDEX_FAILURE = "Assigned indices do not exist in the currently shown list!";
    public static final String MESSAGE_TAG_FAILURE = "Assigned tags do not exist!";
    public static final String MESSAGE_FAILURE = "Please enter a tag name and at least one index or sub-tag "
            + "to be assigned to the newly created tag. You can refer to the command below.\n" + MESSAGE_USAGE;

    private final Tag tagToAdd;
    private final Set<Tag> subTagSet;
    private final Set<Index> indicesSet;

    /**
     * Creates an AddTagCommand that creates the specified {@code tagToAdd} with {@code subTagSet} as its subtags and
     * {@code personSet} as persons directly under this tag.
     */
    public AddTagCommand(Tag tagToAdd, Set<Tag> subTagSet, Set<Index> indicesSet) {
        this.tagToAdd = tagToAdd;
        this.subTagSet = Set.copyOf(subTagSet);
        this.indicesSet = Set.copyOf(indicesSet);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTag(tagToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
        }

        List<Person> list = model.getSortedFilteredPersonList();
        boolean invalidIndices = indicesSet.stream()
                .anyMatch(index -> index.getZeroBased() >= list.size());
        if (invalidIndices) {
            throw new CommandException(MESSAGE_INDEX_FAILURE);
        }

        boolean invalidTags = subTagSet.stream()
                .anyMatch(tag -> !model.hasTag(tag));
        if (invalidTags) {
            throw new CommandException(MESSAGE_TAG_FAILURE);
        }

        subTagSet.stream().forEach(subTag -> model.addSubTagTo(tagToAdd, subTag));
        indicesSet.stream().forEach(index -> model.addPersonToTag(tagToAdd, list.get(index.getZeroBased())));

        return new CommandResult(String.format(MESSAGE_SUCCESS, tagToAdd));

    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else if (!(object instanceof AddTagCommand)) {
            return false;
        } else {
            AddTagCommand other = (AddTagCommand) object;
            return other.tagToAdd.equals(tagToAdd)
                    && other.indicesSet.equals(indicesSet)
                    && other.subTagSet.equals(subTagSet);
        }
    }

}
