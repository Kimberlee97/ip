package lumi;

import java.io.IOException;

import lumi.exceptions.LumiException;
import lumi.storage.Storage;
import lumi.tasks.Task;
import lumi.tasks.TaskList;
import lumi.ui.Dialogue;

/**
 * The main controller for the Lumi task manager chatbot.
 * This class facilitates interactions between the user and the chatbot through command-line commands.
 */
public class Lumi {
    private static final String DEFAULT_FILE_PATH = "./data/lumi.txt";
    private Storage storage;
    private Dialogue dialogue;
    private TaskList tasks;

    /**
     * Creates a {@code Lumi} instance using the default file path.
     */
    public Lumi() {
        this.dialogue = new Dialogue();
        this.storage = new Storage(DEFAULT_FILE_PATH);
        loadTasksOrInitializeEmpty();
    }

    /**
     * Parses and executes a user command, returning a user-facing message.
     * Any {@link LumiException} raised during handling is converted into a
     * message and returned to the caller.
     *
     * @param input The raw user input.
     * @return The user-facing message describing the effect or error.
     */
    public String processInput(String input) {
        try {
            if (input == null || input.trim().isEmpty()) {
                throw new LumiException("Please type a command.");
            }

            String[] parts = input.split(" ", 2);
            String command = parts[0];
            String args = (parts.length > 1) ? parts[1].trim() : "";

            String output = routeCommand(command, args);
            persistIfChanged(command);
            return output;

        } catch (LumiException e) {
            return e.getMessage();
        }
    }

    /**
     * Routes a parsed command to its specific handler.
     *
     * @param command The command.
     * @param args The remainder of the user input.
     * @return The user-facing message.
     * @throws LumiException If the command is invalid or arguments are missing.
     */
    private String routeCommand(String command, String args) throws LumiException {
        return switch (command) {
            case "bye" -> handleBye();
            case "help" -> handleHelp();
            case "list" -> handleList();
            case "delete" -> handleDelete(args);
            case "find" -> handleFind(args);
            case "mark", "unmark" -> handleMarkUnmark(command, args);
            case "todo", "event", "deadline" -> handleAdd(command, args);
            default -> throw new LumiException("Sorry! I'm not sure what you mean ><");
        };
    }

    /**
     * Handles the {@code bye} command.
     *
     * @return The farewell message.
     */
    private String handleBye() {
        return dialogue.sendGoodbye();
    }

    /**
     * Handles the {@code help} command.
     *
     * @return The help dialogue.
     */
    private String handleHelp() {
        return dialogue.showHelpDialogue();
    }

    /**
     * Handles the {@code list} command.
     *
     * @return The formatted list of tasks or an empty message.
     */
    private String handleList() {
        return tasks.printList();
    }

    /**
     * Handles the {@code find} command.
     *
     * @param args The keywords to search for.
     * @return The matching tasks, if any.
     * @throws LumiException If {@code args} are missing.
     */
    private String handleFind(String args) throws LumiException {
        ensureArgsProvided(args, "Please provide all the necessary details");
        return tasks.find(args);
    }

    /**
     * Handles the {@code todo}/{@code event}/{@code deadline} commands.
     * <p>
     * Reconstructs the original input so the {@link TaskList} parser
     * can determine the exact task subtype and validate its fields.
     *
     * @param command The command.
     * @param args The remainder of the user input.
     * @return The confirmation message upon successful add.
     * @throws LumiException If the task cannot be created.
     */
    private String handleAdd(String command, String args) throws LumiException {
        String raw = command + (args.isEmpty() ? "" : " " + args);
        return tasks.add(raw);
    }

    /**
     * Handles the {@code delete} command (1-based index in UI).
     *
     * @param args The user-supplied index.
     * @return The confirmation message upon successful deletion.
     * @throws LumiException If the index is invalid or missing.
     */
    private String handleDelete(String args) throws LumiException {
        ensureArgsProvided(args, "Please provide all the necessary details");
        Task removed = tasks.delete(args); // TaskList handles index parsing/validation
        return dialogue.printDeleteMessage(removed);
    }

    /**
     * Handles the {@code mark}/{@code unmark} commands (1-based index in UI).
     *
     * @param command Either {@code "mark"} or {@code "unmark"}.
     * @param args The user-supplied index.
     * @return A confirmation message if successful.
     * @throws LumiException If the index is invalid or missing.
     */
    private String handleMarkUnmark(String command, String args) throws LumiException {
        ensureArgsProvided(args, "Please provide a task number");
        try {
            int index = Integer.parseInt(args) - 1;
            if (index < 0 || index >= tasks.getList().size()) {
                throw new LumiException("Please add a valid task number");
            }
            Task t = tasks.getList().get(index);
            Task updated = "unmark".equals(command) ? t.unmark() : t.mark();
            return "unmark".equals(command)
                    ? dialogue.printUnmarkMessage(updated)
                    : dialogue.printMarkMessage(updated);

        } catch (NumberFormatException e) {
            throw new LumiException("Please enter a number after mark / unmark");
        } catch (IndexOutOfBoundsException e) {
            throw new LumiException("Please add a valid task number");
        }
    }

    /**
     * Loads tasks from storage; if loading fails, initializes an empty list and
     * shows a loading error via {@link Dialogue}.
     */
    private void loadTasksOrInitializeEmpty() {
        try {
            this.tasks = new TaskList(storage.load());
        } catch (IOException | LumiException e) {
            new Dialogue().showLoadingError(e);
            this.tasks = new TaskList();
        }
    }

    /**
     * Ensures that a command that requires arguments actually received them.
     *
     * @param args The argument string.
     * @param message The error message to show if missing.
     * @throws LumiException If no arguments were provided.
     */
    private void ensureArgsProvided(String args, String message) throws LumiException {
        if (args == null || args.isEmpty()) {
            throw new LumiException(message);
        }
    }

    /**
     * Persists the current state of the task list to disk for mutating commands.
     *
     * @param command The executed command.
     * @throws LumiException If saving fails.
     */
    private void persistIfChanged(String command) throws LumiException {
        if (command.equals("todo")
                || command.equals("event")
                || command.equals("deadline")
                || command.equals("delete")
                || command.equals("mark")
                || command.equals("unmark")) {
            try {
                storage.updateFile();
            } catch (Exception e) {
                throw new LumiException("I couldn't save your tasks. Please try again.");
            }
        }
    }

    /**
     * Calls processInput(input).
     * @param input
     * @return The output string.
     */
    public String getResponse(String input) {
        return processInput(input);
    }
}
