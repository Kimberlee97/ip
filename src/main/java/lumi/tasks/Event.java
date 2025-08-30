package lumi.tasks;

import lumi.parsers.DateTimeParser;

import lumi.exceptions.LumiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Event extends Task{
    private final String desc;

    public Event(String desc) throws LumiException {
        super(TaskType.EVENT);
        String[] eventParts = desc.split("/from|/to|\\|From: |\\|To: ");
        if (eventParts.length < 3 || eventParts[0].trim().isEmpty() || eventParts[1].trim().isEmpty()
                || eventParts[2].trim().isEmpty()) {
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
