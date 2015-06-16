import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeViewRepository extends CrudRepository<RecipeView, String> {

}
