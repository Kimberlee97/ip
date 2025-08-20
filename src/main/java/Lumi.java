import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Lumi {
    private static final String LOGO = "LUMI (˶ˆᗜˆ˵)";
    private static final String DONE = "[X]";
    private static final String UNDONE = "[ ]";
    private static final String TODO = "[T]";
    private static final String DEADLINE = "[D]";
    private static final String EVENT = "[E]";

    private static List<Task> list = new ArrayList<>();

    static class Task {
        private boolean isDone;
        private final String desc;
        private final String type;

        public Task(String desc) {
            this.isDone = false;
            String[] taskDesc = desc.split(" ", 2);
            String taskType = taskDesc[0];

            switch (taskType) {
                case "todo":
                    this.type = TODO;
                    this.desc = taskDesc[1];
                    break;
                case "deadline":
                    this.type = DEADLINE;
                    String[] deadlineParts = taskDesc[1].split("/by");
                    this.desc = deadlineParts[0] + "(by: " + deadlineParts[1] + ")";
                    break;
                case "event":
                    this.type = EVENT;
                    String[] eventParts = taskDesc[1].split("/from|/to");
                    this.desc = eventParts[0] + "(from: " + eventParts[1] + " to: " + eventParts[2] + ")";
                    break;
                default:
                    throw new IllegalArgumentException("Invalid task type");
            }
        }

        /** Marks task as done */
        public void mark() {
            this.isDone = true;
            System.out.println("Nice! I have marked this task as done: " + this.desc);
        }

        /** Marks task as undone */
        public void unmark() {
            this.isDone = false;
            System.out.println("Oki, I've marked this task as undone: " + this.desc);
        }

        @Override
        public String toString() {
            return this.type + (isDone ? DONE : UNDONE) + " " + this.desc;
        }
    }

    /** Prints a greeting */
    private void greet() {
        System.out.println("Hello from\n" + LOGO + "\nWhat can I do for you?");
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
            System.out.println("added: " + input);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
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
                }
            } else {
                bot.add(input);
            }
        }
        scanner.close();
    }
}
