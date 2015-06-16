public class RecipeCreatedEvent {
    private String id;

    public RecipeCreatedEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
