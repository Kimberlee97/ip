package lumi.ui;

import lumi.tasks.*;

public class UI {
    private static final String LOGO = "LUMI (˶ˆᗜˆ˵)";

    /** Prints a greeting */
    public void greet() {
        System.out.println("Hello from " + LOGO + "\nWhat can I do for you?");
    }

    /** Prints a goodbye message */
    public void bye() {
        System.out.println("Bye! See you next time :>");
    }

    /** Prints a message for successful delete attempts */
    public void printDeleteMessage(Task task) {
        System.out.println("This task has been deleted: " + task.toString());
    }

    /** Prints a message for successful mark attempts */
    public void printMarkMessage(Task task) {
        System.out.println("Yay! I've marked your task done: " + task);
    }

    /** Prints a message for successful mark attempts */
    public void printUnmarkMessage(Task task) {
        System.out.println("Oki, I've marked your task undone: " + task);
    }
}
