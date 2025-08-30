package lumi.parsers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for parsing and formatting date-time strings used in Lumi.
 * This class accepts multiple date formats and provides a standard output format
 * for consistency across the application.
 */
public class DateTimeParser {
    /** The accepted input formats */
    private static final DateTimeFormatter[] ACCEPTED_FORMATS = {
            DateTimeFormatter.ofPattern("dd MM yyyy HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    };
    /** The standard output format */
    private static final DateTimeFormatter RETURN_FORMAT = DateTimeFormatter.ofPattern("dd MM yyyy HH:mm");

    /**
     * Parses the given string into a {@link LocalDateTime} format and returns the {@link LocalDateTime}
     * @param date_time The string to be parsed.
     * @return The parsed {@link LocalDateTime}.
     * @throws DateTimeParseException If the string is not in an accepted format.
     */
    public static LocalDateTime parseDate(String date_time) throws DateTimeParseException {
        for (DateTimeFormatter format : ACCEPTED_FORMATS) {
            try {
                return LocalDateTime.parse(date_time.trim(), format);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new DateTimeParseException("Please enter a date in the correct format", date_time, 0);
    }

    /**
     * Returns the given {@link LocalDateTime} in the standard output format.
     * @param local_date_time The {@Link LocalDateTime} to be formatted in the standard output format.
     * @return A formatted date-time string in {@code dd MM yyyy HH:mm} format.
     */
    public static String format(LocalDateTime local_date_time) {
        return local_date_time.format(RETURN_FORMAT);
    }
}
