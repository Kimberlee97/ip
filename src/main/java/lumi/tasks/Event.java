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
        assert desc != null : "The description must not be null";
        assert !desc.trim().isEmpty() : "The description must not be empty";
        this.desc = parseAndFormat(desc);
    }

    /**
     * Validates and parses the input string into a formatted event description.
     *
     * @param desc User input containing description and event details.
     * @return A formatted description string with the parsed event.
     * @throws LumiException If input is invalid or the event cannot be parsed.
     */
    private String parseAndFormat(String desc) throws LumiException {
        validateNotEmpty(desc);

        String[] parts = splitInput(desc);
        validateParts(parts);

        return formatEvent(parts);
    }

    /**
     * Splits the user input into description and event parts.
     *
     * @param desc The input string containing task details.
     * @return A string array with two parts: description and deadline.
     * @throws LumiException If the input cannot be split into valid parts.
     */
    private String[] splitInput(String desc) throws LumiException {
        String[] eventParts = desc.split("/from|/to|\\|From: |\\|To: ");
        boolean hasValidLength = eventParts.length >= 3;
        if (!hasValidLength) {
            throw new LumiException("Please enter the full description!");
        }
        return eventParts;
    }

    /**
     * Validates that both description and event parts are non-empty.
     *
     * @param parts The string array containing description and deadline.
     * @throws LumiException If either part is empty.
     */
    private void validateParts(String[] parts) throws LumiException {
        boolean hasInvalidDesc = parts[0].trim().isEmpty();
        boolean hasInvalidFromDetails = parts[1].trim().isEmpty();
        boolean hasInvalidEndDetails = parts[2].trim().isEmpty();

        if (hasInvalidDesc || hasInvalidEndDetails || hasInvalidFromDetails) {
            throw new LumiException("Please enter an event task in the "
                    + "format: event <task> /from <date/time> /to <date/time>");
        }
    }

    /**
     * Ensures the task description is not null or empty.
     *
     * @param desc The input string to check.
     * @throws LumiException If the description is empty or null.
     */
    private void validateNotEmpty(String desc) throws LumiException {
        if (desc == null || desc.trim().isEmpty()) {
            throw new LumiException("The task description should not be empty");
        }
    }

    /**
     * Parses and formats the event part into a standardized format.
     * @param eventParts The event string to be parsed.
     * @return A formatted task string including the deadline.
     * @throws LumiException If the deadline cannot be parsed into a {@link LocalDateTime}.
     */
    private String formatEvent(String[] eventParts) throws LumiException {
        try {
            LocalDateTime from = DateTimeParser.parseDate(eventParts[1]);
            LocalDateTime to = DateTimeParser.parseDate(eventParts[2]);
            String desc = eventParts[0].trim() + "|From: " + DateTimeParser.format(from) + "|To: "
                    + DateTimeParser.format(to);
            return desc;
        } catch (DateTimeParseException e) {
            throw new LumiException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return super.toString() + this.desc;
    }
}
