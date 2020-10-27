package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an Events in Calendar.
 */
public class Event {
    private final Description description;
    private final Time time;

    /**
     * Every field must be present and not null.
     */
    public Event(Description description, Time time) {
        requireAllNonNull(description, time);
        this.description = description;
        this.time = time;
    }

    public Description getDescription() {
        return description;
    }

    public Time getTime() {
        return time;
    }

    /**
     * Returns true if both events have the same description
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, time);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getDescription().equals(getDescription())
                && otherEvent.getTime().equals(getTime());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" At: ")
                .append(getTime());
        return builder.toString();
    }
}
