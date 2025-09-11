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

        try {
            if (input.equals("bye")) {
                try {
                    this.storage.updateFile();
                } catch (IOException e) {
                    throw new LumiException(e.getMessage());
                }
                return this.dialogue.sendGoodbye();
            } else if (input.equals("list")) {
                return this.tasks.printList();
            } else if (input.startsWith("unmark") || input.startsWith("mark")) {
                try {
                    String[] parts = input.split(" ");
                    if (parts.length < 2) {
                        throw new LumiException("Please provide a task number e.g. mark 1");
                    }
                    int index = Integer.parseInt(parts[1]) - 1;
                    assert (index > 0) && (index <= this.tasks.getList().size()) : "Your index is invalid";
                    if (parts[0].equals("unmark")) {
                        Task task = this.tasks.getList().get(index).unmark();
                        return dialogue.printUnmarkMessage(task);
                    } else {
                        Task task = this.tasks.getList().get(index).mark();
                        return this.dialogue.printMarkMessage(task);
                    }
                } catch (IndexOutOfBoundsException e) {
                    throw new LumiException("Please enter a valid task number");
                } catch (NumberFormatException e) {
                    throw new LumiException("Please enter a number after 'mark'/'unmark'");
                }
            } else if (input.startsWith("delete")) {
                String[] parts = input.split(" ");
                if (parts.length < 2) {
                    throw new LumiException("Please add the task number!");
                }
                Task task = this.tasks.delete(parts[1]);
                return this.dialogue.printDeleteMessage(task);
            } else if (input.startsWith("find")) {
                String[] parts = input.split("\\s+");
                if (parts.length != 2) {
                    throw new LumiException("Please add exactly one keyword!");
                }
                String keyword = parts[1].trim();
                assert !keyword.isEmpty() : "The keyword should not be empty";
                return this.tasks.find(keyword);
            } else {
                output = this.tasks.add(input);
            }
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
