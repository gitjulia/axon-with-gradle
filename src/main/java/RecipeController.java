import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecipeController {

    private CommandGateway gateway;

    @Autowired
    public RecipeController(CommandGateway gateway) {
        this.gateway = gateway;
    }

    @RequestMapping(value = "/recipe", method = RequestMethod.POST)
    @ResponseBody
    public String createRecipe() {
        CreateRecipeCommand command = new CreateRecipeCommand("some id");
        gateway.send(command);
        return "OK";
    }
}
