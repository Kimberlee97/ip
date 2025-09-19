package lumi.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import lumi.exceptions.LumiException;
import lumi.parsers.DateTimeParser;

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
        assert desc != null : "The description must not be null";
        assert !desc.trim().isEmpty() : "The description must not be empty";
        this.desc = parseAndFormat(desc);
    }

    /**
     * Validates and parses the input string into a formatted deadline description.
     *
     * @param desc User input containing description and deadline.
     * @return A formatted description string with the parsed deadline.
     * @throws LumiException If input is invalid or the deadline cannot be parsed.
     */
    private String parseAndFormat(String desc) throws LumiException {
        validateNotEmpty(desc);

        String[] parts = splitInput(desc);
        validateParts(parts);

        return formatDeadline(parts[0], parts[1]);
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
     * Splits the user input into description and deadline parts.
     *
     * @param desc The input string containing task details.
     * @return A string array with two parts: description and deadline.
     * @throws LumiException If the input cannot be split into valid parts.
     */
    private String[] splitInput(String desc) throws LumiException {
        String[] parts = desc.split("/by |\\|By: ");
        if (parts.length < 2) {
            throw new LumiException("Please add the full details!");
        }
        return parts;
    }

    /**
     * Validates that both description and deadline parts are non-empty.
     *
     * @param parts The string array containing description and deadline.
     * @throws LumiException If either part is empty.
     */
    private void validateParts(String[] parts) throws LumiException {
        if (parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new LumiException(
                    "Please enter a deadline task in the format: deadline <task> /by <deadline>"
            );
        }
    }

    /**
     * Parses and formats the deadline part into a standardized format.
     *
     * @param task The task description.
     * @param deadline The deadline string to be parsed.
     * @return A formatted task string including the deadline.
     * @throws LumiException If the deadline cannot be parsed into a {@link LocalDateTime}.
     */
    private String formatDeadline(String task, String deadline) throws LumiException {
        try {
            LocalDateTime dateTime = DateTimeParser.parseDate(deadline.trim());
            assert dateTime != null : "Date time should not be null";
            return task.trim() + "|By: " + DateTimeParser.format(dateTime);
        } catch (DateTimeParseException e) {
            throw new LumiException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return super.toString() + desc;
    }
}
