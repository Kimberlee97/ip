import exceptions.InvalidTaskException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private final String desc;

    public Deadline(String desc) throws InvalidTaskException {
        super(TaskType.DEADLINE);
        String[] deadlineParts = desc.split("/by |\\|By: ");
        if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() || deadlineParts[1].trim().isEmpty()) {
            throw new InvalidTaskException("Please enter a deadline task "
                    + "in the format: deadline <task> /by <deadline>");
        } else {
            try {
                LocalDateTime date_time = DateTimeParser.parseDate(deadlineParts[1]);
                this.desc = deadlineParts[0] + "|By: " + DateTimeParser.format(date_time);
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
        return super.toString() + " " + desc;
    }
}
