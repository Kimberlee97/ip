package lumi.tasks;

/**
 * Represents the different types of tasks supported by Lumi.
 * Each type has a corresponding string representation.
 */
public enum TaskType {
    TODO("[T]"), DEADLINE("[D]"), EVENT("[E]");

    private final String label;

    /**
     * Creates a {@link TaskType} with the given label.
     * @param label The string representation of the task type.
     */
    TaskType(String label) {
        this.label = label;

    }

    @Override
    public String toString() {
        return this.label;
    }
}

