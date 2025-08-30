package lumi.tasks;

import lumi.exceptions.*;

public abstract class Task {
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
    public Task mark() throws LumiException {
        if (this.isDone) {
            throw new LumiException("This task has already been marked done");
        }
        this.isDone = true;
        return this;
    }

    /** Marks task as undone */
    public Task unmark() throws LumiException {
        if (!this.isDone) {
            throw new LumiException("This task has already been marked undone");
        }
        this.isDone = false;
        return this;
    }

    @Override
    public String toString() {
        return this.taskType.toString() + "|" + (isDone ? DONE : UNDONE) + "|";
    }
}
