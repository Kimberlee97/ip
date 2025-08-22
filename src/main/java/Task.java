import exceptions.InvalidTaskException;

public class Task {
    private boolean isDone;
    private final String desc;
    private final String type;

    public Task(String desc) throws InvalidTaskException {
        this.isDone = false;
        String tempDesc = "";
        String tempType = "";
        String[] taskDesc = desc.split(" ", 2);
        if (taskDesc.length < 2) throw new InvalidTaskException("Please add a task in the format:" +
                "todo <task>\n" +
                "deadline <task> /by <deadline>\n" +
                "event <task> /from <date/time> /to <date/time>");

        switch (taskDesc[0]) {
            // adds a todo task
            case "todo":
                if (taskDesc[1].trim().isEmpty()) throw new InvalidTaskException("Please add a todo task in " +
                        "the format:\ntodo <task> (task should not be empty :> )");
                tempDesc = taskDesc[1];
                Lumi.Type t = new Lumi.Type(Lumi.TaskType.TODO);
                tempType = t.getTag();
                break;
            // adds a deadline task
            case "deadline":
                String[] deadlineParts = taskDesc[1].split("/by ");
                if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() ||
                        deadlineParts[1].trim().isEmpty()) {
                    throw new InvalidTaskException("Please enter a deadline task " +
                            "in the format: deadline <task> /by <deadline>");
                }
                tempDesc = deadlineParts[0] + "(by: " + deadlineParts[1] + ")";
                Lumi.Type d = new Lumi.Type(Lumi.TaskType.DEADLINE);
                tempType = d.getTag();
                break;
            // adds an event
            case "event":
                String[] eventParts = taskDesc[1].split("/from |/to ");
                if (eventParts.length < 3 || eventParts[0].trim().isEmpty() || eventParts[1].trim().isEmpty() ||
                        eventParts[2].trim().isEmpty()) {
                    throw new InvalidTaskException("Please enter an event task in the " +
                            "format: event <task> /from <date/time> /to <date/time>");
                }
                tempDesc = eventParts[0] + "(from: " + eventParts[1] + " to: " + eventParts[2] + ")";
                Lumi.Type e = new Lumi.Type(Lumi.TaskType.EVENT);
                tempType = e.getTag();
                break;
            default:
                throw new InvalidTaskException("Oh no! >.<\nI'm not sure what this is, please try again!");
        }
        this.type = tempType;
        this.desc = tempDesc;
    }
}
