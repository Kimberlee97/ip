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
     * Instantiates a new {@code Lumi} object.
     * @param filePath The path to the text file where tasks will be stored.
     */
    public Lumi(String filePath) {
        this.dialogue = new Dialogue();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (IOException | LumiException e) {
            dialogue.showLoadingError(e);
            this.tasks = new TaskList();
        }
    }

    /**
     * Instantiates a new {@code Lumi} object.
     * Sets the text file used to DEFAULT_FILE_PATH, lumi.txt.
     */
    public Lumi() {
        this.dialogue = new Dialogue();
        this.storage = new Storage(DEFAULT_FILE_PATH);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (IOException | LumiException e) {
            dialogue.showLoadingError(e);
            this.tasks = new TaskList();
        }
    }

    /**
     * Processes the user's input.
     * @param input
     * @return The output string.
     */
    public String processInput(String input) {
        String output = "";
        assert !input.trim().isEmpty() : "Input should not be empty";
        String[] parts = input.split(" ", 2);
        String command = parts[0];

        try {
            switch (command) {
            case "bye":
                output = this.dialogue.sendGoodbye();
                break;
            case "list":
                output = this.tasks.printList();
                break;
            case "unmark", "mark":
                try {
                    if (parts[1].isEmpty()) {
                        throw new LumiException("Please provide a task number");
                    }
                    int index = Integer.parseInt(parts[1]) - 1;
                    assert (index >= 0) && (index <= this.tasks.getList().size()) : "Your index is invalid";
                    Task task = this.tasks.getList().get(index);
                    Task updatedTask;
                    if (command.equals("unmark")) {
                        updatedTask = task.unmark();
                        output = this.dialogue.printUnmarkMessage(updatedTask);
                    } else {
                        updatedTask = task.mark();
                        output = this.dialogue.printMarkMessage(updatedTask);
                    }
                } catch (IndexOutOfBoundsException e) {
                    throw new LumiException("Please add a valid task number");
                } catch (NumberFormatException e) {
                    throw new LumiException("Please enter a number after mark / unmark");
                }
                break;
            case "find":
                if (parts.length < 2) {
                    throw new LumiException("Please provide all the necessary details");
                }
                String keyword = parts[1].trim();
                assert !keyword.isEmpty() : "The keyword should not be empty";
                output = this.tasks.find(keyword);
                break;
            case "todo", "event", "deadline":
                output = this.tasks.add(input);
                break;
            case "delete":
                if (parts.length < 2) {
                    throw new LumiException("Please provide all the necessary details");
                }
                String index = parts[1].trim();
                assert !index.isEmpty() : "The index should not be empty";
                Task task = this.tasks.delete(index);
                output = this.dialogue.printDeleteMessage(task);
                break;
            case "help":
                output = this.dialogue.showHelpDialogue();
                break;
            default:
                throw new LumiException("Sorry! I'm not sure what you mean ><");
            }
            this.storage.updateFile();
        } catch (LumiException e) {
            return e.getMessage();
        }
        return output;
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
