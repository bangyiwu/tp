package seedu.address.model.tag;

import java.util.Collection;
import java.util.Set;

import seedu.address.model.person.Person;

/**
 * Interface dictating the behavior of a {@code TagManager} class that keeps track of {@code Person}s
 * under each {@code Tag}.
 */
public interface TagManager {

    /**
     * Returns a set of all {@code Person}s that has this tag.
     *
     * @param tag common {@code tag} that all persons we are looking for has.
     * @return set of {@code Person}s that has {@tag}.
     */
    Set<Person> getPersonsUnderTag(Tag tag);

    /**
     * Returns a set of all {@code Tag}s found in the {@code TagManager}.
     *
     * @return a set of {@code Tag}s.
     */
    Set<Tag> getTags();

    /**
     * Returns true if {@code tag} is found with at least one contact under it.
     */
    boolean hasTag(Tag tag);

    /**
     * Updates the current tag references in the tag manager.
     * Removes all references to the {@code oldPerson} and adds new references to the {@code newPerson}.
     *
     * @param oldPerson {@code Person} to remove references to.
     * @param newPerson {@code Person} to add new references to.
     */
    void updateExistingPersonTags(Person oldPerson, Person newPerson);

    /**
     * Updates the tag manager with references to a newly added {@code person}.
     *
     * @param person new {@code person} added.
     */
    void addNewPersonTags(Person person);

    /**
     * Updates the tag manager with references to all {@code person} in {@code persons}.
     *
     * @param persons new {@code persons} added.
     */
    default void addNewPersonsTags(Collection<Person> persons) {
        for (Person person : persons) {
            addNewPersonTags(person);
        }
    }

    /**
     * Updates the current {@code tag} references in the {@code tag manager} to remove all references to {@code person}.
     *
     * @param person person being deleted.
     */
    void deletePersonTags(Person person);

    /**
     * Copies the content of the {@code otherTagManager} into this {@code TagManager}.
     * The new copy must use a separate underlying structure, only sharing references
     * to the same {@code Person}s and {@code Tag}s.
     */
    void copy(TagManager otherTagManager);

    /**
     * Clears the TagManager of all current tagging.
     */
    void clear();

}
