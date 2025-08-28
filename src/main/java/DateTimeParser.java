import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeParser {
    private static final DateTimeFormatter[] ACCEPTED_FORMATS = {
            DateTimeFormatter.ofPattern("dd MM yyyy HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    };
    private static final DateTimeFormatter RETURN_FORMAT = DateTimeFormatter.ofPattern("dd MM yyyy HH:mm");

    /** Parse the string containing the date and time into LocalDateTime */
    public static LocalDateTime parseDate(String date_time) throws DateTimeParseException {
        for (DateTimeFormatter format : ACCEPTED_FORMATS) {
            try {
                return LocalDateTime.parse(date_time.trim(), format);
            } catch (DateTimeParseException ignored) {
                // continue
            }
        }
        throw new DateTimeParseException("Please enter a date in the correct format", date_time, 0);
    }

    /** Format the LocalDateTime as a string */
    public static String format(LocalDateTime local_date_time) {
        return local_date_time.format(RETURN_FORMAT);
    }
}
