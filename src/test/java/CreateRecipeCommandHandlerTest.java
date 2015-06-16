import org.axonframework.test.Fixtures;
import org.axonframework.test.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class CreateRecipeCommandHandlerTest {

    FixtureConfiguration fixture;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture(Recipe.class);
        RecipeCommandHandler paymentCommandHandler = new RecipeCommandHandler(fixture.getRepository());
        paymentCommandHandler.setRecipeRepository(fixture.getRepository());
        fixture.registerAnnotatedCommandHandler(paymentCommandHandler);
    }

    @Test
    public void shouldCreateRecipeFromCommand() {
        String recipeId = "recipe Id";
        CreateRecipeCommand createRecipeCommand = new CreateRecipeCommand(recipeId);

        fixture .given()
                .when(createRecipeCommand)
                .expectEvents(new RecipeCreatedEvent(recipeId));
    }

}