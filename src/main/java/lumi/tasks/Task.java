package lumi.tasks;

import lumi.exceptions.LumiException;

/**
 * This class represents a generic task in the Lumi application.
 * {@code Task} objects can be marked as done or undone, and their state will be reflected in their
 * string representation.
 * Subclasses may extend this class to include additional information such as deadlines or event periods.
 */
public class Task {
    private static final String DONE = "[X]";
    private static final String UNDONE = "[ ]";
    private boolean isDone;
    private final TaskType taskType;

    /**
     * Constructs a new {@code Task} object with the give {@link TaskType}.
     * @param taskType The type of this task.
     */
    public Task(TaskType taskType) {
        this.isDone = false;
        this.taskType = taskType;

    }

    /**
     * Marks the current {@code Task} object as done.
     * @return The {@code Task} instance.
     * @throws LumiException If the task could not be marked as done.
     */
    public Task mark() throws LumiException {
        if (this.isDone) {
            throw new LumiException("This task has already been marked done");
        }
        this.isDone = true;
        return this;
    }

    /**
     * Marks the current {@code Task} object as undone.
     * @return The {@code Task} instance.
     * @throws LumiException If the task could not be marked as undone.
     */
    public Task unmark() throws LumiException {
        if (!this.isDone) {
            throw new LumiException("This task has already been marked undone");
        }
        this.isDone = false;
        return this;
    }

    @Override
    public String toString() {
        return this.taskType.toString() + (isDone ? DONE : UNDONE);
    }
}
