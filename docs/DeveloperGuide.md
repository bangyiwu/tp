---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<img src="images/ArchitectureDiagram.png" width="450" />

The ***Architecture Diagram*** given above explains the high-level design of the App. Given below is a quick overview of each component.

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/AY2021S1-CS2103T-W10-4/tp/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

**`Main`** has two classes called [`Main`](https://github.com/AY2021S1-CS2103T-W10-4/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2021S1-CS2103T-W10-4/tp/tree/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

Each of the four components,

* defines its *API* in an `interface` with the same name as the Component.
* exposes its functionality using a concrete `{Component Name}Manager` class (which implements the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class which implements the `Logic` interface.

![Class Diagram of the Logic Component](images/LogicClassDiagram.png)

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

The sections below give more details of each component.

### UI component

![Structure of the UI Component](images/UiClassDiagram.png)

**API** :
[`Ui.java`](https://github.com/AY2021S1-CS2103T-W10-4/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2021S1-CS2103T-W10-4/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2021S1-CS2103T-W10-4/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Listens for changes to `Model` data so that the UI can be updated with the modified data.

### Logic component

![Structure of the Logic Component](images/LogicClassDiagram.png)

**API** :
[`Logic.java`](https://github.com/AY2021S1-CS2103T-W10-4/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
1. This results in a `Command` object which is executed by the `LogicManager`.
1. The command execution can affect the `Model` (e.g. adding a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.
1. In addition, the `CommandResult` object can also instruct the `Ui` to perform certain actions, such as displaying help to the user.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

### Model component

![Structure of the Model Component](images/ModelClassDiagram.png)

**API** : [`Model.java`](https://github.com/AY2021S1-CS2103T-W10-4/tp/tree/master/src/main/java/seedu/address/model/Model.java)

The `Model` encapsulates all data required for Athena to run. In particular, it stores all contacts (as `person` objects), tags and events (to be implemented).
It is meant to fulfill the Facade pattern as the Facade class by hiding the individual classes and forcing higher level components like the `Command`s and `Logic` to interact only with `Model`.

* stores a `UserPref` object that represents the user’s preferences.
* stores the `AddressBook` data of contacts represented by `Person`s.
    * `AddressBook` controls the list of contacts, any changes has to go through this class.
    * `TagManager` is a separate class used to keep track which `Person`s fall under a specific `Tag`. This was done to 
    avoid cyclic dependency between `Tag` and `Person`. Whenever `AddressBook` makes an edit to the `Person` list, it should also update the `TagManager`.
* stores the `TagTree` which keeps track of tag-to-tag relationships.
* exposes an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

This design choice is aimed at properly encapsulating three separate functionalities.
1. `AddressBook` tracks the state of the `Person` objects only. 
2. `TagTree` tracks the tag-to-tag relationships only. In particular, it keeps track of which set of `tag`s are sub-`tag`s 
of other `tag`s. 
3. `TagManager` tracks the `Person` objects directly under each `Tag`. A `Person` object has to have the `tag` for it to be recorded in the `TagManager`.
`AddressBook` should associate with the `TagManager` such that whenever there is a change to the list of `Person`s, the tag-to-person changes are reflected immediately in `TagManager`.

In separating out these three components, it allows higher-level components to easily query for the necessary, accurate information and/or make the appropriate changes to `Person`s, `Tag`s, or both.
For example, contact-only commands such as `addContactCommand`, `editContactCommand` would only need to interact with the `AddressBook` through the `Model` facade.
For tag-only commands such as `editTagCommand`, when we assign sub-tags to a specific tag, it will only need to invoke `Model` methods that belong to `TagTree`.

Lastly, we have the `ContactTagIntegrationManager` which aims to provide a fixed set of methods relating to both `Tag`s and `Person`s (e.g. deleting all contacts with a certain tag). 
The `ContactTagIntegrationManager` abstracts out these methods and handles the implementation and necessary changes to the `TagTree` and `AddressBook` so that higher-level modules can make use of these methods.
For more details on exactly which types of `Person`-`Tag` methods are supported, the documentation for this class will be available [here]().


### Storage component

![Structure of the Storage Component](images/StorageClassDiagram.png)

**API** : [`Storage.java`](https://github.com/AY2021S1-CS2103T-W10-4/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the address book data in json format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

![CommitActivityDiagram](images/CommitActivityDiagram.png)

#### Design consideration:

##### Aspect: How undo & redo executes

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation o=-f each individual command are correct.

_{more aspects and alternatives to be added}_

### Sort contacts feature

Implementation

The implemented sorting of contacts mechanism is facilitated by SortContactCommand. It extends Command.

SortContactCommand#execute() : Does validity check of input and then sorts the contact according to user input

These operations are exposed in the Command interface as Command#execute(). The following sequence diagram shows
how the sorting operation works.

Design consideration:
The sorting function should not modify the addressbook permanently, it should just create a temporary display. This is 
such that users can go back to the original sorting by date added easily. It also makes undoing a sort easier. If the
user wishes to sort the address book permanently, we are implementing a permanent sort function as well. 



--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of contacts and events
* requires seamless integration between contact and event tracking (vastly differing participants for different events, for instance)
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage contacts and events faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                  | I want to …​                       | So that I can…​                                                                                        |
| -------- | ------------------------------------------- | ------------------------------------- | --------------------------------------------------------------------------------------------------------- |
| `* * *`  | new user                                    | see usage instructions                | understand how to get started by adding new contacts, removing sample ones and learning advanced commands |
| `* * *`  | user                                        | add a new contact                     | keep track of my contacts                                                                                 |
| `* * *`  | user                                        | delete a contact                      | remove contacts that I no longer need, keeping my contact storage neat and uncluttered                    |
| `* * *`  | user                                        | view my contacts in an ordered manner | view the details of my contacts                                                                           |
| `* * *`  | user                                        | add a new event                       | keep track of my events                                                                                   |
| `* * *`  | user                                        | delete an event                       | remove events that I no longer need, keeping my event storage neat and uncluttered                        |
| `* * *`  | user                                        | view my events in an ordered manner   | view the details of my events                                                                             |
| `* *`    | user                                        | search for a contact                  | locate details of contacts without having to go through the entire list                                   |
| `* *`    | user                                        | edit a contact's details              | change outdated information without having to delete and re-add contacts                                  |
| `* *`    | user with many contacts in the address book | sort contacts by name                 | so that I can view my contacts in a more consistent manner and find the contacts I want quickly           |
| `* *`    | user                                        | search for an event                   | locate details of events without having to go through the entire list                                     |
| `* *`    | user                                        | edit an event's details               | change outdated information without having to delete and re-add events                                    |
| `* *`    | user with many events in the address book   | sort events by name                   | so that I can view my events in a more consistent manner and find the events I want quickly               |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `Athena` and the **Actor** is the `user`, unless specified otherwise)

**Use case: View introduction**

1.  User opens Athena for the first time

1.  Athena displays an introduction message, with a guide on how to use basic commands.

    Use case ends.


**Use case: Add a contact**

**MSS**

1.  User requests to add a contact

    Use case ends.

**Extensions**

* 1a. User input missing one or multiple fields
    * 1a1. Athena shows an error message.

  Use case ends.

**Use case: Delete a contact**

**MSS**

1.  User requests to list contacts
2.  Athena shows a list of contacts
3.  User requests to delete a specific contact in the list
4.  Athena deletes the contact

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Athena shows an error message.

      Use case resumes at step 2.

**Use case: Sort through contacts**

**MSS**

1.  User requests to list contacts
2.  Athena shows a list of contacts in default order
3.  User requests to sort the contacts in the list
4.  Athena displays the contacts in the requested order

    Use case ends.

**Extensions**

* 2a. The list is empty.
    Use case ends.

* 3a. The given index is invalid.

    * 3a1. Athena shows an error message.

      Use case resumes at step 2.

**Use case: Editing contact information**

Preconditions: The contact the user wishes to edit is displayed on the UI.

**MSS**

1.  User uses the edit command to inform the system of which fields of the contact they wish to change.
2.  Athena updates the contact information.
3.  Athena sends an acknowledgement message to the user to notify of the edits made.

    Use case ends.

**Extensions**

* 1a. Tag to be removed does not exist.
    * 1a1. Athena notifies the user of the error.
    * 1a2. Athena aborts the command without making changes.

      Use case ends.

**Use case: Searching for contacts**

**MSS**

1. User uses the find command and informs the systems of keywords and additional fields the contact has to match.
2. Athena displays a list of contacts that matches the keywords and contains the additional fields.

    Use case ends.

**Extensions**

* 2a. The list is empty.
    * 2a1. Athena informs the user that their search did not come up with any valid matches.

       Use case ends.

**Use case: Add an event**

**MSS**

1.  User requests to add an event

    Use case ends.

**Extensions**

* 2a. The description and/or date time is missing.

    * 2a1. Athena shows an error message.

      Use case ends.

* 3a. The date time format is not accepted.

    * 3a1. Athena shows an error message.
       
      Use case ends.
         
**Use case: Delete an event**

**MSS**

1.  User requests to view events
2.  Athena shows a list of events
3.  User requests to delete a specific event in the list
4.  Athena deletes the event

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Athena shows an error message.

      Use case resumes at step 2.
      
**Use case: Edit an event**

**MSS**

1.  User requests to view events
2.  Athena shows a list of events
3.  User requests to edit a specific event in the list
4.  Athena edits the event

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Athena shows an error message.

      Use case resumes at step 2.
      
* 4a. The persons to be added or removed is not found.

    * 4a1. Athena shows an error message.

      Use case resumes at step 2.

#### **Use case: search for events**

**MSS**

1.  User requests to search for a particular event 
2.  Athena shows a list of events that match the criteria (e.g. contain a certain keyword) 

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given keyword is invalid.

    * 3a1. Athena shows an error message.

      Use case resumes at step 2.
      
#### **Use case: view all saved events**

**MSS**

1.  User requests to view all events recorded 
2.  Athena shows a list of events  

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
1.  Should be able to hold up to 1000 contacts and events without a noticeable sluggishness in performance for typical usage.
1.  Should be sufficiently intuitive for new or inexperienced users to understand and navigate.
1.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
1.  Should not require an internet connection (bar downloading the app)

*{More to be added}*

### Glossary

* **Contact**: A person to be tracked by Athena; comprises a name, phone number, email and address, as well as an arbitrary quantity of tags
* **Event**: An event to be tracked by Athena; comprises a name and a date and time, as well as an arbitrary quantity of tags
* **Mainstream OS**: Windows, Linux, Unix, OS-X 

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
