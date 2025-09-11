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

    /**
     * Returns a dialogue with detailed descriptions of the list of commands and formats.
     */
    public String showHelpDialogue() {
        String greet = "₊✩‧₊˚౨ৎ˚₊✩‧₊ Lumi is here to help ₊✩‧₊˚౨ৎ˚₊✩‧₊\nHere are our list of commands:\n";
        String listHeader = "\n⋆˚✿˖° List-Viewing Commands ⋆˚✿˖°\n\n";
        String list = "list: prints out the task list in the format <index>.[<task type>][<status>] <task description>"
                + "\n- index: refers to the task's index in the list (from earliest date added to latest)\n"
                + "- task type: refers to the task's type e.g. T for todo, E for event, D for deadline\n"
                + "- status: refers to the task's status e.g. X for done, <empty space> for undone\n\n";
        String bye = "bye: saves the updates made to the task list into the file\n\n";
        String find = "find <keyword>: finds tasks containing the keyword and prints the matching tasks\n\n";
        String delete = "delete <index>: deletes the task at the given index from the task list\n\n";
        String taskHeader = "\n⋆˚✿˖° Task Commands ⋆˚✿˖°\n";
        String todo = "todo <task description>: creates a todo task with the given description\n\n";
        String deadline = "deadline <task description> /by <date and time>: "
                + "creates a deadline task with the given description and deadline\n\n";
        String event = "event <task description> /from <date and time> /to <date and time>: "
                + "creates an event with the given description and dates and times\n\n";
        String unmark = "unmark <index>: if the task at the given index was marked as done, it will be unmarked\n"
                + "e.g. [T][X] <task description> becomes [T][ ] <task description>\n\n";
        String mark = "mark <index>: if the task at the given index was marked as undone, it will be marked as done\n"
                + "e.g. [T][ ] <task description> becomes [T][X] <task description>\n\n";
        String goodbyeMessage = "I hope this helped! ⋆˚✿˖°\n" + "                            ╱|、\n"
                + "                          (˚ˎ 。7  \n"
                + "                           |、˜〵          \n"
                + "                          じしˍ,)ノ";
        return greet + listHeader + list + bye + find + delete + taskHeader + todo + deadline + event + unmark + mark
                + goodbyeMessage;
    }
}
