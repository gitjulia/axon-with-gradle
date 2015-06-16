import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CreateRecipeCommandHandler {

    private Repository<Recipe> recipeRepository;

    @Autowired
    public CreateRecipeCommandHandler(@Qualifier("recipeRepository") Repository<Recipe> recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @CommandHandler
    public void createRecipe(CreateRecipeCommand command) {
        recipeRepository.add(new CreateRecipeCommand(command.getId()));
    }
}
