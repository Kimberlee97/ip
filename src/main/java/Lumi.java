import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import exceptions.InvalidTaskException;

public class Lumi {
    private static final String LOGO = "LUMI (˶ˆᗜˆ˵)";

    private static List<Task> list = new ArrayList<>();

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
            Task newTask = new Task(input);
            list.add(newTask);
            System.out.println("added: " + newTask.toString());
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
    private static void printList() {
        if (list.isEmpty()) {
            System.out.println("No items yet");
        }
        for (int i = 0; i < list.size(); i++) {
            int index = i + 1;
            System.out.println(index + ". " + list.get(i).toString());
        }
    }

    public static void main(String[] args) {
        Lumi bot = new Lumi();
        bot.greet();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.trim().isEmpty()) continue;
            if (input.equals("bye")) {
                bot.bye();
                break;
            } else if (input.equals("list")) {
                printList();
            } else if (input.startsWith("unmark") || input.startsWith("mark")) {
                try {
                    String[] parts = input.split(" ");
                    if (parts.length < 2) {
                        System.out.println("Please provide a task number e.g. mark 1");
                        continue;
                    }
                    int index = Integer.parseInt(parts[1]) - 1;
                    if (parts[0].equals("unmark")) {
                        list.get(index).unmark();
                    } else {
                        list.get(index).mark();
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
