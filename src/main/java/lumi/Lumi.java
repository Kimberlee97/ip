package lumi;

import lumi.storage.Storage;

import lumi.tasks.Task;
import lumi.tasks.TaskList;

import lumi.exceptions.LumiException;

import lumi.ui.UI;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Scanner;

/**
 * The main controller for the Lumi task manager chatbot.
 * This class facilitates interactions between the user and the chatbot through command-line commands.
 */
public class Lumi {
    private Storage storage;
    private UI ui;
    private TaskList tasks;

    /**
     * Instantiates a new {@code Lumi} object.
     * @param filePath The path to the text file where tasks will be stored.
     */
    public Lumi(String filePath) {
        this.ui = new UI();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (IOException | LumiException e) {
            ui.showLoadingError(e);
            this.tasks = new TaskList();
        }
    }

    /**
     * Starts the Lumi application loop.
     * Continuously reads user commands from the command-line, processes commands and updates the task list.
     * The loop terminates when the user enters <code>bye</code>, and the task list is saved to the text file.
     */
    public void run() {
        this.ui.greet();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.trim().isEmpty()) continue;
            try {
                if (input.equals("bye")) {
                    try {
                        this.storage.updateFile();
                    } catch (IOException e) {
                        throw new LumiException(e.getMessage());
                    }
                    this.ui.sendGoodbye();
                    break;
                } else if (input.equals("list")) {
                    this.tasks.printList();
                } else if (input.startsWith("unmark") || input.startsWith("mark")) {
                    try {
                        String[] parts = input.split(" ");
                        if (parts.length < 2) {
                            throw new LumiException("Please provide a task number e.g. mark 1");
                        }
                        int index = Integer.parseInt(parts[1]) - 1;
                        if (parts[0].equals("unmark")) {
                            Task task = this.tasks.getList().get(index).unmark();
                            ui.printUnmarkMessage(task);
                        } else {
                            Task task = this.tasks.getList().get(index).mark();
                            ui.printMarkMessage(task);
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
                    this.ui.printDeleteMessage(task);
                } else {
                    this.tasks.add(input);
                }
            } catch (LumiException e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }

    /**
     * Creates a new {@code Lumi} instance with the given filepath.
     * @param args
     * @throws
     */
    public static void main(String[] args) {
        Lumi lumi = new Lumi("./data/lumi.txt");
        lumi.run();
    }
}
