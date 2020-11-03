package seedu.address.ui;

import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.reminder.Reminder;


public class ReminderWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(IntroWindow.class);
    private static final String FXML = "ReminderWindow.fxml";

    private Logic logic;

    @javafx.fxml.FXML
    private Label reminderMessage;


    /**
     * Creates a new ReminderWindow.
     */
    public ReminderWindow(Logic logic) {
        super(FXML, new Stage());
        System.out.println(logic == null);
        this.logic = logic;

        reminderMessage.setWrapText(true);
        reminderMessage.setText(buildAlertMessage());
    }

    /**
     * Shows the intro window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing upcoming reminders");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Hides the reminder window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the reminder window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Builds the string for the pop up reminder window.
     */
    public String buildAlertMessage() {
        String alertMessage = "Here are your active reminders: \n";
        System.out.println(logic == null);
        ObservableList<Reminder> reminders = logic.getFilteredReminderList();
        if (reminders.size() == 0) {
            return "You currently do not have any reminders";
        } else {
            int counter = 1;
            for (Reminder r: reminders) {
                if (r.getReminderDate().getTime().toLocalDate().isBefore(LocalDate.now())
                        || r.getReminderDate().getTime().toLocalDate().isEqual(LocalDate.now())) {
                    alertMessage += counter + ") " + r.toString() + "\n";
                }
            }
        }
        return alertMessage;
    }

}
