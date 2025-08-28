import exceptions.InvalidTaskException;

public class Task {
    private static final String DONE = "[X]";
    private static final String UNDONE = "[ ]";
    private boolean isDone;
    private final TaskType taskType;

    public Task(TaskType taskType) {
        this.isDone = false;
        this.taskType = taskType;

    }

    public boolean getIsDone() {
        return this.isDone;
    }

    /** Marks task as done */
    public void mark() {
        this.isDone = true;
    }

    /** Marks task as undone */
    public void unmark() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return this.taskType.toString() + (isDone ? DONE : UNDONE);
    }
}
