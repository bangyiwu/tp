package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BREAKFAST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_LUNCH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Calendar;
import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event MEETING = new EventBuilder().withDescription("CS2103 Meeting")
            .withTime("12-10-2020 12:00").build();
    public static final Event CONSULTATION = new EventBuilder().withDescription("IS1103 Consultation")
            .withTime("15-11-2020 13:15").build();
    public static final Event GATHERING = new EventBuilder().withDescription("Family Gathering").build();
    public static final Event DINNER = new EventBuilder().withDescription("Welcome Dinner").build();
    public static final Event SHOPPING = new EventBuilder().withDescription("Shopping").build();
    public static final Event WEBINAR = new EventBuilder().withDescription("Webinar Meeting").build();
    public static final Event OUTING = new EventBuilder().withDescription("Family Outing").build();

    // Manually added
    public static final Event LESSON = new EventBuilder().withDescription("Piano Lesson").build();
    public static final Event PROJECT = new EventBuilder().withDescription("Project Discussion").build();

    // Manually added - Event's details found in {@code CommandTestUtil}
    public static final Event LUNCH = new EventBuilder().withDescription(VALID_DESCRIPTION_LUNCH).build();
    public static final Event BREAKFAST = new EventBuilder().withDescription(VALID_DESCRIPTION_BREAKFAST).build();

    public static final String KEYWORD_MATCHING_MEETING = "Meeting"; // A keyword that matches MEETING

    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns an {@code TimeBook} with all the typical events.
     */
    public static Calendar getTypicalCalendar() {
        Calendar ab = new Calendar();
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(MEETING, CONSULTATION, GATHERING, DINNER, SHOPPING, WEBINAR, OUTING));
    }
}
