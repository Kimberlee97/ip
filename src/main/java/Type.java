public class Type {
    private final String tag;
    public Type(Lumi.TaskType type) {
        this.tag = switch (type) {
            case TODO -> "[T]";
            case DEADLINE -> "[D]";
            case EVENT -> "[E]";
        };
    }
    public String getTag() {
        return this.tag;
    }
}
