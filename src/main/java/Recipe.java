import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

public class Recipe extends AbstractAnnotatedAggregateRoot {

    @AggregateIdentifier
    private String id;

    public Recipe() {
    }

    public Recipe(String id) {
        apply(new RecipeCreatedEvent(id));
    }

    @EventHandler
    public void on(RecipeCreatedEvent event) {
        this.id = event.getId();
        System.out.println("IT WORKED!");
    }
}
