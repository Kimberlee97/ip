import exceptions.LumiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private final String desc;

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
