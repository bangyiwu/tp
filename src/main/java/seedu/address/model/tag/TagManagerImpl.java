package seedu.address.model.tag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.person.Person;

/**
 * Concrete implementation class of {@code TagManager} that uses a {@code HashMap}.
 */
public class TagManagerImpl implements TagManager {

    private static final String MESSAGE_ERROR_PERSON_NOT_FOUND = "Person instance not found in tag manager!";

    private final Map<Tag, Set<Person>> tagPersonSetMap;

    public TagManagerImpl() {
        tagPersonSetMap = new HashMap<>();
    }

    @Override
    public Set<Person> getPersonsUnderTag(Tag tag) {
        return tagPersonSetMap.get(tag) == null ? Set.of() : tagPersonSetMap.get(tag);
    }

    @Override
    public Set<Tag> getTags() {
        return tagPersonSetMap.keySet();
    }

    @Override
    public boolean hasTag(Tag tag) {
        return tagPersonSetMap.containsKey(tag);
    }

    /**
     * Removes all references in {@code tagPersonSetMap} to this {@code person} based on the {@code tag}s it has.
     */
    private void removeAllTagReferencesOfPerson(Person person) {
        assert person != null : "Invalid person object, person is a null.";

        for (Tag oldTag : person.getTags()) {
            Set<Person> tagSet = tagPersonSetMap.get(oldTag);
            Objects.requireNonNull(tagSet);
            if (!tagSet.contains(person)) {
                throw new NoSuchElementException(MESSAGE_ERROR_PERSON_NOT_FOUND);
            }

            tagSet.remove(person);
            if (tagSet.size() == 0) {
                tagPersonSetMap.remove(oldTag);
            }
        }
    }

    /**
     * Adds all references to {@code tagPersonSetMap} from this {@code person} based on the {@code tag}s it has.
     */
    private void addAllTagReferencesOfPerson(Person person) {
        assert person != null : "Invalid person object, person is a null.";

        for (Tag newTag : person.getTags()) {
            Optional.ofNullable(tagPersonSetMap.get(newTag))
                    .ifPresentOrElse(
                        set -> set.add(person), () -> tagPersonSetMap.put(newTag, new HashSet<>(List.of(person))));
        }
    }

    @Override
    public void updateExistingPersonTags(Person oldPerson, Person newPerson) {
        removeAllTagReferencesOfPerson(oldPerson);
        addAllTagReferencesOfPerson(newPerson);
    }

    @Override
    public void addNewPersonTags(Person person) {
        addAllTagReferencesOfPerson(person);
    }

    @Override
    public void deletePersonTags(Person person) {
        removeAllTagReferencesOfPerson(person);
    }

    @Override
    public void clear() {
        tagPersonSetMap.clear();
    }

    @Override
    public void copy(TagManager otherTagManager) {
        HashMap<Tag, Set<Person>> newMap = new HashMap<>();
        for (Tag tag : otherTagManager.getTags()) {
            newMap.put(tag, new HashSet<>(otherTagManager.getPersonsUnderTag(tag)));
        }
        tagPersonSetMap.putAll(newMap);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof TagManagerImpl)) {
            return false;
        } else {
            return tagPersonSetMap.equals(((TagManagerImpl) o).tagPersonSetMap);
        }
    }

}
