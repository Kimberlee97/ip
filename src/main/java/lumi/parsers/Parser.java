package lumi.parsers;

import lumi.tasks.*;

import lumi.exceptions.*;

public class Parser {
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
