package lumi.ui;

import lumi.tasks.*;

/**
 * Handles all user interaction messages for the application.
 * Provides methods to display greeting, goodbye, delete, mark, unmark and error messages.
 */
public class UI {
    private static final String LOGO = "LUMI (˶ˆᗜˆ˵)";

    /**
     * Prints a greeting message.
     */
    public void greet() {
        System.out.println("Hello from " + LOGO + "\nWhat can I do for you?");
    }

    /**
     * Prints a goodbye message.
     */
    public void bye() {
        System.out.println("Bye! See you next time :>");
    }

    /**
     * Prints a delete message with the description of the deleted task.
     * @param task The task that had been deleted.
     */
    public void printDeleteMessage(Task task) {
        System.out.println("This task has been deleted: " + task.toString());
    }

    /**
     * Prints a message after the given {@link Task} has been marked as done.
     * @param task The task that had been marked.
     */
    public void printMarkMessage(Task task) {
        System.out.println("Yay! I've marked your task done: " + task);
    }

    /**
     * Prints a message after the given {@link Task} has been unmarked.
     * @param task The task that had been unmarked.
     */
    public void printUnmarkMessage(Task task) {
        System.out.println("Oki, I've marked your task undone: " + task);
    }

    /**
     * Prints an error message for a failed load.
     * @param e The exception thrown from the failed load attempt.
     */
    public void showLoadingError(Exception e) {
        System.out.println("Unable to load your file: " + e.getMessage() + "\n" + "Starting with a new list...");
    }
}
