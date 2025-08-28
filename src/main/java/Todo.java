import exceptions.InvalidTaskException;

public class Todo extends Task {
    private final String desc;

    public Todo(String desc) throws InvalidTaskException {
        super(TaskType.TODO);
        if (desc.trim().isEmpty()) throw new InvalidTaskException("Please add a todo task in "
                + "the format:\ntodo <task> (task should not be empty :> )");
        this.desc = desc;
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
