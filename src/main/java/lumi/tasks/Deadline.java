package lumi.tasks;

import lumi.parsers.DateTimeParser;

import lumi.exceptions.LumiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * This class represents a {@code Deadline} task that has a description and the associated due date and times.
 * The input string is validated and parsed into a {@link LocalDateTime} using {@link DateTimeParser}.
 * If the input is malformed, a {@link LumiException} is thrown.
 */
public class Deadline extends Task {
    private final String desc;

    /**
     * Constructs a {@code Deadline} from the given user input string.
     * @param desc User input containing the task description and the deadline.
     * @throws LumiException If the input string is unable to be parsed successfully.
     */
    public Deadline(String desc) throws LumiException {
        super(TaskType.DEADLINE);
        String[] deadlineParts = desc.split("/by |\\|By: ");
        if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() || deadlineParts[1].trim().isEmpty()) {
            throw new LumiException("Please enter a deadline task "
                    + "in the format: deadline <task> /by <deadline>");
        } else {
            try {
                LocalDateTime date_time = DateTimeParser.parseDate(deadlineParts[1]);
                this.desc = deadlineParts[0] + "|By: " + DateTimeParser.format(date_time);
            } catch (DateTimeParseException e) {
                throw new LumiException(e.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + " " + desc;
    }
}
