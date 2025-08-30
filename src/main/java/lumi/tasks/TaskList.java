package lumi.tasks;

import lumi.parsers.Parser;

import lumi.exceptions.LumiException;

import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> list;
    public TaskList(List<Task> taskList) {
        this.list = taskList;
    }

    public TaskList() {
        this.list = new ArrayList<>();
    }

    /** Adds a new item */
    public void add(String input) {
        try {
            Task newTask = Parser.parse(input);
            this.list.add(newTask);
            System.out.println("Task added: " + newTask);
        } catch (LumiException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Deletes an item */
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

    /** Prints out the list */
    public void printList() throws FileNotFoundException {
        if (this.list.isEmpty()) {
            System.out.println("No items yet!");
        } else {
            for (int i = 0; i < this.list.size(); i++) {
                System.out.println(this.list.get(i));
            }
        }
    }

    /** Returns the list */
    public List<Task> getList() {
        return this.list;
    }
}
