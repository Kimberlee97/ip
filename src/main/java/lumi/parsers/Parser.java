package lumi.parsers;

import lumi.tasks.*;

import lumi.exceptions.*;

/**
 * Utility class for parsing raw user input into {@Link Task} objects.
 * The {@code Parser} analyzes the command string and determines the correct task type to instantiate.
 * If no suitable task types are found, a LumiException will be thrown.
 */
public class Parser {
    /**
     * Parses the given input string and creates the corresponding Task.
     * @param desc The raw input given by the user.
     * @return The parsed {@link Task}.
     * @throws LumiException If the input is malformed or does not match a valid command.
     */
    public static Task parse(String desc) throws LumiException {
        String[] taskParts = desc.split(" ", 2);
        if (taskParts.length <= 1) {
            throw new LumiException("Please add a task in the format: todo <task>\n"
                    + "deadline <task> /by <dd/MM/yyyy or dd MM yyyy HH:mm>\n"
                    + "event <task> /from <dd/MM/yyyy HH:mm or dd MM yyyy HH:mm> "
                    + "/to <dd/MM/yyyy HH:mm or dd MM yyyy HH:mm");
        } else {
            Task task = null;
            switch (taskParts[0]) {
            case "todo":
                task = new Todo(taskParts[1]);
                return task;
            case "deadline":
                task = new Deadline(taskParts[1]);
                return task;
            case "event":
                task = new Event(taskParts[1]);
                return task;
            default:
                throw new LumiException("Oh no! >.<\nI'm not sure what this is, please try again!");
            }
        }
    }
}
