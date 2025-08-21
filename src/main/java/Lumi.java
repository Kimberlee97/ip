import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import exceptions.InvalidTaskException;

public class Lumi {
    private static final String LOGO = "LUMI (˶ˆᗜˆ˵)";
    private static final String DONE = "[X]";
    private static final String UNDONE = "[ ]";

    private static List<Task> list = new ArrayList<>();

    public enum TaskType {
        TODO, DEADLINE, EVENT
    }

    public static class Type {
        private final String tag;
        public Type(TaskType type) {
            this.tag = switch (type) {
                case TODO -> "[T]";
                case DEADLINE -> "[D]";
                case EVENT -> "[E]";
            };
        }
        public String getTag() {
            return this.tag;
        }
    }

    static class Task {
        private boolean isDone;
        private final String desc;
        private final String type;

        public Task(String desc) throws InvalidTaskException {
            this.isDone = false;
            String tempDesc = "";
            String tempType = "";
            String[] taskDesc = desc.split(" ", 2);
            if (taskDesc.length < 2) throw new InvalidTaskException("Please add a task in the format:" +
                    "todo <task>\n" +
                    "deadline <task> /by <deadline>\n" +
                    "event <task> /from <date/time> /to <date/time>");

            switch (taskDesc[0]) {
                // adds a todo task
                case "todo":
                    if (taskDesc[1].trim().isEmpty()) throw new InvalidTaskException("Please add a todo task in " +
                            "the format:\ntodo <task> (task should not be empty :> )");
                    tempDesc = taskDesc[1];
                    Type t = new Type(TaskType.TODO);
                    tempType = t.getTag();
                    break;
                // adds a deadline task
                case "deadline":
                    String[] deadlineParts = taskDesc[1].split("/by ");
                    if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() ||
                            deadlineParts[1].trim().isEmpty()) {
                        throw new InvalidTaskException("Please enter a deadline task " +
                                "in the format: deadline <task> /by <deadline>");
                    }
                    tempDesc = deadlineParts[0] + "(by: " + deadlineParts[1] + ")";
                    Type d = new Type(TaskType.DEADLINE);
                    tempType = d.getTag();
                    break;
                // adds an event
                case "event":
                    String[] eventParts = taskDesc[1].split("/from |/to ");
                    if (eventParts.length < 3 || eventParts[0].trim().isEmpty() || eventParts[1].trim().isEmpty() ||
                            eventParts[2].trim().isEmpty()) {
                        throw new InvalidTaskException("Please enter an event task in the " +
                                "format: event <task> /from <date/time> /to <date/time>");
                    }
                    tempDesc = eventParts[0] + "(from: " + eventParts[1] + " to: " + eventParts[2] + ")";
                    Type e = new Type(TaskType.EVENT);
                    tempType = e.getTag();
                    break;
                default:
                    throw new InvalidTaskException("Oh no! >.<\nI'm not sure what this is, please try again!");
            }
            this.type = tempType;
            this.desc = tempDesc;
        }

        /** Marks task as done */
        public void mark() {
            if (this.isDone) {
                System.out.println("This task has already been marked as done ՞. .՞");
                return;
            }
            this.isDone = true;
            System.out.println("Yay! I have marked this task as done: " + this.desc);
        }

        /** Marks task as undone */
        public void unmark() {
            if (!this.isDone) {
                System.out.println("This task has already been marked undone ՞. .՞");
                return;
            }
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
