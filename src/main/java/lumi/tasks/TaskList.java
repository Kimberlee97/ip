package lumi.tasks;

import lumi.parsers.Parser;

import lumi.exceptions.LumiException;

import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a list of {@link Task} objects in the Lumi application.
 * The {@code TaskList} manages adding and deleting of tasks, and printing of the list.
 */
public class TaskList {
    private final List<Task> list;

    /**
     * Constructs a new {@code TaskList} using the given {@code List<Task>}.
     * @param taskList The list of tasks to initialize with.
     */
    public TaskList(List<Task> taskList) {
        this.list = taskList;
    }

    /**
     * Constructs a new empty {@code TaskList}.
     */
    public TaskList() {
        this.list = new ArrayList<>();
    }

    /**
     * Adds a new task to this {@code TaskList}.
     * @param input The string input to be parsed into a {@link Task} object and added to the {@code TaskList}.
     */
    public void add(String input) {
        try {
            Task newTask = Parser.parse(input);
            this.list.add(newTask);
            System.out.println("Task added: " + newTask);
        } catch (LumiException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a {@link Task} object from the {@code TaskList}.
     * @param i The index of the task to be deleted.
     * @return The {@link Task} that was deleted.
     * @throws LumiException If the {@link Task} could not be deleted.
     */
    public Task delete(String i) throws LumiException {
        Task task = null;
        try {
            int index = Integer.parseInt(i) - 1;
            task = list.get(index);
            this.list.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new LumiException("Please input a valid task number!");
        } catch (NumberFormatException e) {
           throw new LumiException("Please input a number! e.g. delete 1");
        }
        return task;
    }

    /**
     * Prints out all tasks in the list.
     */
    public void printList() {
        if (this.list.isEmpty()) {
            System.out.println("No items yet!");
        } else {
            for (int i = 0; i < this.list.size(); i++) {
                System.out.println(this.list.get(i));
            }
        }
    }

    public List<Task> getList() {
        return this.list;
    }
}
