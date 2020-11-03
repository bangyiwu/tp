package seedu.address.model.reminder;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import seedu.address.model.event.Event;
import seedu.address.model.event.Time;


public class Reminder {

    private final Event eventToRemind;
    private final Time reminderDate;


    /**
     * @param eventToRemind the event that you want the reminder for
     * @param daysInAdvance how many days in advance do you want to start getting reminders for this event
     */
    public Reminder(Event eventToRemind, int daysInAdvance) {
        this.eventToRemind = eventToRemind;
        this.reminderDate = new Time(eventToRemind.getTime().time.minusDays(daysInAdvance));
    }

    /**
     * @param eventToRemind the index of the event that you want the reminder for
     * @param date the starting date of when you will start getting reminders for this event
     */
    public Reminder(Event eventToRemind, String date) {
        this.eventToRemind = eventToRemind;
        this.reminderDate = new Time(date);
    }

    public Event getEventToRemind() {
        return eventToRemind;
    }

    public Time getReminderDate() {
        return reminderDate;
    }

    /**
     * Checks if both reminders are the same
     */
    public boolean isSameReminder (Reminder otherReminder) {
        if (otherReminder == this) {
            return true;
        }

        return otherReminder != null
                && otherReminder.getEventToRemind().equals(getEventToRemind());
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventToRemind, reminderDate);
    }

    /**
     * Calculates the difference between the current date and the date of the event.
     */
    public String countdownFromNow() {
        LocalDate now = LocalDate.now();
        long diff = ChronoUnit.DAYS.between(now, eventToRemind.getTime().time.toLocalDate());
        return String.valueOf(diff);
    }

    @Override
    public String toString() {
        return eventToRemind.getDescription().toString() + " in " + countdownFromNow() + " days "
                + "(Reminds " + ChronoUnit.DAYS.between(reminderDate.getTime().toLocalDate(),
                eventToRemind.getTime().time.toLocalDate())
                + " days in advance)";
    }

}
