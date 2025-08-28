import exceptions.InvalidTaskException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Event extends Task{
    private final String desc;

    public Event(String desc) throws InvalidTaskException {
        super(TaskType.EVENT);
        String[] eventParts = desc.split("/from|/to|\\|From: |\\|To: ");
        if (eventParts.length < 3 || eventParts[0].trim().isEmpty() || eventParts[1].trim().isEmpty()
                || eventParts[2].trim().isEmpty()) {
            throw new InvalidTaskException("Please enter an event task in the "
                    + "format: event <task> /from <date/time> /to <date/time>");
        } else {
            try {
                LocalDateTime from = DateTimeParser.parseDate(eventParts[1]);
                LocalDateTime to = DateTimeParser.parseDate(eventParts[2]);
                this.desc = eventParts[0] + "|From: " + DateTimeParser.format(from) + "|To: "
                        + DateTimeParser.format(to);
            } catch (DateTimeParseException e) {
                throw new InvalidTaskException(e.getMessage());
            }
        }
    }

    @Override
    public void mark() {
        if (super.getIsDone()) {
            System.out.println("This task has already been marked as done ՞. .՞");
            return;
        }
        super.mark();
        System.out.println("Yay! I have marked this task as done: " + this.desc);
    }

    @Override
    public void unmark() {
        if (!this.getIsDone()) {
            System.out.println("This task has already been marked undone ՞. .՞");
            return;
        }
        super.unmark();
        System.out.println("Oki, I've marked this task as undone: " + this.desc);
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.desc;
    }
}
