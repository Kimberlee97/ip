public enum TaskType {
    TODO("[T]"), DEADLINE("[D]"), EVENT("[E]");

    private final String label;
    TaskType(String label) {
        this.label = label;

    }

    @Override
    public String toString() {
        return this.label;
    }
}

