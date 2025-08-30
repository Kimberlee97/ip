package lumi.tasks;

import lumi.exceptions.LumiException;

/**
 * This class represents a {@code Todo} task that has a description.
 * If the input is malformed, a {@link LumiException} is thrown.
 */
public class Todo extends Task {
    private final String desc;

    /**
     * Constructs a {@code Todo} task with the given string input.
     * @param desc The description of the {@code Todo} task.
     * @throws LumiException If the task could not be created successfully.
     */
    public Todo(String desc) throws LumiException {
        super(TaskType.TODO);
        if (desc.trim().isEmpty()) {
            throw new LumiException("Please add a todo task in "
                    + "the format:\ntodo <task> (task should not be empty :> )");
        }
        this.desc = desc;
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.desc;
    }
}
