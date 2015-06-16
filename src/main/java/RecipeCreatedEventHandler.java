//import org.springframework.stereotype.Component;
//import static java.util.UUID.randomUUID;
//
//@Component
//public class RecipeCreatedEventHandler {
//
//    private final RecipeRepository repository;
//
//    public RecipeCreatedEventHandler(RecipeRepository repository) {
//        this.repository = repository;
//    }
//
//    void on(RecipeCreatedEvent event) {
//        repository.add(new RecipeView(event.getId()));
//    }
//}
