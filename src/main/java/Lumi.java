import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;

import exceptions.InvalidTaskException;

public class Lumi {
    private static final String LOGO = "LUMI (˶ˆᗜˆ˵)";

    private List<Task> list = new ArrayList<>();

    private static final String FILEPATH = "./data/lumi.txt";

    /** Converts the file to a List<Task> */
    private void createList() throws IOException, InvalidTaskException {
        File file = new File(FILEPATH);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            file.createNewFile();
            scanner = new Scanner(file);
        }
        while (scanner.hasNext()) {
            Task task = convertStringToTask(scanner.nextLine());
            this.list.add(task);
        }
    }

    /** Converts a String to a Task */
    private static Task convertStringToTask(String string) throws InvalidTaskException {
        String[] taskParts = string.split("\\| |\\|", 3);
        String type = taskParts[0];
        String status = taskParts[1];
        String desc = taskParts[2];
        String typeInput = "";
        boolean isDone = false;
        switch (type) {
        case "[T]":
            typeInput = "todo";
            break;
        case "[D]":
            typeInput = "deadline";
            break;
        case "[E]":
            typeInput = "event";
        }

        switch (status) {
        case "[X]":
            isDone = true;
            break;
        case "[ ]":
            isDone = false;
            break;
        }
        String input = typeInput + " " + desc;
        Task task = Parser.parse(input);
        if (isDone) {
            task.mark();
        }
        return task;
    }

    /** Prints a greeting */
    private void greet() {
        System.out.println("Hello from " + LOGO + "\nWhat can I do for you?");
    }

    /** Prints a goodbye message */
    private void bye() {
        System.out.println("Bye! See you next time :>");
    }

    /** Adds a new item */
    private void add(String input) {
        try {
            Task newTask = Parser.parse(input);
            list.add(newTask);
            System.out.println("Task added: " + newTask.toString());
        } catch (InvalidTaskException e) {
            System.out.println(e.getMessage());
        }

    }

    /** Deletes an item */
    private void delete(String i) {
        try {
            int index = Integer.parseInt(i) - 1;
            String task = list.get(index).toString();
            list.remove(index);
            System.out.println("This task has been deleted: " + task);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Please input a valid task number!");
        } catch (NumberFormatException e) {
            System.out.println("Please input a number! e.g. delete 1");
        }
    }

    /** Prints out the list */
    private void printList() throws FileNotFoundException {
        if (this.list.isEmpty()) {
            System.out.println("No items yet!");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }
        }
    }

    /** Updates the file */
    private void updateFile() throws IOException {
        try {
            FileWriter fw = new FileWriter(FILEPATH);
            for (int i = 0; i < this.list.size(); i++) {
                fw.write(this.list.get(i).toString());
                fw.write("\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Unable to update your file, sorry!");
            throw e;
        }
        System.out.println("Your file has been updated >.<");
    }

    public static void main(String[] args) throws IOException, InvalidTaskException {
        Lumi bot = new Lumi();
        bot.createList();
        bot.greet();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.trim().isEmpty()) continue;
            if (input.equals("bye")) {
                bot.updateFile();
                bot.bye();
                break;
            } else if (input.equals("list")) {
                try {
                    bot.printList();
                } catch (FileNotFoundException e) {
                    System.out.println("Error! We can't find the list :");
                }
            } else if (input.startsWith("unmark") || input.startsWith("mark")) {
                try {
                    String[] parts = input.split(" ");
                    if (parts.length < 2) {
                        System.out.println("Please provide a task number e.g. mark 1");
                        continue;
                    }
                    int index = Integer.parseInt(parts[1]) - 1;
                    if (parts[0].equals("unmark")) {
                        bot.list.get(index).unmark();
                    } else {
                        bot.list.get(index).mark();
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Please enter a valid task number");
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number after 'mark'/'unmark'");
                }
            } else if (input.startsWith("delete")) {
                String[] parts = input.split(" ");
                if (parts.length < 2) {
                    System.out.println("Please provide the task you wish to delete e.g. delete 1");
                    continue;
                }
                bot.delete(parts[1]);
            } else {
                bot.add(input);
            }
        }
        scanner.close();
    }
}
