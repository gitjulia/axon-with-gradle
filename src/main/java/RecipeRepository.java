import org.axonframework.repository.Repository;
import org.springframework.stereotype.Component;

@Component
public interface RecipeRepository extends Repository<RecipeView> {
}
