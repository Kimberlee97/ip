package lumi.tasks;

import lumi.exceptions.*;

public class Todo extends Task {
    private final String desc;

    public Todo(String desc) throws LumiException {
        super(TaskType.TODO);
        if (desc.trim().isEmpty()) throw new LumiException("Please add a todo task in "
                + "the format:\ntodo <task> (task should not be empty :> )");
        this.desc = desc;
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.desc;
    }
}
