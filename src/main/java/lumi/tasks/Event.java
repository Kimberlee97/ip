package lumi.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import lumi.exceptions.LumiException;
import lumi.parsers.DateTimeParser;


/**
 * This class represents a {@code Event} task that has a description and associated start and end dates and times.
 * The input string is validated and parsed into a {@link LocalDateTime} using {@link DateTimeParser}.
 * If the input is malformed, a {@link LumiException} is thrown.
 */
public class Event extends Task {
    private final String desc;

    /**
     * Constructs an {@link Event} task from the given user input string.
     * @param desc User input containing the task description and the event duration.
     * @throws LumiException If the input string is unable to be parsed successfully.
     */
    public Event(String desc) throws LumiException {
        super(TaskType.EVENT);
        String[] eventParts = desc.split("/from|/to|\\|From: |\\|To: ");
        boolean hasValidLength = eventParts.length >= 3;
        assert hasValidLength : "Please enter the full description!";

        boolean hasInvalidDesc = eventParts[0].trim().isEmpty();
        boolean hasInvalidFromDetails = eventParts[1].trim().isEmpty();
        boolean hasInvalidEndDetails = eventParts[2].trim().isEmpty();

        if (hasInvalidDesc || hasInvalidEndDetails || hasInvalidFromDetails) {
            throw new LumiException("Please enter an event task in the "
                    + "format: event <task> /from <date/time> /to <date/time>");
        } else {
            try {
                LocalDateTime from = DateTimeParser.parseDate(eventParts[1]);
                LocalDateTime to = DateTimeParser.parseDate(eventParts[2]);
                this.desc = eventParts[0] + "|From: " + DateTimeParser.format(from) + "|To: "
                        + DateTimeParser.format(to);
            } catch (DateTimeParseException e) {
                throw new LumiException(e.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.desc;
    }
}
