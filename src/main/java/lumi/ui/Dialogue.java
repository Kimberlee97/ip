package lumi.ui;

import lumi.tasks.Task;

/**
 * Handles all user interaction messages for the application.
 * Provides methods to display greeting, goodbye, delete, mark, unmark and error messages.
 */
public class Dialogue {
    private static final String LOGO = "LUMI (˶ˆᗜˆ˵)";

    /**
     * Returns the logo
     */
    public String getLogo() {
        return LOGO;
    }

    /**
     * Returns a greeting message.
     */
    public String greet() {
        return "Hello from " + LOGO + "\nWhat can I do for you?";
    }

    /**
     * Returns a goodbye message.
     */
    public String sendGoodbye() {
        return "Bye! See you next time :>";
    }

    /**
     * Returns a delete message with the description of the deleted task.
     * @param task The task that had been deleted.
     */
    public String printDeleteMessage(Task task) {
        return "This task has been deleted: " + task.toString();
    }

    /**
     * Returns a message after the given {@link Task} has been marked as done.
     * @param task The task that had been marked.
     */
    public String printMarkMessage(Task task) {
        return "Yay! I've marked your task done: " + task;
    }

    /**
     * Returns a message after the given {@link Task} has been unmarked.
     * @param task The task that had been unmarked.
     */
    public String printUnmarkMessage(Task task) {
        return "Oki, I've marked your task undone: " + task;
    }

    /**
     * Returns an error message for a failed load.
     * @param e The exception thrown from the failed load attempt.
     */
    public String showLoadingError(Exception e) {
        return "Unable to load your file: " + e.getMessage() + "\n" + "Starting with a new list...";
    }
}
