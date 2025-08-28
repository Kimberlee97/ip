import java.io.FileNotFoundException;
import java.io.IOException;
import exceptions.LumiException;

import java.util.Scanner;

public class Lumi {
    private Storage storage;
    private UI ui;
    private TaskList tasks;

    public Lumi(String filePath) throws LumiException {
        this.ui = new UI();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (IOException e) {
            this.tasks = new TaskList();
            throw new LumiException("Unable to initialize: " + e.getMessage());
        }
    }

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
                    this.ui.bye();
                    break;
                } else if (input.equals("list")) {
                    try {
                        this.tasks.printList();
                    } catch (FileNotFoundException e) {
                        throw new LumiException("Error! We can't find the list :");
                    }
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

    public static void main(String[] args) throws LumiException {
        Lumi lumi = new Lumi("./data/lumi.txt");
        lumi.run();
    }
}
